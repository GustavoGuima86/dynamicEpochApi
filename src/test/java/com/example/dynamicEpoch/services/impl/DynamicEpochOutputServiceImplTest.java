package com.example.dynamicEpoch.services.impl;

import com.example.dynamicEpoch.entities.DataInputEntity;
import com.example.dynamicEpoch.entities.DataSourceEntity;
import com.example.dynamicEpoch.entities.DynamicEpochValueEntity;
import com.example.dynamicEpoch.repositories.DataInputRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DynamicEpochOutputServiceImplTest {

    public static final String AUTHENTICATION_TOKEN = "123456";
    public static final Integer DATA_SOURCE = 8;
    public static final Integer DYNAMIC_VALUE_TYPE = 3000;
    public static final Integer HEART_RATE = 80;
    public static final String VALUE_TYPE = "LONG";

    public static final Long START_TIMESTAMP_UNIX_LONG = 1605595355000l;
    public static final Long END_TIMESTAMP_UNIX_LONG = 1605595394000l;
    public static final Long CREATED_AT_UNIX_LONG = 1605597330067l;
    public static final Date START_TIMESTAMP_UNIX = new Date(START_TIMESTAMP_UNIX_LONG);
    public static final Date END_TIMESTAMP_UNIX = new Date(END_TIMESTAMP_UNIX_LONG);
    public static final Date CREATED_AT_UNIX = new Date(CREATED_AT_UNIX_LONG);

    @Mock
    private DataInputRepository dataInputRepository;
 
    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private DynamicEpochOutputServiceImpl dynamicEpochOutputServiceImpl;

    @Test
    public void getRawData_notFound_NoSuchElementException(){
        String userId = "123456";
        when(dataInputRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            dynamicEpochOutputServiceImpl.getRawData(userId, null, null, null);
        });
    }

    @Test
    public void getHeartRate_notFound_NoSuchElementException(){
        String userId = "123456";
        when(dataInputRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            dynamicEpochOutputServiceImpl.getHeartRate(userId, null, null, null);
        });
    }

    @Test
    public void getRawData_byId_success(){
        when(dataInputRepository.findById(AUTHENTICATION_TOKEN)).thenReturn(createDataInputReturn());

        var raw=dynamicEpochOutputServiceImpl.getRawData(AUTHENTICATION_TOKEN, null, null, null);
        var datasource = raw.getDataSources().get(0);
        var dynamicEpoch = datasource.getData().get(0);

        assertEquals(AUTHENTICATION_TOKEN, raw.getAuthenticationToken());
        assertEquals(DATA_SOURCE, datasource.getDataSource());
        assertEquals(START_TIMESTAMP_UNIX_LONG, dynamicEpoch.getStartTimestampUnix());
        assertEquals(END_TIMESTAMP_UNIX_LONG, dynamicEpoch.getEndTimestampUnix());
        assertEquals(CREATED_AT_UNIX_LONG, dynamicEpoch.getCreatedAtUnix());
        assertEquals(DYNAMIC_VALUE_TYPE, dynamicEpoch.getDynamicValueType());
        assertEquals(HEART_RATE, dynamicEpoch.getHeartRate());
        assertEquals(VALUE_TYPE, dynamicEpoch.getValueType());
    }

    @Test
    public void getRawData_byIdAndDatasource_success(){
        when(dataInputRepository.findById(AUTHENTICATION_TOKEN)).thenReturn(createDataInputReturn());

        var raw=dynamicEpochOutputServiceImpl.getRawData(AUTHENTICATION_TOKEN, DATA_SOURCE, null, null);
        var datasource = raw.getDataSources().get(0);
        var dynamicEpoch = datasource.getData().get(0);

        assertEquals(AUTHENTICATION_TOKEN, raw.getAuthenticationToken());
        assertEquals(DATA_SOURCE, datasource.getDataSource());
        assertEquals(START_TIMESTAMP_UNIX_LONG, dynamicEpoch.getStartTimestampUnix());
        assertEquals(END_TIMESTAMP_UNIX_LONG, dynamicEpoch.getEndTimestampUnix());
        assertEquals(CREATED_AT_UNIX_LONG, dynamicEpoch.getCreatedAtUnix());
        assertEquals(DYNAMIC_VALUE_TYPE, dynamicEpoch.getDynamicValueType());
        assertEquals(HEART_RATE, dynamicEpoch.getHeartRate());
        assertEquals(VALUE_TYPE, dynamicEpoch.getValueType());
    }

    @Test
    public void getRawData_byIdAndDatasourceEmpty_success(){
        when(dataInputRepository.findById(AUTHENTICATION_TOKEN)).thenReturn(createDataInputReturn());

        var raw=dynamicEpochOutputServiceImpl.getRawData(AUTHENTICATION_TOKEN, 9, null, null);

        assertEquals(AUTHENTICATION_TOKEN, raw.getAuthenticationToken());
        assertEquals(0, raw.getDataSources().size());
    }

    @Test
    public void getHeartRate_byId_success(){
        when(dataInputRepository.findById(AUTHENTICATION_TOKEN)).thenReturn(createDataInputReturn());

        var raw=dynamicEpochOutputServiceImpl.getHeartRate(AUTHENTICATION_TOKEN, null, null, null);

        assertEquals(Double.valueOf(HEART_RATE), raw);
    }

    @Test
    public void getHeartRate_byIdByDate_success(){
        when(dataInputRepository.findById(AUTHENTICATION_TOKEN)).thenReturn(createDataInputReturn());

        var raw=dynamicEpochOutputServiceImpl.getHeartRate(AUTHENTICATION_TOKEN, null, new Date(974482820000L), new Date(1668706820000L));

        assertEquals(Double.valueOf(HEART_RATE), raw);
    }

    @Test
    public void getHeartRate_byIdByDateOutOfRange_success(){
        when(dataInputRepository.findById(AUTHENTICATION_TOKEN)).thenReturn(createDataInputReturn());

        var raw=dynamicEpochOutputServiceImpl.getHeartRate(AUTHENTICATION_TOKEN, null, new Date(974482820000L), new Date(974482820000L));

        assertEquals(0L, raw);
    }

    private Optional<DataInputEntity> createDataInputReturn() {
        var dynamicEpochValue = DynamicEpochValueEntity.builder()
                .startTimestampUnix(START_TIMESTAMP_UNIX)
                .endTimestampUnix(END_TIMESTAMP_UNIX)
                .createdAtUnix(CREATED_AT_UNIX)
                .valueType(VALUE_TYPE)
                .dynamicValueType(DYNAMIC_VALUE_TYPE)
                .heartRate(HEART_RATE)
                .build();
        var dataSource = DataSourceEntity.builder().dataSource(DATA_SOURCE).data(List.of(dynamicEpochValue)).build();

        return Optional.of(DataInputEntity.builder().authenticationToken(AUTHENTICATION_TOKEN).dataSources(List.of(dataSource)).build());
    }
}
