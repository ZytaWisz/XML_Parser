package org.example.parser;

import java.time.LocalDateTime;

public class ParsingResult {
    private LocalDateTime firstPost;
    private LocalDateTime lastPost;
    private int totalPosts;
    private int totalAcceptedPosts;
    private int sumOfScores;
    private int sumOfComment;

    public ParsingResult() {
    }

    public ParsingResult(LocalDateTime firstPost, LocalDateTime lastPost, int totalPosts, int totalAcceptedPosts, int sumOfScores, int sumOfComment) {
        this.firstPost = firstPost;
        this.lastPost = lastPost;
        this.totalPosts = totalPosts;
        this.totalAcceptedPosts = totalAcceptedPosts;
        this.sumOfScores = sumOfScores;
        this.sumOfComment = sumOfComment;
    }

    public int getAvgScore() {
        return (int) Math.round(sumOfScores / (double) totalPosts);
    }

    public int getAvgComment() {
        return (int) Math.round(sumOfComment / (double) totalPosts);
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

    public Integer getTotalPosts() {
        return totalPosts;
    }

    public Integer getTotalAcceptedPosts() {
        return totalAcceptedPosts;
    }

    public void increasePostNumber() {
        this.totalPosts = this.totalPosts + 1;
    }

    public void increaseTotalAcceptedPosts() {
        this.totalAcceptedPosts = this.totalAcceptedPosts + 1;
    }

    public void increaseSumOfScores(int score) {
        this.sumOfScores = this.sumOfScores + score;
    }

    public void increaseSumOfComment(int comment) {
        this.sumOfComment = this.sumOfComment + comment;
    }
}


