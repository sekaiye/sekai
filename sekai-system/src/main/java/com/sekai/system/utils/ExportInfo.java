package com.sekai.system.utils;

import java.util.Map;

public class ExportInfo {
	Map<String, String> exportFields;
	String sql;
	
	public ExportInfo() {
		
	}
	public ExportInfo(Map<String, String> exportFields, String sql) {
		this.exportFields = exportFields;
		this.sql = sql;
	}
	public Map<String, String> getExportFields() {
		return exportFields;
	}
	public void setExportFields(Map<String, String> exportFields) {
		this.exportFields = exportFields;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
}
