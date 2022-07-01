package br.com.zedelivery.parceiroze.core.usecase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String type;
    private Double[] coordinates;
    //private String geohash;
}
