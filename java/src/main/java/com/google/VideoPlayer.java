package com.google;

import java.util.*;

public class VideoPlayer {

    private final VideoLibrary videoLibrary;
    private boolean isVideoPlaying, isVideoPaused;
    private Stack<String> playingStack = new Stack<>();
    private Stack<String> pauseStack = new Stack<>();
    private List<String> allVideoPlaylists = new ArrayList<>();
    private List<Video> videoList = new ArrayList<>();
    Map<String, List<Video>> videoPlaylistMap = new HashMap<>();
    private String pausedVideo;
    private String showPlayedVideoDetails;


    public VideoPlayer() {
        this.videoLibrary = new VideoLibrary();
    }

    public void numberOfVideos() {
        System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
    }

    public void showAllVideos() {
        System.out.println("Here's a list of all available videos:");
        List<Video> videos = videoLibrary.getVideos();
//        Iterator videoListIterator = videos.listIterator();
//        while (videoListIterator.hasNext()) {
//            Video video = (Video) videoListIterator.next();
//            List<String> tags = video.getTags();
//            String showVideos = video.getTitle() +
//                    " (" + video.getVideoId() + ") " + tags + "\n";
//            String newShowVideos = showVideos.replaceAll(",", "");
//            System.out.print(newShowVideos);
//        }

        for (Video video : videos) {
            List<String> tags = video.getTags();
            String showVideos = video.getTitle() +
                    " (" + video.getVideoId() + ") " + tags + "\n";
            String newShowVideos = showVideos.replaceAll(",", "");
            System.out.print(newShowVideos);
        }

    }

    public void playVideo(String videoId) {
        Video playedVideo = videoLibrary.getVideo(videoId);

        if (playedVideo == null) {
            System.out.println("Cannot play video: Video does not exist");
        } else if (isVideoPlaying) {
            System.out.println("Stopping video: " + playingStack.pop());
            System.out.println("Playing video: " + playedVideo.getTitle());
            playingStack.push(playedVideo.getTitle());
            isVideoPaused = false;
            String playedVideoDetails = playedVideo.getTitle() +
                    " (" + playedVideo.getVideoId() + ") " + playedVideo.getTags();
            showPlayedVideoDetails = playedVideoDetails.replaceAll(",", "");
        } else {
            System.out.println("Playing video: " + playedVideo.getTitle());
            isVideoPlaying = true;
            playingStack.push(playedVideo.getTitle());
            isVideoPaused = false;
            String playedVideoDetails = playedVideo.getTitle() +
                    " (" + playedVideo.getVideoId() + ") " + playedVideo.getTags();
            showPlayedVideoDetails = playedVideoDetails.replaceAll(",", "");
        }
    }

    public void stopVideo() {
        if (playingStack.empty()) {
            System.out.println("Cannot stop video: No video is currently playing");
        } else {
            System.out.println("Stopping video: " + playingStack.pop());
            isVideoPaused = false;
            isVideoPlaying = false;
        }
    }

    public void playRandomVideo() {
        List<Video> videos = videoLibrary.getVideos();
        Random rand = new Random();
        int randomIndex = rand.nextInt(videos.size());
        Video randomVideo = videos.get(randomIndex);
        playVideo(randomVideo.getVideoId());
    }


    public void pauseVideo() {
        if (isVideoPaused) {
            System.out.println("Video already paused: " + pausedVideo);
        } else if (isVideoPlaying) {
            //isVideoPlaying = false;
            pauseStack.push(playingStack.peek());
            pausedVideo = pauseStack.pop();
            System.out.println("Pausing video: " + pausedVideo);
            isVideoPaused = true;
        } else {
            System.out.println("Cannot pause video: No video is currently playing");
        }
    }

    public void continueVideo() {
        if (isVideoPaused) {
            System.out.println("Continuing video: " + pausedVideo);
            isVideoPaused = false;
            isVideoPlaying = true;
        } else if (isVideoPlaying) {
            System.out.println("Cannot continue video: Video is not paused");
        } else {
            System.out.println("Cannot continue video: No video is currently playing");
        }
    }

    public void showPlaying() {
        if (isVideoPaused) {
            System.out.println("Currently playing: " + showPlayedVideoDetails + " - PAUSED");
        } else if (isVideoPlaying) {
            System.out.println("Currently playing: " + showPlayedVideoDetails);
        } else {
            System.out.println("No video is currently playing");
        }
    }

    public void createPlaylist(String playlistName) {
        if (!allVideoPlaylists.stream().anyMatch(playlistName::equalsIgnoreCase) && !playlistName.isEmpty()) {
            allVideoPlaylists.add(playlistName.toLowerCase());
            System.out.println("Successfully created new playlist: " + playlistName);
        } else {
            System.out.println("Cannot create playlist: A playlist with the same name already exists");
        }
    }

    public void addVideoToPlaylist(String playlistName, String videoId) {
        Video video = videoLibrary.getVideo(videoId);
        if(!allVideoPlaylists.contains(playlistName.toLowerCase())) {
            System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
        }else if(!allVideoPlaylists.contains(playlistName.toLowerCase()) && video == null) {
            System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
        }else if(video == null){
            System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
        }else if(allVideoPlaylists.contains(playlistName.toLowerCase()) && videoList.contains(video)) {
            System.out.println("Cannot add video to " + playlistName + ": Video already added");
        }else if(allVideoPlaylists.contains(playlistName.toLowerCase())){
            videoList.add(video);
            videoPlaylistMap.put(playlistName.toLowerCase(), videoList);
            System.out.println("Added video to " + playlistName + ": " + video.getTitle());
//            System.out.println(videoList);
//            System.out.println(videoPlaylistMap);
        }else {
            System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
        }
    }

    public void showAllPlaylists() {
        if(allVideoPlaylists.isEmpty()){
            System.out.println("No playlists exist yet");
        }else{
            System.out.println("Showing all playlists:");
            Collections.sort(allVideoPlaylists);
            for(String videoPlaylist : allVideoPlaylists){
                System.out.println(videoPlaylist);
            }
        }
    }

    public void showPlaylist(String playlistName) {
        System.out.println("showPlaylist needs implementation");
    }

    public void removeFromPlaylist(String playlistName, String videoId) {
        System.out.println("removeFromPlaylist needs implementation");
    }

    public void clearPlaylist(String playlistName) {
        System.out.println("clearPlaylist needs implementation");
    }

    public void deletePlaylist(String playlistName) {
        if(!allVideoPlaylists.contains(playlistName.toLowerCase())) {
            System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
        }else{
            allVideoPlaylists.remove(playlistName);
            System.out.println("Deleted playlist: " + playlistName);
        }
    }

    public void searchVideos(String searchTerm) {
        System.out.println("searchVideos needs implementation");
    }

    public void searchVideosWithTag(String videoTag) {
        System.out.println("searchVideosWithTag needs implementation");
    }

    public void flagVideo(String videoId) {
        System.out.println("flagVideo needs implementation");
    }

    public void flagVideo(String videoId, String reason) {
        System.out.println("flagVideo needs implementation");
    }

    public void allowVideo(String videoId) {
        System.out.println("allowVideo needs implementation");
    }
}