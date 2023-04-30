package org.example;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class exp {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("ser.bin");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ois.readObject();
        ois.close();
    }
}