package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZeGateway;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ParceiroZeDataprovider implements ParceiroZeGateway {

    public static final String ERRO_INSERT = "Erro ao inserir na base. Parceiro Zé [%s / %s]";
    public static final String ERRO_FIND_BY_ID = "ParceiroZe não encontrado com Identificador: [%s]";
    public static final String ERRO_FIND_BY_COORDINATES = "Erro ao pesquisar parceiro próximo à coordenada: [%s]";

    private final ParceiroZeDataproviderMapper parceiroZeDataproviderMapper;

    private final ParceiroZeRepository parceiroZeRepository;

    @Override
    public void salvarParceiroZe(ParceiroZeDataproviderDto parceiroZeDataproviderDto) {
        var parceiroZeEntity = parceiroZeDataproviderMapper.parceiroZeDataproviderDtoToParceiroZeEntity(parceiroZeDataproviderDto);
        try {
            parceiroZeRepository.insert(parceiroZeEntity);
        } catch (MongoException e) {
            log.error(String.format(ERRO_INSERT, parceiroZeEntity.getDocument(), parceiroZeEntity.getOwnerName()), e);
            throw new InternalServerErrorException(String.format(ERRO_INSERT, parceiroZeEntity.getDocument(), parceiroZeEntity.getOwnerName()));
        }
    }

    @Override
    public ParceiroZeDataproviderDto buscarParceiroZePorID(ParceiroZeDataproviderDto parceiroZeDataproviderDto) {
        try {
            var parceiroResult = parceiroZeRepository.findById(parceiroZeDataproviderDto.getId());
            return parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(parceiroResult.get());
        } catch (MongoException e) {
            log.error(String.format(ERRO_FIND_BY_ID, parceiroZeDataproviderDto.getId()), e);
            throw new InternalServerErrorException(String.format(ERRO_FIND_BY_ID, parceiroZeDataproviderDto.getId()));
        }
    }

    public ParceiroZeDataproviderDto buscarParceiroZePorCoordenadas(CoordenadaCliente coordenadaCliente) {
        Double[] coordenadas = {coordenadaCliente.getLongitude(), coordenadaCliente.getLatitude()};
        try {
            var parceiroZeEntity = parceiroZeRepository.findByCoverageAreaCoordinates(coordenadas);
            return parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(parceiroZeEntity);
        } catch (MongoException e) {
            log.error(String.format(ERRO_FIND_BY_COORDINATES, coordenadas), e);
            throw new InternalServerErrorException(String.format(ERRO_FIND_BY_COORDINATES, coordenadas));
        }
    }
}
