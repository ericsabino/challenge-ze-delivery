package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ParceiroZeDataproviderTest {

    @InjectMocks
    private ParceiroZeDataprovider dataprovider;
    @Mock
    private ParceiroZeDataproviderMapper parceiroZeDataproviderMapper;
    @Mock
    private ParceiroZeRepository parceiroZeRepository;

    @Test
    void testSalvarParceiroZe() {
        when(parceiroZeDataproviderMapper.parceiroZeDataproviderDtoToParceiroZeEntity(any())).thenReturn(ParceiroZeEntity.builder().build());

        dataprovider.salvarParceiroZe(any());

        verify(parceiroZeRepository, times(1)).insert(any(ParceiroZeEntity.class));
    }
}