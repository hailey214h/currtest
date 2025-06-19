package com.example.bit.model.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CurrencyBaseReq {

    @NotBlank(message = "code cannot be blank")
    @Size(max = 5, message = "code must not exceed 5 characters")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
