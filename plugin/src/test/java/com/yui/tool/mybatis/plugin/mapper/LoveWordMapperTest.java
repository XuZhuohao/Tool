package com.yui.tool.mybatis.plugin.mapper;

import com.yui.tool.mybatis.plugin.PluginApplicationTests;
import com.yui.tool.mybatis.plugin.po.LoveWord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class LoveWordMapperTest extends PluginApplicationTests {
    @Autowired
    LoveWordMapper loveWordMapper;
    @Test
    public void find (){
        final LoveWord loveWord = loveWordMapper.selectByPrimaryKey(1L);
        System.out.println(loveWord.getCreateDate());
        loveWord.setId(null);
        loveWordMapper.insert(loveWord);
    }

}