package br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoverageAreaDataproviderDto {
    private String type;
    private List<List<List<List<Double>>>> coordinates;
}
