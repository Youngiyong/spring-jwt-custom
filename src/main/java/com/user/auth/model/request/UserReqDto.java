package com.user.auth.model.request;

import com.cheese.domain.enums.user.EUserGenderFlag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserReqDto {

    @Getter
    public static class UserSignUpRequest {

        @NotNull(message = "이름은 필수값입니다.")
        @ApiModelProperty(required = true,  value = "이름", example = "홍길동", dataType = "java.lang.String")
        private String name;

        @NotNull(message = "휴대폰 번호는 필수값입니다.")
        @Size(min = 10, max = 11)
        @Pattern(regexp="(^$|[0-9]{11})")
        @ApiModelProperty(required = true, value = "휴대폰번호", example = "01092069357", dataType = "java.lang.String")
        private String hp;

        @NotNull(message = "cid 값은 필수값입니다.")
        @ApiModelProperty(required = true,  value = "password", example = "1234", dataType = "java.lang.String")
        private String password;

        @ApiModelProperty(value = "Z:미설정, M:남자, W:여자", example = "M")
        private EUserGenderFlag gender;

        @NotNull(message = "생년월일(년)은 필수값입니다.")
        @Size(min = 4, max = 4)
        @ApiModelProperty(required = true,  value = "1997", example = "1997", dataType = "java.lang.String")
        private String year;

        @NotNull(message = "생년월일(월)은 필수값입니다.")
        @Size(min = 2, max = 2)
        @ApiModelProperty(required = true, value = "05", example = "05", dataType = "java.lang.String")
        private String month;

        @NotNull(message = "생년월일(일)은 필수값입니다.")
        @Size(min = 2, max = 2)
        @Length(min = 1, max = 12)
        @ApiModelProperty(required = true, value = "03", example = "03", dataType = "java.lang.String")
        private String day;

        @ApiModelProperty(required = true)
        private List<TermReqDto.TermSignUpRequest> terms;

        @ApiModelProperty(required = true)
        private DeviceReqDto.DeviceRequest device;
    }

    @Getter
    public static class UserRequest {

        @Pattern(regexp="(^$|[0-9]{11})")
        @NotNull(message = "휴대폰 번호는 필수값입니다.")
        @ApiModelProperty(value = "휴대폰번호", example = "01092069357", dataType = "java.lang.String")
        private String hp;

        @NotNull(message = "cid 값은 필수값입니다.")
        @ApiModelProperty(value = "password", example = "1234", dataType = "java.lang.String")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(hp, password);
        }
    }


}
