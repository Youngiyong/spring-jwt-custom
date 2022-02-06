package com.user.auth.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class TermReqDto {

    @Getter
    public static class TermSignUpRequest {
        @ApiModelProperty(required = true, value = "이용약관 id PK", example = "1", dataType = "java.lang.Long")
        private Long termId;

        @ApiModelProperty(required = true, value = "이용약관 동의 여부", example = "true", dataType = "java.lang.Boolean")
        private Boolean agree;
    }
}
