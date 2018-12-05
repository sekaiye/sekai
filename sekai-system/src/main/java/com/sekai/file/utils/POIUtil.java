package com.sekai.file.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.xml.crypto.Data;

/**
 * excel读写工具类 */
public class POIUtil {
    private  Logger logger  = Logger.getLogger(POIUtil.class);
    private Map<Integer,String> columns = new LinkedHashMap<>();

    public List<Map<String,String>> readExcel(InputStream inputStream, String fileName) throws IOException{
        checkFile(fileName);
        List<Map<String,String>> list = new ArrayList<>();
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(inputStream,fileName);
        if(workbook == null){
            System.out.println("workbook == null");
          return  null;
        }
        //获得当前sheet工作表
        Sheet sheet = workbook.getSheetAt(0);
        if(sheet == null){
            System.out.println("sheet == null");
            return null;
        }
        //获得当前sheet的开始行
        int firstRowNum  = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        Row row = sheet.getRow(firstRowNum);
        if(row == null){
            return null;
        }
        //获得当前行的开始列
        int firstCellNum = row.getFirstCellNum();
        //获得当前行的列数
        int lastCellNum = row.getPhysicalNumberOfCells();
        for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++) {
            Cell cell = row.getCell(cellNum);
            String val = getCellValue(cell);
            columns.put(cellNum,val);
        }

        for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){

            //获得当前行
            row = sheet.getRow(rowNum);
            if(row == null){
                continue;
            }
            //获得当前行的开始列
            firstCellNum = row.getFirstCellNum();
            //获得当前行的列数
            lastCellNum = row.getPhysicalNumberOfCells();
            String[] cells = new String[row.getPhysicalNumberOfCells()];
            Map<String, String> map = new LinkedHashMap();
            for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                Cell cell = row.getCell(cellNum);
                String val = getCellValue(cell);
                String columnName=columns.get(cellNum);
                map.put(columnName,val);
            }
            list.add(map);
        }

        workbook.close();

        return list;
    }
    void checkFile(String fileName) throws IOException{
        //判断文件是否是excel文件
        if(!fileName.toLowerCase().endsWith("xls")
                && !fileName.toLowerCase().endsWith("xlsx")){
            logger.error(fileName + "不是excel文件");
            throw new IOException(fileName + "不是excel文件");
        }
    }
    public Workbook getWorkBook(InputStream inputStream,String fileName) {
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.toLowerCase().endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(inputStream);
            }else if(fileName.toLowerCase().endsWith("xlsx")){
                //2007
                workbook = new XSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }
    public String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

}
