package com.user.auth.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenReqDto {
    @ApiModelProperty(value = "엑세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhbWxhYnMuY28ua3IiLCJzdWIiOiIyMSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2NDQxMzIyMzZ9.w9EklGepkMJetpy3W_O4b-9A94Or1Kh9W05xDa9JphHK9iS9UqJkp0x1H5zVZzFe1jxFl7vamESPKwwsOPhINA", dataType = "java.lang.String")
    private String accessToken;

    @ApiModelProperty(value = "리프레쉬 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NDQ3MzUyMzZ9.rRGsve90IJKdTQCja028wFJkE9JBiUtV41FmnXRFVZftwV_orgQgo08OHohCHV6RgDwIoq0-lFcSPoGnAPZQ8Q", dataType = "java.lang.String")
    private String refreshToken;
}
