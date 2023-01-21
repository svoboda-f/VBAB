package cz.osu.vbab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.osu.vbab.model.Playlist;
import cz.osu.vbab.service.PlaylistService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/list")
    public ResponseEntity<Object> getAllPlaylists(@RequestParam int offset, @RequestParam int size) {
        List<Playlist> playlists = this.playlistService.getAllPlaylists(offset, size);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/list/{userId}")
    public List<Playlist> getAllPlaylistsByUserId(@PathVariable long userId, @RequestParam int offset,
            @RequestParam int size) {
        return this.playlistService.getAllPlaylistsByUserId(userId, offset, size);
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<Object> getPlaylistById(@PathVariable long playlistId) {
        try {
            Playlist ret = this.playlistService.getPlaylistById(playlistId);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    @PostMapping()
    public ResponseEntity<Object> createPlaylist(@RequestBody Playlist playlist) {
        try {
            Playlist ret = this.playlistService.createPlaylist(playlist);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{playlistId}")
    public ResponseEntity<Object> updatePlaylistInfo(@PathVariable long playlistId, @RequestBody String newName) {
        try {
            Playlist ret = this.playlistService.updatePlaylistName(playlistId, newName);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Object> deletePlaylist(@PathVariable long playlistId) {
        try {
            this.playlistService.deletePlaylist(playlistId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{playlistId}/{videoId}")
    public ResponseEntity<Object> addVideoToPlaylist(@PathVariable long playlistId, @PathVariable long videoId) {
        try {
            this.playlistService.addVideoToPlaylist(playlistId, videoId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{playlistId}/{videoId}")
    public ResponseEntity<Object> removeVideofromPlaylist(@PathVariable long playlistId, @PathVariable long videoId) {
        try {
            this.playlistService.removeVideofromPlaylist(playlistId, videoId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
