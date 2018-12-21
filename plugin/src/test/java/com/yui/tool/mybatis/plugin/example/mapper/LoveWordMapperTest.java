package com.yui.tool.mybatis.plugin.example.mapper;

import com.yui.tool.mybatis.plugin.PluginApplicationTests;
import com.yui.tool.mybatis.plugin.example.po.LoveWord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LoveWordMapperTest extends PluginApplicationTests {
    @Autowired
    LoveWordMapper loveWordMapper;
    @Test
    public void find (){
        final LoveWord loveWord = loveWordMapper.selectByPrimaryKey(1L);
        System.out.println(loveWord.getCreateDate());
        loveWord.setId(null);
        loveWord.setTimes(null);
        loveWordMapper.insert(loveWord);
    }

}