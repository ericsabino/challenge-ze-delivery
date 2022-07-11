package br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ParceiroZeRepository extends MongoRepository<ParceiroZeEntity, String> {

    @Query(value = "{'coverageArea.coordinates' : {$elemMatch:{$elemMatch:{$elemMatch:{$elemMatch : { $in : ?0}}}}}}")
    public List<ParceiroZeEntity> findByCoverageAreaCoordinates(Double[] lngLat);
}
