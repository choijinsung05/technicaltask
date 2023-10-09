package com.example.technicaltask.store.data.entity;

import com.example.technicaltask.member.data.entity.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    private String storeName;
    private String storePhoneNum;

    private String storeAddress;
    private double lnt;
    private double lat;

    private LocalDateTime openTime;
    private LocalDateTime closeTime;

    private double star;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
