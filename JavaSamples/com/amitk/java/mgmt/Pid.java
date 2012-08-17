package com.amitk.java.mgmt;

import java.lang.management.ManagementFactory;

public class Pid {

    public static void main (String[] args){
        System.out.println(ManagementFactory.getRuntimeMXBean().getName() + ", usr " + System.getProperty("user.name"));
    }

}
