package com.adou.springboot.mybatis.common.constants;
/**
 * 共通返回码常量
 * 
 * @author zhoudoujun01
 * @date 2019年8月6日15:19:15
 */
public interface CommResultCode {
    /**
     * 成功
     */
    String SUCCESS = "0000";
    /**
     * 服务器内部错误
     */
    String SYSTEM_ERROR = "0001";
    /**
     * 请求参数非法
     */
    String PARAM_INVALID = "0002";
    /**
     * ak不存在或者非法
     */
    String AK_FAILURE = "0005";

}
