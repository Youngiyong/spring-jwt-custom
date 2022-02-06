package com.user.auth.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


public class TokenResDto {

    @Setter
    @Getter
    @Builder
    public static class TokenCheckResponse {
        @ApiModelProperty(value = "유저 PK", example = "1", dataType = "java.lang.Long")
        private Long userId;
        @ApiModelProperty(value = "유저 권한", example = "ROLE_USER", dataType = "java.lang.String")
        private String auth;
        @ApiModelProperty(value = "만료 시간", example = "1644070098187", dataType = "java.lang.Long")
        private Long exp;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TokenResponse {
        @ApiModelProperty(value = "토큰 타입", example = "bearer", dataType = "java.lang.String")
        private String grantType;
        @ApiModelProperty(value = "엑세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhbWxhYnMuY28ua3IiLCJzdWIiOiIyMSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2NDQxMzIyMzZ9.w9EklGepkMJetpy3W_O4b-9A94Or1Kh9W05xDa9JphHK9iS9UqJkp0x1H5zVZzFe1jxFl7vamESPKwwsOPhINA", dataType = "java.lang.String")
        private String accessToken;
        @ApiModelProperty(value = "리프레쉬 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NDQ3MzUyMzZ9.rRGsve90IJKdTQCja028wFJkE9JBiUtV41FmnXRFVZftwV_orgQgo08OHohCHV6RgDwIoq0-lFcSPoGnAPZQ8Q", dataType = "java.lang.String")
        private String refreshToken;
        @ApiModelProperty(value = "엑세스 토큰 만료 시간", example = "1644070098187", dataType = "java.lang.Long")
        private Long accessTokenExpiresIn;
    }
}
