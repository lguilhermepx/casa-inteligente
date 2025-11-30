package automacao;

public interface Mediador {
    void notificar(Componente remetente, String evento);
}