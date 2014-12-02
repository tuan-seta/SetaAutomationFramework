package com.seta.automation.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class LoadableObject {

	private Map<String, Field> map = new HashMap<String, Field>();

	public boolean containsField(String key){
		return map.containsKey(key);
	}
	
	public String getFieldType(String key){
		return map.get(key).getFieldType();
	}
	
	public String getFieldValue(String key){
		return map.get(key).getFieldValue();
	}
	
	public void put(String name, String type, String value){
		Field dataField = new Field(type, value);
		map.put(name, dataField);
	}
	
	public Set<String> keySet(){
		return map.keySet();
	}
	
	public void clear(){
		map.clear();
	}
}

class Field {
	String fieldType;
	String fieldValue;
	
	public Field(String fieldType, String fieldValue) {
		this.fieldType = fieldType;
		this.fieldValue = fieldValue;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
}
