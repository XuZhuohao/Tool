//package com.yui.tool.mybatis.plugin.aspectj;
//
//
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//import java.util.Date;
//
///**
// * 公用数据插入切面
// *
// * @author XuZhuohao
// * @date 2018/12/14
// */
////@Aspect
//@Component
//public class CommonDataInject {
//    /**
//     * 拦截 Mapper 的 insert 方法，如果 createBy，createTime 为空时自动进行赋值
//     * createBy shiro 的登陆用户， createTime 为当前用户
//     * @param pjp 暴露对象
//     * @return 调用结果
//     * @throws Throwable Throwable
//     */
//    @Around("execution(* com.bluemoon.finenter.invoice.mapper.*Mapper.insert*(..))")
//    public Object doInsertAround(ProceedingJoinPoint pjp) throws Throwable {
//        // 获取调用方法的参数数组
//        Object[] args = pjp.getArgs();
//        // 获取 shiro 的主要对象
//        final Object principal = SecurityUtils.getSubject().getPrincipal();
//        // 循环处理参数
//        for (int i = 0; i < args.length; i++) {
//            Object arg = args[i];
//            // 校验并补全 createBy from principal.getUserName())
//            addValueToProperty("createBy",arg,((SysUser)principal).getUserName());
//            // 校验并补全 createTime = now time
//            addValueToProperty("createTime",arg, new Date());
//        }
//        // 继续调用下一个方法，并返回结果
//        return pjp.proceed();
//    }
//    /**
//     * 拦截 Mapper 的 insert 方法，如果 createBy，createTime 为空时自动进行赋值
//     * updateBy shiro 的登陆用户， updateTime 为当前用户
//     * @param pjp 暴露对象
//     * @return 调用结果
//     * @throws Throwable Throwable
//     */
//    @Around("execution(* com.bluemoon.finenter.invoice.mapper.*Mapper.update*(..))")
//    public Object doUpdateAround(ProceedingJoinPoint pjp) throws Throwable {
//        Object[] args = pjp.getArgs();
//        final Object principal = SecurityUtils.getSubject().getPrincipal();
//        for (int i = 0; i < args.length; i++) {
//            Object arg = args[i];
//            addValueToProperty("updateBy",arg,((SysUser)principal).getUserName());
//            addValueToProperty("updateTime",arg, new Date());
//        }
//        return pjp.proceed();
//    }
//
//    private void addValueToProperty(String propertyName, Object object, Object value){
//        // 首字母大写
//        propertyName = propertyName.toUpperCase().substring(0,1) + propertyName.substring(1);
//        try {
//            // 获取 get 方法
//            Method getMethod = object.getClass().getMethod("get" + propertyName);
//            // 获取 set 方法
//            Method setMethod = object.getClass().getMethod("set" + propertyName, value.getClass());
//            // 如果 get 不到值， 将 value set 进去
//            if (getMethod.invoke(object) == null){
//                setMethod.invoke(object, value);
//            }
//        } catch (Exception e) {
//            // 异常时处理位
//            // 可能发生异常：
//            //      1.找不到对应的 set / get 方法；
//            //      2.对应的 set 方法，入参类型与 value 类型不符合
//            //      3.没有对应的属性
//            e.printStackTrace();
//        }
//    }
//}
