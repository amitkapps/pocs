package com.amitk.mybcel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.logging.Formatter;

public class BCExUtil {

	private static String javaFileContent = "import com.amitk.mybcel.*; import java.io.PrintWriter; public class Test{ public static void execute(PrintWriter pw){pw.println(\"Hello from mybcel !!\"); } }";
	private static String javaFileContent2 = "public class Test2{ public static void main(String args[]){Test.main(null);} }";

	private static final String javaFilePrefix = "Test";
	private static final String javaFileSuffix = ".java";
	
	
	public static void main(String[] args){
		try{
			System.out.println(executeAndReturnResults(null, javaFileContent));
		}catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	public static synchronized String executeAndReturnResults(String tempDir, String javaSource)
		throws IOException{
		String javaClassName = generateJavaClassName();
		String javaSrcNewClassName = changeClassName(javaSource, javaClassName); 
		String javaFileDirPath = getTempDir(tempDir);
		String javaFilePath = javaFileDirPath + File.separator + javaClassName + javaFileSuffix;
		saveFile(javaSrcNewClassName, javaFilePath);
		String compileOutput = compileJavaFile(javaFilePath);
		
		if(null != compileOutput && compileOutput.trim().length() > 0){
			throw new IllegalArgumentException("Non Blank compiler output!!: " + compileOutput);
		}
		
		return execJavaMethod(javaClassName, javaFileDirPath);
		
	}
	
	private static synchronized String generateJavaClassName(){
		String javaClassName = javaFilePrefix + System.currentTimeMillis();
		try {
			Thread.sleep(2);
		} catch (Exception e) {	/*ignore*/ }
		
		return javaClassName;
	}
	
	private static String changeClassName(String javaSource, String newClassName){
		String classDeclaration = "class " + newClassName + "{";
		String newJavaSource = javaSource.replaceFirst("class\\s.\\w*\\s*\\{", classDeclaration);
		
		return newJavaSource;
	}
	
	private static String getTempDir(String tempDirPath){
		boolean tempDirExists = false;
		String sysTempDir = System.getProperty("java.io.tmpdir");
		
		if(null != tempDirPath && tempDirPath.trim().length()>0){
			try{
				File tempDir = new File(tempDirPath);
				if(tempDir.isDirectory() && tempDir.canWrite() && tempDir.canRead()){
					tempDirExists = true;
				}
			}catch(Exception ex){
				//logger.warn("Passed temp dir: " + tempDirPath + ", does not exist, using system temp dir " + sysTempDir);
				ex.printStackTrace();
			}
		}
		if(tempDirExists){
			return tempDirPath;
		}else{
			return sysTempDir;
		}
	}
	
	private static void saveFile (String fileContents, String filePath)
		throws IOException{
		
		File file = new File(filePath);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(fileContents);
		fileWriter.flush();
		fileWriter.close();
	}
	
	
	private static String compileJavaFile(String javaFilePath){
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		//"-classpath", System.getProperty("java.class.path"),
		String[] compilerOptions = new String[] { javaFilePath};
			
		com.sun.tools.javac.Main.compile(compilerOptions, pw);
		
		return sw.toString();

	}
	
	private static String execJavaMethod( String javaClassName, String javaClassPath){
		StringWriter sw = new StringWriter();
		try {
			URL classURL = new URL("file://" + javaClassPath);
			URLClassLoader ucl = new URLClassLoader(new URL[]{classURL});
			Class clazz = ucl.loadClass(javaClassName);
			Method method = clazz.getMethod("execute", new Class[]{PrintWriter.class});
			PrintWriter pw = new PrintWriter(sw);
			method.invoke(null, new Object[]{pw});
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception executing class method:" + e);
		}
		
		return sw.toString();

	}
	
	public static String execJavaMethod( byte[] classByteCodes)
	throws Exception{
	StringWriter sw = new StringWriter();
	Class clazz = null;
	try {
		BCExByteStreamClassLoader bcl = new BCExByteStreamClassLoader();
		clazz = bcl.loadClass(classByteCodes);
		Method method = clazz.getMethod("execute", new Class[]{PrintWriter.class});
		PrintWriter pw = new PrintWriter(sw);
		method.invoke(null, new Object[]{pw});
	} catch (Exception e) {
		throw new Exception("Exception executing class method execute, for the class:" + ((null == clazz) ? "null" : clazz.getName()),e);
	}
	
	return sw.toString();

}

	
}
