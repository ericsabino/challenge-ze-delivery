package br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParceiroZeDto {
    private float id;
    private String tradingName;
    private String ownerName;
    private String document;
    private CoverageAreaDto coverageArea;
    private AddressDto address;
}
