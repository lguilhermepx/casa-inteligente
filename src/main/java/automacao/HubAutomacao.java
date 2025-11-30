package automacao;

public class HubAutomacao implements Mediador {

    // o Hub precisa conhecer os aparelhos para controlar
    private Lampada lampada;
    private ArCondicionado ar;
    private Botao botao;

    // registrar os aparelhos no Hub
    public void registrarLampada(Lampada lampada) { this.lampada = lampada; }
    public void registrarAr(ArCondicionado ar) { this.ar = ar; }
    public void registrarBotao(Botao botao) { this.botao = botao; }

    @Override
    public void notificar(Componente remetente, String evento) {

        if (remetente == botao && evento.equals("CLICK")) {
            System.out.println("--> Hub: Detectei clique. Iniciando 'Modo Cheguei em Casa'...");

            // Hub comanda os outros componentes
            if (lampada != null) lampada.ligar();
            if (ar != null) ar.ligar();
        }

    }
}