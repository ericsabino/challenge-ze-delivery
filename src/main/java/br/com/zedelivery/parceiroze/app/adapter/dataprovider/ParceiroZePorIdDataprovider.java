package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.configuration.exception.BusinessException;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZePorIdGateway;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ParceiroZePorIdDataprovider implements ParceiroZePorIdGateway {

    public static final String ID_NOT_FOUND = "Identificador [%s] não encontrado";
    public static final String ERRO_FIND_BY_ID = "ParceiroZe não encontrado com Identificador: [%s]";
    private final ParceiroZeDataproviderMapper parceiroZeDataproviderMapper;
    private final ParceiroZeRepository parceiroZeRepository;

    @Override
    public ParceiroZeDataproviderDto buscarParceiroZePorID(ParceiroZeDataproviderDto parceiroZeDataproviderDto) {
        try {
            var parceiroResult = parceiroZeRepository.findById(parceiroZeDataproviderDto.getId());
            if (parceiroResult.isPresent()) {
                return parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(Arrays.asList(parceiroResult.get())).get(0);
            } else {
                throw new BusinessException(String.format(ID_NOT_FOUND, parceiroZeDataproviderDto.getId()));
            }
        } catch (MongoException e) {
            log.error(String.format(ERRO_FIND_BY_ID, parceiroZeDataproviderDto.getId()), e);
            throw new InternalServerErrorException(String.format(ERRO_FIND_BY_ID, parceiroZeDataproviderDto.getId()));
        }
    }
}
