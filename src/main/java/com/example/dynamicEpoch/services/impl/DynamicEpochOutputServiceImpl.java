package com.example.dynamicEpoch.services.impl;

import com.example.dynamicEpoch.DTO.output.DataOutputDTO;
import com.example.dynamicEpoch.entities.DataInputEntity;
import com.example.dynamicEpoch.entities.DataSourceEntity;
import com.example.dynamicEpoch.entities.DynamicEpochValueEntity;
import com.example.dynamicEpoch.repositories.DataInputRepository;
import com.example.dynamicEpoch.services.DynamicEpochOutputService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
@Slf4j
public class DynamicEpochOutputServiceImpl implements DynamicEpochOutputService {

    @Autowired
    private DataInputRepository dataInputRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * return raw data inputted for user + datasource or/and date window
     * @param userId
     * @param dataSource
     * @param fromDate
     * @param toDate
     * @return object contains all inputted data for given filter
     */
    @Override
    public DataOutputDTO getRawData(String userId, Integer dataSource, Date fromDate, Date toDate) {

        var epochDb = dataInputRepository.findById(userId);

        if(epochDb.isPresent()){
            filterDataSource(dataSource, epochDb);
            filterDynamicEpochValueByDate(fromDate, toDate, epochDb);
            return objectMapper.convertValue(epochDb.get(), DataOutputDTO.class);
        }else{
            throw new NoSuchElementException();
        }
    }

    /**
     * return heart rate for user + datasource or/and date window
     * @param userId
     * @param dataSource
     * @param fromDate
     * @param toDate
     * @return the heart rate for the given filter
     */
    @Override
    public Double getHeartRate(String userId, Integer dataSource, Date fromDate, Date toDate) {
        var epochDb = dataInputRepository.findById(userId);

        if(epochDb.isPresent()){
            filterDataSource(dataSource, epochDb);
            filterDynamicEpochValueByDate(fromDate, toDate, epochDb);
            return calculateAverageHeartRate(collectHeartRates(epochDb));
        }else{
            throw new NoSuchElementException();
        }
    }

    private List<Integer> collectHeartRates(Optional<DataInputEntity> epochDb) {
        return epochDb.get().getDataSources()
                .stream()
                .map(dataSourceDb -> dataSourceDb.getData()
                        .stream()
                        .map(DynamicEpochValueEntity::getHeartRate)
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private Double calculateAverageHeartRate(List<Integer> heartRates){
        return heartRates.stream()
             .mapToInt(Integer::intValue)
             .summaryStatistics()
             .getAverage();
    }

    private void filterDynamicEpochValueByDate(Date fromDate, Date toDate, Optional<DataInputEntity> epochDb) {
        if(fromDate != null && toDate != null) {
            Predicate<DynamicEpochValueEntity> streamsPredicateDateBetween = item ->
                    item.getStartTimestampUnix().after(fromDate) && item.getStartTimestampUnix().before(toDate);

            epochDb.get().getDataSources().stream().forEach(dataSourceDb -> {
                dataSourceDb.setData(dataSourceDb.getData().stream()
                        .filter(streamsPredicateDateBetween)
                        .collect(Collectors.toList()));
            });
        }
    }

    private void filterDataSource(Integer dataSource, Optional<DataInputEntity> epochDb) {
        if(dataSource != null) {
            Predicate<DataSourceEntity> streamsPredicateDataSource = item -> item.getDataSource() == dataSource;

            epochDb.get().setDataSources(epochDb.get().getDataSources().stream()
                    .filter(streamsPredicateDataSource)
                    .collect(Collectors.toList()));
        }
    }
}
