package com.seta.automation.data;

import java.util.List;

import com.seta.automation.utils.ExcelUtils;

public class LoadableExcel extends LoadableObject {
	
	public LoadableExcel(String filePath, String sheetName, int keyRow, int dataRow){
		LoadKeyValue(filePath, sheetName, keyRow, dataRow);
	}
	
	public void LoadKeyValue(String filePath, String sheetName, int keyRow, int dataRow) {
		try {
			ExcelUtils.setExcelFile(filePath, sheetName);
			
			List<String> keywords = ExcelUtils.getDatarow(keyRow);
			List<String> data = ExcelUtils.getDatarow(dataRow);
			
			LoadData(keywords, data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
