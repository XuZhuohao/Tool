package com.yui.tool.github.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * makedown中对应的图片信息
 *
 * @author XuZhuohao
 * @date 2018/10/15
 */
@Setter
@Getter
public class MdUrlObject {
    /**
     * url在文本中的行文字  ！[]()
     */
    private String mdTag;
    /**
     * url String
     */
    private String url;
    /**
     * 对应的md文件指向
     */
    private File file;
}
