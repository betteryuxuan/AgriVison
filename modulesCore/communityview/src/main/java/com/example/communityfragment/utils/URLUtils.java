package com.example.communityfragment.utils;

public class URLUtils {
    private static final String BASE_URL = "http://101.200.122.3:8080";
    // 查询帖子列表
    public static final String GET_POSTS_URL = BASE_URL + "/community-post/posts";
    // 查询指定社区帖子列表
    public static final String GET_SPECIFIC_POSTS_URL = BASE_URL + "/community-post/community";
    // 查询当前用户帖子列表
    public static final String GET_USER_POSTS_URL = BASE_URL + "/community-post/user/posts";
    // 查询当前用户点赞帖子列表
    public static final String GET_LIKED_POSTS_URL = BASE_URL + "/community-post/user/likes";
    // 游客查询帖子列表
    public static final String GET_GUEST_POSTS_URL = BASE_URL + "/community-post/posts/guest";
    // 游客查询社区下的帖子列表
    public static final String GET_GUEST_SPECIFIC_POSTS_URL = BASE_URL + "/community-post/community";

    // 发布帖子
    public static final String PUBLISH_POST_URL = BASE_URL + "/community-post/post";
    // 删除帖子
    public static final String DELETE_POST_URL = BASE_URL + "/community-post/post";
    // 帖子投票
    public static final String VOTE_POST_URL = BASE_URL + "/community-post/post/vote";

    // 上传图片
    public static final String UPLOAD_URL = BASE_URL + "/community-post/upload";

    // 发布评论
    public static final String PUBLISH_COMMENT_URL = BASE_URL + "/community-post/comment";
    // 查询一级评论
    public static final String GET_FIRST_LEVEL_COMMENTS_URL = BASE_URL + "/community-post/first-level-comment";
    // 查询所有评论
    public static final String GET_ALL_COMMENTS_URL = BASE_URL + "/community-post/comment";
    // 删除评论
    public static final String DELETE_COMMENT_URL = BASE_URL + "/community-post/comment";

}
