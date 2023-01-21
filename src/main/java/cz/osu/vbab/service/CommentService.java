package cz.osu.vbab.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cz.osu.vbab.model.Comment;
import cz.osu.vbab.model.Video;
import cz.osu.vbab.repository.CommentRepository;
import cz.osu.vbab.repository.VideoRepository;

@Service
public class CommentService {
    @Autowired
    private AuthService authService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private Logger logger ;

    public List<Comment> getComments(long videoId, int offset, int size) throws Exception{
        // Video video = this.videoRepository.findById(videoId).orElseThrow(() -> new Exception());
        Pageable pageable = PageRequest.of(offset, size);
        Page<Comment> page = this.commentRepository.findAllByVideoId(videoId, pageable);
        return page.toList();
    }

    public Comment newComment(long videoId, String text) throws Exception{
        Video video = this.videoRepository.findById(videoId).orElseThrow(()->new Exception());
        Comment comment = new Comment(text, this.authService.getCurrentUser(), video);
        video.getComments().add(comment);
        return this.commentRepository.save(comment);
    }

    public void deleteComment(long commentId) throws Exception {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new Exception());
        if (comment.getUser().getId() != this.authService.getCurrentUserId()) {
            throw new Exception();
        }
        this.commentRepository.delete(comment);
    }

	public Comment updateComment(long commentId, String text) throws Exception{
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new Exception());
        if (comment.getUser().getId() != this.authService.getCurrentUserId()) {
            throw new Exception();
        }
        comment.setText(text);
        return this.commentRepository.save(comment);
	}
}
