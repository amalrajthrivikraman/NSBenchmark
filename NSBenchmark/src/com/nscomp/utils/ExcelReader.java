package com.nscomp.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ExcelReader {

	@SuppressWarnings("deprecation")
	public static Map<String, String> readXLSXFile(String fileName) {
		InputStream XlsxFileToRead = null;
		XSSFWorkbook workbook = null;
		Map<String, String> testInputData;
		DateFormat dateFormat;

		try {
			XlsxFileToRead = new FileInputStream(fileName);	
			workbook = new XSSFWorkbook(XlsxFileToRead);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//getting the first sheet from the workbook using sheet name. 
		// We can also pass the index of the sheet which starts from '0'.
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		XSSFRow row;
		XSSFCell cell;
		
		//Iterating all the rows in the sheet
		Iterator<Row> rows = sheet.rowIterator();
		testInputData = new HashMap<String, String>();
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			
			//Iterating all the cells of the current row
			Iterator<Cell> cells = row.cellIterator();
			Integer colId;
			String key=null, value=null;
			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();
				colId = cell.getColumnIndex();
				if(colId > 1) continue;
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
					if(colId == 0) key = cell.getStringCellValue();
					else if(colId ==1) value = cell.getStringCellValue();
				} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					if(DateUtil.isCellDateFormatted(cell)){
						//dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						dateFormat = new SimpleDateFormat("MM-dd-yyyy");
						System.out.println(dateFormat.format(cell.getDateCellValue()).toString());
						if(colId == 0) key = dateFormat.toString();
						else if(colId ==1) value = dateFormat.toString();					
					}
					else if(colId == 0) key = Double.toString(cell.getNumericCellValue());
					else if(colId ==1) value = Double.toString(cell.getNumericCellValue());	
				} else {
							key = null;
							//XSSFCell.CELL_TYPE_BOOLEAN
							// XSSFCell.CELL_TYPE_BLANK
							// XSSFCell.CELL_TYPE_FORMULA
							// XSSFCell.CELL_TYPE_ERROR
				}
				if(key!=null && colId==1) testInputData.put(key, value);
			}
			try {
				XlsxFileToRead.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return testInputData;
	}
	
//	public static void main(String[] args) {
//		//ExcelReader readXlsx = new ExcelReader();
//		Map<String, String> excelData = new HashMap<String, String>();
//		excelData = ExcelReader.readXLSXFile("D:\\TestData\\TC.xlsx");
//		System.out.println(excelData);
//	}
}