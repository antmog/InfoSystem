package com.infosystem.springmvc.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class EditUserDto implements Serializable {
    private Integer userId;
    @NotNull
    private String dataInstance;
    @NotNull
    private String value;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public EditUserDto(String dataInstance, String value, Integer userId) {
        this.dataInstance = dataInstance;
        this.value = value;
        this.userId = userId;
    }

    public EditUserDto() {
    }

    public String getDataInstance() {
        return dataInstance;
    }

    public void setDataInstance(String dataInstance) {
        this.dataInstance = dataInstance;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
