package br.com.zedelivery.parceiroze.app.configuration.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BadRequestDetail {
    private String campo;
    private String mensagem;
    private String valor;
}
