package br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity;

import lombok.Data;

@Data
public class AddressEntity {
    private String type;
    private Double[] coordinates;
}
