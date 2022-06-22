package br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParceiroZeDataproviderDto {
    private String id;
    private String tradingName;
    private String ownerName;
    private String document;
    private CoverageAreaDataproviderDto coverageArea;
    private AddressDataproviderDto address;
}
