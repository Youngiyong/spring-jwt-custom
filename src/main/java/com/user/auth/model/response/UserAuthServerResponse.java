package com.user.auth.model.response;

import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserAuthServerResponse<T> {

    private int code = 0;
    private ZonedDateTime timestamp = ZonedDateTime.now();
    private T data;

    @Builder
    public UserAuthServerResponse(T data){
        this.data = data;
    }

}
