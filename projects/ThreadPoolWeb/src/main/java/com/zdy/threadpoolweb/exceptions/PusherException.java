package com.zdy.threadpoolweb.exceptions;

import com.zdy.threadpoolweb.enums.ResultEnum;
import lombok.Data;

@Data
public class PusherException extends RuntimeException {

    private Integer code;

    public PusherException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public PusherException(String message) {
        super(message);
    }

    public PusherException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
