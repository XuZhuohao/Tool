package com.yui.tool.dynamicdubbo.service.impl;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.yui.tool.dynamicdubbo.cache.DubboServiceCache;
import com.yui.tool.dynamicdubbo.config.MavenConfig;
import com.yui.tool.dynamicdubbo.dto.Dependency;
import com.yui.tool.dynamicdubbo.dto.DubboIntefaceInfo;
import com.yui.tool.dynamicdubbo.dto.MethodInfo;
import com.yui.tool.dynamicdubbo.dto.ResultBean;
import com.yui.tool.dynamicdubbo.service.DubboService;
import com.yui.tool.dynamicdubbo.utils.FileUtils;
import com.yui.tool.dynamicdubbo.utils.JarUtils;
import com.yui.tool.dynamicdubbo.utils.MavenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author XuZhuohao
 * @date 2020/1/3
 */
@Service
public class DubboServiceImpl implements DubboService {

    @Autowired
    private ReferenceConfig<GenericService> reference;
    @Autowired
    private MavenConfig mavenConfig;
    /**
     * TODO:数据库主键id模拟
     */
    private static AtomicInteger ID = new AtomicInteger(0);
    private static AtomicInteger METHOD_ID = new AtomicInteger(0);

    @Override
    public ResultBean<List<DubboIntefaceInfo>> addDubboApi(String dependency) {
        final List<Dependency> dependencies = MavenUtils.analysisDependencies(dependency);
        try {
            // TODO: 先检查缓存（数据库） 是否已经加载过该依赖
            String path = FileUtils.getNewDirectory(mavenConfig.getDownloadPath());
            String pomPath = path + File.separator + "download.xml";
            MavenUtils.downloadDependency(dependencies, pomPath, mavenConfig.getSettingFile());

            final File file = new File(path + File.separator + "target" + File.separator + "dependency");
            final JarUtils jarUtils = new JarUtils();
            final File[] files = file.listFiles();
            if (files == null) {
                return new ResultBean<List<DubboIntefaceInfo>>().toFail("添加api异常：文件不存在");
            }
            List<DubboIntefaceInfo> dubboIntefaceInfos = new ArrayList<>(16);
            jarUtils.loadJars(Arrays.asList(files));
            for (File jarFile : files) {
                for (Dependency dependencyTemp : dependencies) {
                    if (jarFile.getName().startsWith(dependencyTemp.getArtifactId())) {
                        dubboIntefaceInfos.addAll(jarUtils.getDubboInterfaceInfo(jarFile));
                    }
                }
            }
            // TODO:删除下载的jar
            // TODO:保存到数据库增加主键参数
            dubboIntefaceInfos.forEach(dubboIntefaceInfo -> {
                dubboIntefaceInfo.setId(ID.getAndIncrement());
                dubboIntefaceInfo.getMethodInfo().forEach(methodInfo -> methodInfo.setId(METHOD_ID.getAndIncrement()));
            });


            // 添加到缓存中
            dubboIntefaceInfos.forEach(DubboServiceCache::addDubboInteface);
            return new ResultBean<List<DubboIntefaceInfo>>().toSuccess(dubboIntefaceInfos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean<List<DubboIntefaceInfo>>().toFail("异常提示：" + e.getLocalizedMessage());
        }
    }

    @Override
    public ResultBean<String> invoke(String interfaceName, String method, String version, Map<String, Object> inMap) {
        return null;
    }

    @Override
    public ResultBean<String> invoke(String id, String version, Map<String, Object> inMap) {
        MethodInfo methodInfo = DubboServiceCache.getCacheMethod().get(Integer.parseInt(id));
        // 弱类型接口名
        reference.setInterface(methodInfo.getClassName());
        reference.setVersion(version);
        // 声明为泛化接口
        reference.setGeneric("true");

        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

        /// 基本类型以及Date,List,Map等不需要转换，直接调用
//        List<Object> paramsList = new ArrayList<>(16);
//        for (String key : methodInfo.getParamType()) {
//            paramsList.add(inMap.get(key));
//        }
//        Object result = genericService.$invoke(methodInfo.getName(), methodInfo.getParamType(), paramsList.toArray());

        // 用Map表示POJO参数，如果返回值为POJO也将自动转成Map
        // 如果返回POJO将自动转成Map
        Object result = genericService.$invoke(methodInfo.getName(), methodInfo.getParamType(), new Object[]{inMap});

        return new ResultBean<String>().toSuccess(JSON.toJSONString(result));
    }
}
