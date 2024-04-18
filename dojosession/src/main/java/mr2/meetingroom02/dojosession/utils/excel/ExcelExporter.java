package mr2.meetingroom02.dojosession.utils.excel;

import mr2.meetingroom02.dojosession.lunchSchedule.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.UpcomingWeekOrderDishesByDepartmentDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.ejb.Singleton;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Singleton
public class ExcelExporter {

    public byte[] exportToExcel(List<UpcomingWeekOrderDishesByDepartmentDTO> dataList, LunchScheduleResponseDTO lunchScheduleResponseDTO) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Upcoming Week Meals");

            Row startDateRow = sheet.createRow(0);
            startDateRow.createCell(0).setCellValue("Start Date:");
            startDateRow.createCell(1).setCellValue(lunchScheduleResponseDTO.getStartDate().toString());
            Row endDateRow = sheet.createRow(1);
            endDateRow.createCell(0).setCellValue("End Date:");
            endDateRow.createCell(1).setCellValue(lunchScheduleResponseDTO.getEndDate().toString());

            Row headerRow = sheet.createRow(2);
            headerRow.createCell(0).setCellValue("Date");
            headerRow.createCell(1).setCellValue("Department Name");
            headerRow.createCell(2).setCellValue("Dish Name");
            headerRow.createCell(3).setCellValue("Count");

            int rowNum = 3;
            for (UpcomingWeekOrderDishesByDepartmentDTO dto : dataList) {
                Row row = sheet.createRow(rowNum++);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = dto.getMenuDate().format(formatter);

                row.createCell(0).setCellValue(formattedDate);
                row.createCell(1).setCellValue(dto.getDepartmentName());
                row.createCell(2).setCellValue(dto.getDishName());
                row.createCell(3).setCellValue(dto.getCount());

            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        }

    }
}
