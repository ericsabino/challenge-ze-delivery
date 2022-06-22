package br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document(collation = "parceiroze")
public class ParceiroZeEntity {
    //@MongoId
    private String id;
    private String tradingName;
    private String ownerName;
    //@Indexed(unique = true)
    private String document;
    private CoverageAreaEntity coverageArea;
    //@Indexed
    private AddressEntity address;
}
