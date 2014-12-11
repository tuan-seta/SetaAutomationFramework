package com.seta.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.util.TextUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.seta.automation.data.LoadableData;

public class ExcelHelper {
	private static Sheet excelSheet;
	private static Workbook excelBook;
	private String fPath;
	private String sheetName;
	
	private HashMap<Integer, LoadableData> testcaseData;
	
	// This method is to set the File path and to open the Excel file, Pass
	// Excel Path and Sheetname as Arguments to this method
	public ExcelHelper(String fPath, String sheetName) throws Exception {
		this.fPath = fPath;
		this.sheetName = sheetName;
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(fPath);
			// Access the required test data sheet
			excelBook = WorkbookFactory.create(ExcelFile);
			setExcelSheet(sheetName);
			parseTestcaseData();
			ExcelFile.close();
		}catch (FileNotFoundException e) {
			String message = "Load data file error, file name: " + fPath + "cause: FileNotFoundException";
			Log.error(message);
			throw new FileNotFoundException(message);
			
		}catch (IOException e) {
			String message = "Load data file error, file name: " + fPath + "cause: IOException";
			Log.error(message);
			throw new IOException(message);
		}
		catch (Exception e) {			
			String message = "Load data file error, file name: " + fPath;
			Log.error(message);
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

	//check data sheet format
	//initiate hashmap to load testcase data 
	//return number of testcase in excel sheet
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
		//Initiate hashmap parsing testcase data
		testcaseData = new HashMap<Integer, LoadableData>(count);
		for (int i=2; i< count + 2; i++){
			LoadableData testcase = new LoadableData();
			testcaseData.put(i, testcase);
		}
		return count;
	}
	
	private void parseTestcaseData(){
		int count = getNumOfTestcase();
		if (count == 0) return;
		
		Row fieldData;
		for (int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
			fieldData = excelSheet.getRow(i);
			if (fieldData == null)
				continue;
			String fieldName = "";
			String fieldType = "";
			String fieldValue = "";
			for (Cell cell : fieldData) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				int col = cell.getColumnIndex(); 
				if (col == 0){
					fieldName = cell.getStringCellValue();
				} else if (col == 1){
					fieldType = cell.getStringCellValue();
				} else if (col < count + 2){
					fieldValue = cell.getStringCellValue();
					if (!TextUtils.isEmpty(fieldType) && fieldType.contains(LoadableData.DTAG_UNIQUE)) {
						fieldValue = fieldValue + Utils.today();
					}
					
					//get record corresponding to column index
					LoadableData testcase = testcaseData.get(col);
					testcase.put(fieldName, fieldType, fieldValue);
				}
			}
		}
	}
	
	public Iterator<Object> getDataProvider(){
		ArrayList<Object> result = new ArrayList<Object>();
		for(int i: testcaseData.keySet()){
			LoadableData testcase = testcaseData.get(i);
			result.add((Object) testcase);
		}
		Log.info(this.getClass().getName() + " cast all loaded testcase to Object");
		return result.iterator();
	}

	public LoadableData getFirstCase(){
		if (testcaseData.size() > 0)
			return testcaseData.get(2);
		
		return null;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("Parse testcase data from \n");
		result.append("File  : " + fPath);
		result.append("\nSheet : " + sheetName);
		for(int i: testcaseData.keySet()){
			LoadableData testcase = testcaseData.get(i);
			result.append("\nTestcase's fields at column " + i + ": ");
			result.append(testcase.toString());
		}
		return result.toString();
	}
}