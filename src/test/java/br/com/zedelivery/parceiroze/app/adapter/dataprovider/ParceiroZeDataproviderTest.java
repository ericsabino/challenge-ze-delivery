package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.getParceiroZeEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ParceiroZeDataproviderTest {

    @InjectMocks
    private ParceiroZeDataprovider dataprovider;
    @Mock
    private ParceiroZeDataproviderMapper parceiroZeDataproviderMapper;
    @Mock
    private ParceiroZeRepository parceiroZeRepository;

    @Test
    public void testDeveSalvarParceiroZeComSucesso() {
        when(parceiroZeDataproviderMapper.parceiroZeDataproviderDtoToParceiroZeEntity(any())).thenReturn(Arrays.asList(getParceiroZeEntity()));
        dataprovider.salvarParceiroZe(any());
        verify(parceiroZeRepository, times(1)).insert(any(ParceiroZeEntity.class));
    }

    @Test(expected = InternalServerErrorException.class)
    public void testDeveRetornarInternalServerErrorExceptionAoSalvarParceiro() {
        when(parceiroZeDataproviderMapper.parceiroZeDataproviderDtoToParceiroZeEntity(any())).thenReturn(Arrays.asList(getParceiroZeEntity()));
        when(parceiroZeRepository.insert(any(ParceiroZeEntity.class))).thenThrow(RuntimeException.class);
        dataprovider.salvarParceiroZe(any());
        verify(parceiroZeRepository, times(1)).insert(any(ParceiroZeEntity.class));
    }

}