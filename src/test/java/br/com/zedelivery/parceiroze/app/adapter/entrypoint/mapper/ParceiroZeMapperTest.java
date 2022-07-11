package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.impl.ParceiroZeMapperImpl;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ParceiroZeMapperTest {

    @InjectMocks
    private ParceiroZeMapperImpl mapper;
    @Mock
    private AddressMapper addressMapper ;
    @Mock
    private CoverageAreaMapper coverageAreaMapper ;

    @Test
    public void testParceiroZeDtoToParceiroZeModel() {
        when(addressMapper.addressDtoToAddressModel(any())).thenReturn(getAddressModel());
        when(coverageAreaMapper.coverageAreaDtoToCovarageAreaModel(any())).thenReturn(getCoverageModel());

        var result = mapper.parceiroZeDtoToParceiroZeModel(getParceiroZeDto());
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("1432132123891/0001", result.getDocument());
        assertEquals("Adega da Cerveja - Pinheiros", result.getTradingName());
        assertEquals("Zé da Silva", result.getOwnerName());
        assertEquals(getCoverageModel(), result.getCoverageArea());
        assertEquals(getAddressModel(), result.getAddress());
    }

    private ParceiroZeDto getParceiroZeDto() {
        return ParceiroZeDto
                .builder()
                .id("1")
                .document("1432132123891/0001")
                .tradingName("Adega da Cerveja - Pinheiros")
                .ownerName("Zé da Silva")
                .coverageArea(getCoverageDto())
                .address(getAddressDto())
                .build();
    }

    @Test
    public void testParceiroZeModelToParceiroZeDto() {
        when(addressMapper.addressModelToAddressDto(any())).thenReturn(getAddressDto());
        when(coverageAreaMapper.covarageAreaModelToCovarageAreaDto(any())).thenReturn(getCoverageDto());

        var result = mapper.parceiroZeModelToParceiroZeDto(Arrays.asList(getParceiroZeModel()));
        assertNotNull(result);
        result.forEach(r -> {
            assertEquals("1", r.getId());
            assertEquals("1432132123891/0001", r.getDocument());
            assertEquals("Adega da Cerveja - Pinheiros", r.getTradingName());
            assertEquals("Zé da Silva", r.getOwnerName());
            assertEquals(getCoverageDto(), r.getCoverageArea());
            assertEquals(getAddressDto(), r.getAddress());
        });
    }

    @Test
    public void testParceiroZeModelNulo() {
        var parceiroZe = mapper.parceiroZeDtoToParceiroZeModel(null);
        assertNull(parceiroZe);
    }

    @Test
    public void testParceiroZeDtoNulo() {
        var parceiroZeDto = mapper.parceiroZeModelToParceiroZeDto(null);
        assertNull(parceiroZeDto);
    }

    private ParceiroZe getParceiroZeModel() {
        return ParceiroZe
                .builder()
                .id("1")
                .document("1432132123891/0001")
                .tradingName("Adega da Cerveja - Pinheiros")
                .ownerName("Zé da Silva")
                .coverageArea(getCoverageModel())
                .address(getAddressModel())
                .build();
    }
}