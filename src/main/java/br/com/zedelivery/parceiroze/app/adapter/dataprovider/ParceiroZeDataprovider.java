package br.com.zedelivery.parceiroze.app.adapter.dataprovider;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper.ParceiroZeDataproviderMapper;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.ParceiroZeRepository;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZeGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ParceiroZeDataprovider implements ParceiroZeGateway {

    private final ParceiroZeDataproviderMapper parceiroZeDataproviderMapper;

    private final ParceiroZeRepository parceiroZeRepository;

    @Override
    public void salvarParceiroZe(ParceiroZeDataproviderDto parceiroZeDataproviderDto) {
        var parceiroZeEntity = parceiroZeDataproviderMapper.parceiroZeDataproviderDtoToParceiroZeEntity(parceiroZeDataproviderDto);
        try {
            parceiroZeRepository.insert(parceiroZeEntity);

        } catch (Exception e) {
            log.error(String.format("Erro ao inserir na base. Parceiro Zé [%s / %s]", parceiroZeEntity.getDocument(), parceiroZeEntity.getOwnerName()), e);
            throw new InternalServerErrorException(String.format("Erro ao inserir na base. Parceiro Zé [ Document: %s / OwnerName: %s]", parceiroZeEntity.getDocument(), parceiroZeEntity.getOwnerName()));
        }
    }
}
