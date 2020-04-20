package com.mars.utils;

import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性文件工具类
 */
public class MarsPropertyUtil {

    private static Logger logger = LoggerFactory.getLogger(MarsPropertyUtil.class);// 日志

    private MarsPropertyUtil(){ }

    /**
     * 获取对象
     * @param path 路径
     * @return 数据
     */
    public static Properties getPropertyUtils(String path){
        return loadProperty(path);
    }

    /**
     * 加载属性文件
     * @param path 路径
     * @return  数据
     */
    private static Properties loadProperty(String path){
        try {
            Properties pop = new Properties();

            InputStream inputStream = MarsFileUtil.class.getResourceAsStream(path);
            pop.load(inputStream);

            return pop;
        } catch (Exception e){
            logger.error("加载属性文件报错", e);
            return null;
        }
    }
}
