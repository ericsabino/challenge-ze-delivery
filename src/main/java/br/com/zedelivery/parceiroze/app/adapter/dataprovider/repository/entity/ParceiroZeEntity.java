package br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "parceiroze")
public class ParceiroZeEntity {
    @Id
    private String id;
    private String tradingName;
    private String ownerName;
    @Indexed(unique = true)
    private String document;
    private CoverageAreaEntity coverageArea;
    @Indexed
    private AddressEntity address;
}
