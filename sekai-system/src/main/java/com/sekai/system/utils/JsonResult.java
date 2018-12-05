package com.sekai.system.utils;

public class JsonResult {
	public int success = 1;
	
	public String data;
	
	public JsonResult(){
	}
	
	public JsonResult(int success, String data){
		this.success = success;
		this.data = data;
	}
}
