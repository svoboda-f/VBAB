package cz.osu.vbab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.osu.vbab.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    
    Page<Comment> findAllByVideoId(long videoId, Pageable pageable);
}
