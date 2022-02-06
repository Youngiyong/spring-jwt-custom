package com.user.auth.controller;

import com.user.auth.exception.InvalidParameterException;
import com.user.auth.model.request.TokenAccessReqDto;
import com.user.auth.model.request.TokenReqDto;
import com.user.auth.model.request.UserReqDto;
import com.user.auth.model.response.ErrorResDto;
import com.user.auth.model.response.TokenResDto;
import com.user.auth.model.response.UserAuthServerResponse;
import com.user.auth.model.response.UserAuthServerSuccessResponse;
import com.user.auth.service.AuthService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    final AuthService authService;

    @PostMapping("/check_token")
    public ResponseEntity<UserAuthServerResponse<TokenResDto.TokenCheckResponse>> checkToken(final @RequestBody TokenAccessReqDto payload) {

       UserAuthServerResponse response = UserAuthServerResponse.builder()
                .data(authService.checkToken(payload))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserAuthServerSuccessResponse> signup(final @Valid @RequestBody UserReqDto.UserSignUpRequest payload,
                                                                BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidParameterException(result);
        }
        authService.signup(payload);

        return new ResponseEntity<>(new UserAuthServerSuccessResponse(), HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(
                    code = 400, message = "Bad Request", response = ErrorResDto.class  , responseContainer = "Object")
    })
    @PostMapping("/token")
    public ResponseEntity<UserAuthServerResponse<TokenResDto.TokenResponse>> login(@Valid @RequestBody UserReqDto.UserRequest payload,
                                                                                   BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidParameterException(result);
        }

        UserAuthServerResponse response = UserAuthServerResponse.builder()
                .data(authService.login(payload))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(
                    code = 400, message = "Bad Request", response = HttpClientErrorException.class  , responseContainer = "Object")
    })
    @PostMapping("/refresh_token")
    public ResponseEntity<UserAuthServerResponse<TokenResDto.TokenResponse>> refresh(@RequestBody TokenReqDto payload) {

        UserAuthServerResponse response = UserAuthServerResponse.builder()
                .data(authService.reissue(payload))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
