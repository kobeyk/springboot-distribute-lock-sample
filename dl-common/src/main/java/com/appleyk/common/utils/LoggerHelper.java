package com.appleyk.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerHelper {

    private static Logger gxLogger = LoggerFactory.getLogger(LoggerHelper.class);
    public static void info(String message){
        gxLogger.info(message);
    }
    public static void debug(String message){
        gxLogger.debug(message);
    }
    public static void error(String message,Exception ex){
        gxLogger.error(message,ex);
    }
    public static void error(Integer errCode,String message){
        gxLogger.error("错误码："+errCode+"，错误消息："+message);
    }
    public static void error(String message){
        gxLogger.error("错误消息："+message);
    }
    public static void error(Integer errCode,String message,Exception ex){
        gxLogger.error("错误码："+errCode+"，错误消息："+message+",异常信息："+ex.getMessage());
    }

}
