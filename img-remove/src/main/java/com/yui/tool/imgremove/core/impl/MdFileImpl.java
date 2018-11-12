package com.yui.tool.imgremove.core.impl;

import com.yui.tool.imgremove.core.MdFile;
import com.yui.tool.imgremove.dto.ImageDto;
import com.yui.tool.imgremove.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * markdown 文件解析 实现
 *
 * @author XuZhuohao
 * @date 2018/11/12
 */
@Service
public class MdFileImpl implements MdFile {
    private final static String MD_SUFFIX = "MD";
    private final static String IMG_ROOT_PATH = "D:\\File\\Github\\picture";

    @Override
    public List<ImageDto> getImagesFromMdFile(String path) {
        List<ImageDto> result = new ArrayList<>();
        List<File> allFile = this.getAllMdFile(path);
        allFile.forEach(file -> {
            result.addAll(getImageDtoInMdFile(file));
        });
        return this.dealWithImageDto(result, path);
    }


    @Override
    public List<File> getAllMdFile(String path) {
        List<File> result = new ArrayList<>();
        List<File> allFile = FileUtil.getFiles(new File(path));
        allFile.forEach(file -> {
            if (file.getName().toUpperCase().endsWith(MD_SUFFIX)) {
                result.add(file);
            }
        });
        return result;
    }

    private List<ImageDto> dealWithImageDto(List<ImageDto> imageDtos, String path) {
        List<ImageDto> result = new ArrayList<>();
        imageDtos.forEach(imageDto -> {
            String filePath = imageDto.getMdFile().getAbsolutePath();
            // 去除MdFile文件名
            filePath = filePath.substring(0, filePath.lastIndexOf("\\"));
            // 图片名称
            String imageName = imageDto.getName() + "." + imageDto.getSuffix();
            imageDto.setPath(filePath.replace(path, IMG_ROOT_PATH) + File.separator + imageName);
            // https://raw.githubusercontent.com/XuZhuohao/picture/master/test/test01.jpg
            // 去除根目录，格式化
            filePath = filePath.replace(path, "").replace("\\", "/");
            imageDto.setNewUrl("https://raw.githubusercontent.com/XuZhuohao/picture/master" + filePath + "/" + imageName);
            result.add(imageDto);
        });
        return result;
    }


    /**
     * file to String
     *
     * @param file 文件对象
     * @return String
     * @throws Exception FileNotFoundException, IOException
     */
    private static String getStringOfFile(File file) throws Exception {
        StringBuilder result = new StringBuilder();
        FileInputStream fis = new FileInputStream(file);
        byte[] cache = new byte[1024];
        while (fis.read(cache) != -1) {
            result.append(new String(cache));
        }
        return result.toString();
    }

    /**
     * @param file md文件
     * @return ImageDto集合
     */
    public static List<ImageDto> getImageDtoInMdFile(File file) {
        List<ImageDto> mdUrlObjects = new ArrayList<>(16);
        //(![](()))  group 1 -> ![]()   group2 -> ()
        String patternStr = "([!][\\[][^]]*[]][(]([^)]*)[)])";
        // 编译正则表达式规则
        Pattern pattern = Pattern.compile(patternStr);
        String text = null;
        try {
            text = getStringOfFile(file);
        } catch (Exception e) {
            System.err.println("文件转String失败：" + file.getAbsoluteFile());
        }
        // 进行匹配
        Matcher matcher = pattern.matcher(text);
        // 循环获取结果
        while (matcher.find()) {
            // group(1) ![...](...www.xx.com....)
            // group(2)  www.xx.com
            try {
                String url = matcher.group(2);
                ImageDto imageDto = new ImageDto();
                imageDto.setMdFile(file);
                imageDto.setOldUrl(url);
                // https://github.com/XuZhuohao/studyNote-git-markdown-File-img/blob/master/spring%20boot/1.install/testCLI.png?raw=true
                // https://raw.githubusercontent.com/XuZhuohao/studyNote-git-markdown-File-img/master/spring%20boot/1.install/testCLI.png
                // 连接转换
                url = url.replace("github.com", "raw.githubusercontent.com")
                        .replace("www.","")
                        .replace("/raw/","/")
                        .replace("?raw=true", "")
                        .replace("/blob/","/");
                imageDto.setOldUrlFormat(url);
                String imageName = url.substring(url.lastIndexOf("/") + 1);

                imageDto.setName(imageName.substring(0, imageName.lastIndexOf(".")));
                imageDto.setSuffix(imageName.substring(imageName.lastIndexOf(".") + 1).replace("?raw=true", ""));
                mdUrlObjects.add(imageDto);
            } catch (Exception e) {
                System.err.println("获取图片对象失败：" + file.getAbsoluteFile());
                System.err.println("tag:" + matcher.group(1));
                e.printStackTrace();
            }
        }
        return mdUrlObjects;
    }

}
