package com.liupeiqing.http.core.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态码
 * @author liupeqing
 * @date 2019/2/25 11:49
 */
public enum StatusEnum {

    /**
     * 枚举实例
     */
    SUCCESS("9000","success"),
    REQUEST_ERROR("7000","request_error"),
    DUPLICATE_IOC("8000","Duplicate ioc impl erro"),
    NULL_PACKAGE("8000","Your main class is empty of package"),
    /** 404 */
    NOT_FOUND("404", "Need to declare a method by using @CicadaRoute!"),
    /** IllegalArgumentException
     * 非法数据异常
     *  或者无法通过一个标识或基本扩展转换将指定值转换为基础数组的指定类型
     *  如果位置描述为空
     *  如果指定对象参数不是一个数组
     *
     */
    ILLEGAL_PARAMETER("404", "IllegalArgumentException: You can only have two parameters at most by using @CicadaRoute!");


    private final String code;
    private final String message;
    private StatusEnum(String code,String message){
        this.code = code;
        this.message = message;
    }
    /**
     * 得到枚举值码。
     * @return 枚举值码。
     */
    public String getCode() {
        return code;
    }

    /**
     * 得到枚举描述。
     * @return 枚举描述。
     */
    public String getMessage() {
        return message;
    }

    /**
     * 得到枚举值码。
     * @return 枚举值码。
     */
    public String code() {
        return code;
    }

    /**
     * 得到枚举描述。
     * @return 枚举描述。
     */
    public String message() {
        return message;
    }

    public static StatusEnum findStatus(String code){
        for (StatusEnum statusEnum : values()){
            if (statusEnum.getCode().equals(code)){
                return statusEnum;
            }
        }
        throw new IllegalArgumentException("ResultInfo StatusEnum not legal:" + code);
    }

    /**
     * 获取全部的枚举值
     * @return
     */
    public static List<StatusEnum> getAllStatus(){
        List<StatusEnum> statusEnums = new ArrayList<>();
        for (StatusEnum statusEnum : values()){
            statusEnums.add(statusEnum);
        }
        return statusEnums;
    }

    /**
     * 获取全部的枚举值码
     * @return
     */
    public static List<String> getAllStatusCode(){
        List<String> codes = new ArrayList<>();
        for (StatusEnum statusEnum : values()){
            codes.add(statusEnum.getCode());
        }
        return codes;
    }
}
