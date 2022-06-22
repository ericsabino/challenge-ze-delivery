package br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDataproviderDto {
    private String type;
    private Double[] coordinates;
}
