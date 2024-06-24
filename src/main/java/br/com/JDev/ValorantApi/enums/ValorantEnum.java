package br.com.JDev.ValorantApi.enums;

public enum ValorantEnum {

    AGENTE(1),

    ARMA(2);

    private final int codigo;

    ValorantEnum(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}