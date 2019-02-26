package com.liupeiqing.http.core.action.req;

/**
 * @author liupeqing
 * @date 2019/2/25 14:47
 */
public class WorkReq {

    private Integer timeStamp;

    public Integer getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Integer timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "WorkReq{" +
                "timeStamp=" + timeStamp +
                '}';
    }
}
