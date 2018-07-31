package cc.develop.tool.config.utils;

import cc.develop.tool.config.utils.excel.ExcelObj;
import cc.develop.tool.config.utils.excel.annotation.ExcelField;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Field;
import java.util.*;

public class ExcelUtil {

    private static final HashMap<Class<?>,HashMap<Integer,Field>> FIELD_MAP
            = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static void parseXSSFRow(Row row, String[] values) {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    values[i] = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        values[i] = DateFormatUtils.format(date,"yyyy-MM-dd HH:mm:ss");
                    } else {
                        values[i] = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    values[i] = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    values[i] = cell.getCellFormula();
                    break;
                case BLANK:
                    values[i] = "";
                    break;
                default:
                    values[i] = cell.toString();
            }
        }
    }


    public static <T extends ExcelObj> ParseFileResult<T,String> parseToObject(Workbook workbook, Class<T> cls) {
        ParseFileResult<T,String> fileResultBo =
                new ParseFileResult<>();
        HashMap<Integer,Field> integerFieldHashMap
                =getIfExist(cls);
        Iterator<Sheet> iterator = workbook.sheetIterator();
        List<T> ts = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        while (iterator.hasNext()){
            Sheet sheet = iterator.next();
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue;
                }
                Objenesis objenesis = new ObjenesisStd();
                T excelObj = objenesis.newInstance(cls);
                for (Map.Entry<Integer, Field> entry : integerFieldHashMap.entrySet()) {
                    Cell cell = row.getCell(entry.getKey());
                    Field field = entry.getValue();
                    Object value;
                    if (cell == null) {
                        continue;
                    }
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            value = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                value = DateFormatUtils.format(date,"yyyy-MM-dd HH:mm:ss");
                            } else {
                                value = cell.getNumericCellValue();
                            }
                            break;
                        case BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                        case FORMULA:
                            value = cell.getCellFormula();
                            break;
                        case BLANK:
                            value = "";
                            break;
                        default:
                            value = cell.toString();
                    }
                    try {
                        field.setAccessible(true);
                        if (value instanceof Number) {
                            if (field.getType() == Byte.class) {
                                field.set(excelObj, field.getType().cast(((Number) value).byteValue()));
                            }
                            if (field.getType() == Short.class) {
                                field.set(excelObj, field.getType().cast(((Number) value).shortValue()));
                            }
                            if (field.getType() == Integer.class) {
                                field.set(excelObj, field.getType().cast(((Number) value).intValue()));
                            }
                            if (field.getType() == Float.class) {
                                field.set(excelObj, field.getType().cast(((Number) value).doubleValue()));
                            }
                            if (field.getType() == Double.class) {
                                field.set(excelObj, field.getType().cast(((Number) value).doubleValue()));
                            }
                            if (field.getType() == Long.class) {
                                field.set(excelObj, field.getType().cast(((Number) value).longValue()));
                            }
                        } else {
                            field.set(excelObj, value);
                        }
                    } catch (Exception e) {
                        errors.add("处理第" + row.getRowNum() + "行出错:" + value);
                    }
                }
                ts.add(excelObj);
            }
        }
        fileResultBo.setSuccess(ts);
        fileResultBo.setError(errors);
        return fileResultBo;
    }

    public static <T extends ExcelObj> Workbook parseToExcel(List<T> list) throws IllegalAccessException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row headRow = sheet.createRow(0);
        if (list == null || list.size() <= 0 ) {
            throw new RuntimeException("empty list");
        }
        Class<?>  tClass = list.get(0).getClass();
        HashMap<Integer,Field> integerFieldHashMap
                =getIfExist(tClass);
        Field[] fields = integerFieldHashMap.values().toArray(new Field[]{});
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelField.class)){
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                headRow.createCell(excelField.index()).setCellValue(excelField.name());
            }
        }
        for (int a = 0 ; a < list.size() ; a ++){
            Row valueRow = sheet.createRow(a + 1);
            T t = list.get(a);
            for (Map.Entry<Integer, Field> entry : integerFieldHashMap.entrySet()) {
                Object value = entry.getValue().get(t);
                if (value != null ) {
                    valueRow.createCell(entry.getKey()).setCellValue(value.toString());
                }
            }
        }
        return workbook;
    }



    @SuppressWarnings("unchecked")
    private static <T> HashMap<Integer,Field> getIfExist(Class<T> cls) {
        HashMap result  = FIELD_MAP.get(cls);
        if (result == null){
            result = new HashMap();
            for (Field field : cls.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ExcelField.class)){
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    result.put(excelField.index(),field);
                }
            }
            FIELD_MAP.put(cls,result);
        }
        return result;
    }
}
