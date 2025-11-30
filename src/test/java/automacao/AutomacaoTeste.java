package automacao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutomacaoTeste {

    // capturar o console
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // componentes do padrão
    private HubAutomacao hub;
    private Botao botao;
    private Lampada lampada;
    private ArCondicionado ar;

    @BeforeEach
    public void setUp() {
        // 1. Redireciona a saída do console
        System.setOut(new PrintStream(outContent));

        // 2. Inicializa o Mediador e os Componentes
        hub = new HubAutomacao();

        // Passa o hub para os componentes
        botao = new Botao(hub);
        lampada = new Lampada(hub);
        ar = new ArCondicionado(hub);

        // 3. Registra os componentes no Hub
        hub.registrarBotao(botao);
        hub.registrarLampada(lampada);
        hub.registrarAr(ar);
    }

    @AfterEach
    public void tearDown() {
        // console original
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Ao pressionar o botão, o Hub deve ligar a Lâmpada e o Ar Condicionado")
    void testeFluxoCompletoChegueiEmCasa() {
        // Act: O usuário aperta o botão
        botao.pressionar();

        // Assert: Verifica se a cadeia de eventos aconteceu lendo o console
        String saida = outContent.toString();

        // 1. O botão notificou?
        assertTrue(saida.contains("Botão: Fui pressionado!"),
                "O botão deveria confirmar o clique.");

        // 2. O Hub reagiu?
        assertTrue(saida.contains("Hub: Detectei clique"),
                "O Hub deveria interceptar a mensagem do botão.");

        // 3. A Lâmpada ligou?
        assertTrue(saida.contains("Lâmpada: Está ACESA"),
                "O Hub deveria ter mandado ligar a lâmpada.");

        // 4. O Ar ligou?
        assertTrue(saida.contains("Ar Condicionado: Ligando refrigeração"),
                "O Hub deveria ter mandado ligar o ar condicionado.");
    }

    @Test
    @DisplayName("Hub não deve quebrar se um componente não estiver registrado (ex: sem Ar Condicionado)")
    void testeHubSemArCondicionado() {
        // um novo hub pra este teste
        HubAutomacao hubParcial = new HubAutomacao();
        Botao botaoParcial = new Botao(hubParcial);
        Lampada lampadaParcial = new Lampada(hubParcial);

        // registra botão e lâmpada, esquece o Ar
        hubParcial.registrarBotao(botaoParcial);
        hubParcial.registrarLampada(lampadaParcial);

        // Act
        botaoParcial.pressionar();
        String saida = outContent.toString();

        // Assert
        assertTrue(saida.contains("Lâmpada: Está ACESA"), "A lâmpada ainda deve ligar.");
        assertFalse(saida.contains("Ar Condicionado"), "Não deve haver menção ao Ar Condicionado.");
    }

    @Test
    @DisplayName("Lâmpada deve poder ser ligada diretamente (sem mediador) se necessário")
    void testeLampadaDireta() {
        // verifica se o metodo da lampada funciona isoladamente
        lampada.ligar();
        String saida = outContent.toString();
        assertTrue(saida.contains("Lâmpada: Está ACESA"));
    }

    @Test
    @DisplayName("O Hub só deve reagir ao evento específico 'CLICK'")
    void testeHubEventoDesconhecido() {
        // simula uma notificação manual com evento errado
        hub.notificar(botao, "EVENTO_DESCONHECIDO");

        String saida = outContent.toString();

        // o Hub n deve ter feito nada
        assertFalse(saida.contains("Iniciando 'Modo Cheguei em Casa'"),
                "O Hub não deveria reagir a eventos desconhecidos.");
        assertFalse(saida.contains("Lâmpada: Está ACESA"),
                "A lâmpada não deveria ligar.");
    }
}