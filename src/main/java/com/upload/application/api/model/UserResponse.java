package com.upload.application.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {

    private Integer code;
    private UserBody body;

    @Getter
    @NoArgsConstructor
    public class UserBody {
        private Long id;
        private String nickname;
    }
}
