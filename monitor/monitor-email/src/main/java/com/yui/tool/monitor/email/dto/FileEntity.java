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
public class FileEntity {

    private String fileName;

    private String path;
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FileEntity)) {
            return false;
        }
        FileEntity temp = (FileEntity) obj;
        if (temp.getFileName() != null && temp.getFileName().equals(this.fileName)){
            return false;
        }
        return temp.getPath() == null || !temp.getPath().equals(this.path);
    }

}
