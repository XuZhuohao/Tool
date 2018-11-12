package com.yui.tool.imgremove.core.impl;

import com.alibaba.fastjson.JSON;
import com.yui.tool.imgremove.core.MoveService;
import com.yui.tool.imgremove.dto.ImageDto;
import com.yui.tool.imgremove.dto.MdEditDto;
import com.yui.tool.imgremove.util.ImageUtil;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现
 *
 * @author XuZhuohao
 * @date 2018/11/12
 */
@Service
public class MoveServiceImpl implements MoveService {
    @Override
    public void move(List<ImageDto> images) {
        // 图片下载
        System.out.println("==========下载图片：count = " + images.size());
        downloadImage(images);
        // md文件修改
        System.out.print("==========文件修改：");
        editMdFile(images);
    }

    /**
     * 图片下载
     * @param images 图片对象集合
     */
    private void downloadImage(List<ImageDto> images){
        images.forEach(imageDto -> {
            try {
                ImageUtil.downloadImage(imageDto.getOldUrlFormat(), imageDto.getPath());
            } catch (Exception e) {
                System.err.println("下载失败: " + JSON.toJSONString(imageDto));
                e.printStackTrace();
            }
        });

    }

    private void editMdFile(List<ImageDto> images) {
        // 编辑对象
        List<MdEditDto> mdEditDtoList = formatObject(images);
        System.out.println("count = " + mdEditDtoList.size());
        System.out.println(JSON.toJSONString(mdEditDtoList));
        for (MdEditDto mdEditDto : mdEditDtoList) {
            replaceImageUrl(mdEditDto);
        }
    }

    private void replaceImageUrl(MdEditDto mdEditDto){
        PrintWriter printWriter = null;
        try (FileInputStream fis = new FileInputStream(mdEditDto.getMdFile());
             BufferedReader br = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            StringBuffer sbf = new StringBuffer();
            // 读取封装的输入流
            String temp;
            while ((temp = br.readLine()) != null) {
                sbf.append(temp).append("\r\n");
            }
            String edit = sbf.toString();
            //FileOutputStream fos = new FileOutputStream(file);
            for (ImageDto image : mdEditDto.getImages()) {
                // 对连接中的空格进行处理
                if (image.getOldUrl().indexOf("%20") > 0){
                    // TODO: 文件中url存在%20，无法识别replaceAll，无法处理
                    System.out.println(image.getOldUrl());
                }
                edit = edit.replaceAll(image.getOldUrl(), image.getNewUrl());
            }
            printWriter = new PrintWriter(mdEditDto.getMdFile(), "UTF-8");
            printWriter.write(edit);
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null){
                printWriter.close();
            }
        }
    }

    private List<MdEditDto> formatObject(List<ImageDto> images){
        Map<File, MdEditDto> inMap = new HashMap<>(16);
        images.forEach(imageDto -> {
            MdEditDto mdEditDto;
            if(inMap.containsKey(imageDto.getMdFile())){
                mdEditDto = inMap.get(imageDto.getMdFile());
                mdEditDto.getImages().add(imageDto);
            }else {
                mdEditDto = new MdEditDto();
                List<ImageDto> imageList = new ArrayList<>();
                imageList.add(imageDto);
                mdEditDto.setImages(imageList);
                mdEditDto.setMdFile(imageDto.getMdFile());
                inMap.put(mdEditDto.getMdFile(), mdEditDto);
            }
        });
        List<MdEditDto> result = new ArrayList<>();
        inMap.forEach((key,value) -> result.add(value));
        return result;
    }
}
