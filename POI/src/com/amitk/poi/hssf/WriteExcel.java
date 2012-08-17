package com.amitk.poi.hssf;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.amitk.javaio.i18n.CharEncoding;

import sun.io.CharacterEncoding;

import java.util.Date;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class WriteExcel {

	
	private static HSSFSheet sheet;
	
	public static void main(String[] args) throws Exception{
		ArrayList al = CharEncoding.readFile("C:\\AmitK\\work\\project\\Learning\\EclipseWorkSpace\\POI\\src\\data\\GB2312-English+Chinese.txt");
		ArrayList encodedStrAl = new ArrayList();
		for (Iterator iterator = al.iterator(); iterator.hasNext();) {
			String inputRaw = (String) iterator.next();
			String encodedString = new String(inputRaw.getBytes("ISO-8859-1"), "GB2312");
			encodedStrAl.add(encodedString);
		}
		FileOutputStream fos = new FileOutputStream("C:\\AmitK\\work\\project\\Learning\\EclipseWorkSpace\\POI\\src\\data\\chinese.xls");
		doExport(encodedStrAl, fos);
		fos.close();
	}
	
    public static void doExport(ArrayList rows, OutputStream out) throws Exception
    {
        try
        {
            HSSFWorkbook wb = new HSSFWorkbook();
            sheet = wb.createSheet("-");

            int rowNum = 0;
            int colNum = 0;

            if (false)
            {
                // Create an header row
                HSSFRow xlsRow = sheet.createRow(rowNum++);

                HSSFCellStyle headerStyle = wb.createCellStyle();
                headerStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
                headerStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
                HSSFFont bold = wb.createFont();
                bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                bold.setColor(HSSFColor.WHITE.index);
                headerStyle.setFont(bold);

                Iterator iterator = new ArrayList().iterator();//this.model.getHeaderCellList().iterator();

                while (iterator.hasNext())
                {
                    //HeaderCell headerCell = (HeaderCell) iterator.next();

                    String columnHeader = "";//headerCell.getTitle();

                    if (columnHeader == null)
                    {
                        columnHeader = columnHeader;//StringUtils.capitalize(headerCell.getBeanPropertyName());
                    }

                    HSSFCell cell = xlsRow.createCell((short) colNum++);
                    cell.setCellValue(columnHeader);
                    cell.setCellStyle(headerStyle);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                }
            }

            // get the correct iterator (full or partial list according to the exportFull field)
            Iterator rowIterator = rows.iterator();
            // iterator on rows

            while (rowIterator.hasNext())
            {
                //Row row = rowIterator.next();
            	Object row = rowIterator.next();
                HSSFRow xlsRow = sheet.createRow(rowNum++);
                colNum = 0;

                // iterator on columns
                //ColumnIterator columnIterator = row.getColumnIterator(this.model.getHeaderCellList());

                //while (columnIterator.hasNext())
                {
                    //Column column = columnIterator.nextColumn();

                    // Get the value to be displayed for the column
                    //Object value = column.getValue(this.decorated);

                    HSSFCell cell = xlsRow.createCell((short) colNum++);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);

                    if (row instanceof Number)
                    {
                        Number num = (Number) row;
                        cell.setCellValue(num.doubleValue());
                    }
                    else if (row instanceof Date)
                    {
                        cell.setCellValue((Date) row);
                    }
                    else if (row instanceof Calendar)
                    {
                        cell.setCellValue((Calendar) row);
                    }
                    else
                    {
                        cell.setCellValue((String)row);
                    }

                }
            }
            wb.write(out);
        }catch(Exception e){
        	throw new Exception("Error creating excel", e);
        }
    }
	
}
