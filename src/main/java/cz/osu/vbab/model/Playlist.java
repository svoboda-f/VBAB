package cz.osu.vbab.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Playlist name can't be empty")
    private String name;
    private LocalDate lastUpdated;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "playlist_video",
        joinColumns = @JoinColumn(name = "playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "video_id") 
    )
    private List<Video> videos;

    public Playlist() {}
    
    public Playlist(String name, User user) {
        this.name = name;
        this.user = user;
        this.lastUpdated = LocalDate.now();
        this.videos = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public User getUser() {
        return user;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setName(String name2) {
    }
}
