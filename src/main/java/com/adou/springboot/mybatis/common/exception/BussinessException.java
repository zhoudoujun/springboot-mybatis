package com.adou.springboot.mybatis.common.exception;

/**
 * 自定义业务异常，只可以使用在业务相关的代码内。
 * 
 * @author zhoudoujun01
 * @date 2019年8月26日15:26:46
 */
public class BussinessException extends RuntimeException {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = -2309944573679313296L;

    /**
     * 构造函数
     * 
     * @param status 业务结果码，例如：1001
     */
    public BussinessException(String status) {
        this.status = status;
    }

    /**
     * 构造函数
     * 
     * @param status 业务结果码，例如：1001
     * @param args 结果码对应的消息的替换参数
     */
    public BussinessException(String status, Object... args) {
        this.status = status;
        this.args = args;
    }

    /**
     * 状态码
     */
    private String status = null;

    /**
     * 状态消息的替换参数
     */
    private Object[] args = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

}
