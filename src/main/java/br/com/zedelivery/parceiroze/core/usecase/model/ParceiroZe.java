package br.com.zedelivery.parceiroze.core.usecase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParceiroZe {
    private String id;
    private String tradingName;
    private String ownerName;
    private String document;
    private CoverageArea coverageArea;
    private Address address;
}
