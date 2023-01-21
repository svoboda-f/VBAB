package cz.osu.vbab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.osu.vbab.model.Comment;
import cz.osu.vbab.service.CommentService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    
    @GetMapping("/list/{videoId}")
    public ResponseEntity<Object> getComments(@PathVariable long videoId, @RequestParam int offset, @RequestParam int size) {
        try {
            List<Comment> comments = this.commentService.getComments(videoId,offset,size);
            return ResponseEntity.ok(comments);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{videoId}")
    public ResponseEntity<Object> newComment(@PathVariable long videoId, @RequestBody String text) {
        try {
            Comment comment = this.commentService.newComment(videoId, text);
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("");
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable long videoId, @PathVariable long commentId) {
        try {
            this.commentService.deleteComment(commentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Object> editComment(@PathVariable long commentId, @RequestBody String text) {
        try {
            Comment comment = this.commentService.updateComment(commentId, text);
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
