package br.com.zedelivery.parceiroze.app.configuration.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorCamposResponse {
    private String campo;
    private String mensagem;
    private String valor;
}
