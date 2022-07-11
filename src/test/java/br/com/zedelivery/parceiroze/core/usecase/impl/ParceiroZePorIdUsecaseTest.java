package br.com.zedelivery.parceiroze.core.usecase.impl;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZePorIdGateway;
import br.com.zedelivery.parceiroze.core.usecase.mapper.ParceiroZeUsecaseMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.getParceiroZeDataproviderDto;
import static br.com.zedelivery.parceiroze.mocks.MapperMocks.getParceiroZeModel;
import static org.mockito.Mockito.*;

@SpringBootTest
class ParceiroZePorIdUsecaseTest {

    @InjectMocks
    private ParceiroZePorIdUsecaseImpl parceiroZeUsecase;
    @Mock
    private ParceiroZePorIdGateway parceiroZeGateway;
    @Mock
    private ParceiroZeUsecaseMapper parceiroZeUsecaseMapper;

    @Test
    void testDeveCadastrarParceiroPorId() {
        ParceiroZeDataproviderDto build = ParceiroZeDataproviderDto.builder().id("003").build();
        when(parceiroZeUsecaseMapper.identificadorToParceiroZeDataproviderDto(anyString())).thenReturn(build);
        when(parceiroZeGateway.buscarParceiroZePorID(build)).thenReturn(getParceiroZeDataproviderDto());
        when(parceiroZeUsecaseMapper.parceiroZeDataproviderDtoToParceiroZeModel(Arrays.asList(getParceiroZeDataproviderDto()))).thenReturn(Arrays.asList(getParceiroZeModel()));
        ParceiroZe parceiroZe = parceiroZeUsecase.buscarParceiroPorId(anyString());

        verify(parceiroZeGateway, times(1)).buscarParceiroZePorID(build);
        Assert.assertEquals(getParceiroZeModel(), parceiroZe);
    }
}