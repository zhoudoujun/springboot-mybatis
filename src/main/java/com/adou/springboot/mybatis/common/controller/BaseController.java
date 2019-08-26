package com.adou.springboot.mybatis.common.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.adou.springboot.mybatis.common.constants.CommResultCode;
import com.adou.springboot.mybatis.common.constants.ResponseDTO;
import com.adou.springboot.mybatis.common.exception.ParamCheckException;


/**
 * 
 * 控制层基类
 * 
 * @author wangzheng
 * 
 */
public abstract class BaseController {
    /**
     * 日志相关
     */
    protected static Logger LOG = LoggerFactory.getLogger(BaseController.class);

    /**
     * 消息资源对象
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * 成功时返回消息对象。
     * 
     * @param data 业务消息体对象
     * @param T 业务消息体类定义
     * @return 应答结果对象
     */
    public <T> ResponseDTO<T> onSuccess(T data) {
        ResponseDTO<T> out = new ResponseDTO<T>();

        out.setReturnCode(CommResultCode.SUCCESS);
        out.setReturnMsg(messageSource.getMessage(CommResultCode.SUCCESS, null, LocaleContextHolder.getLocale()));
        out.setResult(data);
        return out;
    }

    /**
     * 业务失败时返回错误失败对象。
     * 
     * @param resultCode 结果码
     * @return 应答结果对象
     */
    public ResponseDTO<?> onFailedBiz(String resultCode) {
        ResponseDTO<?> out = new ResponseDTO();

        out.setReturnCode(resultCode);
        out.setReturnMsg(messageSource.getMessage(resultCode, null, LocaleContextHolder.getLocale()));
        out.setResult(null);
        return out;
    }
    
    /**
     * 业务失败时返回错误失败对象+data
     * 
     * @param resultCode 结果码
     * @param data 错误描述
     * @return 应答结果对象
     */
    
    public ResponseDTO onFailedBiz(String resultCode, String data) {
        ResponseDTO<String> out = new ResponseDTO();

        out.setReturnCode(resultCode);
        out.setReturnMsg(messageSource.getMessage(resultCode, null, LocaleContextHolder.getLocale()));
        out.setResult(data);
        return out;
    }

    /**
     * 业务失败时返回错误失败对象。
     * 
     * @param resultCode 结果码
     * @param msgArgs 返回信息内置参数
     * @return 应答结果对象
     */
    public ResponseDTO<?> onFailedBiz(String resultCode, Object[] msgArgs) {
        ResponseDTO<?> out = new ResponseDTO();

        out.setReturnCode(resultCode);
        out.setReturnMsg(messageSource.getMessage(resultCode, msgArgs, LocaleContextHolder.getLocale()));
        out.setResult(null);
        return out;
    }
    
    /**
     * 时间类型的参数转化绑定
     * @param binder 数据绑定器
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new MyDateEditor());
    }

    /**
     * 默认的时间转化类。支持以下格式的字符串转化为Date类。
     * <li>yyyy-MM-dd HH:mm:ss</li>
     * <li>yyyy-MM-dd HH:mm</li>
     * <li>yyyy-MM-dd</li>
     * 
     * @author wangzheng
     *
     */
    class MyDateEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            if (text == null || text.equals("")) {
                setValue(date);
                return;
            }
            try {
                date = format.parse(text);
            } catch (ParseException e) {
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    date = format.parse(text);
                } catch (ParseException e1) {
                    format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        date = format.parse(text);
                    } catch (ParseException e2) {
                        throw new ParamCheckException("comm.constraints.AssertDate.message", text);
                    }
                }
            }
            setValue(date);
        }
    } 

}
