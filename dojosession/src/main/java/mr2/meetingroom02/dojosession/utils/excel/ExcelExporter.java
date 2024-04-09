package mr2.meetingroom02.dojosession.utils.excel;

import mr2.meetingroom02.dojosession.lunch.dto.UpcomingWeekMealsDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.ejb.Singleton;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Singleton
public class ExcelExporter {

    public byte[] exportToExcel(List<UpcomingWeekMealsDTO> dataList, String filePath) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Upcoming Week Meals");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Department Name");
            headerRow.createCell(1).setCellValue("Dish Name");
            headerRow.createCell(2).setCellValue("Count");

            int rowNum = 1;
            for (UpcomingWeekMealsDTO dto : dataList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dto.getDepartmentName());
                row.createCell(1).setCellValue(dto.getDishName());
                row.createCell(2).setCellValue(dto.getCount());
            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        }
    }
}
