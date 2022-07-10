package com.example.dynamicEpoch.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DataInputEntity {

    @Id
    private String authenticationToken;

    @OneToMany(mappedBy = "dataInput", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<DataSourceEntity> dataSources;
}
