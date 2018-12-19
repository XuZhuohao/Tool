# md文件中图片转义
## 1.流程
遍历文件夹 -> 获取所有 ![]() -> 通过 url 进行下载图片，保存到指定目录 -> 修改 md 文件中的 url 为对应的规则生成新的 url -> 手工发布下载的图片  
## 2.修改图片下载时的 url 规则(源代码中以 github 为例)
com.yui.tool.imgremove.core.impl.MdFileImpl#getImageDtoInMdFile  
## 3.修改图片生成的 url 规则(源代码中以 github 为例),和本地保存目录
com.yui.tool.imgremove.core.impl.MdFileImpl#dealWithImageDto 中修改 setNewUrl 的值的构成，生成新的 url  
## 4.以 github 为例(这里将图片，转移到 github 上的另一个项目下)

**修改保存的目录结构(这里目录结构与md文件目录结构保存一直)**
```
（com.yui.tool.imgremove.core.impl.MdFileImpl#dealWithImageDto）
 imageDto.setPath(filePath.replace(path, IMG_ROOT_PATH) + File.separator + imageName);
```

**生成新的 url 规则**  
```
（com.yui.tool.imgremove.core.impl.MdFileImpl#dealWithImageDto）
imageDto.setNewUrl("https://raw.githubusercontent.com/XuZhuohao/picture/master" + filePath + "/" + imageName);
```


**修改下载时的 url 规则（d对获取后的 url 做格式化如下）**  
```
（com.yui.tool.imgremove.core.impl.MdFileImpl#getImageDtoInMdFile  ）
url = url.replace("github.com", "raw.githubusercontent.com")
                        .replace("www.","")
                        .replace("/raw/","/")
                        .replace("?raw=true", "")
                        .replace("/blob/","/");
```
