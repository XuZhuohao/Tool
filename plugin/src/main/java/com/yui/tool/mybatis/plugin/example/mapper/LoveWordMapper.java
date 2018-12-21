package com.yui.tool.mybatis.plugin.example.mapper;

import com.yui.tool.mybatis.plugin.example.po.LoveWord;
import com.yui.tool.mybatis.plugin.example.po.LoveWordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 */
public interface LoveWordMapper {
    int countByExample(LoveWordExample example);

    int deleteByExample(LoveWordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LoveWord record);

    int insertSelective(LoveWord record);

    List<LoveWord> selectByExampleWithBLOBs(LoveWordExample example);

    List<LoveWord> selectByExample(LoveWordExample example);

    LoveWord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LoveWord record, @Param("example") LoveWordExample example);

    int updateByExampleWithBLOBs(@Param("record") LoveWord record, @Param("example") LoveWordExample example);

    int updateByExample(@Param("record") LoveWord record, @Param("example") LoveWordExample example);

    int updateByPrimaryKeySelective(LoveWord record);

    int updateByPrimaryKeyWithBLOBs(LoveWord record);

    int updateByPrimaryKey(LoveWord record);
}