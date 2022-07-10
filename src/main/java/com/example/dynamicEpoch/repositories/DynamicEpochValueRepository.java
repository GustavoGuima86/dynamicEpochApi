package com.example.dynamicEpoch.repositories;

import com.example.dynamicEpoch.entities.DynamicEpochValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DynamicEpochValueRepository extends JpaRepository<DynamicEpochValueEntity, Long> {

}
