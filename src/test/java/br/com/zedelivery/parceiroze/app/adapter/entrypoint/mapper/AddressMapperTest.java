package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.impl.AddressMapperImpl;
import org.junit.jupiter.api.Test;

import static br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.mocks.MapperMocks.getAddressDto;
import static br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.mocks.MapperMocks.getAddressModel;
import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private AddressMapper mapper = new AddressMapperImpl();

    @Test
    public void testAddressDtoToAddressModel() {
        var result = mapper.addressDtoToAddressModel(getAddressDto());
        assertNotNull(result);
        assertEquals("POINT", result.getType());
        assertEquals("ef8vk6w", result.getGeohash());
        assertEquals(-10.0, result.getCoordinates()[0]);
        assertEquals(15.0, result.getCoordinates()[1]);
    }

    @Test
    public void testAddressModelToAddressDto() {
        var result = mapper.addressModelToAddressDto(getAddressModel());
        assertNotNull(result);
        assertEquals("POINT", result.getType());
        assertEquals("ef8vk6w", result.getGeohash());
        assertEquals(-10.0, result.getCoordinates()[0]);
        assertEquals(15.0, result.getCoordinates()[1]);
    }

    @Test
    public void testAddressDtoToAddressModelNull() {
        var result = mapper.addressDtoToAddressModel(null);
        assertNull(result);
    }

    @Test
    public void testAddressModelToAddressDtoNull() {
        var result = mapper.addressModelToAddressDto(null);
        assertNull(result);
    }

}