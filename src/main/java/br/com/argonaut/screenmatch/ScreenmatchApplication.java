package br.com.argonaut.screenmatch;

import br.com.argonaut.screenmatch.service.ConsumeApi;
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
        var json = consumeApi.GetData(scanner.nextLine());
        System.out.println(json);
    }
}
