package automacao;

public abstract class Componente {
    protected Mediador mediador;

    public Componente(Mediador mediador) {
        this.mediador = mediador;
    }
}