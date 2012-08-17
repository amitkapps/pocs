package com.amitk.mybcel;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.logging.Formatter;

public class HelloFromSystem {

	public static void hello() {
		System.out.println("Hello from " + HelloFromSystem.class.getName());
	}

}
