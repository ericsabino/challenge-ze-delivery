package br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ParceiroZeRepository extends MongoRepository<ParceiroZeEntity, String> {

    @Query(value = "{'address.coordinates' : {$near:?0}}")
    public List<ParceiroZeEntity> findByAddressCoordinatesNear(Double[] lngLat);
}
