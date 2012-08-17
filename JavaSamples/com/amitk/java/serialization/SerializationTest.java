package com.amitk.java.serialization;

import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jul 30, 2010
 * Time: 7:57:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class SerializationTest {

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        System.out.println("Testing");

        Employee e1 = new Employee().setId(1L);
        Employee e2 = new Employee().setId(2L);
        Set<Employee> es = new HashSet<Employee>();
        es.add(e1);
        es.add(e2);

        e1.setId(null);
//        es.add(new Employee());
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(es);
        byte[] bytes = baos.toByteArray();
        oos.close();
        System.out.println("Emp bytes"  + bytes);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Set emps = (Set)ois.readObject();
        System.out.println("Emps from is: " + emps);
    }
}
