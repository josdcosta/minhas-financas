package br.com.dominio.minhasfinancas.exception;

public class SalvarListaTransacaoException extends RuntimeException {

    public SalvarListaTransacaoException(String message) {
        super(message);
    }

    public SalvarListaTransacaoException() {
        super();
    }
}
