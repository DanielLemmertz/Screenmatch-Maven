package br.com.argonaut.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeason(@JsonAlias("Title") String title,
                         @JsonAlias("Season") Integer season,
                         @JsonAlias("TotalSeasons") String totalSeasons,
                         @JsonAlias("Episodes") List<DataEpisode> episodes ) {

}
