package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    public static void main(String[] args) throws IOException {

        String fileName="E:\\Project\\cases.xlsx";
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

//        System.out.println("一共"+numberOfSheets+"个sheet");

        //循环每一个sheet
        for(int i=0; i < numberOfSheets; i++){

            //得到第i个sheet
            Sheet sheet = workbook.getSheetAt(i);
//            System.out.println(sheet.getSheetName()+"  sheet");

            //得到行的迭代器
            Iterator<Row> rowIterator = sheet.iterator();

            int rowCount=0;
            //循环每一行
            if(rowIterator.hasNext()){
                rowIterator.next();
            }
            while (rowIterator.hasNext())
            {
                String line = "";
//                System.out.print("第"+(rowCount++)+"行  ");

                //得到一行对象
                Row row = rowIterator.next();
                //得到列对象
                Iterator<Cell> cellIterator = row.cellIterator();

                int columnCount=0;

                //循环每一列
                while (cellIterator.hasNext())
                {
                    //System.out.print("第"+(columnCount++)+"列:  ");

                    //得到单元格对象
                    Cell cell = cellIterator.next();

                    //检查数据类型
                    switch(cell.getCellType()){
                        case Cell.CELL_TYPE_STRING:
//                            System.out.print(cell.getStringCellValue()+"   ");
                            line += cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            DecimalFormat dfs = new DecimalFormat("0");
                            String  str = dfs.format(cell.getNumericCellValue());
//                            System.out.print(str + "  ");
                            line += str;
                    }
                    if(cellIterator.hasNext()){
                        line += ",";
                    }

                } //end of cell iterator

//                System.out.println(line);
                Format inputFormat = new InputFormat("yyyy,mm,dd,n,exp");
                Vector<String> v = inputFormat.match(line);
                int count = 0;
                for (int j = 0; j < v.size() - 1; ++j) {
                    if (!NumberCheck.checkNumberLegality(v.get(j))) {
                        System.out.println(v.get(j) + " Format error!");
    //                    cout << *iter << " Format error!\n";
    //                    out << "Format error!\n";
                        break;
                    }
                    ++count;
                }
                if (count != v.size() - 1) {
                    continue;
                }
                long year =  Long.parseLong(v.get(0));
                int month = Integer.parseInt(v.get(1));
		        long day = Long.parseLong(v.get(2));

            /*Date now(year, month, day);
            if (!DateTool::checkLegality(now)) {
                cout << now << " Format error!\n";
                out << now << " Format error!\n";
                continue;
            }
            cout << "Today is :" << now << endl;
            out << "Today is :" << now << endl;*/
                long dayNum = Long.parseLong(v.get(3));
                String result = DateTool.getNextNDay(year, month, day, dayNum);
                if(result.equals("Format error!")){
                    System.out.println(result);
                }else {
                    System.out.println("Next " + dayNum + " Day Is: " + result);
                }

//                cout << "Next " << dayNum << " Day Is: " << result << endl;
//                out << result << endl;
            } //end of rows iterator



        } //end of sheets for loop


        System.out.println("\nread excel successfully...");

        //close file input stream
        fis.close();





    }
}
