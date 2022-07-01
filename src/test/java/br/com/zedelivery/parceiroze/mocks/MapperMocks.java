package br.com.zedelivery.parceiroze.mocks;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.AddressDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.CoverageAreaDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.AddressEntity;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.CoverageAreaEntity;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.AddressDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.CoverageAreaDto;
import br.com.zedelivery.parceiroze.core.usecase.model.Address;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import br.com.zedelivery.parceiroze.core.usecase.model.CoverageArea;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;

import java.util.Arrays;

public class MapperMocks {

    public static AddressDto getAddressDto() {
        var lon = -10.0;
        var lat = 15.0;
        return AddressDto.builder()
                .type("POINT")
                //.geohash("ef8vk6w")
                .coordinates(new Double[]{lon, lat})
                .build();
    }

    public static ParceiroZe getParceiroZeModel() {
        return ParceiroZe.builder()
                .id("002")
                .document("99.999.999/9999-00")
                .tradingName("Zé")
                .ownerName("Zé Delivery")
                .address(getAddressModel())
                .coverageArea(getCoverageModel())
                .build();
    }

    public static Address getAddressModel() {
        var lon = -10.0;
        var lat = 15.0;
        return Address.builder()
                .type("POINT")
                //.geohash("ef8vk6w")
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
                .coordinates(Arrays.asList(Arrays.asList(Arrays.asList(Arrays.asList(coordenates)))))
                .build();
    }

    public static ParceiroZeEntity getParceiroZeEntity(){
        return  ParceiroZeEntity
                .builder()
                .id("001")
                .document("15.127.213/0001-56")
                .ownerName("Parceiro Ze")
                .tradingName("Ze Delivery")
                .address(AddressEntity.builder().type("Point").build())
                .coverageArea(CoverageAreaEntity.builder().type("MultiPolygon").build())
                .build();
    }

    public static ParceiroZeDataproviderDto getParceiroZeDataproviderDto(){
        return  ParceiroZeDataproviderDto
                .builder()
                .id("001")
                .document("15.127.213/0001-56")
                .ownerName("Parceiro Ze")
                .tradingName("Ze Delivery")
                .address(AddressDataproviderDto.builder().type("Point").build())
                .coverageArea(CoverageAreaDataproviderDto.builder().type("MultiPolygon").build())
                .build();
    }

    public static CoordenadaCliente getCoordenadaCliente() {
        return CoordenadaCliente.builder().longitude(-10d).latitude(2d).build();
    }
}
