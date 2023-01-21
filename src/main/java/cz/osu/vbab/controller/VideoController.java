package cz.osu.vbab.controller;

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

import cz.osu.vbab.model.Video;
import cz.osu.vbab.model.rest.VideoPatch;
import cz.osu.vbab.service.VideoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    //? nepřihlášený
    // @GetMapping("/list")
    // public ResponseEntity<Object> getPaginated(@RequestParam int offset, @RequestParam int size) {
        
    // }

    //? nepřihlášený
    @GetMapping("/{id}")
    public ResponseEntity<Object> getByVideoId(@PathVariable long id) {
        try {
            Video video = this.videoService.getById(id);
            return ResponseEntity.ok(video);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //? nepřihlášený
    // @GetMapping()
    // public ResponseEntity<Object> getVideoContaining(@RequestParam String partialTitle) {
        
    // }

    //! Přihlášený
    @PostMapping()
    public ResponseEntity<Object> newVideo(@RequestBody @Valid Video video) {
        Video ret = this.videoService.newVideo(video);
        return ResponseEntity.ok(ret);
    }

    //! Přihlášený
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVideo(@PathVariable long id) {
        try {
            this.videoService.deleteVideo(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        
    }

    //! Přihlášený
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateVideo(@PathVariable long id, @RequestBody VideoPatch videoPatch) {
        try {
            Video video = this.videoService.updateVideo(id, videoPatch);
            return ResponseEntity.ok(video);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
