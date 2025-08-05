package com.example.communityfragment.bean;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Comment {
    // 帖子基本信息
    private int id;
    private String content;
    private String time;
    private String likeCount;
    private String repliesCount;
    private boolean isLiked;
    private List<Comment> childers = new ArrayList<>();
    private boolean isMoreIndicator = false;

    // 作者
    private String userName;
    private int userid;
    private String userAavatar;

    // 父评论信息
    private int parentId = 0;

    // 父评论作者
    private String parentUserName;
    private String parentAavatar;

    // 根评论
    private int rootId= 0;

    // 如果当前是展开标记符，则表示当前评论的展开状态
    private boolean isExpanded = false;

    public Comment() {
    }

    public List<Comment> getChilders() {
        return childers != null ? childers : new ArrayList<>();
    }

    public void setChilders(List<Comment> childers) {
        this.childers = childers;
    }

    public boolean isMoreIndicator() {
        return isMoreIndicator;
    }

    public void setMoreIndicator(boolean moreIndicator) {
        isMoreIndicator = moreIndicator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentAavatar() {
        return parentAavatar;
    }

    public void setParentAavatar(String parentAavatar) {
        this.parentAavatar = parentAavatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserAavatar() {
        return userAavatar;
    }

    public void setUserAavatar(String userAavatar) {
        this.userAavatar = userAavatar;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(String repliesCount) {
        this.repliesCount = repliesCount;
    }

    public int getParentId() {
        return parentId;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public String getParentUserName() {
        return parentUserName;
    }

    public void setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
    }

    @NonNull
    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", userid=" + userid +
                ", userName='" + userName + '\'' +
                ", userAavatar='" + userAavatar + '\'' +
                ", time='" + time + '\'' +
                ", likeCount='" + likeCount + '\'' +
                ", repliesCount='" + repliesCount + '\'' +
                ", parentUserName='" + parentUserName + '\'' +
                ", parentId=" + parentId +
                ", rootId=" + rootId +
                ", isLiked=" + isLiked +
                '}';
    }
}
