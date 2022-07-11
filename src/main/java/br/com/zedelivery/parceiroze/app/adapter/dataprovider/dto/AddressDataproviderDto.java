package br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDataproviderDto implements Serializable {
    private static final long serialVersionUID = 7546504898450540148L;
    private String type;
    private Double[] coordinates;
}
