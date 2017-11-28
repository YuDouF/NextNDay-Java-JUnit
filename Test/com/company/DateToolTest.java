package com.company;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.*;

import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class DateToolTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private long year;
    private int month;
    private long day;
    private long dayNum;
    private String expected;

    public DateToolTest(String line, String inputFormat){
        Format format = new InputFormat(inputFormat);
        Vector<String> v = format.match(line);
        if(v.size() != format.getSplits().size() + 1){
            System.out.println(DateTool.errorInfo);
            expected =  DateTool.errorInfo;
            year =  0;
            month = 0;
            day = 0;
            dayNum = 0;
            return;
        }
        for (int j = 0; j < v.size() - 1; ++j) {
            if (!NumberCheck.checkNumberLegality(v.get(j))) {
                System.out.println(DateTool.errorInfo);
                expected =  DateTool.errorInfo;
                year =  0;
                month = 0;
                day = 0;
                dayNum = 0;
                return;
            }
        }

        year =  Long.parseLong(v.get(0));
        month = Integer.parseInt(v.get(1));
        day = Long.parseLong(v.get(2));
        dayNum = Long.parseLong(v.get(3));
        expected = v.get(4);
    }

    @Parameterized.Parameters
    public static Collection getCaseFromExcelAndTest() throws Exception {
        List<String[]> cases = new ArrayList<String[]>();
        String fileName="E:\\Project\\Java\\NextNDay-Java-JUnit\\Test\\cases\\cases.xlsx";
        FileInputStream fis = new FileInputStream(fileName);
        Workbook workbook = null;
        //判断excel的两种格式xls,xlsx
        if(fileName.toLowerCase().endsWith("xlsx")){
            workbook = new XSSFWorkbook(fis);
        }else if(fileName.toLowerCase().endsWith("xls")){
            workbook = new HSSFWorkbook(fis);
        }

        //得到sheet的总数
        int numberOfSheets = workbook.getNumberOfSheets();
        //循环每一个sheet
        for(int i=0; i < numberOfSheets; i++) {

            Sheet sheet = workbook.getSheetAt(i);
//            System.out.println(sheet.getSheetName()+"  sheet");
            //得到行的迭代器
            Iterator<Row> rowIterator = sheet.iterator();
            int rowCount = 0;
            //循环每一行
            if(rowIterator.hasNext()){
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
//                System.out.print("第"+(rowCount++)+"行  ");
                String line = "";
                //得到一行对象
                Row row = rowIterator.next();
                //得到列对象
                Iterator<Cell> cellIterator = row.cellIterator();

                int columnCount = 0;

                //循环每一列
                while (cellIterator.hasNext()) {
                    //System.out.print("第"+(columnCount++)+"列:  ");

                    //得到单元格对象
                    Cell cell = cellIterator.next();

                    //检查数据类型
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
//                            System.out.print(cell.getStringCellValue()+"   ");
                            line += cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            DecimalFormat dfs = new DecimalFormat("0");
                            String str = dfs.format(cell.getNumericCellValue());
//                            System.out.print(str + "  ");
                            line += str;
                    }
                    if (cellIterator.hasNext()) {
                        line += ",";
                    }
                } //end of cell iterator
                if(!line.equals("")){
                    String[] temp = {line, "yyyy,mm,dd,n,exp"};
                    cases.add(temp);
                }
            }
        }

        return cases;
    }

    @Test
    public void getNextNDay() throws Exception {
        assertEquals(expected, DateTool.getNextNDay(year, month, day, dayNum));
    }

}