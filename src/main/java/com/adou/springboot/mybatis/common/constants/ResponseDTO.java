package com.adou.springboot.mybatis.common.constants;

import java.io.Serializable;
/**
 * HTTP应答正文对象基类
 * @author zhoudoujun01
 *
 * @param <T>
 * @date 2019年8月6日15:19:47
 */
public class ResponseDTO<T> implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 5455353858801056970L;

    /**
     * 返回码
     */
    private String returnCode;
    /**
     * 返回码描述
     */
    private String returnMsg;
    /**
     * 应答业务节点对象
     */
    private T result;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseDTO [returnCode=" + returnCode + ", returnMsg=" + returnMsg + ", result=" + result + "]";
    }
}
