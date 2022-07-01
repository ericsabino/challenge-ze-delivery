package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZeGateway;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Slf4j
@Component
@AllArgsConstructor
public class ParceiroZeDataprovider implements ParceiroZeGateway {

    public static final String ERRO_INSERT = "Erro ao inserir na base. Parceiro Zé [%s / %s]";
    public static final String ERRO_FIND_BY_ID = "ParceiroZe não encontrado com Identificador: [%s]";
    public static final String ERRO_FIND_BY_COORDINATES = "Erro ao pesquisar parceiro próximo à coordenada: [%s]";
    private static final String PARCEIRO_ZE_CACHE = "parceiroze";
    private static final String PARCEIRO_ZE_CACHE_ERRO = "Cache de ParceiroZe não encontrado";

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
            return parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(parceiroResult.get());
        } catch (MongoException e) {
            log.error(String.format(ERRO_FIND_BY_ID, parceiroZeDataproviderDto.getId()), e);
            throw new InternalServerErrorException(String.format(ERRO_FIND_BY_ID, parceiroZeDataproviderDto.getId()));
        }
    }

    public ParceiroZeDataproviderDto buscarParceiroZePorCoordenadas(CoordenadaCliente coordenadaCliente) {
        Double[] coordenadas = {coordenadaCliente.getLongitude(), coordenadaCliente.getLatitude()};
        try {
            var parceiroZeEntity = this.buscarParceiroZeEmCache(coordenadas);
            if(parceiroZeEntity == null) {
                log.info("Iniciando busca de Parceiro na Base de Dados");
                parceiroZeEntity = parceiroZeRepository.findByCoverageAreaCoordinates(coordenadas);
                if(Objects.nonNull(parceiroZeEntity)) {
                    this.salvarParceiroZeEmCache(coordenadas, parceiroZeEntity);
                }
            }
            return parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(parceiroZeEntity);
        } catch (MongoException e) {
            log.error(String.format(ERRO_FIND_BY_COORDINATES, coordenadas), e);
            throw new InternalServerErrorException(String.format(ERRO_FIND_BY_COORDINATES, coordenadas));
        }
    }

    private void salvarParceiroZeEmCache(Double[] key, ParceiroZeEntity parceiroZeEntity) {
        log.info("Salvando ParceiroZe no cache");
        requireNonNull(cacheManager.getCache(PARCEIRO_ZE_CACHE), PARCEIRO_ZE_CACHE_ERRO).put(key, parceiroZeEntity);
    }

    private ParceiroZeEntity buscarParceiroZeEmCache(Double[] coordenadas) {
        log.info("Iniciando busca de Parceiro em cache!");
        ParceiroZeEntity parceiroZeEntity = requireNonNull(cacheManager.getCache(PARCEIRO_ZE_CACHE), PARCEIRO_ZE_CACHE_ERRO).get(coordenadas, ParceiroZeEntity.class);
        return parceiroZeEntity;
    }
}
