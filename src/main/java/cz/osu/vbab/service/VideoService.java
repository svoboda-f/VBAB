package cz.osu.vbab.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.osu.vbab.model.Video;
import cz.osu.vbab.model.rest.VideoPatch;
import cz.osu.vbab.repository.VideoRepository;

@Service
public class VideoService {
    
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private AuthService authService;

    public List<Video> getAll() {
        return this.videoRepository.findAll();
    }

    public Video getById(long id) throws Exception {
        Optional<Video> video = this.videoRepository.findById(id);

        if(video.isEmpty()) {
            throw new Exception("Video with id " + id + " not found");
        }
        video.get().addView();
        return this.videoRepository.save(video.get());
    }

    public Video newVideo(Video video) {
        Video ret = new Video(video.getTitle(), video.getDescription(), video.getSourceUrl(), this.authService.getCurrentUser());
        return this.videoRepository.save(ret);
    }

    public Video updateVideo(long id, VideoPatch videoPatch) throws Exception{
        Video video = this.videoRepository.findById(id).orElseThrow(() -> new Exception("Video with id " + id + " not found"));
        video.setTitle(videoPatch.title());
        video.setDescription(videoPatch.description());
        return this.videoRepository.save(video);
    }

    public void deleteVideo(long id) throws Exception {
        Optional<Video> video = this.videoRepository.findById(id);

        if(video.isEmpty()) {
            throw new Exception("Video with id " + id + " not found");
        }

        if(video.get().getUser().getId() != this.authService.getCurrentUserId()) {
            throw new Exception("Video with id " + id + " isn't yours");
        }

        this.videoRepository.delete(video.get());
    }

}
