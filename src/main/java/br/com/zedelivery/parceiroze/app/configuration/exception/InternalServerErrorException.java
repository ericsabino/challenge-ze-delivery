package br.com.zedelivery.parceiroze.app.configuration.exception;

import lombok.Data;

@Data
public class InternalServerErrorException extends RuntimeException {
    private String codigo = "";

    public InternalServerErrorException(String codigo, String mensagem) {
        super(mensagem);
        this.codigo = codigo;
    }

    public InternalServerErrorException(String mensagem) {
        super(mensagem);
    }

    public InternalServerErrorException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }

    public InternalServerErrorException(String codigo, String mensagem, Throwable cause) {
        super(mensagem, cause);
        this.codigo = codigo;
    }
}
