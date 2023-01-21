package cz.osu.vbap.utils;

import cz.osu.vbap.exception.NotOwnerException;
import cz.osu.vbap.model.Comment;
import cz.osu.vbap.model.Playlist;
import cz.osu.vbap.model.Video;

public class Ownership {
    


    public static void check(Video video, long userId) throws NotOwnerException {
        if (video.getUser().getId() != userId) {
            throw new NotOwnerException(Video.class, video.getId());
        }
    }

    public static void check(Comment comment, long userId) throws NotOwnerException {
        if (comment.getUser().getId() != userId) {
            throw new NotOwnerException(Comment.class, comment.getId());
        }
    }

    public static void check(Playlist playlist, long userId) throws NotOwnerException {
        if (playlist.getUser().getId() != userId) {
            throw new NotOwnerException(Playlist.class, playlist.getId());
        }
    }


}
