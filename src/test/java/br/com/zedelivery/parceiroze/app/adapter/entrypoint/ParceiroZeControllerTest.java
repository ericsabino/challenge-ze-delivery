package br.com.zedelivery.parceiroze.app.adapter.entrypoint;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.CoordenadaClienteMapper;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.ParceiroZeMapper;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZeUsecase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.hamcrest.Matchers.is;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ParceiroZeControllerTest {

    private static final String URI_PARCEIRO = "/ze/v1/parceiros";
    private static final String URI_PARCEIRO_ID = "/ze/v1/parceiros/{id}";
    private static final String URI_PARCEIRO_POR_COORDENADAS = "/ze/v1/parceiros";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParceiroZeMapper parceiroZeMapper;
    @MockBean
    private CoordenadaClienteMapper coordenadaClienteMapper;
    @MockBean
    private ParceiroZeUsecase parceiroZeUsecase;

    @Test
    public void testCadastrarParceiroZe() throws Exception {
        String requestValid = "{\r\n  \"tradingName\": \"Adega da Cerveja - Pinheiros\",\r\n  \"ownerName\": \"Zé da Silva\",\r\n  \"document\": \"1432132123891/0001\",\r\n  \"coverageArea\": { \r\n    \"type\": \"MultiPolygon\",\r\n    \"coordinates\": [    [[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]],    \r\n                        [[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]],     \r\n                         [[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]    \r\n                         ]\r\n  },\r\n  \"address\": { \r\n    \"type\": \"Point\",\r\n    \"coordinates\": [-46.57421, -21.785741]\r\n  }\r\n}";
        when(parceiroZeMapper.parceiroZeDtoToParceiroZeModel(any())).thenReturn(getParceiroZeModel());

        mockMvc.perform(post(URI_PARCEIRO).contentType(MediaType.APPLICATION_JSON)
                .content(requestValid))
                .andExpect(status().isCreated());
    }

    @Test
    public void testBuscarParceiroPorId() throws Exception {
        when(parceiroZeUsecase.buscarParceiroPorId(anyString())).thenReturn(getParceiroZeModel());
        when(parceiroZeMapper.parceiroZeModelToParceiroZeDto(any())).thenReturn(getParceiroZeDto());

        mockMvc.perform(get(URI_PARCEIRO_ID, "002")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void testBuscarParceiroPorCoordenadas() throws Exception {
        when(parceiroZeUsecase.buscarParceirosProximoPorCoordenadas(any())).thenReturn(getParceiroZeModel());
        when(coordenadaClienteMapper.coordenadaClienteDtoToCoordenadaClienteModel(any(), any())).thenReturn(getCoordenadaCliente());
        when(parceiroZeMapper.parceiroZeModelToParceiroZeDto(any())).thenReturn(getParceiroZeDto());

        mockMvc.perform(get(URI_PARCEIRO_POR_COORDENADAS)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("longitude", "-10")
                .queryParam("latitude", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("002")))
                .andExpect(jsonPath("$.document", is("99.999.999/9999-00")))
                .andExpect(jsonPath("$.coverageArea.type", is("MultiPolygon")))
                .andExpect(jsonPath("$.address.type", is("POINT")));
    }
}