package com.example.technicaltask.store.data.dto;

import com.example.technicaltask.store.data.entity.Store;
import lombok.*;

import java.time.LocalDateTime;

public class RegisterStore {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        private String memberId;
        private String storeName;
        private String storeAddress;
        private String storePhoneNum;
        private LocalDateTime openTime;
        private LocalDateTime closeTime;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String memberId;
        private String storeName;
        private String storeAddress;
        private String storePhoneNum;
        private double lnt;
        private double lat;
        private LocalDateTime openTime;
        private LocalDateTime closeTime;
        private LocalDateTime createdAt;

        public static Response fromEntity(Store store){
            return Response.builder()
                    .memberId(store.getMember().getMemberId())
                    .storeName(store.getStoreName())
                    .storeAddress(store.getStoreAddress())
                    .storePhoneNum(store.getStorePhoneNum())
                    .lnt(store.getLnt())
                    .lat(store.getLat())
                    .openTime(store.getOpenTime())
                    .closeTime(store.getCloseTime())
                    .createdAt(store.getCreatedAt())
                    .build();
        }
    }
}
