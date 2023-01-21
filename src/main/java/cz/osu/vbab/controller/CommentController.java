package cz.osu.vbab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.osu.vbab.exception.NotFoundException;
import cz.osu.vbab.exception.NotOwnerException;
import cz.osu.vbab.model.Comment;
import cz.osu.vbab.service.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    
    @GetMapping("/list/{videoId}")
    public ResponseEntity<Object> getComments(@PathVariable long videoId, @RequestParam int offset, @RequestParam int size) {
        List<Comment> comments = this.commentService.getComments(videoId,offset,size);
        return ResponseEntity.ok(comments);
    }
    
    @PostMapping("/{videoId}")
    public ResponseEntity<Object> newComment(@PathVariable long videoId, @RequestBody String text) {
        try {
            Comment comment = this.commentService.newComment(videoId, text);
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable long videoId, @PathVariable long commentId) {
        try {
            this.commentService.deleteComment(commentId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NotOwnerException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Object> editComment(@PathVariable long commentId, @RequestBody String text) {
        try {
            Comment comment = this.commentService.updateComment(commentId, text);
            return ResponseEntity.ok(comment);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NotOwnerException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
