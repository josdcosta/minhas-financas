package br.com.dominio.minhasfinancas.exception;

public class CategorizarAIException extends RuntimeException{
    public CategorizarAIException(String message) {
        super(message);
    }

    public CategorizarAIException() {
        super();
    }
}
