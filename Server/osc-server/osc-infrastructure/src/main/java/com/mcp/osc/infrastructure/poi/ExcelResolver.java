package com.mcp.osc.infrastructure.poi;

import com.mcp.osc.infrastructure.annotation.ExcelEntity;
import com.mcp.osc.infrastructure.exception.ExcelException;
import com.mcp.osc.infrastructure.annotation.ExcelCol;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author KG(Kelvin Gu)
 * @ClassName: ExcelResolver
 * @Package com.jnj.autocloud.common.util.poi
 * @Description: excel resolve by annotation
 * @date 2022/9/21 20:00
 */
public class ExcelResolver {
    private static final String SETTER_PREFIX = "set";

    public static <T> List<T> resolveSheet(Sheet sheet, Class<T> clazz, int contentRowNum, int colTitleRowNum) {
        List<T> entities = new ArrayList<>();
        Map<Integer, Field> fieldMap = mapColNumWithField(sheet, clazz, colTitleRowNum);
        for (int i = contentRowNum; i <= sheet.getLastRowNum(); i++) {
            entities.add(resolveRow(sheet.getRow(i), clazz, fieldMap));
        }
        return entities;
    }

    private static <T> T resolveRow(Row row, Class<T> clazz, Map<Integer, Field> fieldMap) {
        try {
            T entity = clazz.newInstance();
            for (Cell cell : row) {
                Field field = fieldMap.get(cell.getColumnIndex());
                setField(field, cell, entity);
            }
            return entity;
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            throw new ExcelException("setter method error");
        }
    }

    private static <T> void setField(Field field, Cell cell, T entity) throws NoSuchMethodException {
        Method setter = createSetter(field, entity);
        Class[] paramTypes = setter.getParameterTypes();
        assignField(cell, paramTypes[0], setter, entity);
    }

    private static <T> Method createSetter(Field field, T entity) {
        try {
            String setterName = createSetterName(field.getName());
            return entity.getClass().getDeclaredMethod(setterName, field.getType());
        } catch (NoSuchMethodException e) {
            throw new ExcelException("No Setter");
        }
    }

    private static <T> void assignField(Cell cell, Class paramClass, Method setter, T entity) {
        try {
            if (paramClass.equals(Date.class)) {
                setter.invoke(entity, cell.getDateCellValue());
            } else if (paramClass.equals(String.class)) {
                //setter.invoke(entity, cell.getStringCellValue());
                // fix String value bug of 20594
                DataFormatter formatter = new DataFormatter();
                setter.invoke(entity, formatter.formatCellValue(cell));
            } else if (paramClass.equals(Integer.class)) {
                setter.invoke(entity, (int) cell.getNumericCellValue());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ExcelException("Assign cell value error");
        }
    }

    private static <T> Map<Integer, Field> mapColNumWithField(Sheet sheet, Class<T> clazz, int colTitleRowNum) {
        Row titleRow = sheet.getRow(colTitleRowNum);
        Map<String, Field> fieldMap = extractFieldMap(clazz);
        Map<Integer, Field> fieldColNumMap = new HashMap<>();
        for (Cell cell : titleRow) {
            cell.setCellType(CellType.STRING);
            Field field = fieldMap.get(cell.getStringCellValue());
            if (null != field) {
                fieldColNumMap.put(cell.getColumnIndex(), field);
            }
        }
        return fieldColNumMap;
    }

    private static <T> Map<String, Field> extractFieldMap(Class<T> clazz) {
        checkResolvable(clazz);
        Map<String, Field> fieldMap = new HashMap<>();
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        fields.stream().filter(field -> field.getAnnotation(ExcelCol.class).colName().length() > 0)
                .forEach(fieldForCol -> {
                    String colName = fieldForCol.getAnnotation(ExcelCol.class).colName();
                    fieldMap.put(colName, fieldForCol);
                });
        return fieldMap;
    }

    private static <T> void checkResolvable(Class<T> clazz) {
        ExcelEntity annotation = clazz.getAnnotation(ExcelEntity.class);
        if (null == annotation) {
            throw new ExcelException("Entity is not made for excel conversion");
        }
    }

    private static String createSetterName(String fieldName) {
        if (StringUtils.isEmpty(fieldName)) {
            throw new ExcelException("No Setter");
        }
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        return SETTER_PREFIX + firstLetter + fieldName.substring(1);
    }
}
