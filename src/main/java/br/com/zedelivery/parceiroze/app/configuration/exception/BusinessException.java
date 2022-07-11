package br.com.zedelivery.parceiroze.app.configuration.exception;

import br.com.zedelivery.parceiroze.app.configuration.exception.model.BusinessDetail;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusinessException extends RuntimeException {
    private static final String MESSAGE = "Erro na validação de negócio";
    private List<BusinessDetail> detalhes;

    public BusinessException(){
        this(MESSAGE, null, null);
    }

    public BusinessException(String message) {
        this(message, null, null);
    }

    public BusinessException(String codigo, String menssage){
        this(MESSAGE, null, null);
        this.addDetalhe(codigo, menssage);
    }

    public BusinessException(String message, Throwable cause) {
        this(message, null, cause);
    }

    public BusinessException(List<BusinessDetail> detalhes) {
        this(MESSAGE, detalhes, null);
    }

    public BusinessException(String message, List<BusinessDetail> detalhes) {
        this(message, detalhes, null);
    }

    public BusinessException(String message, List<BusinessDetail> detalhes, Throwable cause) {
        super(message, cause);
        if(CollectionUtils.isEmpty(detalhes)) {
            this.detalhes = new ArrayList<>();
        } else {
            this.detalhes = detalhes;
        }
    }

    private void addDetalhe(String codigo, String menssage) {
        this.detalhes.add(BusinessDetail.builder().codigo(codigo).mensagem(menssage).build());
    }
}
