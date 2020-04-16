package com.mars.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 *
 * @author yuye
 */
public class MarsFileUtil {

    private static Logger logger = LoggerFactory.getLogger(MarsFileUtil.class);// 日志

    /**
     * 根据文件路径 获取文件中的字符串内容
     *
     * @param path 路径
     * @return str
     */
    public static String readTxtFile(String path) {

        try {
            InputStream inputStream = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("读取txt文件的内容出错", e);
        }
        return null;
    }

    /**
     * 往txt写入字符串内容
     *
     * @param txt  内容
     * @param path 文件路径
     */
    public static void writeTxtFile(String txt, String path) {
        try {
            OutputStream outputStream = new FileOutputStream(createTxtFile(path));
            outputStream.write(txt.getBytes("utf-8"));
        } catch (Exception e) {
            logger.error("写入文件出错", e);
        }
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return 数据
     * @throws Exception 异常
     */
    public static boolean deleteFile(String path) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    /**
     * 创建文件
     *
     * @param path 路径
     * @return 创建的文件流
     * @throws Exception 异常
     */
    public static File createTxtFile(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 将file转化成二进制流
     *
     * @param file 文件流
     * @return 转化后的二进制流
     */
    public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            logger.error("File转化成byte[]报错", ex);
        }

        return by;
    }

    /**
     * 将InputStream转化成二进制流
     *
     * @param inStream InputStream
     * @return 二进制流
     */
    public static byte[] getInputStreamToByte(InputStream inStream) {
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] in2b = swapStream.toByteArray();
            return in2b;
        } catch (Exception e) {
            logger.error("InputStream转化成byte[]报错", e);
            return null;
        }

    }

    /**
     * 将BufferedImage转化成二进制流
     *
     * @param bufferedImage BufferedImage
     * @return 二进制流
     */
    public static byte[] getBufferedImageToByte(BufferedImage bufferedImage) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "gif", out);
            byte[] b = out.toByteArray();
            return b;
        } catch (Exception e) {
            logger.error("BufferedImage转化成byte[]报错", e);
            return null;
        } finally {
            try {
                out.close();
            } catch (Exception e) {
            }
        }

    }

    /**
     * 根据File 获取此路径下所有的文件，包含子目录
     * @param filePath
     * @return
     */
    public static List<File> getAllFileForPath(File filePath){
        List<File> list = new ArrayList<>();
        return getAllFilePaths(filePath,list);
    }

    /**
     * 根据File 获取此路径下所有的文件，包含子目录
     * @param filePath 要读的目录
     * @param filePaths 返回的文件
     * @return
     */
    private static List<File> getAllFilePaths(File filePath, List<File> filePaths) {
        File[] files = filePath.listFiles();
        if (files == null) {
            return filePaths;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                getAllFilePaths(f, filePaths);
            } else {
                filePaths.add(f);
            }
        }
        return filePaths;
    }
}
