//package com.example.basicauthen.service.ReadExcelFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReadExcelFileService {
//    public static List<List<String>> readExcelFile(String fileName) throws IOException {
//        //Đường dẫn file
//        String URL = "C:/Users/Admin/Downloads/basic-authen/" + fileName + ".xlsx";
//
//        //Logic
//        FileInputStream file = new FileInputStream(new File(URL));
//        Workbook workbook = new XSSFWorkbook(file);
//        Sheet sheet = workbook.getSheetAt(0);
//        List<List<String>> data = new ArrayList<>();
//        for (Row row : sheet) {
//            List<String> rowData = new ArrayList<>();
//            for (Cell cell : row) {
//                DataFormatter dataFormatter = new DataFormatter();
//                String cellValue = dataFormatter.formatCellValue(cell);
//                rowData.add(cellValue);
//            }
//            data.add(rowData);
//        }
//        workbook.close();
//        file.close();
//        return data;
//    }
//}
