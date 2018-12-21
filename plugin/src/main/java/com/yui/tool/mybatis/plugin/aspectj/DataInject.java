package com.yui.tool.mybatis.plugin.aspectj;

import com.yui.tool.mybatis.plugin.Runtime.AbstractCreateBy;
import com.yui.tool.mybatis.plugin.Runtime.CreateByClosure;
import com.yui.tool.mybatis.plugin.annotation.CreateBy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 数据拦截
 *
 * @author XuZhuohao
 * @date 2018/12/21
 */
@Aspect
@Component
public class DataInject {
    @CreateBy(target = AbstractCreateBy.class)
    private String c;

    @Around("execution(* insert*(..))")
    public Object doInsertAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("every");
        // 获取调用方法的参数数组
        Object[] args = pjp.getArgs();

        // 循环处理参数
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            // create by
            CreateByClosure.addUser(arg);
        }
        // 继续调用下一个方法，并返回结果
        return pjp.proceed();
    }



}
