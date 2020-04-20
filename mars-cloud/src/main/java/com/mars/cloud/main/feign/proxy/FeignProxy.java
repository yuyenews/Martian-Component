package com.mars.cloud.main.feign.proxy;

import com.mars.cloud.main.core.annotation.MarsFeign;
import com.mars.cloud.main.rest.request.MarsRestTemplate;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 用于实现RPC的代理类
 */
public class FeignProxy implements MethodInterceptor {

    private Enhancer enhancer;

    private Class<?> cls;

    /**
     * 获取代理对象
     * @param clazz  bean的class
     * @return 对象
     */
    public Object getProxy(Class<?> clazz) {
        cls = clazz;

        enhancer = new Enhancer();
        // 设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        // 通过字节码技术动态创建子类实例
        return enhancer.create();
    }


    /**
     * 绑定代理
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        MarsFeign marsFeign = cls.getAnnotation(MarsFeign.class);

        check(marsFeign,method);

        Object param = getParam(args);

        return MarsRestTemplate.request(marsFeign.serverName(),method.getName(),param, method.getReturnType());
    }

    /**
     * 校验配置是否符合规则
     * @param marsFeign 注解
     * @param method 方法
     * @throws Exception 异常
     */
    private void check(MarsFeign marsFeign, Method method) throws Exception {
        if(marsFeign.serverName() == null){
            throw new Exception("接口上MarsFeign注解的serverName为空:["+cls.getName()+"."+method.getName()+"]");
        }
    }

    /**
     * 获取请求参数
     * @param args 参数
     * @return 参数
     * @throws Exception 异常
     */
    private Object getParam(Object[] args) throws Exception {
        if(args != null && args.length > 0){
            if (args.length > 1){
                throw new Exception("Feign的方法只允许有一个参数");
            }
            return args[0];
        }
        return null;
    }
}
