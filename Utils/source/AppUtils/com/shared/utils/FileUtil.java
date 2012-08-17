package com.shared.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

public class FileUtil {
	private static Logger logger = Logger.getLogger(FileUtil.class);
	
	public static int writeRowsToFile(ArrayList rows, String filePath, String appInstance) throws Exception{
		String colFilePath= filePath + ".csvcolumns";
		String dataFilePath= filePath + ".csvdata";
		String queryFilePath = filePath + ".amis-update.sql";
		
		StringBuffer csvColNames = new StringBuffer("");
		String strCsvColNames = null;
		ArrayList colList = null;
		
		//Iterator rowsIterator = null;
		
		// Get column names
		if (null != rows && rows.size() > 0){
			//rowsIterator = rows.iterator();
			colList = (ArrayList) rows.get(0);
 
			for (int i=0; i<colList.size(); i++){
				String col = (String) colList.get(i);
				csvColNames.append(col);
				if(i+1<colList.size()) csvColNames.append(","); 
			}
		}
		
		//Write column names to col file
		File colFile = new File(colFilePath);
		colFile.createNewFile();
		FileWriter cfw = new FileWriter(colFile, false);
		strCsvColNames = csvColNames.toString(); 
		cfw.write(strCsvColNames);
		cfw.flush();
		cfw.close();
		
		//Write Data to data file
		File dataFile = new File(dataFilePath);
		File queryFile = new File(queryFilePath);
		//create the files
		dataFile.createNewFile();
		queryFile.createNewFile();
		
		FileWriter dfw = new FileWriter(dataFile, false);
		FileWriter qfw = new FileWriter(queryFile, false);
		
		Hashtable row = null;
		//Iterator colIterator = null;
		String colValue = null;
		String strCsvColValues = null;
		String amisUpdateQuery = null;
		
		StringBuffer colValues = new StringBuffer("");
		
		for (int i=1; i< rows.size(); i++){
			row = null;
			//get iterator for the columns
			//colIterator = colList.iterator();
			
			row = (Hashtable)rows.get(i);
			colValues.setLength(0);
			for(int j=0; j<colList.size(); j++){
				colValue = (String) row.get(colList.get(j));
				colValues.append(colValue);
				if (j+1<colList.size()){
					colValues.append(",");
				}else {
					colValues.append("\n");
				}
			}
			strCsvColValues = colValues.toString();
			amisUpdateQuery = GenUtils.getQueryForRow(strCsvColNames, strCsvColValues, "," , appInstance);
			logger.debug("Amis Upd Qry= [" + amisUpdateQuery + "]");
			dfw.write(strCsvColValues);
			qfw.write(amisUpdateQuery);
		}
		dfw.flush();
		qfw.flush();
		dfw.close();
		qfw.close();
		
		return 1;
	}

	
	public static String getFileContentsAsHtmlTable(String filePath) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        StringBuffer contents = new StringBuffer("<table >");
        String str;
        int lineCtr = 0;
        while ((str = br.readLine()) != null) {
        	lineCtr++;
        	contents.append("<tr><td>"+ lineCtr + "</td>");
            contents.append("<td>"+str + "</td></td>");
        }
        br.close();
        String totalStr = "<b>Total:[" + lineCtr + "]</b></br>";
        contents.insert(0,totalStr);
        String contentStr = contents.append("</table>").toString(); 
        logger.debug("FileContents=\n" + contentStr);
        return contentStr;
    }

	public static ArrayList getFileContentsAsAL(String filePath, String elementDelim) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        StringBuffer contents = new StringBuffer("");
        ArrayList returnList = new ArrayList();
        String str;
        int elementCtr = 0;
        while ((str = br.readLine()) != null) {
        	contents.append(str)
        			.append(" ");
        	if (!"".contentEquals(contents) && str.endsWith(elementDelim)) {

				returnList.add(contents.toString().replace(';',' '));
				elementCtr++;
				contents.setLength(0);
			}
        }
        if( contents.length()!=0 
        		&& !"".equalsIgnoreCase(contents.toString().trim())
        	){
        	returnList.add(contents.toString());
        }
        br.close();
        logger.debug("Elements Added=" + elementCtr);
        return returnList;
    }


	/**
	 * Reads a file and returns its contents as an AL. Each elem of the arraylist 
	 * is a line in the file(AL size = lines in the file).
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static ArrayList getFileContentsAsAL(String filePath) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        ArrayList returnList = new ArrayList();
        String str;
        int elementCtr = 0;
        while ((str = br.readLine()) != null) {
				returnList.add(str);
				elementCtr++;
			}

        br.close();
        logger.debug("Elements Added=" + elementCtr);
        return returnList;
    }
	
	public static String getFolderPath(String filePath) throws Exception{
		File file = new File(filePath);
		if (file.isDirectory())
			return file.getAbsolutePath();
		else {
			return file.getParentFile().getAbsolutePath();
		}
	}


	public static int writeALToFile(String dataFilePath, ArrayList lines) throws Exception{
		
		//Write Data to data file
		File dataFile = new File(dataFilePath);
		//create the files
		dataFile.createNewFile();
		
		FileWriter dfw = new FileWriter(dataFile, false);
		int rowCtr = 0;
		for (; rowCtr < lines.size(); rowCtr++){
			//get iterator for the columns
			//colIterator = colList.iterator();
			
			String line = (String)lines.get(rowCtr);
			dfw.write(line + "\n");
		}
		dfw.flush();
		dfw.close();
		logger.debug((rowCtr) + " Rows written to file-[" + dataFilePath +"]");
		
		return 1;
	}


	public static boolean createFoldersRecursively(String filePath)
		throws Exception{
		
		File file = new File(filePath);
		if (file.exists()){
			if(file.isDirectory())
				return true;
			else
				throw new Exception("File:" + filePath + ", exists, but is not a directory!!!");
		}
		else
			return file.mkdirs();
	}

	public static ArrayList getFilesInDir(String directoryPath)
		throws Exception{
		ArrayList filesInDir = new ArrayList();
		
		File dir = new File(directoryPath);
		if (!dir.isDirectory())
			throw new Exception("Passed path is not a directory");
		File[] fileArrInDir = dir.listFiles();
		for (int i = 0; i < fileArrInDir.length; i++) {
			if(fileArrInDir[i].isFile())
				filesInDir.add(fileArrInDir[i].getName());
		}
		return filesInDir;
	}
}
