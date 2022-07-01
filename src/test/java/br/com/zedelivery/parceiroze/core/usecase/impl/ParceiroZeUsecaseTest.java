package br.com.zedelivery.parceiroze.core.usecase.impl;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZeGateway;
import br.com.zedelivery.parceiroze.core.usecase.mapper.ParceiroZeUsecaseMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import br.com.zedelivery.parceiroze.mocks.MapperMocks;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.*;
import static br.com.zedelivery.parceiroze.mocks.MapperMocks.getParceiroZeDataproviderDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ParceiroZeUsecaseTest {

    @InjectMocks
    private ParceiroZeUsecaseImpl parceiroZeUsecase;
    @Mock
    private ParceiroZeGateway parceiroZeGateway;
    @Mock
    private ParceiroZeUsecaseMapper parceiroZeUsecaseMapper;

    @Test
    void testDeveCadastrarParceiro() {
        when(parceiroZeUsecaseMapper.parceiroZeToParceiroZeDataproviderDto(any())).thenReturn(ParceiroZeDataproviderDto.builder().document("MockOk").build());
        parceiroZeUsecase.cadastrarParceiro(any(ParceiroZe.class));
        verify(parceiroZeGateway, times(1)).salvarParceiroZe(any(ParceiroZeDataproviderDto.class));
    }

    @Test
    void testDeveCadastrarParceiroProximoPorCoordenadas() {
        when(parceiroZeUsecaseMapper.parceiroZeDataproviderDtoToParceiroZeModel(any())).thenReturn(getParceiroZeModel());
        when(parceiroZeGateway.buscarParceiroZePorCoordenadas(getCoordenadaCliente())).thenReturn(getParceiroZeDataproviderDto());
        ParceiroZe parceiroZe = parceiroZeUsecase.buscarParceirosProximoPorCoordenadas(getCoordenadaCliente());

        verify(parceiroZeGateway, times(1)).buscarParceiroZePorCoordenadas(getCoordenadaCliente());
        Assert.assertEquals("002", parceiroZe.getId());
        Assert.assertEquals("99.999.999/9999-00", parceiroZe.getDocument());
        Assert.assertEquals("Zé", parceiroZe.getTradingName());
        Assert.assertEquals("Zé Delivery", parceiroZe.getOwnerName());
        Assert.assertEquals(getAddressModel(), parceiroZe.getAddress());
        Assert.assertEquals(getCoverageModel(), parceiroZe.getCoverageArea());
    }

    @Test
    void testDeveCadastrarParceiroPorId() {
        ParceiroZeDataproviderDto build = ParceiroZeDataproviderDto.builder().id("003").build();
        when(parceiroZeUsecaseMapper.identificadorToParceiroZeDataproviderDto(anyString())).thenReturn(build);
        when(parceiroZeGateway.buscarParceiroZePorID(build)).thenReturn(getParceiroZeDataproviderDto());
        when(parceiroZeUsecaseMapper.parceiroZeDataproviderDtoToParceiroZeModel(getParceiroZeDataproviderDto())).thenReturn(getParceiroZeModel());
        ParceiroZe parceiroZe = parceiroZeUsecase.buscarParceiroPorId(anyString());

        verify(parceiroZeGateway, times(1)).buscarParceiroZePorID(build);
        Assert.assertEquals(getParceiroZeModel(), parceiroZe);
    }
}