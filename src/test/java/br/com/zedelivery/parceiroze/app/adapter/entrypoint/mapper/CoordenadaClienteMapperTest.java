package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.impl.CoordenadaClienteMapperImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CoordenadaClienteMapperTest {

    private CoordenadaClienteMapper mapper = new CoordenadaClienteMapperImpl();

    @Test
    void testCoordenadaClienteDtoToCoordenadaClienteModel() {
        var result = mapper.coordenadaClienteDtoToCoordenadaClienteModel(-10d, 20d);
        assertNotNull(result);
        assertEquals(-10d, result.getLongitude());
        assertEquals(20d, result.getLatitude());
    }

}