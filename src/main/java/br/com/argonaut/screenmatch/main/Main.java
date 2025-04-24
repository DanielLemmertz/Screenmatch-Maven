package br.com.argonaut.screenmatch.main;

import br.com.argonaut.screenmatch.model.DataEpisode;
import br.com.argonaut.screenmatch.model.DataSeason;
import br.com.argonaut.screenmatch.model.DataSerie;
import br.com.argonaut.screenmatch.model.Episode;
import br.com.argonaut.screenmatch.service.ConsumeApi;
import br.com.argonaut.screenmatch.service.DataConverter;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumeApi consumeApi = new ConsumeApi();
    private final DataConverter dataConverter = new DataConverter();
    private final List<DataSeason> seasonsList = new ArrayList<>();


    public void ViewMenu() {
        System.out.println("Seja bem vindo ao Screenmatch!");
        System.out.println("------------------------------");
        System.out.println("Digite qual serie deseja consultar:");
        var typedSerie = scanner.nextLine();

        String ADDRESS = "http://www.omdbapi.com/?t=";
        String API_KEY = "&apikey=29b6d465";
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

        System.out.println("-------------------Melhores Episodios-------------------");
        List<DataEpisode> dataEpisodes = seasonsList.stream().flatMap(t -> t.episodes().stream()).toList();

        dataEpisodes.stream().filter(e -> !e.rating().equalsIgnoreCase("N/A")).sorted(Comparator.comparing(DataEpisode::rating).reversed()).limit(5).forEach(e -> System.out.println("Episódio: " + e.title() + " | Rating: " + e.rating()));

        List<Episode> episodes = seasonsList.stream()
                .flatMap(t -> t.episodes().stream().map(d -> new Episode(t.season(), d)))
                .toList();

        episodes.forEach(System.out::println);

        System.out.println("Digite o nome de um dos episodios:");
        var TitleExcerpt = scanner.nextLine();
        Optional<Episode> lookedEpisode = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(TitleExcerpt.toUpperCase()))
                .findFirst();

        if (lookedEpisode.isPresent()) {
            System.out.println("Episodio encontrado:");
            System.out.println("Temporada: " + lookedEpisode.get().getSeason() + " | Episódio: " + lookedEpisode.get().getNumber() + " | Titulo:" + lookedEpisode.get().getTitle())
            ;
        } else {
            System.out.println("Episodio não encontrado!");
        }
//        System.out.println("A partir de que ano deseja ver os episodios da serie?");
//        var Year = scanner.nextInt();
//        scanner.nextLine();
//        LocalDate searchDate = LocalDate.of(Year, 1, 1);


//        episodes.stream()
//                .filter(e -> e.getDateRelease() != null && e.getDateRelease().isAfter(searchDate))
//                .forEach(System.out::println);

        Map<Integer, Double> reviewsForSeason = episodes.stream()
                .collect(Collectors.groupingBy(Episode::getSeason, Collectors.averagingDouble(Episode::getRating)));
        System.out.println(reviewsForSeason);

        DoubleSummaryStatistics statistics = episodes.stream()
                .collect(Collectors.summarizingDouble(Episode::getRating));
        System.out.println(statistics);

    }
}
