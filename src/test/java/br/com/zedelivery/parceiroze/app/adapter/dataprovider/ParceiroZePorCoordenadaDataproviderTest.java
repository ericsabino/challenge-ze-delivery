package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
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

import java.util.Arrays;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ParceiroZePorCoordenadaDataproviderTest {
    @InjectMocks
    private ParceiroZePorCoordenadaDataprovider dataprovider;
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
    public void testDeveBuscarParceiroZePorCoordenadas() {
        when(parceiroZeRepository.findByAddressCoordinatesNear(any(), any())).thenReturn(Arrays.asList(getParceiroZeEntity(), getParceiroZeEntity()));
        when(parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(Arrays.asList(getParceiroZeEntity(), getParceiroZeEntity()))).thenReturn(Arrays.asList(getParceiroZeDataproviderDto()));
        var parceiroZeDataproviderDto = dataprovider.buscarParceiroZePorCoordenadas(getCoordenadaCliente());

        Assert.assertEquals(1, parceiroZeDataproviderDto.size());
        Assert.assertEquals(Arrays.asList(getParceiroZeDataproviderDto()), parceiroZeDataproviderDto);
    }

    @Test(expected = InternalServerErrorException.class)
    public void testDeveRetornarInternalServerErrorExceptionAoBuscarPorCoordenadas() {
        when(parceiroZeRepository.findByAddressCoordinatesNear(any(), any())).thenThrow(MongoException.class);
        dataprovider.buscarParceiroZePorCoordenadas(getCoordenadaCliente());

        verify(parceiroZeRepository, times(1)).findByAddressCoordinatesNear(any(), any());
    }

}