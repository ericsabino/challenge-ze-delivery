package br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParceiroZeDto {
    @Null(message = "Valor não deve ser enviado na request")
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
