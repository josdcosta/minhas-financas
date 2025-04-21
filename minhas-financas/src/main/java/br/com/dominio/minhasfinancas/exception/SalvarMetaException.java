package br.com.dominio.minhasfinancas.exception;

public class SalvarMetaException extends RuntimeException{
    public SalvarMetaException(String message) {
        super(message);
    }

    public SalvarMetaException() {
        super();
    }
}
