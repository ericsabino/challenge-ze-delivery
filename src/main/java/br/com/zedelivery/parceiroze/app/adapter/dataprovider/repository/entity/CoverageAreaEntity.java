package br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity;

import lombok.Data;

import java.util.List;

@Data
public class CoverageAreaEntity {
    private String type;
    private List<List<List<List<Double>>>> coordinates;
}
