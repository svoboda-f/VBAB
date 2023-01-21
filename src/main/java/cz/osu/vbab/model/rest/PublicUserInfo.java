package cz.osu.vbab.model.rest;

import java.util.List;

import cz.osu.vbab.model.Playlist;
import cz.osu.vbab.model.Video;

public record PublicUserInfo(String username, List<Video> videos, List<Playlist> playlists) {
}
