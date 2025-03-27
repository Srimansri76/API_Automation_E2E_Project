package utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelUtil {
    private String filePath;
    private Workbook workbook;
    private Sheet sheet;

    /**
     * Constructor to initialize Excel file
     */
    public ExcelUtil(String filePath, String sheetName) throws IOException {
        this.filePath = filePath;
        FileInputStream fis = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
    }

    /**
     * Get cell data from Excel
     */
    public String getCellData(int row, int col) {
        Row sheetRow = sheet.getRow(row);
        if (sheetRow != null) {
            Cell cell = sheetRow.getCell(col);
            if (cell != null) {
                return cell.toString();
            }
        }
        return "";
    }

    /**
     * Get the total number of rows in the sheet
     */
    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    /**
     * Get total number of columns in a row
     */
    public int getColumnCount(int rowNum) {
        Row row = sheet.getRow(rowNum);
        return (row != null) ? row.getLastCellNum() : 0;
    }

    /**
     * Write data to Excel
     */
    public void setCellData(int row, int col, String data) throws IOException {
        Row sheetRow = sheet.getRow(row);
        if (sheetRow == null) {
            sheetRow = sheet.createRow(row);
        }
        Cell cell = sheetRow.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell.setCellValue(data);

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    /**
     * Close the workbook
     */
    public void closeWorkbook() throws IOException {
        workbook.close();
    }
}
