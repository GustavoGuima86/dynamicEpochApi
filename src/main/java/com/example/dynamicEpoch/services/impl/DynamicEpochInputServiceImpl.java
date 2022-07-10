package com.example.dynamicEpoch.services.impl;

import com.example.dynamicEpoch.DTO.DynamicEpochValueMessage;
import com.example.dynamicEpoch.entities.DataInputEntity;
import com.example.dynamicEpoch.entities.DataSourceEntity;
import com.example.dynamicEpoch.entities.DynamicEpochValueEntity;
import com.example.dynamicEpoch.repositories.DataInputRepository;
import com.example.dynamicEpoch.repositories.DataSourceRepository;
import com.example.dynamicEpoch.repositories.DynamicEpochValueRepository;
import com.example.dynamicEpoch.services.DynamicEpochInputService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Slf4j
public class DynamicEpochInputServiceImpl implements DynamicEpochInputService {

    @Autowired
    private DataInputRepository dataInputRepository;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private DynamicEpochValueRepository dynamicEpochValueRepository;

    /**
     * execute the action to validate or create the entry of epoch data
     * @param messageInput
     */
    @Override
    @Transactional
    public void inputDataDynamicEpoch(DynamicEpochValueMessage messageInput) {
        log.info("inserting new DynamicEpochValue {}", messageInput.getAuthenticationToken());

        //I'm assuming that the token is the user identification in the DB, it should be available
        var dataInputDB = dataInputRepository.findById(messageInput.getAuthenticationToken());

        if(dataInputDB.isPresent()){
            filterCreateDatasource(messageInput, dataInputDB);
        }else{
            var dataInputDb = createSaveDataInput(messageInput);
            var dataSourceInput = createSaveDataSource(messageInput, dataInputDb);
            createSaveDynamicEpochValue(messageInput, dataSourceInput);
        }
        log.info("inserted with success new DynamicEpochValue {}", messageInput.getAuthenticationToken());
    }

    private void filterCreateDatasource(DynamicEpochValueMessage messageInput, Optional<DataInputEntity> dataInputDB) {
        Predicate<DataSourceEntity> streamsPredicate = item -> item.getDataSource() == messageInput.getDataSource();

        var dataSourceDB = dataInputDB.get().getDataSources().stream()
                .filter(streamsPredicate)
                .findFirst();
        if(dataSourceDB.isPresent()){
            createSaveDynamicEpochValue(messageInput, dataSourceDB.get());
        }else{
            DataSourceEntity dataSourceInput = createSaveDataSource(messageInput, dataInputDB.get());
            createSaveDynamicEpochValue(messageInput, dataSourceInput);
        }
    }

    private DataInputEntity createSaveDataInput(DynamicEpochValueMessage messageInput) {
        var dataInput = DataInputEntity.builder()
                .authenticationToken(messageInput.getAuthenticationToken())
                .build();
        return dataInputRepository.save(dataInput);
    }

    private DataSourceEntity createSaveDataSource(DynamicEpochValueMessage messageInput, DataInputEntity dataInputDb) {
        var dataSourceInput = DataSourceEntity.builder()
                .dataSource(messageInput.getDataSource())
                .dataInput(dataInputDb)
                .build();
        return dataSourceRepository.save(dataSourceInput);
    }

    private DynamicEpochValueEntity createSaveDynamicEpochValue(DynamicEpochValueMessage messageInput, DataSourceEntity dataSourceInput) {
        var dynamicEpochInputDb = DynamicEpochValueEntity.builder()
                .createdAtUnix(messageInput.getCreatedAtUnix())
                .startTimestampUnix(messageInput.getStartTimestampUnix())
                .endTimestampUnix(messageInput.getEndTimestampUnix())
                //should create a proper way to convert it
                .heartRate(Integer.valueOf(messageInput.getValue()))
                .dynamicValueType(messageInput.getDynamicValueType())
                .valueType(messageInput.getValueType())
                .dataSource(dataSourceInput)
                .build();
        return dynamicEpochValueRepository.save(dynamicEpochInputDb);
    }
}

