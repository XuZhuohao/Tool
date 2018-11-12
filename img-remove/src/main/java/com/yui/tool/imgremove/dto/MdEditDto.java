package com.yui.tool.imgremove.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

/**
 * mdEdiit dto
 *
 * @author XuZhuohao
 * @date 2018/11/12
 */
@Setter
@Getter
public class MdEditDto {
    private File mdFile;
    private List<ImageDto> images;
}
