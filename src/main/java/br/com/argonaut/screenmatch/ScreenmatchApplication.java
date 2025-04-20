package br.com.argonaut.screenmatch;

import br.com.argonaut.screenmatch.model.DataSerie;
import br.com.argonaut.screenmatch.service.ConsumeApi;
import br.com.argonaut.screenmatch.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var scanner = new Scanner(System.in);
        var consumeApi = new ConsumeApi();
        String option = "";
        String address = "";

        while (!option.equalsIgnoreCase("sair")) {
            System.out.println("Escolha uma API para consultar:");
            System.out.println("1 - Screenmatch");
            System.out.println("2 - Photo Coffes");
            System.out.println("3 - Sair");
            option = scanner.nextLine();

            if (option.equals("1")) {
                System.out.println("Digite a série que deseja consultar:");
                String serie = scanner.nextLine();
                address = "http://www.omdbapi.com/?t=" + serie + "&apikey=29b6d465";
            } else if (option.equals("2")) {
                System.out.println("Digite o café que deseja consultar:");
                String cafe = scanner.nextLine();
                address = cafe;
            } else if (option.equalsIgnoreCase("sair")) {
                break;
            } else {
                System.out.println("Opção inválida.");
                continue;
            }

            var json = consumeApi.GetData(address);
            System.out.println(json);

            DataConverter dataConverter = new DataConverter();
            DataSerie dataSerie = dataConverter.getData(json, DataSerie.class);
            System.out.println(dataSerie);
        }
    }
}
