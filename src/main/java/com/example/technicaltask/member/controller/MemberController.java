package com.example.technicaltask.member.controller;

import com.example.technicaltask.member.data.dto.SignIn;
import com.example.technicaltask.member.data.dto.SignUp;
import com.example.technicaltask.member.data.entity.Member;
import com.example.technicaltask.member.service.MemberService;
import com.example.technicaltask.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/memeber")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/sign-up/")
    public SignUp.Response signUp(@RequestBody SignUp.Request signUpRequest){
        log.info("detected new sign-up attempts -> " + signUpRequest.getMemberId());
        return memberService.registerMember(signUpRequest);
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignIn.Request request){
        Member member = memberService.login(request);
        String token = tokenProvider.createToken(member.getMemberId(), member.getRoles());
        log.info("login -> " + request.getMemberId());
        return ResponseEntity.ok(token);
    }

}
