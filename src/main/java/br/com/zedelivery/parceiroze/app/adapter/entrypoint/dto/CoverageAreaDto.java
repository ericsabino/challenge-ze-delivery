package br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.geojson.Point;
import org.springframework.data.mongodb.core.geo.GeoJson;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoverageAreaDto {
    private String type;
    private List<List<List<List<Double>>>> coordinates;

}