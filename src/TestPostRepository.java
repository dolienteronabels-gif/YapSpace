import yapspace.repository.PostRepository;
import yapspace.model.Category;
import yapspace.model.Feeling;
import yapspace.model.Post;

public class TestPostRepository {

    public static void main(String[] args) {

        PostRepository repository = new PostRepository();

        int postId = repository.save(
                "testuser",
                "Testing YapSpace MySQL post integration.",
                Category.SCHOOL,
                Feeling.HAPPY
        );

        System.out.println("Post saved successfully! ID: " + postId);

        Post post = repository.findById(postId);

        if (post != null) {
            System.out.println("Retrieved post:");
            System.out.println(post.getSummary());
            System.out.println(post.getContent());
        } else {
            System.out.println("Post not found.");
        }
    }
}