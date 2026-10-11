package org.gomsv.repository;

import java.util.List;
import java.util.Optional;
import org.gomsv.domain.Post;

public interface PostRepository {

    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(Long id);

    void deleteById(Long id);
}
