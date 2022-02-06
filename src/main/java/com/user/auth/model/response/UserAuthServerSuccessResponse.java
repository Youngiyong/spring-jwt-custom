package com.user.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserAuthServerSuccessResponse {

    private int code = 0;
    private ZonedDateTime timestamp = ZonedDateTime.now();
}
