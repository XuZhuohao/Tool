package com.yui.tool.dynamicdubbo.controller;

import com.yui.tool.dynamicdubbo.dto.DubboIntefaceInfo;
import com.yui.tool.dynamicdubbo.dto.ResultBean;
import com.yui.tool.dynamicdubbo.service.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author XuZhuohao
 * @date 2020-03-04 15:52
 */
@RestController
@RequestMapping("dubbo")
public class DubboController {
    @Autowired
    private DubboService dubboService;

    @PostMapping("add")
    public ResultBean<List<DubboIntefaceInfo>> add(@RequestBody String dependency) {
        return dubboService.addDubboApi(dependency);
    }

    @PostMapping("invoke/{id}/{version}")
    public ResultBean<String> invoke(@PathVariable String id, @PathVariable String version, @RequestBody Map<String, Object> inMap) {
        return dubboService.invoke(id,version,inMap);
    }
}
