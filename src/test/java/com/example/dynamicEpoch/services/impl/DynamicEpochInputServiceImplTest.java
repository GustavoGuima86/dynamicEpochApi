package com.example.dynamicEpoch.services.impl;

import com.example.dynamicEpoch.DTO.DynamicEpochValueMessage;
import com.example.dynamicEpoch.entities.DataInputEntity;
import com.example.dynamicEpoch.entities.DataSourceEntity;
import com.example.dynamicEpoch.entities.DynamicEpochValueEntity;
import com.example.dynamicEpoch.repositories.DataInputRepository;
import com.example.dynamicEpoch.repositories.DataSourceRepository;
import com.example.dynamicEpoch.repositories.DynamicEpochValueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DynamicEpochInputServiceImplTest {

    public static final String AUTHENTICATION_TOKEN = "123456";
    public static final Integer DATA_SOURCE = 8;
    public static final Date START_TIMESTAMP_UNIX = new Date(1605595355000l);
    public static final Date END_TIMESTAMP_UNIX = new Date(1605595394000l);
    public static final Date CREATED_AT_UNIX = new Date(1605597330067l);
    public static final Integer DYNAMIC_VALUE_TYPE = 3000;
    public static final String VALUE = "80";
    public static final String VALUE_TYPE = "LONG";

    @Mock
    private DataInputRepository dataInputRepository;

    @Mock
    private DataSourceRepository dataSourceRepository;

    @Mock
    private DynamicEpochValueRepository dynamicEpochValueRepository;

    @InjectMocks
    private DynamicEpochInputServiceImpl dynamicEpochInputServiceImpl;

    @Test
    public void inputDataDynamicEpoch_includingAllNewData_success(){

        when(dataInputRepository.findById(AUTHENTICATION_TOKEN)).thenReturn(Optional.empty());

        dynamicEpochInputServiceImpl.inputDataDynamicEpoch(DynamicEpochValueMessage.builder()
                .authenticationToken(AUTHENTICATION_TOKEN)
                        .dataSource(DATA_SOURCE)
                        .startTimestampUnix(START_TIMESTAMP_UNIX)
                        .endTimestampUnix(END_TIMESTAMP_UNIX)
                        .createdAtUnix(CREATED_AT_UNIX)
                        .dynamicValueType(DYNAMIC_VALUE_TYPE)
                        .value(VALUE)
                        .valueType(VALUE_TYPE)
                .build());

        verify(dataSourceRepository).save(eq(DataSourceEntity.builder()
                .dataSource(DATA_SOURCE).build()));
        verify(dataInputRepository).save(eq(DataInputEntity.builder().authenticationToken(AUTHENTICATION_TOKEN).build()));
        verify(dynamicEpochValueRepository).save(eq(DynamicEpochValueEntity.builder()
                .startTimestampUnix(START_TIMESTAMP_UNIX)
                .endTimestampUnix(END_TIMESTAMP_UNIX)
                .createdAtUnix(CREATED_AT_UNIX)
                .dynamicValueType(DYNAMIC_VALUE_TYPE)
                .heartRate(Integer.valueOf(VALUE))
                .valueType(VALUE_TYPE)
                .build()));
    }
}
