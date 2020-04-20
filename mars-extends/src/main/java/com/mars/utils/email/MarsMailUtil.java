package com.mars.utils.email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 邮件工具类
 */
public class MarsMailUtil {

    private static Logger logger = LoggerFactory.getLogger(MarsMailUtil.class);// 日志

    /**
     * 连接协议
     */
    private static String protocol = "smtp";
    /**
     * 主机名
     */
    private static String host;
    /**
     * 端口号
     */
    private static String port;
    /**
     * 设置是否使用ssl安全连接 ---一般都使用
     */
    private static String smtpSslEnable;
    /**
     * 设置是否显示debug信息 true 会在控制台显示相关信息
     */
    private static String debug;
    /**
     * 发件箱
     */
    private static String sendMail;
    /**
     * 发件箱密码
     * 邮箱开通的stmp服务后得到的客户端授权码
     */
    private static String sendMailPwd;

    private static String auth;

    /**
     * 发送邮件
     * @param sendEmailModel 发送对象
     */
    public static void sendMail(SendEmailModel sendEmailModel){

        Transport transport = null;
        try{
            // 得到回话对象
            Session session = getSession();
            // 获取邮件对象
            Message message = getMessage(sendEmailModel,session);

            // 得到邮差对象
            transport = session.getTransport();
            // 连接自己的邮箱账户
            transport.connect(sendMail, sendMailPwd);
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            logger.error("发送邮件报错", e);
        } finally {
            try{
                if(transport != null){
                    transport.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 发送附件
     * @param multipart 发送邮件的对象
     * @param files 附件集合
     * @throws Exception 异常
     */
    private static void sendFile(Multipart multipart,List<File> files) throws Exception {
        if (null != files && files.size() > 0) {
            for (File file : files) {
                BodyPart attchmentPart = new MimeBodyPart();
                // 设置要发送附件的文件路径
                DataSource source = new FileDataSource(file);
                attchmentPart.setDataHandler(new DataHandler(source));
                // 处理附件名称中文（附带文件路径）乱码问题
                attchmentPart.setFileName(MimeUtility.encodeText(file.getName()));
                multipart.addBodyPart(attchmentPart);
            }
        }
    }

    /**
     * 获取消息对象
     * @param sendEmailModel 发送对象
     * @param session 会话
     * @return 消息对象
     * @throws Exception 异常
     */
    private static Message getMessage(SendEmailModel sendEmailModel,Session session) throws Exception {
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress(sendMail));
        // 设置收件人邮箱地址
        message.setRecipients(Message.RecipientType.TO, getInternetAddress(sendEmailModel.getAddressees()));
        // 设置邮件标题
        message.setSubject(sendEmailModel.getTitle());

        // 创建多重消息
        Multipart multipart = getMultipart(sendEmailModel);

        // 发送完整消息
        message.setContent(multipart);
        return message;
    }

    /**
     * 获取Multipart
     * @param sendEmailModel 发送对象
     * @return Multipart
     * @throws Exception 异常
     */
    private static Multipart getMultipart(SendEmailModel sendEmailModel) throws Exception {
        // 创建多重消息
        Multipart multipart = new MimeMultipart();

        // 设置邮件内容
        MimeBodyPart mbpContent = new MimeBodyPart();
        mbpContent.setContent(sendEmailModel.getContent(),"text/html;charset=utf-8");

        multipart.addBodyPart(mbpContent);

        // 添加附件
        sendFile(multipart,sendEmailModel.getFiles());

        return multipart;
    }

    /**
     * 获取session
     * @return session
     */
    private static Session getSession(){
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", protocol);// 连接协议
        properties.put("mail.smtp.host", host);// 主机名
        properties.put("mail.smtp.port", port);// 端口号
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.ssl.enable", smtpSslEnable);// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", debug);// 设置是否显示debug信息 true 会在控制台显示相关信息
        return Session.getInstance(properties);
    }


    /**
     * 获取收件方邮箱对象
     * @param internetAddress 收件箱
     * @return 数组
     */
    private static InternetAddress[] getInternetAddress(List<String> internetAddress){
        try{
            InternetAddress[] internetAddressList = new InternetAddress[internetAddress.size()];

            for(int i = 0;i<internetAddress.size();i++){
                internetAddressList[i] = new InternetAddress(internetAddress.get(i));
            }

            return internetAddressList;
        } catch (Exception e){
            logger.error("获取收件方邮箱对象报错", e);
            return null;
        }
    }

    public static void setProtocol(String protocol) {
        MarsMailUtil.protocol = protocol;
    }

    public static void setHost(String host) {
        MarsMailUtil.host = host;
    }

    public static void setPort(String port) {
        MarsMailUtil.port = port;
    }

    public static void setSmtpSslEnable(String smtpSslEnable) {
        MarsMailUtil.smtpSslEnable = smtpSslEnable;
    }

    public static void setDebug(String debug) {
        MarsMailUtil.debug = debug;
    }

    public static void setSendMail(String sendMail) {
        MarsMailUtil.sendMail = sendMail;
    }

    public static void setSendMailPwd(String sendMailPwd) {
        MarsMailUtil.sendMailPwd = sendMailPwd;
    }

    public static void setAuth(String auth) {
        MarsMailUtil.auth = auth;
    }
}
