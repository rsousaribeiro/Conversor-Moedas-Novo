package ConversorMoedas;

import ConversorMoedas.modelos.Conversao;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws IOException, InterruptedException {

        Gson gson = new Gson();

        String opcao = " ";
        String moedaInicial = " ";
        String moedaFinal = " ";
        double valorInicial;
        double valorFinal;

        while (!opcao.equalsIgnoreCase("7")) {

            System.out.println("*********************************************************");
            System.out.println("Seja bem-vindo(a) ao conversor de Moedas");
            System.out.println("\n1) Dólar =>> Peso Argentino");
            System.out.println("2) Peso Argentino =>> Dólar");
            System.out.println("3) Dólar =>> Real Brasileiro");
            System.out.println("4) Real Brasileiro =>> Dólar");
            System.out.println("5) Dólar =>> Peso Colombiano");
            System.out.println("6) Peso Colombiano =>> Dólar");
            System.out.println("7) Sair");
            System.out.println("Escolha uma opção válida: ");
            System.out.println("*********************************************************");

            Scanner leitura = new Scanner(System.in);
            opcao = leitura.nextLine();

            if(opcao.equalsIgnoreCase("7")) {
                break;
            }

            if (opcao.equalsIgnoreCase("1")) {
                moedaInicial = "USD";
                moedaFinal = "ARS";
            } else if (opcao.equalsIgnoreCase("2")) {
                moedaInicial = "ARS";
                moedaFinal = "USD";
            } else if (opcao.equalsIgnoreCase("3")) {
                moedaInicial = "USD";
                moedaFinal = "BRL";
            } else if (opcao.equalsIgnoreCase("4")) {
                moedaInicial = "BRL";
                moedaFinal = "USD";
            }  else if (opcao.equalsIgnoreCase("5")) {
                moedaInicial = "USD";
                moedaFinal = "COP";
            } else if (opcao.equalsIgnoreCase("6")) {
                moedaInicial = "COP";
                moedaFinal = "USD";
            }

            System.out.println("Digite o valor que deseja converter: ");
            valorInicial = leitura.nextDouble();

            String endereco = "https://v6.exchangerate-api.com/v6/e9bb9f44a001c5682f0cc23e/pair/" +
                    moedaInicial + "/" + moedaFinal;

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                Conversao percConversao = gson.fromJson(response.body(), Conversao.class);

                valorFinal = valorInicial * percConversao.conversion_rate();

                System.out.println("Valor " + valorInicial + " [" + moedaInicial + "] " +
                        "corresponde ao valor final de =>>> " + valorFinal + " [" + moedaFinal + "]");

            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: ");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Opção inválida! Escolha outra opção!");
            }

            opcao = " ";
            moedaInicial = " ";
            moedaFinal = " ";
            valorInicial = 0;
            valorFinal = 0;
        }
    }
}
