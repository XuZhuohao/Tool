package com.yui.tool.imgremove;

import com.yui.tool.imgremove.core.impl.MdFileImpl;
import com.yui.tool.imgremove.core.impl.MoveServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class ImgRemoveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImgRemoveApplication.class, args);
        MdFileImpl mdFile = new MdFileImpl();
        MoveServiceImpl moveService = new MoveServiceImpl();
        moveService.move(mdFile.getImagesFromMdFile("D:\\File\\Github\\StudySummarize"));
    }
}
