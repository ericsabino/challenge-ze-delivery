package br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParceiroZeDto {
    @NotNull
    private String id;
    private String tradingName;
    @NotBlank(message = "Owner Name não pode ser vazio ou nulo")
    private String ownerName;
    @NotBlank(message = "Documento não pode ser vazio ou nulo")
    private String document;

    private CoverageAreaDto coverageArea;
    @Valid @NotNull(message = "Objeto Address deve ser enviado")
    private AddressDto address;
}
