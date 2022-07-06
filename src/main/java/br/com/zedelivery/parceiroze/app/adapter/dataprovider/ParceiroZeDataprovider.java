package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.configuration.RedisConfig;
import br.com.zedelivery.parceiroze.app.configuration.exception.BusinessException;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZeGateway;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ParceiroZeDataprovider implements ParceiroZeGateway {

    public static final String ERRO_INSERT = "Erro ao inserir na base. Parceiro Zé [%s / %s]";
    public static final String ID_NOT_FOUND = "Identificador [%s] não encontrado";
    public static final String ERRO_FIND_BY_ID = "ParceiroZe não encontrado com Identificador: [%s]";
    public static final String ERRO_FIND_BY_COORDINATES = "Erro ao pesquisar parceiro próximo à coordenada: [%s]";
    private static final String PARCEIRO_ZE_CACHE = "parceiroze";
    private static final String PARCEIRO_ZE_CACHE_ERRO = "Cache de ParceiroZe não encontrado";
    private static final String CACHE_MANAGER = RedisConfig.CACHE_MANAGER_ZE_DELIVERY;

    private final ParceiroZeDataproviderMapper parceiroZeDataproviderMapper;

    private final ParceiroZeRepository parceiroZeRepository;

    private final CacheManager cacheManager;

    @Override
    public void salvarParceiroZe(ParceiroZeDataproviderDto parceiroZeDataproviderDto) {
        var parceiroZeEntity = parceiroZeDataproviderMapper.parceiroZeDataproviderDtoToParceiroZeEntity(parceiroZeDataproviderDto);
        try {
            parceiroZeRepository.insert(parceiroZeEntity);
        } catch (RuntimeException e) {
            log.error(String.format(ERRO_INSERT, parceiroZeEntity.getDocument(), parceiroZeEntity.getOwnerName()), e);
            throw new InternalServerErrorException(String.format(ERRO_INSERT, parceiroZeEntity.getDocument(), parceiroZeEntity.getOwnerName()));
        }
    }

    @Override
    public ParceiroZeDataproviderDto buscarParceiroZePorID(ParceiroZeDataproviderDto parceiroZeDataproviderDto) {
        try {
            var parceiroResult = parceiroZeRepository.findById(parceiroZeDataproviderDto.getId());
            if(parceiroResult.isPresent()) {
                return parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(parceiroResult.get());
            } else {
                throw new BusinessException(String.format(ID_NOT_FOUND, parceiroZeDataproviderDto.getId()));
            }
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
