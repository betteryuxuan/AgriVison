package com.example.communityfragment.contract;

import com.example.communityfragment.bean.Comment;

import java.util.List;

public interface IPostContract {
    interface View {
        void onCommentsSuccess(List<Comment> comments);

        void onCommentsFailure();

        void onPublishCommentSuccess(Comment comment);

        void onPublishCommentFailure();

        void onDeletePostSuccess(int postId);

        void onDeleteCommentSuccess(Comment comment);
    }

    interface Presenter {
        void comment(int postId, String comment, int parentId, int rootId);
        void deletePost(int id);
        void deleteComment(Comment comment);

        void onCommentsSuccess(List<Comment> comments);

        void onCommentsFailure();

        void onPublishCommentSuccess(Comment comment);

        void onPublishCommentFailure();

        void deletePostSuccess(int postId);
        void deleteCommentSuccess(Comment comment);

    }

    interface Model {
        void comment(int postId, String comment, int parentId, int rootId);

        void getComments(int postId);

        void deletePost(int id);

        void deleteComment(Comment comment);

    }


}
