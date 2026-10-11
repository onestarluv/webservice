package org.gomsv.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import org.gomsv.common.ApiResponse;
import org.gomsv.dto.CommentCreateRequest;
import org.gomsv.dto.CommentResponse;
import org.gomsv.dto.CommentUpdateRequest;
import org.gomsv.dto.PostCreateRequest;
import org.gomsv.dto.PostDetailResponse;
import org.gomsv.dto.PostSummaryResponse;
import org.gomsv.dto.PostUpdateRequest;
import org.gomsv.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostDetailResponse>> createPost(
            @Valid @RequestBody PostCreateRequest request
    ) {
        PostDetailResponse response = postService.createPost(request);
        return ResponseEntity.created(URI.create("/api/posts/" + response.id()))
                .body(ApiResponse.success(response));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable @Positive Long postId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = postService.createComment(postId, request);
        return ResponseEntity.created(URI.create("/api/posts/" + postId + "/comments/" + response.id()))
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostSummaryResponse>>> getPosts(
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(ApiResponse.success(postService.getPosts(keyword)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPost(
            @PathVariable @Positive Long postId
    ) {
        return ResponseEntity.ok(ApiResponse.success(postService.getPost(postId)));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> replacePost(
            @PathVariable @Positive Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(postService.replacePost(postId, request)));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable @Positive Long postId,
            @PathVariable @Positive Long commentId,
            @Valid @RequestBody CommentUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(postService.updateComment(postId, commentId, request)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable @Positive Long postId
    ) {
        postService.deletePost(postId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable @Positive Long postId,
            @PathVariable @Positive Long commentId
    ) {
        postService.deleteComment(postId, commentId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
