package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Music {
    private String id;
    private String title;
    private String artist;

    public Music() {
    }

    public Music(String id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("artist")
    public String getArtist() {
        return artist;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist +
                '}';
    }
}