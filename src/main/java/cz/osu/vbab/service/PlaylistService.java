package cz.osu.vbab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cz.osu.vbab.model.Playlist;
import cz.osu.vbab.repository.PlaylistRepository;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private AuthService authService;

    public List<Playlist> getAllPlaylists(int offset, int size) {
        return this.playlistRepository.findAll(PageRequest.of(offset, size)).toList();
    }

    public List<Playlist> getAllPlaylistsByUserId(long userId, int offset, int size) {
        return this.playlistRepository.findAllByUserId(userId, PageRequest.of(offset, size));
    }

    public Playlist getPlaylistById(long playlistId) throws Exception {
        return this.playlistRepository.findById(playlistId).orElseThrow(() -> new Exception());
    }

    public Playlist createPlaylist(Playlist playlist) {
        return null;
    }

    public Playlist updatePlaylistName(long playlistId, String name) throws Exception {
        Playlist playlist = this.playlistRepository.findById(playlistId).orElseThrow(() -> new Exception());
        checkIfOwner(playlist);
        playlist.setName(name);
        return this.playlistRepository.save(playlist);
    }

    public void deletePlaylist(long playlistId) throws Exception {
        Playlist playlist = this.playlistRepository.findById(playlistId).orElseThrow(() -> new Exception());
        checkIfOwner(playlist);
        this.playlistRepository.delete(playlist);
    }

    public void addVideoToPlaylist(long playlistId, long videoId) throws Exception {
        Playlist playlist = this.playlistRepository.findById(playlistId).orElseThrow(() -> new Exception());
        checkIfOwner(playlist);
        
    }

    public void removeVideofromPlaylist(long playlistId, long videoId) throws Exception {
        Playlist playlist = this.playlistRepository.findById(playlistId).orElseThrow(() -> new Exception());
        checkIfOwner(playlist);

    }

    private void checkIfOwner(Playlist playlist) throws Exception {
        if (playlist.getUser().getId() != this.authService.getCurrentUserId()) {
            throw new Exception();
        }
    }

}
