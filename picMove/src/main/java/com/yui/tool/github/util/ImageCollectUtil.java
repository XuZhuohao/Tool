package com.yui.tool.github.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片收集通用类
 *
 * @author XuZhuohao
 * @date 2018/10/16
 */
public class ImageCollectUtil {
    /**
     * TODO: 写成正则表达式util会不会比较好
     * 根据{@param patternStr}正则表达式，查找文本 {@param text}，返回期待结果集合
     * @param patternStr 正则表达式公式
     * @param text 输入文本
     * @param groupIndexes 匹配的子表达式序号
     * @return List<String>
     */
    public static List<String> getUrlByPatternOfStr(String patternStr, String text, int ... groupIndexes) {
        List<String> result = new ArrayList<>(16);
        // 编译正则表达式规则
        Pattern pattern = Pattern.compile(patternStr);
        // 进行匹配
        Matcher matcher = pattern.matcher(text);
        // 循环获取结果
        while (matcher.find()) {
            for (int groupIndex : groupIndexes) {
                result.add(matcher.group(groupIndex));
            }
        }
        return result;
    }

    public static List<String> getUrlByPatternOfUrl(String patternStr, String url, int ... groupIndexes) {
        // 获取网页信息
        String text = HttpUtil.doGet(url, null);
        return getUrlByPatternOfStr(patternStr, text, groupIndexes);
    }

}
