package com.user.auth.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CustomErrorResponse {

    private ZonedDateTime timestamp = ZonedDateTime.now();

    private int code = -1;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("message")
    private List<CustomFieldError> customFieldErrors;

    static public CustomErrorResponse builder() {
        return new CustomErrorResponse();
    }

    public CustomErrorResponse code(int code) {
        this.code = code;
        return this;
    }

    public void setCustomFieldErrors(List<FieldError> fieldErrors) {

        customFieldErrors = new ArrayList<>();

        fieldErrors.forEach(error -> {
            customFieldErrors.add(new CustomFieldError(
                    error.getCodes()[0],
                    error.getRejectedValue(),
                    error.getDefaultMessage()
            ));
        });
    }

    public CustomErrorResponse errors(Errors errors) {
        setCustomFieldErrors(errors.getFieldErrors());
        return this;
    }


    public static class CustomFieldError {

        private String field;
        private Object value;
        private String reason;

        public CustomFieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }
}