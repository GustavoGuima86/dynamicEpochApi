package com.example.dynamicEpoch.controllers;

import com.example.dynamicEpoch.DTO.output.DataOutputDTO;
import com.example.dynamicEpoch.services.DynamicEpochOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/dynamicepochapi")
public class DynamicEpochApi {

    @Autowired
    DynamicEpochOutputService dynamicEpochOutputService;

    @GetMapping("/raw-data/{id}")
    public DataOutputDTO getRawData(@PathVariable String id,
                                    @RequestParam(required = false) Integer dataSource,
                                    @RequestParam(required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
                                    @RequestParam(required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate){
        return dynamicEpochOutputService.getRawData(id, dataSource, fromDate, toDate);
    }

    @GetMapping("/heartrate/{id}")
    public Double getHeartRate(@PathVariable String id,
                                 @RequestParam(required = false) Integer dataSource,
                                 @RequestParam(required = false)
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
                                 @RequestParam(required = false)
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate){
        return dynamicEpochOutputService.getHeartRate(id, dataSource, fromDate, toDate);
    }


}
