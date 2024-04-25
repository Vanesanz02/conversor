import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class conversor {

    private static final String API_KEY = "74069a4ddff39981af30fc05";
    private static final String BASE_URL = "https://api.exchangerate-api.com/v4/latest/USD";

    public static void main(String[] args) {
        conversor converter = new conversor();
        converter.run();
    }

    private void run() {
        try {
            String jsonResponse = fetchExchangeRates();
            JsonObject exchangeRates = parseJson(jsonResponse);
            double usdToArs = exchangeRates.getAsJsonObject("rates").get("ARS").getAsDouble();
            double usdToBrl = exchangeRates.getAsJsonObject("rates").get("BRL").getAsDouble();
            double usdToCop = exchangeRates.getAsJsonObject("rates").get("COP").getAsDouble();

            double arsToUsd = 1 / usdToArs;
            double brlToUsd = 1 / usdToBrl;
            double copToUsd = 1 / usdToCop;

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Sea bienvenido al conversor de moneda:");
                System.out.println("1) Dólar =>> Peso argentino");
                System.out.println("2) Peso argentino =>> Dólar");
                System.out.println("3) Dólar =>> Real brasileño");
                System.out.println("4) Real brasileño =>> Dólar");
                System.out.println("5) Dólar =>> Peso colombiano");
                System.out.println("6) Peso colombiano =>> Dólar");
                System.out.println("7) Salir");
                System.out.print("Elija una opción válida: ");
                int option = scanner.nextInt();

                if (option == 7) {
                    System.out.println("Saliendo...");
                    break;
                }

                System.out.print("Ingrese la cantidad: ");
                double amount = scanner.nextDouble();

                double convertedAmount;
                switch (option) {
                    case 1:
                        convertedAmount = amount * usdToArs;
                        System.out.printf("%.2f USD son %.2f ARS.\n", amount, convertedAmount);
                        break;
                    case 2:
                        convertedAmount = amount * arsToUsd;
                        System.out.printf("%.2f ARS son %.2f USD.\n", amount, convertedAmount);
                        break;
                    case 3:
                        convertedAmount = amount * usdToBrl;
                        System.out.printf("%.2f USD son %.2f BRL.\n", amount, convertedAmount);
                        break;
                    case 4:
                        convertedAmount = amount * brlToUsd;
                        System.out.printf("%.2f BRL son %.2f USD.\n", amount, convertedAmount);
                        break;
                    case 5:
                        convertedAmount = amount * usdToCop;
                        System.out.printf("%.2f USD son %.2f COP.\n", amount, convertedAmount);
                        break;
                    case 6:
                        convertedAmount = amount * copToUsd;
                        System.out.printf("%.2f COP son %.2f USD.\n", amount, convertedAmount);
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String fetchExchangeRates() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?apiKey=" + API_KEY))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private JsonObject parseJson(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }
}

