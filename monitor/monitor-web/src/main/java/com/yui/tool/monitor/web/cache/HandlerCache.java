package com.yui.tool.monitor.web.cache;

import com.yui.tool.monitor.web.dto.WatcherHandlerInfoDto;
import com.yui.tool.monitor.web.handler.WathcerHandler;
import com.yui.tool.monitor.web.utils.PackageScanner;
import com.yui.tool.monitor.zk.service.WatcherHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XuZhuohao
 * @date 2019/7/30
 */
@Getter
public class HandlerCache {
    private static Map<String, Info> WATCHER_HANDLER_MAP = new HashMap<>(16);
    private static Map<String, WatcherHandlerInfoDto> WATCHER_HANDLER_INFO = new HashMap<>(16);

    @Setter
    @Getter
    @Accessors(chain = true)
    static class Info {
        private Class clazz;
        private WathcerHandler wathcerHandler;
    }

    static {
        try {
            PackageScanner packageScanner = new PackageScanner();
            final List<String> classes = packageScanner.doScan("com.yui.tool.monitor.web.handler");
            for (String temp : classes) {
                final Class<?> clzz = Class.forName(temp);
                final WathcerHandler annotation = clzz.getAnnotation(WathcerHandler.class);
                if (annotation != null) {
                    WATCHER_HANDLER_MAP.put(temp, (new Info()).setClazz(clzz).setWathcerHandler(annotation));
                }
            }
            WATCHER_HANDLER_MAP.forEach((key, value) ->
                    WATCHER_HANDLER_INFO.put(key, new WatcherHandlerInfoDto().setKey(key)
                            .setName(value.getWathcerHandler().name())
                            .setDesc(value.getWathcerHandler().desc()))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WatcherHandlerInfoDto getWatcherHandlerInfo(String key) {
        return WATCHER_HANDLER_INFO.get(key);
    }

    public static Class<? extends WatcherHandler> getWatcherHandler(String key) {
        return WATCHER_HANDLER_MAP.get(key).getClazz();
    }

    public static Map<String, Info> getWatcherHandlerMap() {
        return WATCHER_HANDLER_MAP;
    }

    public static Map<String, WatcherHandlerInfoDto> getWatcherHandlerInfo() {
        return WATCHER_HANDLER_INFO;
    }
}
