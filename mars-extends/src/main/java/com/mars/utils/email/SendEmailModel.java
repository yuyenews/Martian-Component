package com.mars.utils.email;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件发送对象
 */
public class SendEmailModel {

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 附件列表
     */
    private List<File> files;

    /**
     * 收件箱地址
     */
    private List<String> addressees;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public void addFiles(File file) {
        if(this.files == null){
            this.files = new ArrayList<>();
        }
        this.files.add(file);
    }

    public List<String> getAddressees() {
        return addressees;
    }

    public void setAddressees(List<String> addressees) {
        this.addressees = addressees;
    }

    public void setAddressees(String addressees) {
        if(this.addressees == null){
            this.addressees = new ArrayList<>();
        }
        this.addressees.add(addressees);
    }
}
