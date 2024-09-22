package br.com.jujubaprojects.parkingapi.exception;

public class ReciboNotFoundException  extends RuntimeException{
    private final String codigo;

    public String getCodigo() {
        return codigo;
    }

    public ReciboNotFoundException(String codigo) {
        this.codigo = codigo;
    }
}