package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import br.com.zedelivery.parceiroze.app.configuration.exception.BusinessException;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZeGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class ParceiroZeDataprovider implements ParceiroZeGateway {

    public static final String ERRO_INSERT = "Erro ao inserir na base. Parceiro Zé [%s / %s]";
    public static final String ERRO_FIND_BY_ID = "ParceiroZe não encontrado com Identificador: [%s]";

    private final ParceiroZeDataproviderMapper parceiroZeDataproviderMapper;

    private final ParceiroZeRepository parceiroZeRepository;

    @Override
    public void salvarParceiroZe(ParceiroZeDataproviderDto parceiroZeDataproviderDto) {
        var parceiroZeEntity = parceiroZeDataproviderMapper.parceiroZeDataproviderDtoToParceiroZeEntity(parceiroZeDataproviderDto);
        try {
            parceiroZeRepository.insert(parceiroZeEntity);
        } catch (Exception e) {
            log.error(String.format(ERRO_INSERT, parceiroZeEntity.getDocument(), parceiroZeEntity.getOwnerName()), e);
            throw new InternalServerErrorException(String.format(ERRO_INSERT, parceiroZeEntity.getDocument(), parceiroZeEntity.getOwnerName()));
        }
    }

    @Override
    public ParceiroZeDataproviderDto buscarParceiroZePorID(ParceiroZeDataproviderDto parceiroZeDataproviderDto) {
            Optional<ParceiroZeEntity> parceiroResult = Optional.ofNullable(parceiroZeRepository.findById(parceiroZeDataproviderDto.getId())
                    .orElseThrow(() -> new BusinessException(String.format(ERRO_FIND_BY_ID, parceiroZeDataproviderDto.getId()))));

            return parceiroZeDataproviderMapper.parceiroZeEntityToParceiroZeDataproviderDto(parceiroResult.get());
    }
}
