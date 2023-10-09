package com.example.technicaltask.store.data.dto;

import com.example.technicaltask.store.data.entity.Store;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResult {
    private Store store;
    private double distance;
}
