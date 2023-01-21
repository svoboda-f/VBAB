package cz.osu.vbap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cz.osu.vbap.exception.NotFoundException;
import cz.osu.vbap.exception.NotOwnerException;
import cz.osu.vbap.model.Video;
import cz.osu.vbap.model.rest.VideoPatch;
import cz.osu.vbap.repository.VideoRepository;
import cz.osu.vbap.utils.Ownership;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private AuthService authService;

    public List<Video> findAll(int offset, int size) {
        return this.videoRepository.findAll(PageRequest.of(offset, size)).toList();
    }

    public List<Video> findAllByTitleContaining(String partialTitle, int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size);
        return this.videoRepository.findAllByTitleContaining(partialTitle, pageable);
    }

    public Video getById(long videoId) throws NotFoundException {
        Video video = this.videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(Video.class, videoId));
        video.addView();
        return this.videoRepository.save(video);
    }

    public Video newVideo(Video video) {
        Video newVideo = new Video(video.getTitle(), video.getDescription(), video.getSourceUrl(),
                this.authService.getCurrentUser());
        return this.videoRepository.save(newVideo);
    }

    public Video updateVideo(long videoId, VideoPatch videoPatch) throws NotFoundException, NotOwnerException {
        Video video = this.videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(Video.class, videoId));
        Ownership.check(video, this.authService.getCurrentUserId());
        video.setTitle(videoPatch.title());
        video.setDescription(videoPatch.description());
        return this.videoRepository.save(video);
    }

    public void deleteVideo(long videoId) throws NotFoundException, NotOwnerException {
        Video video = this.videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(Video.class, videoId));
        Ownership.check(video, this.authService.getCurrentUserId());
        video.getPlaylists().forEach(playlist -> playlist.getVideos().remove(video));
        this.videoRepository.delete(video);
    }

}
