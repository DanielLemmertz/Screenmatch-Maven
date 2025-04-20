package br.com.argonaut.screenmatch.main;

import br.com.argonaut.screenmatch.model.DataEpisode;
import br.com.argonaut.screenmatch.model.DataSeason;
import br.com.argonaut.screenmatch.model.DataSerie;
import br.com.argonaut.screenmatch.service.ConsumeApi;
import br.com.argonaut.screenmatch.service.DataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private final String ADDRESS = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=29b6d465";
    private ConsumeApi consumeApi = new ConsumeApi();
    private DataConverter dataConverter = new DataConverter();
    private List<DataSeason> seasonsList = new ArrayList<>();


    public void ViewMenu() {
        System.out.println("Seja bem vindo ao Screenmatch!");
        System.out.println("");
        System.out.println("Digite qual serie deseja consultar:");
        var typedSerie = scanner.nextLine();

        var json = consumeApi.GetData(ADDRESS + typedSerie.replace(" ", "+") + API_KEY);
        DataSerie dataSerie = dataConverter.getData(json, DataSerie.class);
        System.out.println("Serie:");
        System.out.println(dataSerie);


        for (int i = 1; i <= dataSerie.totalSeasons(); i++) {

            json = consumeApi.GetData(ADDRESS + typedSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            DataSeason dataSeason = dataConverter.getData(json, DataSeason.class);
            seasonsList.add(dataSeason);

        }

        System.out.println("------------Dados das Temporadas----------");
        seasonsList.forEach(System.out::println);

        seasonsList.forEach(t -> t.episodes().forEach(e -> System.out.println(("Episodio " + e.number() + "/Temp: " + t.season() + ": " + e.title()))));
    }
}
