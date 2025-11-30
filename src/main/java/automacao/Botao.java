package automacao;

public class Botao extends Componente {

    public Botao(Mediador mediador) {
        super(mediador);
    }

    public void pressionar() {
        System.out.println("Botão: Fui pressionado!");
        //  só avisa o chefe (mediador).
        mediador.notificar(this, "CLICK");
    }
}