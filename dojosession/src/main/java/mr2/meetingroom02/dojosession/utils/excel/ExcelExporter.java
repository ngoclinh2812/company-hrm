package mr2.meetingroom02.dojosession.utils.excel;

import mr2.meetingroom02.dojosession.lunch.dto.UpcomingWeekMealsDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.ejb.Singleton;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Singleton
public class ExcelExporter {

    public byte[] exportToExcel(List<UpcomingWeekMealsDTO> dataList) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Upcoming Week Meals");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Date");
            headerRow.createCell(1).setCellValue("Department Name");
            headerRow.createCell(2).setCellValue("Dish Name");
            headerRow.createCell(3).setCellValue("Count");

            LocalDate previousMenuDate = null;

            int startRow = 1;
            int rowNum = 1;
            for (UpcomingWeekMealsDTO dto : dataList) {
                Row row = sheet.createRow(rowNum++);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = dto.getMenuDate().format(formatter);

                row.createCell(0).setCellValue(formattedDate);
//                row.createCell(0).setCellValue(dto.getMenuDate());
                row.createCell(1).setCellValue(dto.getDepartmentName());
                row.createCell(2).setCellValue(dto.getDishName());
                row.createCell(3).setCellValue(dto.getCount());

//                LocalDate currentMenuDate = dto.getMenuDate();
//                if (currentMenuDate.equals(previousMenuDate)) {
//                    if (rowNum - startRow > 1) {
//                        sheet.addMergedRegion(new CellRangeAddress(startRow, rowNum - 1, 0, 0));
//                    }
//                } else {
//                    startRow = rowNum;
//                }
//                previousMenuDate = currentMenuDate;
            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        }

    }
}
