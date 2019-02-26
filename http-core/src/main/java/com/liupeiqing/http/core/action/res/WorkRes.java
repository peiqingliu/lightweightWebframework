package com.liupeiqing.http.core.action.res;

/**
 * @author liupeqing
 * @date 2019/2/25 14:46
 */
public class WorkRes<T> {

    private String code;
    private String message;

    T dataBody;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getDataBody() {
        return dataBody;
    }

    public void setDataBody(T dataBody) {
        this.dataBody = dataBody;
    }

}
