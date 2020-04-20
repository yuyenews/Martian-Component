package com.yuyenews.cp.core.vo;

/**
 * 请求成功返回
 */
public class ResultVO {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object result;

    public void setResult(Object result){
        this.result = result;
    }

    /**
     * 请求成功返回相应数据
     * @param result 数据
     * @return 数据
     */
    public static ResultVO success(Object result){
        ResultVO resultVO = successMsg("请求成功");
        resultVO.setResult(result);
        return resultVO;
    }

    /**
     * 提示请求成功，但是不返回数据
     * @param msg 信息
     * @return 数据
     */
    public static ResultVO successMsg(String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.code=0;
        resultVO.msg=msg;
        return resultVO;
    }

    /**
     * 提示请求失败
     * @param code 状态码
     * @param msg 信息
     * @return 数据
     */
    public static ResultVO errorMsg(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.code=code;
        resultVO.msg=msg;
        return resultVO;
    }
}
