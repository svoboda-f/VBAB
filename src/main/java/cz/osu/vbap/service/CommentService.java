package cz.osu.vbap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cz.osu.vbap.exception.NotFoundException;
import cz.osu.vbap.exception.NotOwnerException;
import cz.osu.vbap.model.Comment;
import cz.osu.vbap.model.Video;
import cz.osu.vbap.repository.CommentRepository;
import cz.osu.vbap.repository.VideoRepository;
import cz.osu.vbap.utils.Ownership;

@Service
public class CommentService {
    @Autowired
    private AuthService authService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VideoRepository videoRepository;

    public List<Comment> getComments(long videoId, int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size);
        Page<Comment> page = this.commentRepository.findAllByVideoId(videoId, pageable);
        return page.toList();
    }

    public Comment newComment(long videoId, String text) throws NotFoundException {
        Video video = this.videoRepository.findById(videoId).orElseThrow(()-> new NotFoundException(Video.class, videoId));
        Comment comment = new Comment(text, this.authService.getCurrentUser(), video);
        video.getComments().add(comment);
        return this.commentRepository.save(comment);
    }

    public void deleteComment(long commentId) throws NotFoundException, NotOwnerException {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new NotFoundException(Comment.class, commentId));
        Ownership.check(comment, this.authService.getCurrentUserId());
        this.commentRepository.delete(comment);
    }

	public Comment updateComment(long commentId, String text) throws NotFoundException, NotOwnerException{
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(Comment.class, commentId));
        Ownership.check(comment, this.authService.getCurrentUserId());
        comment.setText(text);
        return this.commentRepository.save(comment);
	}

}
