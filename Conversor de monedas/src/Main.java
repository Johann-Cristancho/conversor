import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        outerLoop:
        while(true){
            System.out.println("seleccione el numero de la opcion que dedea realizar: 1. cambiar divisas 2. salir del programa ");
            Scanner read = new Scanner(System.in);
            int option = read.nextInt();
            switch (option){

                case 1:
                    Scanner lectura = new Scanner(System.in);
                    System.out.println("Escriba la divisa base (por ejemplo, USD): ");
                    String baseCurrency = lectura.nextLine().toUpperCase();

                    System.out.println("Escriba la divisa que desea consultar (por ejemplo, EUR): ");
                    String targetCurrency = lectura.nextLine().toUpperCase();

                    System.out.println("Ingrese la cantidad de " + baseCurrency + " que desea convertir: ");
                    double amount = lectura.nextDouble();

                    String apiKey = "4f9056d7c64d2ee4e66115a1";
                    String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency;

                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    Gson gson = new Gson();
                    JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

                    if (jsonResponse.has("conversion_rates")) {
                        JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");
                        if (rates.has(targetCurrency)) {
                            double exchangeRate = rates.get(targetCurrency).getAsDouble();
                            double convertedAmount = amount * exchangeRate;
                            System.out.println(amount + " " + baseCurrency + " son equivalentes a " + convertedAmount + " " + targetCurrency);
                        } else {
                            System.out.println("No se encontró la divisa objetivo: " + targetCurrency);
                        }
                    } else {
                        System.out.println("Error en la respuesta de la API o divisa base no válida.");
                    }
                    break;
                case 2:
                    System.out.println("Opción 2 seleccionada, saliendo del programa");
                    break outerLoop;

                default:
                    System.out.println("opcion invalida intente nuevamente ");
                     break ;
            }


        }
    }
}



