package com.user.auth.service;

import com.user.auth.exception.ErrorCode;
import com.user.auth.exception.UserAuthServerException;
import com.user.auth.model.request.*;
import com.user.auth.model.response.TokenResDto;
import com.user.auth.util.TokenProvider;
import com.cheese.domain.domain.term.Term;
import com.cheese.domain.domain.term.TermRepository;
import com.cheese.domain.domain.user.User;
import com.cheese.domain.domain.user.UserRepository;
import com.cheese.domain.domain.userAccessToken.UserAccessToken;
import com.cheese.domain.domain.userAccessToken.UserAccessTokenRepository;
import com.cheese.domain.domain.userDevice.UserDevice;
import com.cheese.domain.domain.userDevice.UserDeviceRepository;
import com.cheese.domain.domain.userHasDevice.UserHasDevice;
import com.cheese.domain.domain.userHasDevice.UserHasDeviceRepository;
import com.cheese.domain.domain.userHasRole.UserHasRole;
import com.cheese.domain.domain.userHasRole.UserHasRoleRepository;
import com.cheese.domain.domain.userHasTerm.UserHasTerm;
import com.cheese.domain.domain.userHasTerm.UserHasTermRepository;
import com.cheese.domain.domain.userRefreshToken.UserRefreshToken;
import com.cheese.domain.domain.userRole.UserRole;
import com.cheese.domain.domain.userRole.UserRoleRepository;
import com.cheese.domain.enums.user.EUserRole;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.cheese.domain.domain.user.QUser.user;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserHasDeviceRepository userHasDeviceRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final TermRepository termRepository;
    private final UserHasTermRepository userHasTermRepository;
    private final UserHasRoleRepository userHasRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserAccessTokenRepository userAccessTokenRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public TokenResDto.TokenCheckResponse checkToken(TokenAccessReqDto payload){
        return tokenProvider.getTokenClaim(payload);
    }

    @Transactional
    public void signup(UserReqDto.UserSignUpRequest payload) {

        //?????? ?????? ??????
        Integer duplicateUser = jpaQueryFactory
                .selectOne()
                .from(user)
                .where(user.hp.eq((payload.getHp())))
                .fetchFirst();

        if (duplicateUser != null) throw new UserAuthServerException(ErrorCode.EXIST_USER, "???????????? ???????????????.");

        UserRole userRole = userRoleRepository.findByName(EUserRole.ROLE_USER)
                .orElseThrow(() -> new UserAuthServerException(ErrorCode.NOT_FOUND_USER_ROLE, "???????????? ?????? USER ROLE?????????."));

        User user = User.builder()
                .hp(payload.getHp())
                .password(passwordEncoder.encode(payload.getPassword()))
                .year(payload.getYear())
                .month(payload.getMonth())
                .day(payload.getDay())
                .gender(payload.getGender())
                .name(payload.getName())
                .build();


        UserHasRole userHasRole = UserHasRole.builder()
                .user(user)
                .role(userRole)
                .build();

        userRepository.save(user);
        userHasRoleRepository.save(userHasRole);

        DeviceReqDto.DeviceRequest deviceRequest = payload.getDevice();

        /*
        ToDo : Device Check??
         */
//        UserDevice checkDevice = userDeviceRepository.findByUuidAndDeleteDateIsNull(deviceRequest.getUuid())
//                .orElseThrow(() -> new UserAuthServerException(ErrorCode.DUPLICATE_DEVICE, "?????? ???????????? ?????? ??????????????? ??????????????."));

        UserDevice userDevice = UserDevice.builder()
                .uuid(deviceRequest.getUuid())
                .application(deviceRequest.getApplication())
                .version(deviceRequest.getVersion())
                .device(deviceRequest.getDevice())
                .platform(deviceRequest.getPlatform())
                .platformVersion(deviceRequest.getPlatformVersion())
                .adid(deviceRequest.getAdid())
                .push(deviceRequest.getPush())
                .isRooted(deviceRequest.getIsRooted())
                .lat(deviceRequest.getLat())
                .lon(deviceRequest.getLon())
                .country(deviceRequest.getCountry())
                .language(deviceRequest.getLanguage())
                .ip(deviceRequest.getIp())
                .build();

        UserHasDevice userHasDevice = UserHasDevice.builder()
                .device(userDevice)
                .user(user)
                .build();

        userDeviceRepository.save(userDevice);
        userHasDeviceRepository.save(userHasDevice);

        for (TermReqDto.TermSignUpRequest terms : payload.getTerms()) {
            Term term = termRepository.findById(terms.getTermId())
                    .orElseThrow(() -> new UserAuthServerException(ErrorCode.NOT_FOUND_TERM, "???????????? ?????? TERM:" + terms.getTermId() + " ?????????."));


            UserHasTerm userHasTerm = UserHasTerm.builder()
                    .user(user)
                    .term(term)
                    .isAgree(terms.getAgree())
                    .build();

            userHasTermRepository.save(userHasTerm);
        }
    }

    @Transactional
    public TokenResDto.TokenResponse login(UserReqDto.UserRequest payload) {

        UsernamePasswordAuthenticationToken authenticationToken = payload.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Optional<UserAccessToken> optionalAccessToken = userAccessTokenRepository.findByUserId(authentication.getName());

        if (optionalAccessToken.isEmpty()) {
            TokenResDto.TokenResponse tokenDto = tokenProvider.generateTokenDto(authentication);

            String uuid = UUID.randomUUID().toString();

            UserRefreshToken userRefreshToken = UserRefreshToken.builder()
                    .tokenId(uuid)
                    .token(tokenDto.getRefreshToken())
                    .build();

            UserAccessToken userAccessToken = UserAccessToken.builder()
                    .token(tokenDto.getAccessToken())
                    .exp(tokenDto.getAccessTokenExpiresIn())
                    .refreshToken(userRefreshToken)
                    .userId(authentication.getName())
                    .build();

            userAccessTokenRepository.save(userAccessToken);
            return tokenDto;
        }

        UserAccessToken accessToken = optionalAccessToken.get();

        if(!tokenProvider.validateToken(accessToken.getToken())){
            TokenResDto.TokenResponse tokenDto = tokenProvider.generateTokenDto(authentication);

            UserRefreshToken refreshToken = UserRefreshToken.builder()
                    .tokenId(accessToken.getRefreshToken().getTokenId())
                    .token(tokenDto.getRefreshToken())
                    .updateDate(ZonedDateTime.now())
                    .build();

            accessToken.setExp(tokenDto.getAccessTokenExpiresIn());
            accessToken.setToken(tokenDto.getAccessToken());
            accessToken.setUpdateDate(ZonedDateTime.now());
            accessToken.setRefreshToken(refreshToken);
            userAccessTokenRepository.save(accessToken);

            return TokenResDto.TokenResponse.builder()
                    .grantType("bearer")
                    .accessToken(tokenDto.getAccessToken())
                    .accessTokenExpiresIn(tokenDto.getAccessTokenExpiresIn())
                    .refreshToken(tokenDto.getRefreshToken())
                    .build();
        }

        return TokenResDto.TokenResponse.builder()
                .grantType("bearer")
                .accessToken(accessToken.getToken())
                .accessTokenExpiresIn(accessToken.getExp())
                .refreshToken(accessToken.getRefreshToken().getToken())
                .build();
    }

    public TokenResDto.TokenResponse reissue(TokenReqDto tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new UserAuthServerException(ErrorCode.BAD_REQUEST_REFRESH_TOKEN, "????????? ????????? refresh token?????????.");
       }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        UserAccessToken accessToken = userAccessTokenRepository.findByUserId(authentication.getName())
                .orElseThrow(() -> new UserAuthServerException(ErrorCode.NOT_FOUND_ACCESS_TOKEN, "???????????? ?????? Access Token?????????."));

        if (!accessToken.getRefreshToken().getToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new UserAuthServerException(ErrorCode.BAD_REQUEST_REFRESH_TOKEN_EQUAL, "???????????? ?????? refresh token?????????.");
        }

        TokenResDto.TokenResponse tokenDto = tokenProvider.generateTokenDto(authentication);

        UserRefreshToken refreshToken = UserRefreshToken.builder()
                .tokenId(accessToken.getRefreshToken().getTokenId())
                .token(tokenDto.getRefreshToken())
                .updateDate(ZonedDateTime.now())
                .build();

        accessToken.setExp(tokenDto.getAccessTokenExpiresIn());
        accessToken.setToken(tokenDto.getAccessToken());
        accessToken.setUpdateDate(ZonedDateTime.now());
        accessToken.setRefreshToken(refreshToken);
        userAccessTokenRepository.save(accessToken);

        return tokenDto;
    }
}