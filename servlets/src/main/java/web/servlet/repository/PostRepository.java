package web.servlet.repository;

import org.springframework.stereotype.Repository;
import web.servlet.exception.NotFoundException;
import web.servlet.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository("repository")
public class PostRepository {
    Map<Long, Post> postRepo = new ConcurrentHashMap<>();
    AtomicLong atomicLong = new AtomicLong();

    public List<Post> all() {
        return new ArrayList<>(postRepo.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(postRepo.getOrDefault(id, null));
//        return Optional.of(postRepo.get(id));
    }

    public Post save(Post post) {
        long idR;
        if (post.getId() == 0) {
            idR = atomicLong.getAndIncrement();
            post.setId(idR);
            postRepo.put(post.getId(), post);
        }
        postRepo.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        if (postRepo.containsKey(id)) {
            postRepo.remove(id);
        } else {
            throw new NotFoundException("Поста с таким номером не существует.");
        }
    }
}
