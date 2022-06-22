package br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParceiroZeRepository extends MongoRepository<ParceiroZeEntity, String> {
}
