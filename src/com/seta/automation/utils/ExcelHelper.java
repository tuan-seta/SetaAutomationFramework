package com.seta.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.seta.automation.data.LoadableData;

public class ExcelHelper {
	private static Sheet excelSheet;
	private static Workbook excelBook;
	
	private ArrayList<LoadableData> testcaseData;
	
	// This method is to set the File path and to open the Excel file, Pass
	// Excel Path and Sheetname as Arguments to this method
	public void ExcelHelper(String fPath, String SheetName) throws Exception {
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(fPath);
			// Access the required test data sheet
			excelBook = WorkbookFactory.create(ExcelFile);
			setExcelSheet(SheetName);
			parseTestcaseData();
			ExcelFile.close();
		}catch (FileNotFoundException e) {
			String message = "Load data file error, file name: " + fPath + "cause: FileNotFoundException";
			Log.error("Load data file error, file name: " + fPath + "cause: IOException");
			throw new FileNotFoundException(message);
			
		}catch (IOException e) {
			String message = "Load data file error, file name: " + fPath + "cause: IOException";
			throw new IOException(message);
		}
		catch (Exception e) {			
			String message = "Load data file error, file name: " + fPath;
			throw new Exception(message);
		}
	}

	public void setExcelSheet(int index) {
		excelSheet = excelBook.getSheetAt(index);
		excelBook.getCreationHelper().createFormulaEvaluator();
	}

	public void setExcelSheet(String sheetName) {
		excelSheet = excelBook.getSheet(sheetName);
		excelBook.getCreationHelper().createFormulaEvaluator();
	}

	private int getNumOfTestcase(){
		Row firstRow = excelSheet.getRow(0);
		int count = 0;

		for (Cell cell : firstRow) {
			if (cell.getColumnIndex() == 0 && !cell.getStringCellValue().equalsIgnoreCase(LoadableData.DTAG_FIELDNAME)){
				String message = "Data file is wrong format";
				Log.error(message);
				return count;
			} else if (cell.getColumnIndex() == 1 && !cell.getStringCellValue().equalsIgnoreCase(LoadableData.DTAG_FIELDTYPE)){
				String message = "Data file is wrong format";
				Log.error(message);
				return count;
			} else if (cell.getStringCellValue().equals(LoadableData.DTAG_TESTCASE)) {
				count ++;
			} 
		}
		return count;
	}
	
	private void parseTestcaseData(){
		int count = getNumOfTestcase();
		if (count == 0){
			return;
		} 
		
		testcaseData = new ArrayList<LoadableData>(count);
		Row fieldData;
		for (int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
			fieldData = excelSheet.getRow(i);
			if (fieldData != null) {
				String fieldName = "";
				String fieldType = "";
				String fieldValue = "";
				for (Cell cell : fieldData) {
					int col = cell.getColumnIndex(); 
					if (col == 0){
						fieldName = cell.getStringCellValue();
					} else if (col == 1){
						fieldType = cell.getStringCellValue();
					} else if (col < count + 2){
						fieldValue = cell.getStringCellValue();
						
					}
				}
			}
		}
	}
}