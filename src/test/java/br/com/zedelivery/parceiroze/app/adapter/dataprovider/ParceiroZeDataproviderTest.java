package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import com.mongodb.MongoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.*;
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
    @Mock
    private CacheManager cacheManager;

    Cache cache = mock(Cache.class);

    @Before
    public void setup() {
        when(cacheManager.getCache(any())).thenReturn(cache);
    }

    @Test
    public void testDeveSalvarParceiroZeComSucesso() {
        when(parceiroZeDataproviderMapper.parceiroZeDataproviderDtoToParceiroZeEntity(any())).thenReturn(getParceiroZeEntity());
        dataprovider.salvarParceiroZe(any());
        verify(parceiroZeRepository, times(1)).insert(any(ParceiroZeEntity.class));
    }

    @Test(expected = InternalServerErrorException.class)
    public void testDeveRetornarInternalServerErrorExceptionAoSalvarParceiro() {
        when(parceiroZeDataproviderMapper.parceiroZeDataproviderDtoToParceiroZeEntity(any())).thenReturn(getParceiroZeEntity());
        when(parceiroZeRepository.insert(any(ParceiroZeEntity.class))).thenThrow(RuntimeException.class);
        dataprovider.salvarParceiroZe(any());
        verify(parceiroZeRepository, times(1)).insert(any(ParceiroZeEntity.class));
    }

    @Test
    public void testDeveBuscarParceiroZePorId() {
        when(parceiroZeRepository.findById(anyString())).thenReturn(Optional.of(getParceiroZeEntity()));
        when(parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(getParceiroZeEntity())).thenReturn(getParceiroZeDataproviderDto());
        var parceiroZeDataproviderDto = dataprovider.buscarParceiroZePorID(ParceiroZeDataproviderDto.builder().id("001").build());

        Assert.assertEquals(getParceiroZeDataproviderDto(), parceiroZeDataproviderDto);
    }

    @Test(expected = InternalServerErrorException.class)
    public void testDeveRetornarInternalServerErrorExceptionAoBuscarPorId() {
        when(parceiroZeRepository.findById(anyString())).thenThrow(MongoException.class);
        dataprovider.buscarParceiroZePorID(ParceiroZeDataproviderDto.builder().id("001").build());

        verify(parceiroZeRepository, times(1)).findById(anyString());
    }

    @Test
    public void testDeveBuscarParceiroZePorCoordenadas() {
        when(parceiroZeRepository.findByCoverageAreaCoordinates(any())).thenReturn(getParceiroZeEntity());
        when(parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(getParceiroZeEntity())).thenReturn(getParceiroZeDataproviderDto());
        var parceiroZeDataproviderDto = dataprovider.buscarParceiroZePorCoordenadas(getCoordenadaCliente());

        Assert.assertEquals(getParceiroZeDataproviderDto(), parceiroZeDataproviderDto);
    }

    @Test(expected = InternalServerErrorException.class)
    public void testDeveRetornarInternalServerErrorExceptionAoBuscarPorCoordenadas() {
        when(parceiroZeRepository.findByCoverageAreaCoordinates(any())).thenThrow(MongoException.class);
        dataprovider.buscarParceiroZePorCoordenadas(getCoordenadaCliente());

        verify(parceiroZeRepository, times(1)).findByCoverageAreaCoordinates(any());
    }
}