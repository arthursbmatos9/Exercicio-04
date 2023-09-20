import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Exercicio04 {
    public static void main(String[] args) {
        String apiKey = "c88fa9fc990d410c8713e413ce84d738";
        String serviceUrl = "https://southcentralus.api.cognitive.microsoft.com/text/analytics/v3.0/sentiment";

        try {
            URL url = new URL(serviceUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", apiKey);

            // Corpo da solicitação JSON
            String requestBody = "{\"documents\":[{\"language\":\"en\",\"id\":\"1\",\"text\":\"Este é um ótimo produto!\"}]}";

            // Envia os dados JSON no corpo da solicitação
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Lê a resposta do serviço
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println(response.toString());
                }
            } else {
                System.err.println("Erro na solicitação. Código de resposta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
