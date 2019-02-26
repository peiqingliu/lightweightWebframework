package com.liupeiqing.http.core.exception;

import com.liupeiqing.http.core.enums.StatusEnum;

/**
 * @author liupeqing
 * @date 2019/2/25 11:47
 */
public class HttpException extends GenericException {

    public HttpException(String errorCode,String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HttpException(Exception e, String errorCode, String errorMessage) {
        super(e, errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HttpException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public HttpException(StatusEnum statusEnum) {
        super(statusEnum.getMessage());
        this.errorMessage = statusEnum.message();
        this.errorCode = statusEnum.getCode();
    }

    public HttpException(StatusEnum statusEnum, String message) {
        super(message);
        this.errorMessage = message;
        this.errorCode = statusEnum.getCode();
    }

    public HttpException(Exception oriEx) {
        super(oriEx);
    }

    public HttpException(Throwable oriEx) {
        super(oriEx);
    }

    public HttpException(String message, Exception oriEx) {
        super(message, oriEx);
        this.errorMessage = message;
    }

    public HttpException(String message, Throwable oriEx) {
        super(message, oriEx);
        this.errorMessage = message;
    }

    public static boolean isResetByPeer(String msg) {
        if ("Connection reset by peer".equals(msg)) {
            return true;
        }
        return false;
    }
}
