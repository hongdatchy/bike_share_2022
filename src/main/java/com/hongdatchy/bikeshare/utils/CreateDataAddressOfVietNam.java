/**
 * Copyright(C) 2022 SanLab Hust
 * CreateDataAddressOfVietNam.java, 21/04/2022
 */
package com.hongdatchy.bikeshare.utils;

import com.hongdatchy.bikeshare.entities.model.City;
import com.hongdatchy.bikeshare.entities.model.District;
import com.hongdatchy.bikeshare.entities.model.Ward;
import com.hongdatchy.bikeshare.repo.CityRepoJpa;
import com.hongdatchy.bikeshare.repo.DistrictRepoJpa;
import com.hongdatchy.bikeshare.repo.WardRepoJpa;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 *  class để tạo dữ liệu 3 table country, district, ward trong database
 *  tư file excel (format file xem trong file TinhHuyenXa.xlsx trong project)
 *
 *  Note: chỉ dùng khi cần update lại 3 bảng này
 *
 *  cách dùng: autowire class và gọi hàm này
 *
 * @author hongdatchy
 */
@Component
public class CreateDataAddressOfVietNam {

    @Autowired
    CityRepoJpa cityRepoJpa;

    @Autowired
    DistrictRepoJpa districtRepoJpa;

    @Autowired
    WardRepoJpa wardRepoJpa;

    /**
     * hàm đọc file và lưu vào database
     *
     * @param excelFilePath đường dẫn đến file excel
     * @throws IOException
     */
    public void readExcel(String excelFilePath) throws IOException {

        Set<City> cities = new TreeSet<>();
        Set<District>districts = new TreeSet<>();
        Set<Ward>wards = new TreeSet<>();


        // Get file
        InputStream inputStream = new FileInputStream(new File(excelFilePath));

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        for (Row nextRow : sheet) {
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
//            Book book = new Book();
            City city = new City();
            District district = new District();
            Ward ward = new Ward();

            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }

                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case 0:
                        city.setName((String) getCellValue(cell));
                        break;
                    case 1:
//                        Double double1 = (Double) getCellValue(cell);
                        city.setId(Integer.parseInt((String) getCellValue(cell)));
                        break;
                    case 2:
                        district.setName((String) getCellValue(cell));
                        break;
                    case 3:
//                        Double double2 = (Double) getCellValue(cell);
//                        district.setId(double2.intValue());
                        district.setId(Integer.parseInt((String) getCellValue(cell)));
                        district.setCityId(city.getId());
                        break;
                    case 4:
                        ward.setName((String) getCellValue(cell));
                        break;
                    case 5:
//                        Double double3 = (Double) getCellValue(cell);
//                        ward.setId(double3.intValue());
                        ward.setId(Integer.parseInt((String) getCellValue(cell)));
                        ward.setDistrictId(district.getId());
                        break;
                    default:
                        break;
                }

            }
            cities.add(city);
            districts.add(district);
            wards.add(ward);
        }

        workbook.close();
        inputStream.close();


//        for (City city : cities) {
//            System.out.println(city);
//        }
//        System.out.println(cities.size());

//        for (District district : districts) {
//            System.out.println(district);
//
//        }
//        for (Ward ward : wards) {
//            System.out.println(ward);
//        }
        cityRepoJpa.saveAll(cities);
        districtRepoJpa.saveAll(districts);
        wardRepoJpa.saveAll(wards);

    }

    // Get Workbook
    private Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            default:
                break;
        }

        return cellValue;
    }
}
