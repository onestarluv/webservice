package org.gomsv.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.gomsv.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPostRepository implements PostRepository {

    private final AtomicLong postIdSequence = new AtomicLong(0);
    private final ConcurrentHashMap<Long, Post> store = new ConcurrentHashMap<>();

    @Override
    public Post save(Post post) {
        store.put(post.getId(), post);
        return post;
    }

    public Post create(String title, String content) {
        Post post = new Post(postIdSequence.incrementAndGet(), title, content);
        return save(post);
    }

    @Override
    public List<Post> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(Post::getId))
                .toList();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
