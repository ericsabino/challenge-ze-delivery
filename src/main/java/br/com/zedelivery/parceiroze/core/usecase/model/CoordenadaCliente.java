package br.com.zedelivery.parceiroze.core.usecase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoordenadaCliente {
    private Double longitude;
    private Double latitude;
}
