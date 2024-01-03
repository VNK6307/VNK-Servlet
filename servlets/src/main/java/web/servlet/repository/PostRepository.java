package web.servlet.repository;

import web.servlet.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Stub
public class PostRepository {

    // TODO - Выбрать тип коллекции. Но тип переменных менять нельзя.
    public List<Post> all() {
        return Collections.emptyList();
    }

    public Optional<Post> getById(long id) {
        return Optional.empty();
    }

    public Post save(Post post) {
        return post;
    }

    public void removeById(long id) {
    }
}
