/**
 * Holds the jar's version information.
 */
package com.matson.vinsight.admin.common;

/**
 * @author axk
 *
 */
public final class Version {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getImplementationVersion());

	}
	
	public static final String getImplementationTitle(){
		Class cls = Version.class;
		Package pkg = cls.getPackage();
		return pkg == null ? null : pkg.getImplementationTitle();
	}

	public static final String getImplementationVersion(){
		Class cls = Version.class;
		Package pkg = cls.getPackage();
		return pkg == null ? null : pkg.getImplementationVersion();
	}

	public static final String getImplementationVendor(){
		Class cls = Version.class;
		Package pkg = cls.getPackage();
		return pkg == null ? null : pkg.getImplementationVendor();
	}
	
	public static final String getSpecificationVersion(){
		Class cls = Version.class;
		Package pkg = cls.getPackage();
		return pkg == null ? null : pkg.getSpecificationVersion();
	}


}
