package com.example.dynamicEpoch.services;

import com.example.dynamicEpoch.DTO.output.DataOutputDTO;

import java.util.Date;

public interface DynamicEpochOutputService {

    DataOutputDTO getRawData(String userId, Integer dataSource, Date fromDate, Date toDate);

    Double getHeartRate(String userId, Integer dataSource, Date fromDate, Date toDate);
}
