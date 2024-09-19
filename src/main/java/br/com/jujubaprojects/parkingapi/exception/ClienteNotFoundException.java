package br.com.jujubaprojects.parkingapi.exception;

public class ClienteNotFoundException extends RuntimeException{
    private final Long codigo;

    public Long getCodigo() {
        return codigo;
    }

    public ClienteNotFoundException(Long codigo) {
        this.codigo = codigo;
    }
}
