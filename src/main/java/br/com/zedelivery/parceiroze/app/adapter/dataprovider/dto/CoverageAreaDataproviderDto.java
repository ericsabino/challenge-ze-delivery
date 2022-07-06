package br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoverageAreaDataproviderDto implements Serializable {
    private static final long serialVersionUID = 7546504898450540147L;
    private String type;
    private List<List<List<List<Double>>>> coordinates;
}
