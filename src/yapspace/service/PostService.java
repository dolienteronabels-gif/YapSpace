package yapspace.service;

import yapspace.exception.NotFoundException;
import yapspace.exception.PermissionException;
import yapspace.exception.ValidationException;
import yapspace.model.Category;
import yapspace.model.Comment;
import yapspace.model.Feeling;
import yapspace.model.Post;
import yapspace.model.Reaction;
import yapspace.model.ReactionType;
import yapspace.model.User;
import yapspace.storage.DataStore;
import yapspace.repository.PostRepository;
import yapspace.repository.CommentRepository;
import yapspace.repository.ReactionRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReactionRepository reactionRepository;

    public PostService(DataStore store) {
        this.postRepository = new PostRepository();
        this.commentRepository = new CommentRepository();
        this.reactionRepository = new ReactionRepository();
    }

    public Post createPost(User author, String content,
                           String categoryCode, String feelingCode) {
        String text = Validator.postContent(content);
        Category category = Category.parse(categoryCode);
        Feeling feeling = Feeling.parse(feelingCode);

        int postId = postRepository.save(
                author.getUsername(), text, category, feeling);

        return getPost(postId);
    }

    public Post editPost(User editor, int postId, String content,
                         String categoryCode, String feelingCode) {
        String text = Validator.postContent(content);
        Category category = Category.parse(categoryCode);
        Feeling feeling = Feeling.parse(feelingCode);

        Post post = getPost(postId);

        if (!editor.canEdit(post)) {
            throw new PermissionException(
                    "You can only edit your own posts.");
        }

        post.update(text, category, feeling);
        postRepository.update(post);

        return getPost(postId);
    }

    public void deletePost(User actor, int postId) {
        Post post = getPost(postId);

        if (!actor.canDelete(post)) {
            throw new PermissionException(
                    "You can only delete your own posts.");
        }

        reactionRepository.deleteByPost(postId);
        commentRepository.deleteByPost(postId);
        postRepository.delete(postId);
    }

    public Post getPost(int postId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException(
                    "That post does not exist (it may have been deleted).");
        }

        return post;
    }

    public List<Post> allPosts() {
        List<Post> posts = new ArrayList<>(
                postRepository.findAll());

        posts.sort(newestFirst());
        return posts;
    }

    public List<Post> postsBy(String username) {
        List<Post> posts = new ArrayList<>(
                postRepository.findByAuthor(username));

        posts.sort(newestFirst());
        return posts;
    }

    private static Comparator<Post> newestFirst() {
        return Comparator.comparing(Post::getCreatedAt)
                .thenComparingInt(Post::getId)
                .reversed();
    }

    public ReactionType reactToPost(
            User user, int postId, String reactionCode) {

        ReactionType type = ReactionType.parse(reactionCode);
        getPost(postId);

        Reaction existing =
                reactionRepository.findByUser(
                        postId, user.getUsername());

        if (existing == null) {
            return addReaction(user, postId, type);
        }

        if (existing.getType() == type) {
            removeReaction(user, postId);
            return null;
        }

        reactionRepository.update(
                postId, user.getUsername(), type);

        return type;
    }

    public ReactionType addReaction(
            User user, int postId, ReactionType type) {

        getPost(postId);

        if (reactionRepository.findByUser(
                postId, user.getUsername()) != null) {
            throw new ValidationException(
                    "You already reacted to this post.");
        }

        reactionRepository.save(
                postId, user.getUsername(), type);

        return type;
    }

    public void removeReaction(User user, int postId) {
        reactionRepository.delete(
                postId, user.getUsername());
    }

    public ReactionType reactionOf(
            int postId, String username) {

        Reaction reaction =
                reactionRepository.findByUser(postId, username);

        return reaction == null ? null : reaction.getType();
    }

    public Map<ReactionType, Integer> reactionCounts(int postId) {
        Map<ReactionType, Integer> counts =
                new EnumMap<>(ReactionType.class);

        for (ReactionType type : ReactionType.values()) {
            counts.put(type, 0);
        }

        for (Reaction reaction :
                reactionRepository.findByPost(postId)) {
            counts.merge(reaction.getType(), 1, Integer::sum);
        }

        return counts;
    }

    public Comment addComment(
            User author, int postId, String content) {

        String text = Validator.commentContent(content);
        getPost(postId);

        int commentId = commentRepository.save(
                postId, author.getUsername(), text);

        return getComment(commentId);
    }

    public Comment editComment(
            User editor, int commentId, String content) {

        String text = Validator.commentContent(content);
        Comment comment = getComment(commentId);

        if (!editor.canEdit(comment)) {
            throw new PermissionException(
                    "You can only edit your own comments.");
        }

        comment.edit(text);
        commentRepository.update(comment);

        return getComment(commentId);
    }

    public void deleteComment(User actor, int commentId) {
        Comment comment = getComment(commentId);

        if (!actor.canDelete(comment)) {
            throw new PermissionException(
                    "You can only delete your own comments.");
        }

        commentRepository.delete(commentId);
    }

    public Comment getComment(int commentId) {
        Comment comment = commentRepository.findById(commentId);

        if (comment == null) {
            throw new NotFoundException(
                    "That comment does not exist (it may have been deleted).");
        }

        return comment;
    }

    public List<Comment> commentsFor(int postId) {
        return commentRepository.findByPost(postId);
    }

    public int commentCount(int postId) {
        return commentsFor(postId).size();
    }
}