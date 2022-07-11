package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.impl.CoverageAreaMapperImpl;
import org.junit.jupiter.api.Test;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.getCoverageDto;
import static br.com.zedelivery.parceiroze.mocks.MapperMocks.getCoverageModel;
import static org.junit.jupiter.api.Assertions.*;

class CoverageAreaMapperTest {

    CoverageAreaMapper mapper = new CoverageAreaMapperImpl();

    @Test
    public void testCoverageAreaDtoToCovarageAreaModel() {
        var result = mapper.coverageAreaDtoToCovarageAreaModel(getCoverageDto());
        assertNotNull(result);
        assertEquals("MultiPolygon", result.getType());
        assertNotNull(result.getCoordinates());
    }

    @Test
    public void testCoverageAreaDtoToCovarageAreaModelNull() {
        var result = mapper.coverageAreaDtoToCovarageAreaModel(null);
        assertNull(result);
    }

    @Test
    public void testCovarageAreaModelToCovarageAreaDto() {
        var result = mapper.covarageAreaModelToCovarageAreaDto(getCoverageModel());
        assertNotNull(result);
        assertEquals("MultiPolygon", result.getType());
        assertNotNull(result.getCoordinates());
    }

    @Test
    public void testCovarageAreaModelToCovarageAreaDtoNull() {
        var result = mapper.covarageAreaModelToCovarageAreaDto(null);
        assertNull(result);
    }

}