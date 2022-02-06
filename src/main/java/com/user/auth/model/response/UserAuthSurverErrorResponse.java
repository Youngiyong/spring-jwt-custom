package com.user.auth.model.response;

import lombok.*;

import java.time.ZonedDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthSurverErrorResponse {

    private int status;
    private int code = -1;
    private String message;
    private ZonedDateTime timestamp = ZonedDateTime.now();
}
