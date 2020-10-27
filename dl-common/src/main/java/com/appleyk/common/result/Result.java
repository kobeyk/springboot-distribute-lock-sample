package com.appleyk.common.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.Date;

public class Result implements Serializable {

    private static final long serialVersionUID = 2719931935414658118L;

    private  Integer status;
    private  String message;

    @JsonInclude(value = Include.NON_NULL)
    private  Object data;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date timeStamp = new Date();

    public Result(Integer status, String message) {
        super();
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public Result(Integer status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }



    /**
     * 成功后直接掉这个
     * @param data
     */
    private Result(Object data){
        super();
        this.status = 200;
        this.message = "成功！";
        this.data = data;
    }

    public static Result ok(Object data){
        return new Result(data);
    }

    public Result() {
        super();
        this.status = null;
        this.message = null;
        this.data = null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

}
