package br.com.zedelivery.parceiroze.core.usecase.impl;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZeGateway;
import br.com.zedelivery.parceiroze.core.usecase.mapper.ParceiroZeUsecaseMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ParceiroZeUsecaseTest {
    @InjectMocks
    private ParceiroZeUsecaseImpl parceiroZeUsecase;
    @Mock
    private ParceiroZeGateway parceiroZeGateway;
    @Mock
    private ParceiroZeUsecaseMapper parceiroZeUsecaseMapper;

    @Test
    void testDeveCadastrarParceiro() {
        when(parceiroZeUsecaseMapper.parceiroZeToParceiroZeDataproviderDto(any())).thenReturn(ParceiroZeDataproviderDto.builder().document("MockOk").build());
        parceiroZeUsecase.cadastrar(any(ParceiroZe.class));
        verify(parceiroZeGateway, times(1)).salvarParceiroZe(any(ParceiroZeDataproviderDto.class));
    }
}
