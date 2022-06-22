package br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoverageAreaEntity {
    private String type;
    private List<List<List<List<Double>>>> coordinates;
}
