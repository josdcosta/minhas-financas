package br.com.dominio.minhasfinancas.exception;

public class TransacoesNaoEncontradasException extends RuntimeException{
    public TransacoesNaoEncontradasException(String message) {
        super(message);
    }

    public TransacoesNaoEncontradasException() {
        super();
    }
}
