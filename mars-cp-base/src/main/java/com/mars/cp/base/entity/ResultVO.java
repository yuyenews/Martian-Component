package com.mars.cp.base.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * api响应对象
 */
public class ResultVO {

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private Map<String,Object> result;

    public static ResultVO successMsg(String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.code = 0;
        resultVO.msg = msg;
        return resultVO;
    }

    public static ResultVO errorMsg(int code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.code = code;
        resultVO.msg = msg;
        return resultVO;
    }

    public static ResultVO success(Object result){
        ResultVO resultVO = successMsg("请求成功");
        resultVO.setResult(result);
        return resultVO;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        if(this.result == null){
            this.result = new HashMap<>();
        }
        this.result.put("data",result);
    }

    public ResultVO addResult(String key,Object result){
        if(this.result == null){
            this.result = new HashMap<>();
        }
        this.result.put(key,result);
        return this;
    }
}
