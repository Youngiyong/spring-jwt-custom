package com.user.auth.model.response;

import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ErrorResponse {

    private int code = -1;
    private ZonedDateTime timestamp = ZonedDateTime.now();
    private String message;

    @Builder
    public ErrorResponse(String message){
        this.message = message;
    }
}