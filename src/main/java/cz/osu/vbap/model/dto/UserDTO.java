package cz.osu.vbap.model.dto;

import java.time.LocalDate;
import java.util.List;

import cz.osu.vbap.model.Comment;
import cz.osu.vbap.model.Playlist;
import cz.osu.vbap.model.Video;

public record UserDTO(String email, String username, String firstName, String lastName, LocalDate dateOfBirth,
        List<Playlist> playlists, List<Video> uploadedVideos, List<Comment> comments) {

}
