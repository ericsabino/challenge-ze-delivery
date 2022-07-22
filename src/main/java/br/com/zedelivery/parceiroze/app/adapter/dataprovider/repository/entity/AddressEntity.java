package br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    private String type;
    @GeoSpatialIndexed
    private Double[] coordinates;
}
