/**
 * 
 */
package com.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;;

/**
 * @author axk
 *
 */
public class GenericQueryResultVO {
	
	private ArrayList colNames = null;//each element is a column name. 
	private ArrayList rows = new ArrayList();	// each element is a hashtable of a row - 
							//key is the column name, value is the column value - both as Strings

	public GenericQueryResultVO (ArrayList colNames, ArrayList rowsAsAL)throws Exception{
		ArrayList row = null;
		HashMap rowTable = null;
		String colName;
		String colValue;
		
		//validations.
		if (null==colNames || colNames.size()==0)
			throw new Exception("Null or 0 columns passed.");
		this.colNames=colNames;
		
		if (null==rowsAsAL || rowsAsAL.size()==0)
			return;

		for (int rowCtr=0; rowCtr < rowsAsAL.size(); rowCtr++){
			row = (ArrayList) rowsAsAL.get(rowCtr);
			if (row.size() != colNames.size()) {
				throw new Exception("No of Columns in the row <> passed Colum list size. rowCols="
						+row.size() +", colNos="+colNames.size());
			}
			rowTable = new HashMap();

			for (int colCtr=0; colCtr<colNames.size(); colCtr++){
				colName = (String) colNames.get(colCtr);
				colValue = (String) row.get(colCtr);
				rowTable.put(colName, colValue);
			}
			rows.add(rowTable);
		}
	};

	//public void setRow(){}
	//public void addColumnName(String colName){ colNames.add(colName);}
	public int getColumnCount(){return colNames.size();}
	public int getRowCount(){return rows.size();}
	/**
	 * returns an ArrayList of ColumnNames, each element is a String.
	 * @return
	 */
	public ArrayList getColumns(){return colNames;}
	/**
	 * returns an ArrayList of HashTables.
	 * each element of the outer ArrayList stores a HashTable. The inner HashTable   
	 * stores column-name, column-value pairs.
	 * query.
	 * @return
	 */
	public ArrayList getRowsAsALofHT(){return rows;}
	
	/**
	 * returns an ArrayList of String elements. Each element is a column value
	 * corresponding to the passed columnName
	 * @param columnName - the column names whose values will be returned
	 * @return
	 */
	public ArrayList getSpecificColumnsValuesAsAL(String columnName)
		throws Exception{
		
		if(null == columnName || !colNames.contains(columnName)){
			throw new Exception("Invalid Column Name:" + columnName + ", valid col names:" + colNames); 
		}
		
		String colValue = null;
		ArrayList colValuesAsAL = new ArrayList();
		HashMap row = null;
		//iterate over rows
		for (int rowCnt = 0; rowCnt < rows.size(); rowCnt++) {
			row =  (HashMap)rows.get(rowCnt);
			colValue = (String) row.get(columnName);
			colValuesAsAL.add(colValue);
		}
		return colValuesAsAL;
	}
	
	/**
	 * returns an ArrayList of String elements. Each element is a column value
	 * corresponding to the passed columnIndex (1 is the first column index)
	 * @param columnIndex - the column index whose values will be returned
	 * @return
	 */
	public ArrayList getSpecificColumnsValuesAsAL(int columnIndex)
		throws Exception{
		if(columnIndex > colNames.size())
			throw new Exception ("Does not have "+ columnIndex + " columns(only "+ colNames.size() +" columns).");
		String colName = (String) colNames.get(columnIndex - 1);
		return getSpecificColumnsValuesAsAL(colName);
	}
	
	/**
	 * returns an ArrayList of ArrayLists.
	 * each element of the outer ArrayList stores an ArrayList. The inner ArrayList is a list  
	 * of column values (String). The sequence of the columns is the same as was defined in the
	 * query.
	 * @return
	 */
	public ArrayList getRowsAsALofAL(){
		String colName = null;
		String colValue = null;
		ArrayList rowsAsALofAL = new ArrayList();
		HashMap row = null;
		//iterate over rows
		for (int rowCnt = 0; rowCnt < rows.size(); rowCnt++) {
			row =  (HashMap)rows.get(rowCnt);
			ArrayList colValues = new ArrayList(colNames.size());
			//iterate over columns
			for (int colCnt = 0; colCnt < colNames.size(); colCnt++) {
				colName = (String) colNames.get(colCnt);
				colValue = (String) row.get(colName);
				colValues.add(colValue);
			}
			rowsAsALofAL.add(colValues);
		}
		return rowsAsALofAL;
	}
	
	public String getRowsAsHTML(){
		String totalRows = "Total Rows=[" + rows.size() + "] <br/>";
		StringBuffer HTMLTableStrBuf = new StringBuffer( totalRows + "<table border=\"1\" style=\"font-size: 9\">"); 
		ArrayList rowsAL = getRowsAsALofAL();
		String TableHead = getHTMLTableHeaderForCols(colNames);
		String TableBody = getHTMLTableBodyForColValues(rowsAL);

		HTMLTableStrBuf.append(TableHead)
						.append(TableBody)
						.append("</table>");
		return HTMLTableStrBuf.toString();
	}
	
	private static final String getHTMLTableHeaderForCols(ArrayList colNames){
		String HTMLTableHeaderStr = null;
		StringBuffer HTMLTableHeaderStrBuf = new StringBuffer("<tr><td>SNo</td>");
		String colName = null;
		final String HTMLTableColOpen = "<td>";
		final String HTMLTableColClose = "</td>";
		for (int colCtr=0; colCtr<colNames.size(); colCtr++){
			colName = (String) colNames.get(colCtr);
			HTMLTableHeaderStrBuf.append(HTMLTableColOpen)
								.append(colName)
								.append(HTMLTableColClose);
			
		}
		HTMLTableHeaderStrBuf.append("</tr>");
		HTMLTableHeaderStr = HTMLTableHeaderStrBuf.toString(); 
		return HTMLTableHeaderStr;
	}
	
	
	private static final String getHTMLTableBodyForColValues(ArrayList rowsAL){
		String HTMLTableBodyStr = null;
		StringBuffer HTMLTableBodyStrBuf = new StringBuffer("");
		String colValue = null;
		ArrayList colValues = null;
		
		final String HTMLTableRowOpen = "<tr>";
		final String HTMLTableRowClose = "</tr>";
		final String HTMLTableColOpen = "<td>";
		final String HTMLTableColClose = "</td>";

		//iterate thru row
		for (int rowCtr=0; rowCtr<rowsAL.size(); rowCtr++){
			String sNo = "<td>" + (rowCtr + 1) + "</td>"; 
			HTMLTableBodyStrBuf.append(HTMLTableRowOpen)
								.append(sNo);
			colValues = (ArrayList)rowsAL.get(rowCtr);
			//iterate thru columns
			for (int colCtr=0; colCtr<colValues.size(); colCtr++){
				colValue = (String) colValues.get(colCtr);
				HTMLTableBodyStrBuf.append(HTMLTableColOpen)
									.append(colValue)
									.append(HTMLTableColClose);
			}
			HTMLTableBodyStrBuf.append(HTMLTableRowClose);
		}
			
		HTMLTableBodyStrBuf.append("");
		HTMLTableBodyStr = HTMLTableBodyStrBuf.toString(); 
		return HTMLTableBodyStr;

	}
}
