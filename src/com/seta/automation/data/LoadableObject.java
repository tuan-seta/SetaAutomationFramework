package com.seta.automation.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class LoadableObject {

	private Map<String, Field> map = new HashMap<String, Field>();

	public boolean containsField(String key){
		return map.containsKey(key);
	}
	
	public String getType(String key){
		return map.get(key).getFieldType();
	}
	
	public String getValue(String key){
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

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("(Name;Type;Value)\n");
		for (String name: map.keySet()){
			result.append("(" + name);
			result.append("; " + getType(name));
			result.append("; " + getValue(name) + ")\n");
		}
		return result.toString();
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
