import yapspace.service.UserService;
import yapspace.storage.DataStore;
import yapspace.model.User;

import java.nio.file.Paths;

public class TestUserLogin {

    public static void main(String[] args) {

        DataStore store = new DataStore(Paths.get("data"));
        UserService users = new UserService(store);

        try {
            User user = users.login("testuser", "TestPassword123!");

            System.out.println("LOGIN SUCCESSFUL!");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("User ID: " + user.getId());

        } catch (Exception e) {
            System.out.println("LOGIN FAILED!");
            e.printStackTrace();
        }
    }
}