package com.oigbuy.jeesite.modules.ebay.product.utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by luwang.wang on 2017-10-18.
 */
public class ExcelUtil {


        /** 工作表*/
        private Workbook rwb;

        /** 写操作表*/
        private WritableWorkbook wwb;

        public ExcelUtil(){}

        /**
         * 构造函数
         * @param fileName - 文件名
         */
        public ExcelUtil(String fileName) {
            try {
                rwb = jxl.Workbook.getWorkbook(new FileInputStream(fileName));
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 静态工厂方法,返回读操作的ExcelUtil对象
         * @param fileName - 文件名
         * @return this
         */
        public static ExcelUtil getReadExcelUtil(String fileName) {
            ExcelUtil eu = new ExcelUtil();
            try {
                eu.rwb = Workbook.getWorkbook(new FileInputStream(fileName));
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return eu;
        }

        /**
         * 静态工厂方法,返回写操作的ExcelUtil对象
         * @param fileName - 文件名
         * @return this
         */
        public static ExcelUtil getWriteExcelUtil(String fileName) {
            ExcelUtil eu = new ExcelUtil();
            try {
                eu.wwb = Workbook.createWorkbook(new FileOutputStream(fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return eu;
        }

        /**
         * 读取给定.xls文件的给定工作表
         * @param sheet - 工作表
         * @param  - 要读的列数集合(索引从０开始)
         * @return - String[] 返回读到的数组
         */
        public List<String[]> readExcel(int sheet, int[] needSheets) {
            sheet = sheet < 0 ? 0 : sheet;
            List<String[]> list = new ArrayList<String[]>();
            Sheet[] sheets = rwb.getSheets();
            int sheetsLen = sheets.length;
            //判断要处理的工作表是否存在
            if(sheetsLen < sheet + 1) {
                return list;
            }
            //获得指定Sheet含有的行数
            Sheet rs = rwb.getSheet(sheet);
            int num = rs.getRows();
            //循环读取数据
            for(int i=0;i<num;i++) {
                //得到第i行的数据..返回cell数组
                Cell[] cell = rs.getRow(i);
                //装载读取数据的集合
                String[] results = new String[needSheets.length];
                for(int j = 0; j < needSheets.length && j < cell.length; j++) {
                    results[j] = cell[needSheets[j]].getContents();
                }
                list.add(results);
            }
            return list;
        }



        /**
         * 读取文件某工作表的第N列
         * @param sheet - 工作表
         * @param  - 列数(索引从０开始)
         * @return - List<String>
         */
        public List<String> readExcelList(int sheet, int listNum) {
            listNum = listNum < 0 ? 0 : listNum;
            List<String> list = new ArrayList<String>();
            try {
                //获得所有的工作表数
                Sheet[] sheets = rwb.getSheets();
                int sheetsLen = sheets.length;
                //判断要处理的工作表是否存在
                if(sheetsLen < sheet) {
                    list.add("no such sheet!");
                    return list;
                }
                //获得指定Sheet含有的行数
                Sheet rs = rwb.getSheet(sheet);
                int num = rs.getRows();
                //循环读取数据
                for(int i=0;i<num;i++) {
                    Cell[] cell = rs.getRow(i);
                    if(listNum + 1 <= cell.length) {
                        list.add(cell[listNum].getContents());
                    }
                }
                return list;
            } catch(Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        /**
         * 读取给定工作表指定列数的数据
         * @param sheet　-　工作表
         * @param start - 读取的开始列数（start >= 0）
         * @param len - 读取的列数
         * @return　- List<String[]>
         */
        public List<String[]> readExcelLists(int sheet, int start, int len){
            start = start < 0 ? 0 : start;
            if(len <= 0) {
                return null;
            }
            List<String[]> list = new ArrayList<String[]>();
            try {
                //获得总 Sheets(所有的工作表)
                Sheet[] sheets = rwb.getSheets();
                int sheetsLen = sheets.length;
                //判断要处理的工作表是否存在
                if(sheetsLen == 0) {
                    String[] results = new String[len];
                    results[0] = "no such sheet!";
                    list.add(results);
                    return list;
                }
                //获得指定Sheet含有的行数
                Sheet rs = rwb.getSheet(sheet);
                int num = rs.getRows();
                //循环读取数据
                for(int i=0;i<num;i++) {
                    //得到第i行的数据..返回cell数组
                    Cell[] cell = rs.getRow(i);
                    //装载读取数据的集合
                    String[] results = new String[len];
                    for(int j = 0; j < len; j++) {
                        int need = start + j;
                        if(need <= cell.length) {
                            results[j] = cell[need].getContents();
                        }
                    }
                    list.add(results);
                }
                return list;
            } catch(Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        /**
         * 读取给定工作表，m行，n列对应的字符值
         * @param sheet　-　工作表
         * @param lineNum　-　读取的行
         * @param listNum　-　读取的列
         * @return　-　String 该行该列对应的字符串
         */
        public String readString(int sheet, int lineNum, int listNum) {
            if(lineNum < 0 || listNum < 0) {
                return null;
            }
            try {
                //获得总 Sheets(所有的工作表)
                Sheet[] sheets = rwb.getSheets();
                int sheetsLen = sheets.length;
                //判断要处理的工作表是否存在
                if(sheetsLen < sheet + 1) {
                    return null;
                }
                //获得指定Sheet含有的行数
                Sheet rs = rwb.getSheet(sheet);
                if(lineNum + 1 > rs.getRows()) {
                    return null;
                }

                //获取m行，n列的值
                return rs.getCell(lineNum, listNum).getContents();
            } catch(Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        /**
         * 以文本方式,将数据写入到excel表中
         * @param sheetName - 工作表名
         * @param sheet - 工作表索引 从0开始
         * @param labels - 工作表目录(目录可以为null,为null时,内容从第一行开始)
         * @param sources - 工作表内容(元素)
         */
        public void writeExcel(String sheetName, int sheet, String[] labels, List<String[]> sources) {
            //创建Excel工作表
            WritableSheet ws = wwb.createSheet(sheetName, sheet);

            int i = 0; // 行索引
            Label label;
            try {
                //添加目录Label
                if (labels != null && labels.length > 0) {
                    for (int j = 0; j < labels.length; j++) {
                        label = new Label(j, i, labels[j]);
                        ws.addCell(label);
                    }
                    i++;
                }

                //添加元素
                if (sources != null && !sources.isEmpty()) {
                    for (String[] source : sources) {
                        int j = 0;
                        for (String s : source) {
                            label = new Label(j++, i, s);
                            ws.addCell(label);
                        }
                        i++;
                    }
                }
                wwb.write(); // 写文件
                wwb.close(); // 关闭文件
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public List<String[]> readXlsx(String fileName) throws IOException{
        String value = null;
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook( fileName);
        List<String[]> stringList = new ArrayList<>();

        // 循环工作表Sheet
        for(int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            if (numSheet == 0) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow == null) {
                        continue;
                    }

                    // 循环列Cell
                    String[] results = new String[xssfRow.getLastCellNum()];
                    for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++) {
                            XSSFCell xssfCell = xssfRow.getCell(cellNum);
                            if (xssfCell == null) {
                                continue;
                            }
                            if(xssfRow.getCell(cellNum)!=null){//处理函数单元格问题，先将单元格内容转为String
                                xssfRow.getCell(cellNum).setCellType(1);
                            }
                            value = getValue(xssfCell);
                            results[cellNum] = value;
                    }
                    stringList.add(results);
                }
            }
        }
        return stringList;
    }

    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfCell){
        if(xssfCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN){
            return String.valueOf( xssfCell.getBooleanCellValue());
        }else if(xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
            return String.valueOf( xssfCell.getNumericCellValue());
        }else{
            return String.valueOf( xssfCell.getStringCellValue());
        }
    }


}
