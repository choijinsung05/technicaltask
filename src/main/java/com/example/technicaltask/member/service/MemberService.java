package com.example.technicaltask.member.service;

import com.example.technicaltask.exception.MemberException;
import com.example.technicaltask.member.data.dto.SignIn.Request;
import com.example.technicaltask.member.data.dto.SignUp;
import com.example.technicaltask.member.data.dto.SignUp.Response;
import com.example.technicaltask.member.data.entity.Member;
import com.example.technicaltask.member.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.technicaltask.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을수 없습니다. -> " + memberId));
    }

    public Response registerMember(SignUp.Request signUpRequest){
        boolean exists = memberRepository.existsByMemberId(signUpRequest.getMemberId());

        if(exists){
            throw new MemberException(MEMBER_ALREADY_REGISTERED);
        }

        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Member member = memberRepository.save(signUpRequest.toEntity());
        return Response.fromEntity(member);
    }

    public Member login (Request signInRequest){
        Member member = memberRepository.findByMemberId(signInRequest.getMemberId())
                .orElseThrow(() -> new MemberException(MEMBER_NONEXIST));

        if(!passwordEncoder.matches(signInRequest.getPassword(), member.getPassword())){
            throw new MemberException(PASSWORD_MISMATCH);
        }

        return member;
    }


}
