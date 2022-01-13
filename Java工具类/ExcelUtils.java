package cn.soyadokio.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel 工具类
 * @author SoyaDokio
 * @date   2020-07-24
 */
public class ExcelUtils {

    private static List<ArrayList<String>> sheetData = new ArrayList<ArrayList<String>>();

    /**
     * 获取工作表的类型，是2003格式（xls）还是2007格式（xlsx）
     * @param filePath
     * @return
     */
    private static String getFileType(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * 获取 IO 流
     * @param filePath
     * @return
     * @throws Exception
     */
    private static FileInputStream getStream(String filePath) throws FileNotFoundException {
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fi == null) {
            throw new FileNotFoundException("文件不存在 - " + filePath);
        }
        return fi;
    }

    /**
     * 通过 sheet 名称获取指定工作簿的指定 sheet 对象
     * @param filePath
     * @param sheetName
     * @return
     */
    private static Sheet getSheet(String filePath, String sheetName) {
        Sheet sheet = null;
        String fileType = getFileType(filePath);
        if ("xls".equals(fileType)) {
            HSSFWorkbook _2003wookbook = null;
            try {
                _2003wookbook = new HSSFWorkbook(getStream(filePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sheet = _2003wookbook.getSheet(sheetName);
        }
        if ("xlsx".equals(fileType)) {
            XSSFWorkbook _2007wookbook = null;
            try {
                _2007wookbook = new XSSFWorkbook(getStream(filePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sheet = _2007wookbook.getSheet(sheetName);
        }
        return sheet;
    }

    /**
     * 通过 sheet 索引获取指定工作簿的指定 sheet 对象
     * @param filePath
     * @param sheetIndex
     * @return
     */
    private static Sheet getSheet(String filePath, int sheetIndex) {
        Sheet sheet = null;
        String fileType = getFileType(filePath);
        if ("xls".equals(fileType)) {
            HSSFWorkbook _2003wookbook = null;
            try {
                _2003wookbook = new HSSFWorkbook(getStream(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
            sheet = _2003wookbook.getSheetAt(sheetIndex);
        }
        if ("xlsx".equals(fileType)) {
            XSSFWorkbook _2007wookbook = null;
            try {
                _2007wookbook = new XSSFWorkbook(getStream(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
            sheet = _2007wookbook.getSheetAt(sheetIndex);
        }
        return sheet;
    }

    /**
     * 获取指定 sheet 的数据
     * @param sheet
     * @return
     * @throws Exception
     */
    private static List<ArrayList<String>> getSheetData(Sheet sheet) {
        if (sheet == null) {
            return null;
        }

        int rowMax = sheet.getLastRowNum() + 1;
        for (int i = 0; i < rowMax; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                sheetData.add(new ArrayList<String>());
            } else {
                ArrayList<String> rowData = new ArrayList<String>();
                int cellMax = row.getLastCellNum();
                for (int j = 0; j < cellMax; j++) {
                    Cell cell = row.getCell(j);
                    String value = getCellDataAsString(cell);
                    rowData.add(value);
                }
                sheetData.add(rowData);
            }
        }
        return sheetData;
    }

    /**
     * 根据 sheet 索引获取指定 sheet 的数据
     * @param filePath
     * @param sheetIndex
     * @return
     * @throws Exception
     */
    public static List<ArrayList<String>> getSheetData(String filePath, int sheetIndex) {
        Sheet sheet = getSheet(filePath, sheetIndex);
        return getSheetData(sheet);
    }

    /**
     * 根据 sheet 名称获取指定 sheet 的数据
     * @param filePath
     * @param sheetName
     * @return
     * @throws Exception
     */
    public static List<ArrayList<String>> getSheetData(String filePath, String sheetName) {
        Sheet sheet = getSheet(filePath, sheetName);
        return getSheetData(sheet);
    }

    /**
     * 获取单元格的字符串形式的值
     * @param cell
     * @return
     */
    private static String getCellDataAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        String cellData = null;
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_BLANK:
            cellData = "";
            break;
        case Cell.CELL_TYPE_STRING:
            cellData = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
        case Cell.CELL_TYPE_FORMULA:
        case Cell.CELL_TYPE_BOOLEAN:
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cellData = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_ERROR:
            // empty
        }
        return cellData;
    }

}
