package com.example.technicaltask.member.controller;

import com.example.technicaltask.exception.ErrorCode;
import com.example.technicaltask.exception.MemberException;
import com.example.technicaltask.member.data.dto.SignIn;
import com.example.technicaltask.member.data.dto.SignUp;
import com.example.technicaltask.member.data.entity.Member;
import com.example.technicaltask.member.data.type.MemberType;
import com.example.technicaltask.member.service.MemberService;
import com.example.technicaltask.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberControllerTest {
    @Mock
    private MemberService memberService;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 회원가입성공() {
        // Given
        SignUp.Request signUpRequest = new SignUp.Request();
        signUpRequest.setMemberId("testuser");
        signUpRequest.setPassword("testpassword");

        SignUp.Response signUpResponse = new SignUp.Response();
        signUpResponse.setMemberId("testuser");

        when(memberService.registerMember(signUpRequest)).thenReturn(signUpResponse);

        // When
        SignUp.Response response = memberController.signUp(signUpRequest);

        // Then
        assertNotNull(response);
        assertEquals("testuser", response.getMemberId());

        verify(memberService, times(1)).registerMember(signUpRequest);
    }

    @Test
    public void 로그인성공() {
        // Given
        SignIn.Request signInRequest = new SignIn.Request();
        signInRequest.setMemberId("testuser");
        signInRequest.setPassword("testpassword");

        Member member = new Member();
        member.setMemberId("testuser");
        member.setMemberType(MemberType.CUSTOMER);

        when(memberService.login(signInRequest)).thenReturn(member);
        when(tokenProvider.createToken(member.getUsername(), member.getRoles()))
                .thenReturn("token");

        // When
        ResponseEntity<?> responseEntity = memberController.signIn(signInRequest);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.hasBody());

        String token = (String) responseEntity.getBody();
        assertNotNull(token);

        verify(memberService, times(1)).login(signInRequest);
    }

    @Test
    public void 중복가입여부확인() {
        // Given
        SignUp.Request signUpRequest = new SignUp.Request();
        signUpRequest.setMemberId("existinguser");
        signUpRequest.setPassword("testpassword");

        when(memberService.registerMember(signUpRequest)).thenThrow(new MemberException(ErrorCode.MEMBER_ALREADY_REGISTERED));

        // When, Then
        assertThrows(MemberException.class, () -> memberController.signUp(signUpRequest));
        verify(memberService, times(1)).registerMember(signUpRequest);
    }

    @Test
    public void 존재하지않는아이디로로그인확인() {
        // Given
        SignIn.Request signInRequest = new SignIn.Request();
        signInRequest.setMemberId("nonexistentuser");
        signInRequest.setPassword("testpassword");

        when(memberService.login(signInRequest)).thenThrow(new MemberException(ErrorCode.MEMBER_NONEXIST));

        // When, Then
        assertThrows(MemberException.class, () -> memberController.signIn(signInRequest));
        verify(memberService, times(1)).login(signInRequest);
    }

    @Test
    public void 잘못된비밀번호() {
        // Given
        SignIn.Request signInRequest = new SignIn.Request();
        signInRequest.setMemberId("testuser");
        signInRequest.setPassword("incorrectpassword");

        when(memberService.login(signInRequest)).thenThrow(new MemberException(ErrorCode.PASSWORD_MISMATCH));

        // When, Then
        assertThrows(MemberException.class, () -> memberController.signIn(signInRequest));
        verify(memberService, times(1)).login(signInRequest);
    }
}