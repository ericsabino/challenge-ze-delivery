package br.com.zedelivery.parceiroze.app.adapter.entrypoint;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.CoordenadaClienteMapper;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.ParceiroZeMapper;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZePorCoordenadaUsecase;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZePorIdUsecase;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZeUsecase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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
    private ParceiroZePorIdUsecase parceiroZePorIdUsecase;
    @MockBean
    private ParceiroZePorCoordenadaUsecase parceiroZePorCoordenadaUsecase;
    @MockBean
    private ParceiroZeUsecase parceiroZeUsecase;

    @Test
    public void testCadastrarParceiroZe() throws Exception {
        String requestValid = "{\r\n  \"tradingName\": \"Adega da Cerveja - Pinheiros\",\r\n  \"ownerName\": \"Zé da Silva\",\r\n  \"document\": \"1432132123891/0001\",\r\n  \"coverageArea\": { \r\n    \"type\": \"MultiPolygon\",\r\n    \"coordinates\": [    [[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]],    \r\n                        [[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]],     \r\n                         [[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]    \r\n                         ]\r\n  },\r\n  \"address\": { \r\n    \"type\": \"Point\",\r\n    \"coordinates\": [-46.57421, -21.785741]\r\n  }\r\n}";
        when(parceiroZeMapper.parceiroZeDtoToParceiroZeModel(any())).thenReturn(getParceiroZeModel());

        mockMvc.perform(post(URI_PARCEIRO).contentType(MediaType.APPLICATION_JSON)
                .content(requestValid))
                .andExpect(status().isCreated());

        verify(parceiroZeUsecase, times(1)).cadastrar(getParceiroZeModel());
    }

    @Test
    public void testBuscarParceiroPorId() throws Exception {
        when(parceiroZePorIdUsecase.buscarParceiroPorId(anyString())).thenReturn(getParceiroZeModel());
        when(parceiroZeMapper.parceiroZeModelToParceiroZeDto(any())).thenReturn(Arrays.asList(getParceiroZeDto()));

        mockMvc.perform(get(URI_PARCEIRO_ID, "002")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        verify(parceiroZePorIdUsecase, times(1)).buscarParceiroPorId(anyString());
    }

    @Test
    public void testBuscarParceiroPorCoordenadas() throws Exception {
        when(parceiroZePorCoordenadaUsecase.buscarParceirosProximoPorCoordenadas(any())).thenReturn(Arrays.asList(getParceiroZeModel()));
        when(coordenadaClienteMapper.coordenadaClienteDtoToCoordenadaClienteModel(any(), any())).thenReturn(getCoordenadaCliente());
        when(parceiroZeMapper.parceiroZeModelToParceiroZeDto(any())).thenReturn(Arrays.asList(getParceiroZeDto()));

        mockMvc.perform(get(URI_PARCEIRO_POR_COORDENADAS)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("longitude", "-10")
                .queryParam("latitude", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is("002")))
                .andExpect(jsonPath("$.[0].document", is("99.999.999/9999-00")))
                .andExpect(jsonPath("$.[0].coverageArea.type", is("MultiPolygon")))
                .andExpect(jsonPath("$.[0].address.type", is("POINT")));

        verify(parceiroZePorCoordenadaUsecase, times(1)).buscarParceirosProximoPorCoordenadas(any());
    }
}