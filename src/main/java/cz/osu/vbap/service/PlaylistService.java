package cz.osu.vbap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cz.osu.vbap.exception.NotFoundException;
import cz.osu.vbap.exception.NotOwnerException;
import cz.osu.vbap.model.Playlist;
import cz.osu.vbap.model.Video;
import cz.osu.vbap.repository.PlaylistRepository;
import cz.osu.vbap.repository.VideoRepository;
import cz.osu.vbap.utils.Ownership;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private AuthService authService;

    public List<Playlist> getAllPlaylists(int offset, int size) {
        return this.playlistRepository.findAll(PageRequest.of(offset, size)).toList();
    }

    public List<Playlist> getAllPlaylistsByUserId(long userId, int offset, int size) {
        return this.playlistRepository.findAllByUserId(userId, PageRequest.of(offset, size));
    }

    public Playlist getPlaylistById(long playlistId) throws NotFoundException {
        return this.playlistRepository.findById(playlistId).orElseThrow(() -> new NotFoundException(Playlist.class, playlistId));
    }

    public Playlist createPlaylist(Playlist playlist) {
        Playlist newPlaylist = new Playlist(playlist.getName(), this.authService.getCurrentUser());
        return this.playlistRepository.save(newPlaylist);
    }

    public Playlist updatePlaylistName(long playlistId, String name) throws NotFoundException, NotOwnerException {
        Playlist playlist = this.playlistRepository.findById(playlistId).orElseThrow(() -> new NotFoundException(Playlist.class, playlistId));
        Ownership.check(playlist, this.authService.getCurrentUserId());
        playlist.setName(name);
        return this.playlistRepository.save(playlist);
    }

    public void deletePlaylist(long playlistId) throws NotFoundException, NotOwnerException {
        Playlist playlist = this.playlistRepository.findById(playlistId).orElseThrow(() -> new NotFoundException(Playlist.class, playlistId));
        Ownership.check(playlist, this.authService.getCurrentUserId());
        this.playlistRepository.delete(playlist);
    }

    public void addVideoToPlaylist(long playlistId, long videoId) throws NotFoundException, NotOwnerException {
        Playlist playlist = this.playlistRepository.findById(playlistId).orElseThrow(() -> new NotFoundException(Playlist.class, playlistId));
        Ownership.check(playlist, this.authService.getCurrentUserId());
        Video video = this.videoRepository.findById(videoId).orElseThrow(() -> new NotFoundException(Video.class, videoId));
        playlist.getVideos().add(video);
        this.playlistRepository.save(playlist);
    }

    public void removeVideofromPlaylist(long playlistId, long videoId) throws NotFoundException, NotOwnerException {
        Playlist playlist = this.playlistRepository.findById(playlistId).orElseThrow(() -> new NotFoundException(Playlist.class, playlistId));
        Ownership.check(playlist, this.authService.getCurrentUserId());
        Video video = this.videoRepository.findById(videoId).orElseThrow(() -> new NotFoundException(Video.class, videoId));
        playlist.getVideos().remove(video);
        this.playlistRepository.save(playlist);
    }

}
