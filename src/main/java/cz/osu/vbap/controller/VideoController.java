package cz.osu.vbap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.osu.vbap.exception.NotFoundException;
import cz.osu.vbap.exception.NotOwnerException;
import cz.osu.vbap.model.Video;
import cz.osu.vbap.model.rest.VideoPatch;
import cz.osu.vbap.service.VideoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/list")
    public ResponseEntity<Object> findAll(@RequestParam int offset, @RequestParam int size) {
        List<Video> videos = this.videoService.findAll(offset, size);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long videoId) {
        try {
            Video video = this.videoService.getById(videoId);
            return ResponseEntity.ok(video);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Object> findAllByTitleContaining(@RequestParam String partialTitle, @RequestParam int offset,
            @RequestParam int size) {
        List<Video> videos = this.videoService.findAllByTitleContaining(partialTitle, offset, size);
        return ResponseEntity.ok(videos);
    }

    @PostMapping()
    public ResponseEntity<Object> newVideo(@RequestBody @Valid Video video) {
        Video ret = this.videoService.newVideo(video);
        return ResponseEntity.ok(ret);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVideo(@PathVariable long videoId) {
        try {
            this.videoService.deleteVideo(videoId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NotOwnerException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateVideo(@PathVariable long videoId, @RequestBody VideoPatch videoPatch) {
        try {
            Video video = this.videoService.updateVideo(videoId, videoPatch);
            return ResponseEntity.ok(video);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NotOwnerException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

}
