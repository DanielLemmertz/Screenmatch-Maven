package br.com.argonaut.screenmatch.model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer number;
    private double rating;
    private LocalDate dateRelease;

    public Episode(Integer season, DataEpisode dataEpisode) {
        this.season = season;
        this.title = dataEpisode.title();
        this.number = dataEpisode.number();
        try {
            this.rating = Double.parseDouble(dataEpisode.rating());
        } catch (NumberFormatException ex) {
            this.rating = 0.0;
        }
        try {
            this.dateRelease = LocalDate.parse(dataEpisode.dateRelease());
        } catch (DateTimeParseException ex) {
            this.dateRelease = null;
        }
    }

    public LocalDate getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(LocalDate dateRelease) {
        this.dateRelease = dateRelease;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Override
    public String toString() {
        return
                "Temporada =" + season + "| Titulo =" + title + "| Episodio =" + number + "| Avaliação=" + rating + "| Data de lançamento =" + dateRelease.format(dateTimeFormatter);

    }
}
