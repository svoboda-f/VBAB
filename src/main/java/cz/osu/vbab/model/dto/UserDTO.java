package cz.osu.vbab.model.dto;

import java.time.LocalDate;
import java.util.List;

import cz.osu.vbab.model.Comment;
import cz.osu.vbab.model.Playlist;
import cz.osu.vbab.model.Video;

public record UserDTO(String email, String username, String firstName, String lastName, LocalDate dateOfBirth,
        List<Playlist> playlists, List<Video> uploadedVideos, List<Comment> comments) {

}
