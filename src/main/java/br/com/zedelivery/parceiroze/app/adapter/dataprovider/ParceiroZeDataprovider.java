package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZeGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@AllArgsConstructor
public class ParceiroZeDataprovider implements ParceiroZeGateway {

    public static final String ERRO_INSERT = "Erro ao inserir na base. Parceiro Zé [%s / %s]";

    private final ParceiroZeDataproviderMapper parceiroZeDataproviderMapper;

    private final ParceiroZeRepository parceiroZeRepository;

    @Override
    public void salvarParceiroZe(ParceiroZeDataproviderDto parceiroZeDataproviderDto) {
        var parceiroZeEntity = parceiroZeDataproviderMapper.parceiroZeDataproviderDtoToParceiroZeEntity(Arrays.asList(parceiroZeDataproviderDto));
        try {
            parceiroZeRepository.insert(parceiroZeEntity.get(0));
        } catch (RuntimeException e) {
            log.error(String.format(ERRO_INSERT, parceiroZeEntity.get(0).getDocument(), parceiroZeEntity.get(0).getOwnerName()), e);
            throw new InternalServerErrorException(String.format(ERRO_INSERT, parceiroZeEntity.get(0).getDocument(), parceiroZeEntity.get(0).getOwnerName()));
        }
    }


}
