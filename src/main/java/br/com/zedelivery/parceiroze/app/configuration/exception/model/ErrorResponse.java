package br.com.zedelivery.parceiroze.app.configuration.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
    private String codigo;
    private String mensagem;
    private List<ErrorCamposResponse> campos;
}
