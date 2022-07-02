package br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String type;
    @NotNull(message = "Coordenadas do endereço devem ser enviadas")
    private Double[] coordinates;
}
