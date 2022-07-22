package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZePorCoordenadasGateway;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ParceiroZePorCoordenadaDataprovider implements ParceiroZePorCoordenadasGateway {
    public static final String ERRO_FIND_BY_COORDINATES = "Erro ao pesquisar parceiro próximo à coordenada: [%s]";
    private static final int PAGESIZE = 1;
    private final ParceiroZeDataproviderMapper parceiroZeDataproviderMapper;
    private final ParceiroZeRepository parceiroZeRepository;

    @Override
    public List<ParceiroZeDataproviderDto> buscarParceiroZePorCoordenadas(CoordenadaCliente coordenadaCliente) {
        Double[] coordenadas = {coordenadaCliente.getLongitude(), coordenadaCliente.getLatitude()};
        try {
            var parceiroZeEntity = parceiroZeRepository.findByAddressCoordinatesNear(coordenadas, PageRequest.ofSize(PAGESIZE));
            return parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(parceiroZeEntity);
        } catch (MongoException e) {
            log.error(String.format(ERRO_FIND_BY_COORDINATES, coordenadas), e);
            throw new InternalServerErrorException(String.format(ERRO_FIND_BY_COORDINATES, coordenadas));
        }
    }
}
