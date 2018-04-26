package com.nhsoft.module.report.dto;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Comparator;

public class BranchProfitComparator<T> implements Comparator<T> {

    private static final Logger logger = LoggerFactory.getLogger(TransferProfitByPosItemPageDTO.class);

    //排序
    private String sortField;
    private String sortType;
    private String defaultField;

    public BranchProfitComparator() {
    }

    public BranchProfitComparator(String sortField, String sortType) {
        this.sortField = toUpperCaseFirst(sortField);
        this.sortType = sortType;
    }

    public BranchProfitComparator(String sortField, String sortType, String defaultField) {
        this.sortField = toUpperCaseFirst(sortField);
        this.sortType = sortType;
        this.defaultField = toUpperCaseFirst(defaultField);
    }

    //首字母转大写
    public static String toUpperCaseFirst(String str){
        if(Character.isUpperCase(str.charAt(0)))
            return str;
        else
            return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    @Override
    public int compare(T data01, T data02) {
        int value01 = 0;
        int value02 = 0;
        if("asc".equals(sortType) || "ASC".equals(sortType)){
            value01 = 1;
            value02 = -1;
        }
        if("desc".equals(sortType) || "DESC".equals(sortType)){
            value01 = -1;
            value02 = 1;
        }

        try {
            Class clazz01 = data01.getClass();
            Method method01 = clazz01.getMethod("get" + sortField);

            Class clazz02 = data02.getClass();
            Method method02 = clazz02.getMethod("get" + sortField);
            int compare = 0;

            if(StringUtils.isNotBlank(defaultField)){
                Method defDethod01 = clazz01.getMethod("get" + defaultField);
                Method defDethod02 = clazz02.getMethod("get" + defaultField);
                //默认按配送门店(或配送门店)升序排序  门店都是Integer
                Integer defInvoke01 = (Integer)defDethod01.invoke(data01);
                Integer defInvoke02 = (Integer)defDethod02.invoke(data02);
                compare = defInvoke01.compareTo(defInvoke02);
            }


            Type type=method02.getGenericReturnType();
            String end = type.toString();
            String substring = end.substring(type.toString().lastIndexOf(".") + 1, end.length());
            if("Integer".equals(substring)){

                if(compare == 0){
                    Integer invoke01 = (Integer)method01.invoke(data01);
                    Integer invoke02 = (Integer)method02.invoke(data02);
                    if(invoke01 == null && invoke02 == null){
                        return 0;
                    }else if(invoke01 == null){
                        return value02;    //假如升序，v01为null   01 < 02 返回 -1     字段为空的判断
                    } else if(invoke02 == null){
                        return value01;
                    }else if (invoke01.compareTo(invoke02) > 0) {
                        return value01;
                    } else if (invoke01.compareTo(invoke02) < 0) {
                        return value02;
                    } else {
                        return 0;
                    }
                }else{
                    return compare;
                }

            }
            if("String".equals(substring)){
                if(compare == 0){
                    String invoke01 = (String)method01.invoke(data01);
                    String invoke02 = (String)method02.invoke(data02);
                    if(invoke01 == null && invoke02 == null){
                        return 0;
                    }else if(invoke01 == null){
                        return value02;    //假如升序，v01为null   01 < 02 返回 -1     字段为空的判断
                    } else if(invoke02 == null){
                        return value01;
                    }else if (invoke01.compareTo(invoke02) > 0) {
                        return value01;
                    } else if (invoke01.compareTo(invoke02) < 0) {
                        return value02;
                    } else {
                        return 0;
                    }
                }else{
                    return compare;
                }

            }
            if("BigDecimal".equals(substring)){

                if(compare == 0){
                    BigDecimal invoke01 = (BigDecimal)method01.invoke(data01);
                    BigDecimal invoke02 = (BigDecimal)method02.invoke(data02);

                    if(invoke01 == null && invoke02 == null){
                        return 0;
                    }else if(invoke01 == null){
                        return value02;    //假如升序，v01为null   01 < 02 返回 -1     字段为空的判断
                    } else if(invoke02 == null){
                        return value01;
                    }else if (invoke01.compareTo(invoke02) > 0) {
                        return value01;
                    } else if (invoke01.compareTo(invoke02) < 0) {
                        return value02;
                    } else {
                        return 0;
                    }
                }else{
                    return compare;
                }

            }

        } catch (Exception e) {
        }

        return 0;
    }

}
