package com.zdy.threadpoolweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PushTask implements Serializable {

    private static final long serialVersionUID = 1095351988501724233L;

    /**
     * PushTask的id，需要保持唯一性
     */
    @JsonProperty("id")
    private String Id = "";


}
