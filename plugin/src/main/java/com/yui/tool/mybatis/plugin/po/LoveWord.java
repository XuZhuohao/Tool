package com.yui.tool.mybatis.plugin.po;

import java.util.Date;

public class LoveWord {
    private Long id;

    private Date createDate;

    private Byte isDelete;

    private String nickName;

    private Long times;

    private Integer useDate;

    private String word;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Integer getUseDate() {
        return useDate;
    }

    public void setUseDate(Integer useDate) {
        this.useDate = useDate;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }
}