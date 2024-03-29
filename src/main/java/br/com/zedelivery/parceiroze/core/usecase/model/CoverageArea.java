package br.com.zedelivery.parceiroze.core.usecase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoverageArea {
    private String type;
    private List<List<List<List<Double>>>> coordinates;

}