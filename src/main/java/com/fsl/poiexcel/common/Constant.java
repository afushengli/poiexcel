package com.fsl.poiexcel.common;

public class Constant {

    // 过期删除时间
    public static final  Integer EXPIRE_DELETE_TIME = 10 * 60;


    //public static final String RBBIT_MQ_HOST="192.168.189.51";
    public static final String RBBIT_MQ_HOST="127.0.0.1";
    public static final int RBBIT_MQ_POSRT=5672;
    public static final String RBBIT_MQ_USERNAME="guest";
    public static final String RBBIT_MQ_PASSWORD="guest";
    public static final String FILE_PATH="C:/poiexcel_app/xlsx/";
    //public static final String FILE_PATH="/Users/a123/Desktop";


    //审批流程模板所在的相对路径
    public static final String PROCESS_TEMP_FILE_PATH="C:/poiexcel_app/xlsx/";
    //创建流程后的excel所在的路径
    public static final String PROCESS_FILE_PATH = "C:/poiexcel_app/xlsx/";

    //public static final String PROCESS_TEMP_FILE_PATH="/Users/a123/";
    // public static final String PROCESS_FILE_PATH = "/Users/a123/Desktop";

    public interface Redis {
        String OK = "OK";
        Integer EXPIRE_TIME_MINUTE = 60;// 过期时间, 60s, 一分钟
        Integer EXPIRE_TIME_HOUR = 60 * 60;// 过期时间, 一小时
        Integer EXPIRE_TIME_DAY = 60 * 60 * 24;// 过期时间, 一天
        String TOKEN_PREFIX = "token:";
        String MSG_CONSUMER_PREFIX = "consumer:";
        String ACCESS_LIMIT_PREFIX = "accessLimit:";
    }

    public interface LogType {
        Integer LOGIN = 1;// 登录
        Integer LOGOUT = 2;// 登出
    }

    public interface MsgLogStatus {
        Integer SENDING = 0;
        Integer SUCCESS = 1;
        Integer FAIL = 2;
    }



}
