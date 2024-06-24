package br.com.JDev.ValorantApi.principal;

import br.com.JDev.ValorantApi.enums.ValorantEnum;
import br.com.JDev.ValorantApi.model.ValorantDados;
import br.com.JDev.ValorantApi.util.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class Principal {

    @Value("${valorant.url.endpoint}")
    private String endpoint;

    @Autowired
    private ValorantAPI valorantAPI;

    @Autowired
    private ConverteDados converteDados;

    private final Scanner sc = new Scanner(System.in);

    public void executarPrograma() {
        exibirMenu();
        String opcao = obterSelecao();
        realizarOperacoes(opcao);
    }

    public void exibirMenu() {
        System.out.println("""
                Digite a sua opção:
                1. Agentes
                2. Armas
                """);
    }

    public String obterSelecao() {
        return sc.nextLine();
    }

    private void realizarOperacoes(String opcao) {
        String OPCAO_INVALIDA = "Opção inválida!";
        String endpointCompleto = ehAgente(opcao) ? endpoint + "agents" :
                (ehArma(opcao) ? endpoint + "weapons" : OPCAO_INVALIDA);

        if (endpointCompleto.equals(OPCAO_INVALIDA)) {
            return;
        }

        String json = valorantAPI.obterDados(endpointCompleto);

        List<ValorantDados> dados = converteDados.obterLista(json, ValorantDados.class);

        if (Objects.nonNull(dados)) {
            dados.forEach(d -> System.out.println(d.displayName()));
        } else {
            System.out.println("Não foi possível obter os dados.");
            return;
        }

        System.out.println("Qual opção você gostaria de saber as informações?");
        String escolhaInformacao = sc.nextLine();

        ValorantDados opcaoEscolhida = dados.stream()
                .filter(d -> d.displayName().equalsIgnoreCase(escolhaInformacao))
                .findFirst().orElse(null);

        if (Objects.nonNull(opcaoEscolhida)) {
            if (ehAgente(opcao)) {
                imprimirDadosAgente(opcaoEscolhida);
            } else if (ehArma(opcao)) {
                imprimirDadosArma(opcaoEscolhida);
                imprimirSkins(obterDadosSkins(), escolhaInformacao);
            }
        } else {
            System.out.println(OPCAO_INVALIDA);
        }
    }

    private boolean ehAgente(String opcao) {
        return opcao.equalsIgnoreCase(ValorantEnum.AGENTE.name()) ||
                ValorantEnum.AGENTE.getCodigo() == Integer.parseInt(opcao);
    }

    private boolean ehArma(String opcao) {
        return opcao.equalsIgnoreCase(ValorantEnum.ARMA.name()) ||
                ValorantEnum.ARMA.getCodigo() == Integer.parseInt(opcao);
    }

    private void imprimirDadosAgente(ValorantDados opcaoEscolhida) {
        System.out.println("Nome do agente: " + opcaoEscolhida.displayName());
        System.out.println("Posição: " + opcaoEscolhida.role().displayName());
        opcaoEscolhida.abilities().forEach(a -> System.out.println("Habilidades" + a.displayName()));
    }

    private void imprimirDadosArma(ValorantDados opcaoEscolhida) {
        System.out.println("Nome da arma: " + opcaoEscolhida.displayName());
        System.out.println("Categoria da arma: " + opcaoEscolhida.category());
    }

    private List<ValorantDados> obterDadosSkins() {
        String json = valorantAPI.obterDados(endpoint + "weapons/skins");
        return converteDados.obterLista(json, ValorantDados.class);
    }

    private void imprimirSkins(List<ValorantDados> skinDaArma, String escolhaInformacao) {
        skinDaArma.stream()
                .filter(s -> s.displayName().toLowerCase().contains(escolhaInformacao.toLowerCase()))
                .forEach(s -> System.out.println("Skin da arma: " + s.displayName()));
    }
}