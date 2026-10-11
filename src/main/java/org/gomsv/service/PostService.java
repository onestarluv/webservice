package org.gomsv.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.gomsv.common.ErrorMessages;
import org.gomsv.domain.Comment;
import org.gomsv.domain.Post;
import org.gomsv.dto.CommentCreateRequest;
import org.gomsv.dto.CommentResponse;
import org.gomsv.dto.CommentUpdateRequest;
import org.gomsv.dto.PostCreateRequest;
import org.gomsv.dto.PostDetailResponse;
import org.gomsv.dto.PostSummaryResponse;
import org.gomsv.dto.PostUpdateRequest;
import org.gomsv.exception.NotFoundException;
import org.gomsv.repository.InMemoryPostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final InMemoryPostRepository postRepository;
    private final AtomicLong commentIdSequence = new AtomicLong(0);

    public PostService(InMemoryPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDetailResponse createPost(PostCreateRequest request) {
        Post post = postRepository.create(request.title(), request.content());
        return PostDetailResponse.from(post);
    }

    public CommentResponse createComment(Long postId, CommentCreateRequest request) {
        Post post = getPostEntity(postId);
        Comment comment = new Comment(commentIdSequence.incrementAndGet(), request.content());
        post.addComment(comment);
        return CommentResponse.from(comment);
    }

    public List<PostSummaryResponse> getPosts(String keyword) {
        return postRepository.findAll().stream()
                .filter(post -> keyword == null || keyword.isBlank()
                        || post.getTitle().contains(keyword)
                        || post.getContent().contains(keyword))
                .map(PostSummaryResponse::from)
                .toList();
    }

    public PostDetailResponse getPost(Long postId) {
        return PostDetailResponse.from(getPostEntity(postId));
    }

    public PostDetailResponse replacePost(Long postId, PostUpdateRequest request) {
        Post post = getPostEntity(postId);
        post.replace(request.title(), request.content());
        return PostDetailResponse.from(post);
    }

    public CommentResponse updateComment(Long postId, Long commentId, CommentUpdateRequest request) {
        Comment comment = getCommentEntity(getPostEntity(postId), commentId);
        comment.update(request.content());
        return CommentResponse.from(comment);
    }

    public void deletePost(Long postId) {
        getPostEntity(postId);
        postRepository.deleteById(postId);
    }

    public void deleteComment(Long postId, Long commentId) {
        Post post = getPostEntity(postId);
        Comment comment = getCommentEntity(post, commentId);
        post.removeComment(comment);
    }

    private Post getPostEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.POST_NOT_FOUND));
    }

    private Comment getCommentEntity(Post post, Long commentId) {
        return post.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorMessages.COMMENT_NOT_FOUND));
    }
}
