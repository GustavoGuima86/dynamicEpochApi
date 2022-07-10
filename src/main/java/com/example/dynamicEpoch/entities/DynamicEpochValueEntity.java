package com.example.dynamicEpoch.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DynamicEpochValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private Date startTimestampUnix;

    private Date endTimestampUnix;

    private Date createdAtUnix;

    private Integer dynamicValueType;

    //change the name here because of the name reserved
    //no time now to research and find a fancy solution!!
    @JsonProperty("value")
    private Integer heartRate;

    private String valueType;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dataSource_id", nullable = false)
    @JsonIgnore
    private DataSourceEntity dataSource;
}
