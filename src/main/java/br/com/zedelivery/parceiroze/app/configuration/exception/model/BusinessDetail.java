package br.com.zedelivery.parceiroze.app.configuration.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessDetail {

    private String codigo;
    private String mensagem;
}
