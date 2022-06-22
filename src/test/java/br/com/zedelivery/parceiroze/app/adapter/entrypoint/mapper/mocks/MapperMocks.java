package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.mocks;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.AddressDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.CoverageAreaDto;
import br.com.zedelivery.parceiroze.core.usecase.model.Address;
import br.com.zedelivery.parceiroze.core.usecase.model.CoverageArea;

import java.util.Arrays;

public class MapperMocks {

    public static AddressDto getAddressDto() {
        var lon = -10.0;
        var lat = 15.0;
        return AddressDto.builder()
                .type("POINT")
                .geohash("ef8vk6w")
                .coordinates(new Double[]{lon, lat})
                .build();
    }

    public static Address getAddressModel() {
        var lon = -10.0;
        var lat = 15.0;
        return Address.builder()
                .type("POINT")
                .geohash("ef8vk6w")
                .coordinates(new Double[]{lon, lat})
                .build();
    }

    public static CoverageAreaDto getCoverageDto() {
        Double[] coordenates = {-10.0, 15.0};
        return CoverageAreaDto
                .builder()
                .type("MultiPolygon")
                .coordinates( Arrays.asList(Arrays.asList(Arrays.asList(Arrays.asList(coordenates)))))
                .build();
    }

    public static CoverageArea getCoverageModel() {
        Double[] coordenates = {-10.0, 15.0};
        return CoverageArea
                .builder()
                .type("MultiPolygon")
                .coordinates( Arrays.asList(Arrays.asList(Arrays.asList(Arrays.asList(coordenates)))))
                .build();
    }
}
