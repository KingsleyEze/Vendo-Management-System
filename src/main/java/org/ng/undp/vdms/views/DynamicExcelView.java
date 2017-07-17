package org.ng.undp.vdms.views;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.ng.undp.vdms.domains.User;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by macbook on 7/7/17.
 */
public class DynamicExcelView extends AbstractXlsView
{
    @SuppressWarnings("unchecked")


    @Override

    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        {
            //VARIABLES REQUIRED IN MODEL
            String sheetName = (String) model.get("sheetname");
            List<String> headers = (List<String>) model.get("headers");
            List<List<String>> results = (List<List<String>>) model.get("results");
            List<String> numericColumns = new ArrayList<String>();
            if (model.containsKey("numericcolumns"))
                numericColumns = (List<String>) model.get("numericcolumns");
            //BUILD DOC
            Sheet sheet = workbook.createSheet("User Detail");

            sheet.setDefaultColumnWidth(30);

            int currentRow = 0;
            short currentColumn = 0;
            //CREATE STYLE FOR HEADER
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            headerStyle.setFillForegroundColor(HSSFColor.BLUE.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            font.setBold(true);
            font.setColor(HSSFColor.WHITE.index);
            headerStyle.setFont(font);

            //POPULATE HEADER COLUMNS
            Row headerRow = sheet.createRow(currentRow);
            for (String header : headers) {
                HSSFRichTextString text = new HSSFRichTextString(header);
                Cell cell = headerRow.createCell(currentColumn);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(text);
                currentColumn++;
            }
            //POPULATE VALUE ROWS/COLUMNS
            currentRow++;//exclude header
            for (List<String> result : results) {
                currentColumn = 0;
                Row row = sheet.createRow(currentRow);
                for (String value : result) {//used to count number of columns
                    Cell cell = row.createCell(currentColumn);
                    if (numericColumns.contains(headers.get(currentColumn))) {
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue((value));
                    } else {
                        HSSFRichTextString text = new HSSFRichTextString(value);
                        cell.setCellValue(text);
                    }
                    currentColumn++;
                }
                currentRow++;
            }

        }}}
