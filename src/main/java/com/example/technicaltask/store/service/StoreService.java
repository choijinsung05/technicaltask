package com.example.technicaltask.store.service;

import com.example.technicaltask.exception.MemberException;
import com.example.technicaltask.exception.StoreException;
import com.example.technicaltask.member.data.entity.Member;
import com.example.technicaltask.member.data.repository.MemberRepository;
import com.example.technicaltask.store.data.dto.RegisterStore.Request;
import com.example.technicaltask.store.data.dto.RegisterStore.Response;
import com.example.technicaltask.store.data.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.technicaltask.exception.ErrorCode.*;
import static com.example.technicaltask.member.data.type.MemberType.PARTNER;

@Service
@AllArgsConstructor
public class StoreService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public Response registerStore(Request request) throws IOException {
        Member partnerMember = getPartnerMember(request.getMemberId());
        checkDuplication(request);


    }

    private Member getPartnerMember(String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberException(MEMBER_NONEXIST));
        if (member.getMemberType() != PARTNER) {
            throw new MemberException(MEMBER_TYPE_NOT_PARTNER);
        }
        return member;
    }

    private void checkDuplication(Request request){
        if (storeRepository.existsByStoreName(request.getStoreName())
                || storeRepository.existsByStoreAddress(request.getStoreAddress())) {
            throw new StoreException(STORE_ALREADY_EXIST);
        }
    }


}
