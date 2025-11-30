package automacao;

public class ArCondicionado extends Componente {
    private boolean ligado = false;

    public ArCondicionado(Mediador mediador) {
        super(mediador);
    }

    public void ligar() {
        ligado = true;
        System.out.println("Ar Condicionado: Ligando refrigeração.");
    }

    public void desligar() {
        ligado = false;
        System.out.println("Ar Condicionado: Desligando.");
    }
}