package com.example.technicaltask.member.data.dto;

import com.example.technicaltask.member.data.entity.Member;
import com.example.technicaltask.member.data.type.MemberType;
import lombok.*;

import java.time.LocalDateTime;

public class SignUp {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String memberId;
        private String password;
        private String phoneNum;
        private MemberType memberType;

        public Member toEntity() {
            return Member.builder()
                    .memberId(this.getMemberId())
                    .password(this.getPassword())
                    .phoneNum(this.getPhoneNum())
                    .memberType(this.getMemberType())
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String memberId;
        private MemberType memberType;
        private LocalDateTime createdAt;

        public static Response fromEntity(Member member){
            return Response.builder()
                    .memberId(member.getMemberId())
                    .memberType(member.getMemberType())
                    .createdAt(member.getCreatedAt())
                    .build();
        }

    }

}
