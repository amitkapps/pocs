package com.amitk.java.classloaders;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Hashtable;

/**
 * Special case class loader that delegates to the parent after
 * trying to load the class on its own.
 * The implementation is specially to resolve issues like unwanted version
 * of services on the application's system class path. E.g. xerces implementation etc.
 * @author axk
 *
 */
public class PostDelegationUrlClassLoader extends URLClassLoader{

	private ClassLoader parent;
	private Hashtable classCache = new Hashtable();
	public PostDelegationUrlClassLoader(URL[] urls, ClassLoader parent){
		super(urls, parent);
		this.parent = parent;
	}

	public synchronized Class loadClass(String name) throws ClassNotFoundException {
		// First, check if the class has already been loaded
		//Class c = findLoadedClass(name);
		Class c = (Class) classCache.get(name);
		if (c == null) {
		    try {
		    	c = findClass(name);
		    	classCache.put(name, c);
		    } catch (ClassNotFoundException e) {
		        // If still not found, then invoke loadClass on the parent in order
		        // to find the class.
		    	//ClassLoader parent = getParent();
				if (parent != null) {
				    c = parent.loadClass(name);
				}else{
					throw e;
				}
		    }
		}

		return c;
	}
}
