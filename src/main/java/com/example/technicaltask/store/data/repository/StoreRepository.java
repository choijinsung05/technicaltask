package com.example.technicaltask.store.data.repository;

import com.example.technicaltask.store.data.dto.StoreResult;
import com.example.technicaltask.store.data.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsByStoreName(String storeName);

    boolean existsByStoreAddress(String address);

    Page<Store> findByOrderByStarDesc(Pageable pageable);

    @Query(value = "select NEW com.example.technicaltask" +
            ".store.data.dto.StoreResult(s, " +
            "ROUND((6371*acos(cos(radians(:lat))*cos(radians(s.lat))" +
            "*cos(radians(s.lnt) -radians(:lnt))+sin(radians(:lat))" +
            "*sin(radians(s.lat)))), 3)) FROM Store s order by " +
            "ROUND((6371*acos(cos(radians(:lat))*cos(radians(s.lat))" +
            "*cos(radians(s.lnt) -radians(:lnt))+sin(radians(:lat))" +
            "*sin(radians(s.lat)))), 3) asc")
    Page<StoreResult> findAllByOrderByDistanceAsc(Pageable pageable,
                                                  @Param("lat") Double lat,
                                                  @Param("lnt") Double lnt);

    Store findByStoreName(String storeName);

    Store findByStoreId(Long storeId);

}
