package br.com.JDev.ValorantApi.principal;

import br.com.JDev.ValorantApi.model.ValorantDados;
import br.com.JDev.ValorantApi.service.ConsumoApi;
import br.com.JDev.ValorantApi.service.ConverteDados;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Principal {

    public void exibirMenu() {
        Scanner sc = new Scanner(System.in);
        ConsumoApi consumoApi = new ConsumoApi();
        ConverteDados converteDados = new ConverteDados();

        final String URL_BASE = "https://valorant-api.com/v1/";

        String menu = "Digite a sua opção: " +
                "\n1. Agentes" +
                "\n2. Armas";

        System.out.println(menu);
        String opcao = sc.nextLine();

        String endereco;

        if (opcao.equalsIgnoreCase("agentes") || opcao.equals("1")) {
            endereco = URL_BASE + "agents";
        } else if (opcao.equalsIgnoreCase("armas") || opcao.equals("2")) {
            endereco = URL_BASE + "weapons";
        } else {
            System.out.println("Opção inválida!");
            return;
        }

        String json = consumoApi.obterDados(endereco);
//        System.out.println(json);

//        var agentes = converteDados.obterLista(json, ValorantDados.class);
//        agentes.stream().sorted(Comparator.comparing(ValorantDados::displayName)).forEach(System.out::println);

        List<ValorantDados> dados = converteDados.obterLista(json, ValorantDados.class);

        if (dados != null) {
            dados.forEach(d -> System.out.println(d.displayName()));
        } else {
            System.out.println("Não foi possível obter os dados.");
        }

            System.out.println("Qual opçao você gostaria de saber as informações?");
            var escolhaInformacao = sc.nextLine();


        ValorantDados opcaoEscolhida = dados.stream()
                .filter(d -> d.displayName().equalsIgnoreCase(escolhaInformacao))
                .findFirst().orElse(null);

        if (opcaoEscolhida != null && opcao == "agentes" || opcao.equals("1")) {
            System.out.println("Nome do agente: " + opcaoEscolhida.displayName());
            System.out.println("Posição: " + opcaoEscolhida.role().displayName());
            opcaoEscolhida.abilities().stream()
                    .forEach(a -> System.out.println("Habilidades" + a.displayName()));
        } else if (opcao.equalsIgnoreCase("armas") || opcao.equals("2")) {
            System.out.println("Nome da arma: " + opcaoEscolhida.displayName());
            System.out.println("Categoria da arma: " + opcaoEscolhida.category());
            endereco =  URL_BASE + "weapons/skins";
            json = consumoApi.obterDados(endereco);
// Tentativa
//            List<ValorantSkin> todasSkins = converteDados.obterLista(json, ValorantSkin.class);
//            List<ValorantSkin> skinDaArma = todasSkins.stream()
//                    .filter(s -> s.uuid().contains(opcaoEscolhida.displayName()))
//                    .collect(Collectors.toList());
//
//            skinDaArma.forEach(sk -> System.out.println(sk.displayName()));

            //Acerto
            List<ValorantDados> skinDaArma = converteDados.obterLista(json, ValorantDados.class);
            skinDaArma.stream()
                    .filter(s -> s.displayName().toLowerCase().contains(escolhaInformacao.toLowerCase()))
                    .forEach(s -> System.out.println("Skin da arma: " + s.displayName()));
// Tentativa
//            Optional<ValorantDados> seila = skinDaArma.stream()
//                    .findFirst()
//                    .filter(d -> d.displayName().contains(escolhaInformacao));
//            if (seila.isPresent()) {
//                System.out.println("Nome da skin: " + seila.get().uuid());
//            }

        } else {
            System.out.println("Opção não encontrada!");
        }



    }
}
