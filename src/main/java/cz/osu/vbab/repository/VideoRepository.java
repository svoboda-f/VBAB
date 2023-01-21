package cz.osu.vbab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.osu.vbab.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

}
