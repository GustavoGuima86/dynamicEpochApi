package com.example.dynamicEpoch.DTO.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSourceDTO {

    private Integer dataSource;

    private List<DynamicEpochValueDTO> data;
}
