package com.example.dynamicEpoch.repositories;

import com.example.dynamicEpoch.entities.DataInputEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataInputRepository extends JpaRepository<DataInputEntity, String> {



}
