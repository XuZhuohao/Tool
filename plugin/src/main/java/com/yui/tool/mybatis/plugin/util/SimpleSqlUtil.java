package com.yui.tool.mybatis.plugin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yui.tool.mybatis.plugin.example.po.LoveWord;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * sql 拼接工具类
 *
 * @author XuZhuohao
 * @date 2018-12-30 10:51
 */
public class SimpleSqlUtil {
    private static final String ARRAY_TYPE="class [L";
    private static Map<Class, String> templateSql;
    static{
        templateSql = new HashMap<>(10);
        templateSql.put(Integer.class, "#{value}");
        templateSql.put(Long.class, "#{value}");
        templateSql.put(Short.class, "#{value}");
        templateSql.put(Byte.class, "#{value}");
        templateSql.put(Double.class, "#{value}");
        templateSql.put(Float.class, "#{value}");
        templateSql.put(String.class,"'#{value}'");
        // char
//        templateSql.put(Date.class, "'#{value}'")
        templateSql.put(null, " is null");
    }
    public synchronized static String getSql(Object entity, Map<String, Object[]> otherValue, boolean isLike, boolean isOr){
        // TODO: 不完善
        StringBuilder result = new StringBuilder();
        String connect = isOr ? " or " : " and ";
        if (isLike){
            templateSql.put(String.class,"'%#{value}%'");
        }
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(entity));
        Map<String, Object> innerMap = jsonObject.getInnerMap();
        innerMap.putAll(otherValue);
        innerMap.forEach((key,value)->{
            result.append(connect).append(key).append(getTypeValue("=",  value)).append("\n");
        });
        if (isLike){
            templateSql.put(String.class,"'#{value}'");
        }
        return result.toString().replaceFirst(connect,"");
    }
    /**
     * 获取 sql and 连接
     * @param inMap
     * @return
     */
    public static String getSqlAnd(Map<String, Object> inMap){
        StringBuilder result = new StringBuilder();
        inMap.forEach((key,value)->{
            result.append(" and ").append(key).append(getTypeValue("=",  value)).append("\n");
        });
        return result.toString().replaceFirst(" and","");
    }
    public static String getSqlOr(Map<String, Object> inMap){
        StringBuilder result = new StringBuilder();
        inMap.forEach((key,value)->{
            result.append(" or ").append(key).append(getTypeValue("=",  value)).append("\n");
        });
        return result.toString().replaceFirst(" or","");
    }

    private static String getTypeValue(String link, Object value){
        // null 类型
        if (value == null){
            return " is null";
        }
        // 通用类型
        String str = templateSql.get(value.getClass());
        if (str != null){
            return link + str.replace("#{value}", String.valueOf(value));
        }
        str = link;
        // boolean
        if (Boolean.class.equals(value.getClass())) {
            str += (Boolean)value? " 1 " : "0";
            // date 类型
        } else if (Date.class.equals(value.getClass())){
            str += "'" + DateUtils.getDateFormat(String.class, (Date)value, "yyyy-MM-dd HH:mm:ss") + "'";
            // 数组类型
        } else if (value.getClass().toString().contains(ARRAY_TYPE)){
            str = getValueOfArray(value);
        }
        return str;
    }

    private static String getValueOfArray(Object value){
        Object[] values = (Object[])value;
        // TODO：2 使用between， in 两个值就会变成between
        if (values.length == 2){
            return " between " + getTypeValue("", values[0]) + " and " + getTypeValue("", values[1]);
        }
        StringBuilder result = new StringBuilder(" in (");
        for (Object temp : values){
            result.append(",").append(getTypeValue("", temp));
        }
        return result.append(")").toString().replaceFirst(",", "");
    }


    public static void main(String[] args) {
        System.out.println("#{value}".replace("#{value}", "1"));
        LoveWord loveWord = new LoveWord();
        loveWord.setId(1L);
        loveWord.setTimes(3L);
    //        loveWord.setCreateDate(new Date[]{new Date(), new Date()})
        loveWord.setIsDelete((byte) 0x00);
        loveWord.setNickName("test");
//        loveWord.setUseDate()
        loveWord.setWord("word");
        /*JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(loveWord));
        Map<String, Object> innerMap = jsonObject.getInnerMap();
        innerMap.put("isDelte", false);
        innerMap.put("CreateDate", new Date[]{new Date(), new Date()});
        innerMap.put("t1",new Integer[]{1,2,3,4,5,6});
        System.out.println(getSqlAnd(innerMap));*/
        Map<String, Object[]> innerMap = new HashMap<>();
        innerMap.put("CreateDate", new Date[]{new Date(), new Date()});
        innerMap.put("t1",new Integer[]{1,2,3,4,5,6});
        System.out.println(getSql(loveWord, innerMap, true, true));
    }
}
