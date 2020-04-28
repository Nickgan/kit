package com.ganbo.kit.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 返回结果集对象
 * Created by gan on 2019/10/6 10:20.
 */
@Data
@NoArgsConstructor
public class ResponseData<T> {

    private Boolean success = true;
    private int code;
    private String message;
    private T data;

    public ResponseData(boolean success, int code, String message, T date) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = date;
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData(true, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> ResponseData<T> success(String message, T data) {
        return new ResponseData(true, ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ResponseData<T> success() {
        return new ResponseData(true, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> ResponseData<T> error(int code, String message) {
        if (StringUtils.isBlank(message)) message = ResultCode.FAILED.getMessage();
        return new ResponseData(false, code, message, null);
    }

    public static <T> ResponseData<T> error(int code, String message, T data) {
        if (StringUtils.isBlank(message)) message = ResultCode.FAILED.getMessage();
        return new ResponseData(false, code, message, data);
    }

    public static <T> ResponseData<T> build(boolean success, IErrorCode e, T data) {
        return new ResponseData(success, e.getCode(), e.getMessage(), data);
    }

    public static <T> ResponseData<T> build(boolean success, IErrorCode e) {
        return new ResponseData(success, e.getCode(), e.getMessage(), null);
    }


}

