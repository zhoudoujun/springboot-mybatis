package com.adou.springboot.mybatis.common.exception;

/**
 * 自定义业务参数异常，只可以使用在业务相关的代码内。
 * 
 * @author zhoudoujun01
 * @date 2019年8月26日15:24:19
 */
public class ParamCheckException extends RuntimeException {
    
    /**
     * 无参的错误消息标识
     */
    private static final String DEFALUT_MESSAGEID_NOPARAM = "comm.constraints.ParamError.message";

    /**
     * 有参的错误消息标识
     */
    private static final String DEFALUT_MESSAGEID_HADPARAM = "comm.constraints.ParamError.full.message";

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = -2309944573679313296L;
    
    /**
     * 构造函数。
     * 将使用默认的错误消息标识寻找错误信息
     */
    public ParamCheckException() {
        this.messageId = DEFALUT_MESSAGEID_NOPARAM;
    }

    /**
     * 构造函数。
     * 将使用默认的错误消息标识寻找错误信息，并且将错误的参数属性名和值显示出来。
     * 
     * @param propertiesName 参数属性名
     * @param value 参数值
     */
    public ParamCheckException(String propertiesName, Object value) {
        this(DEFALUT_MESSAGEID_HADPARAM, propertiesName, value);
    }
    
    /**
     * 构造函数
     * 
     * @param messageId 错误提示消息的标识，例如：constraints.Range.message.notmatch
     */
    public ParamCheckException(String messageId) {
        this.messageId = messageId;
    }

    /**
     * 构造函数
     * 如果想把详细错误信息如参数属性名、参数值等传递至外层，请使用此构造函数。</br>
     * 自定义的消息内容格式如：参数{0}值为{1}，只能为“A”“B”“C”其中之一。
     * 
     * @param messageId 错误提示消息的标识错误提示消息的标识，例如：constraints.Range.message.notmatch
     * @param args 消息的替换参数
     */
     public ParamCheckException(String messageId, Object... args) {
        this.messageId = messageId;
        this.args = args;
    }

    /**
     * 异常提示消息标识
     */
    private String messageId = null;

    /**
     * 异常提示消息的替换参数
     */
    private Object[] args = null;

    public String getMessageId() {
        return messageId;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

}
