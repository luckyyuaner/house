package com.yuan.house.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    private static Logger debugLogger = LoggerFactory.getLogger("debug");
    private static Logger infoLogger = LoggerFactory.getLogger("info");
    private static Logger errorLogger = LoggerFactory.getLogger("error.do");

    /**
     * 记录调试阶段的debug日志
     *
     * @param message
     */
    public static void debug(String message) {
        debugLogger.debug(message);
    }
    public static void debug(String message,Object... arguments) {
        debugLogger.debug(message,arguments);
    }

    /**
     * 记录业务中用到的info级别日志
     *
     * @param message
     */
    public static void info(String message) {
        infoLogger.info(message);
    }

    public static void info(String format, Object... arguments) {
        infoLogger.info(format, arguments);
    }

    /**
     * 记录业务中可能出现的错误情况
     *
     * @param log
     */
    public static void error(String log) {
        errorLogger.error(log);
    }

    public static void error(String format, Object... arguments) {
        errorLogger.error(format, arguments);
    }



    /**
     * 记录业务中出现的异常
     *
     * @param message
     * @param e
     */
    public static void exception(String message, Throwable e) {
        errorLogger.error(message, e);
    }

    /**
     * 记录业务中出现的异常，不带字符串描述的简略版
     *
     * @param e
     */
    public static void exception(Throwable e) {
        errorLogger.error("", e);
    }

    public static void exception(String format, Object... arguments) {
        errorLogger.error(format, arguments);
    }

    /**
     * dubbo接口的输入输出参数，及返回值情况。 该日志一般不需要我们在业务逻辑中调用，该日志会配置在filter中
     *
     * @param message
     */
//    public static void logBusiness(String message) {
//        businessLogger.info(message);
//    }


}
