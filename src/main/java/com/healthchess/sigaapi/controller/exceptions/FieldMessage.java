package com.healthchess.sigaapi.controller.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FieldMessage {

    private String campoNome;
    private String mensagem;

    public FieldMessage(String mensagem) {
        super();
    }

    public FieldMessage(String campoNome, String mensagem) {
        super();
        this.campoNome = campoNome;
        this.mensagem = mensagem;
    }
}
