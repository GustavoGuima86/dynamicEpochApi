package com.example.dynamicEpoch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * The goal here is create an event for each DynamicEpochValue given in the input
 * this way we could process a big inputted JSON file in small messages in
 */
public class DynamicEpochValueMessage {


    private String authenticationToken;
    private Integer dataSource;

    private Date startTimestampUnix;

    private Date endTimestampUnix;
    private Date createdAtUnix;
    private Integer dynamicValueType;
    private String value;
    private String valueType;
}
