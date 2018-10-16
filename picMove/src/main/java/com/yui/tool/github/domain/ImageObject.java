package com.yui.tool.github.domain;

/**
 * 图片对象
 *
 * @author XuZhuohao
 * @date 2018/10/16
 */
public class ImageObject {
    /**
     * 图片url
     */
    private String url;
    /**
     * 图片名
     */
    private String name;
    /**
     * 图片md5
     */
    private String md5;
    /**
     * 图片磁盘路径
     */
    private String diskPath;
    /**
     * 图片大小
     */
    private Integer size;
    /**
     * 图片后缀
     */
    private String suffix;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getDiskPath() {
        return diskPath;
    }

    public void setDiskPath(String diskPath) {
        this.diskPath = diskPath;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
