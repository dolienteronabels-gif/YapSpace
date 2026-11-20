import yapspace.repository.UserRepository;
import yapspace.model.User;
import yapspace.model.Role;
import yapspace.service.PasswordHasher;

public class TestUserRepository {

    public static void main(String[] args) {

        UserRepository repository = new UserRepository();

        String username = "testuser";
        String email = "testuser@example.com";

        String salt = PasswordHasher.newSalt();
        String passwordHash = PasswordHasher.hash("TestPassword123!", salt);

        try {
            int userId = repository.save(
                username,
                email,
                passwordHash,
                salt,
                Role.USER
            );

            System.out.println("User saved successfully!");
            System.out.println("Generated User ID: " + userId);

            User user = repository.findByUsername(username);

            if (user != null) {
                System.out.println("User retrieved successfully!");
                System.out.println("Username: " + user.getUsername());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Role: " + user.getRole());
            }

        } catch (Exception e) {
            System.out.println("Test failed!");
            e.printStackTrace();
        }
    }
}
