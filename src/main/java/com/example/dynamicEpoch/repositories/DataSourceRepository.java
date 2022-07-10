package com.example.dynamicEpoch.repositories;

import com.example.dynamicEpoch.entities.DataSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSourceEntity, Long> {
}
