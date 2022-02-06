package com.user.auth.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DeviceReqDto {

    @Getter
    public static class DeviceRequest {

        @NotNull
        @ApiModelProperty(required = true, value = "uuid", example = "effb9c3a-5ffa-4b97-b01a-65ee90f715f5", dataType = "java.lang.String")
        private String uuid;

        @NotNull
        @ApiModelProperty(required = true, value = "사용중인 어플리케이션 API KEY", example = "apikeysample", dataType = "java.lang.String")
        private String application;

        @ApiModelProperty(value = "사용중인 어플리케이션 version", example = "1.0.0", dataType = "java.lang.String")
        private String version;

        @ApiModelProperty(value = "사용중인 어플리케이션 device", example = "IPhone", dataType = "java.lang.String")
        private String device;

        @ApiModelProperty(value = "사용중인 사용자 OS", example = "platform", dataType = "java.lang.String")
        private String platform;

        @ApiModelProperty(value = "사용중인 OS 버전", example = "2.0.0", dataType = "java.lang.String")
        private String platformVersion;

        @ApiModelProperty(value = "광고식별자, adid/idfa", example = "adid??", dataType = "java.lang.String")
        private String adid;

        @ApiModelProperty(value = "push key", example = "pushkeysample", dataType = "java.lang.String")
        private String push;

        @ApiModelProperty(value = "루팅 여부", example = "true", dataType = "java.lang.Boolean")
        private Boolean isRooted;

        @ApiModelProperty(value = "위도", example = "30.1231313", dataType = "java.lang.String")
        private String lat;

        @ApiModelProperty(value = "경도", example = "123.1232132", dataType = "java.lang.String")
        private String lon;

        @ApiModelProperty(value = "국가코드", example = "KR", dataType = "java.lang.String")
        private String country;

        @ApiModelProperty(value = "언어코드", example = "KR", dataType = "java.lang.String")
        private String language;

        @ApiModelProperty(value = "아이피", example = "8.8.8.8", dataType = "java.lang.String")
        private String ip;
    }
}
