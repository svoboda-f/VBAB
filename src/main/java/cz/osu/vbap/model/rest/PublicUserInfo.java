package cz.osu.vbap.model.rest;

import java.util.List;

import cz.osu.vbap.model.Playlist;
import cz.osu.vbap.model.Video;

public record PublicUserInfo(String username, List<Video> videos, List<Playlist> playlists) {
}
