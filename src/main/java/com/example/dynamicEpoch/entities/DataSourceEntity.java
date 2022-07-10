package com.example.dynamicEpoch.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class DataSourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private Integer dataSource;

    @OneToMany(mappedBy = "dataSource", fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST)
    private List<DynamicEpochValueEntity> data;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dataSource_id", nullable = false)
    @JsonIgnore
    private DataInputEntity dataInput;

}
