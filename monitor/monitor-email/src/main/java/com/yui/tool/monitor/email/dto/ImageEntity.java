package com.yui.tool.monitor.email.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author XuZhuohao
 * @date 2018/10/19
 */
@Setter
@Getter
@Accessors(chain = true)
public class ImageEntity {
    /**
     * 模版的位置
     */
    private String cid;
    /**
     * 源文件的路径
     */
    private String src;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ImageEntity)) {
            return false;
        }
        ImageEntity temp = (ImageEntity) obj;
        if (temp.getCid() != null && temp.getCid().equals(this.cid)){
            return false;
        }
        return temp.getSrc() == null || !temp.getSrc().equals(this.src);
    }
}
