package org.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ParseRequest {

    @NotNull(message = "Field url can't be null")
    @NotBlank(message = "Filed url can't be empty")
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
