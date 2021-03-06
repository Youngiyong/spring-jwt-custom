package com.user.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum ErrorCode {
    // TODO: Need to refine

    
    SUCCESS(200,  "OK"),
    TYPE_MISMATCH(400, "TYPE_MISMATCH"),
    INVALID_PARAMETER(400, "Invalid Request Data"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    UNAUTHORIZED_INVALID_ACCESS_TOKEN(401,"UNAUTHORIZED_INVALID_ACCESS_TOKEN"),

    //Token
    BAD_REQUEST_ACCESS_TOKEN_INVALID(400, "BAD_REQUEST_ACCESS_TOKEN_INVALID"),
    NOT_FOUND_ACCESS_TOKEN(404,  "NOT_FOUND_ACCESS_TOKEN"),
    BAD_REQUEST_REFRESH_TOKEN_EQUAL(400, "BAD_REQUEST_REFRESH_TOKEN_EQUAL"),
    BAD_REQUEST_REFRESH_TOKEN(400, "BAD_REQUEST_REFRESH_TOKEN"),
    BAD_REQUEST_NOT_AUTHORITY(400, "BAD_REQUEST_NOT_AUTHORITY"),
    BAD_REQUEST_UNSUPPORTED_TOKEN(400, "BAD_REQUEST_UNSUPPORTED_TOKEN"),
    BAD_REQUEST_WRONG_SIGNATURE(400, "BAD_REQUEST_WRONG_SIGNATURE"),
    BAD_REQUEST_EXPIRED_TOKEN(400, "BAD_REQUEST_EXPIRED_TOKEN"),
    BAD_REQUEST_ILLEGALARGUMENT_TOKEN(400, "BAD_REQUEST_ILLEGALARGUMENT_TOKEN"),



    //User
    NOT_FOUND_USER(404, "NOT_FOUND_USER"),
    NOT_FOUND_USER_ROLE(404, "NOT_FOUND_USER_ROLE"),
    EXIST_USER(400, "EXIST_USER"),

    NOT_FOUND_PRINCIPAL_USER(404, "NOT_FOUND_PRINCIPAL_USER"),

    //Convert
    NOT_FOUND_ENUM_CODE(404, "NOT_FOUND_ENUM_CODE"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),

    NOT_FOUND_TERM(404, "NOT_FOUND_TERM"),

    //Device
    DUPLICATE_DEVICE(400, "DUPLICATE_DEVICE");
    @Getter private final int status;
    @Getter private final String message;
}