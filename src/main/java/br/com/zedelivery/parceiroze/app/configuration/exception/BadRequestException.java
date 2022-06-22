package br.com.zedelivery.parceiroze.app.configuration.exception;

import br.com.zedelivery.parceiroze.app.configuration.exception.model.BadRequestDetail;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class BadRequestException extends RuntimeException {
    private static String ERROR_MESSAGE = "Erro na validação dos parâmetros de entrada";
    private List<BadRequestDetail> detalhes;

    public BadRequestException() {
        this(ERROR_MESSAGE);
    }

    public BadRequestException(String message) {
        this(message, new ArrayList());
    }

    public BadRequestException(String campo, String message, String valor) {
        this(ERROR_MESSAGE, new ArrayList<>());
        this.addDetalhe(campo, message, valor);
    }

    public BadRequestException(String campo, String message, String valor, Throwable cause) {
        this(ERROR_MESSAGE, new ArrayList<>(), cause);
        this.addDetalhe(campo, message, valor);
    }

    public BadRequestException(String message, Throwable cause) {
        this(message, null, cause);
    }

    public BadRequestException(String message, List<BadRequestDetail> detalhes) {
        this(message, detalhes, (Throwable)null);
    }

    public BadRequestException(List<BadRequestDetail> detalhes) {
        this(ERROR_MESSAGE, (List) detalhes, null);
    }

    public BadRequestException(String message, List<BadRequestDetail> detalhes, Throwable cause) {
        super(message, cause);
        if(CollectionUtils.isEmpty(detalhes)) {
            this.detalhes = new ArrayList<>();
        } else {
            this.detalhes = detalhes;
        }
    }

    private void addDetalhe(String campo, String message, String valor) {
        this.detalhes.add(BadRequestDetail.builder().campo(campo).mensagem(message).valor(valor).build());
    }
}
