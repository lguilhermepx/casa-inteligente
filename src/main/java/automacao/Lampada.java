package automacao;

public class Lampada extends Componente {
    private boolean ligada = false;

    public Lampada(Mediador mediador) {
        super(mediador);
    }

    public void ligar() {
        ligada = true;
        System.out.println("Lâmpada: Está ACESA.");
    }

    public void desligar() {
        ligada = false;
        System.out.println("Lâmpada: Está APAGADA.");
    }
}