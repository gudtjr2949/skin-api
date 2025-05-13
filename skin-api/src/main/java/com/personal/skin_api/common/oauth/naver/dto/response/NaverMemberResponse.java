package com.personal.skin_api.common.oauth.naver.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverMemberResponse {

    @JsonProperty("resultcode")
    private String resultCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("response")
    private NaverMemberDetail naverMemberDetail;

    @ToString
    @Getter
    public static class NaverMemberDetail {
        private String email;
        private String name;
        private String phone;

        public NaverMemberDetail(String email, String name, String mobile) {
            this.email = email;
            this.name = name;
            this.phone = mobile.replaceAll("-", "");
        }
    }
}
