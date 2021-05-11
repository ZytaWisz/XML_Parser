package org.example.dto;

import java.time.LocalDateTime;

public class ParsingDetails {

    private LocalDateTime firstPost;
    private LocalDateTime lastPost;
    private int totalPosts;
    private int totalAcceptedPosts;
    private int avgScore;
    private int avgNoOfComments;

    public ParsingDetails() {
    }

    public ParsingDetails(LocalDateTime firstPost, LocalDateTime lastPost, int totalPosts, int totalAcceptedPosts, int avgScore, int avgNoOfComments) {
        this.firstPost = firstPost;
        this.lastPost = lastPost;
        this.totalPosts = totalPosts;
        this.totalAcceptedPosts = totalAcceptedPosts;
        this.avgScore = avgScore;
        this.avgNoOfComments = avgNoOfComments;
    }

    public LocalDateTime getFirstPost() {
        return firstPost;
    }

    public void setFirstPost(LocalDateTime firstPost) {
        this.firstPost = firstPost;
    }

    public LocalDateTime getLastPost() {
        return lastPost;
    }

    public void setLastPost(LocalDateTime lastPost) {
        this.lastPost = lastPost;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(int totalPosts) {
        this.totalPosts = totalPosts;
    }

    public int getTotalAcceptedPosts() {
        return totalAcceptedPosts;
    }

    public void setTotalAcceptedPosts(int totalAcceptedPosts) {
        this.totalAcceptedPosts = totalAcceptedPosts;
    }

    public int getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(int avgScore) {
        this.avgScore = avgScore;
    }

    public int getAvgNoOfComments() {
        return avgNoOfComments;
    }

    public void setAvgNoOfComments(int avgNoOfComments) {
        this.avgNoOfComments = avgNoOfComments;
    }
}
