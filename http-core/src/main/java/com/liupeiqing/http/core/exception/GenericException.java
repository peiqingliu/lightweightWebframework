package com.liupeiqing.http.core.exception;

import java.io.Serializable;

/**
 * 自定义异常
 * @author liupeqing
 * @date 2019/2/25 11:42
 */
public class GenericException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    String errorCode;
    String errorMessage;

    public GenericException(){}

    public GenericException(String errorMessqge){
        super(errorMessqge);
    }

    public GenericException(Exception e){
        super(e);
    }
    public GenericException(Exception oriEx, String message) {
        super(message, oriEx);
    }
    public GenericException(Throwable oriEx) {
        super(oriEx);
    }

    public GenericException(String message, Exception oriEx) {
        super(message, oriEx);
    }

    public GenericException(String message, Throwable oriEx) {
        super(message, oriEx);
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
