package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.configuration.exception.BusinessException;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import com.mongodb.MongoException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.getParceiroZeDataproviderDto;
import static br.com.zedelivery.parceiroze.mocks.MapperMocks.getParceiroZeEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ParceiroZePorIdDataproviderTest {
    @InjectMocks
    private ParceiroZePorIdDataprovider dataprovider;
    @Mock
    private ParceiroZeDataproviderMapper parceiroZeDataproviderMapper;
    @Mock
    private ParceiroZeRepository parceiroZeRepository;

    @Test
    public void testDeveBuscarParceiroZePorId() {
        when(parceiroZeRepository.findById(anyString())).thenReturn(Optional.of(getParceiroZeEntity()));
        when(parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(Arrays.asList(getParceiroZeEntity()))).thenReturn(Arrays.asList(getParceiroZeDataproviderDto()));
        var parceiroZeDataproviderDto = dataprovider.buscarParceiroZePorID(ParceiroZeDataproviderDto.builder().id("001").build());

        Assert.assertEquals(getParceiroZeDataproviderDto(), parceiroZeDataproviderDto);
    }

    @Test(expected = InternalServerErrorException.class)
    public void testDeveRetornarInternalServerErrorExceptionAoBuscarPorId() {
        when(parceiroZeRepository.findById(anyString())).thenThrow(MongoException.class);
        dataprovider.buscarParceiroZePorID(ParceiroZeDataproviderDto.builder().id("001").build());

        verify(parceiroZeRepository, times(1)).findById(anyString());
    }

    @Test(expected = BusinessException.class)
    public void testDeveRetornarBusinessExceptionAoBuscarPorId() {
        when(parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(any())).thenReturn(null);
        dataprovider.buscarParceiroZePorID(ParceiroZeDataproviderDto.builder().id("001").build());

    }

}