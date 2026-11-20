package yapspace.service;

import yapspace.exception.AuthenticationException;
import yapspace.exception.ValidationException;
import yapspace.model.Role;
import yapspace.model.User;
import yapspace.repository.UserRepository;
import yapspace.storage.DataStore;

public class UserService {

    private static final String DUMMY_SALT = PasswordHasher.newSalt();

    private final DataStore store;
    private final UserRepository repository;

    public UserService(DataStore store) {
        this.store = store;
        this.repository = new UserRepository();
    }

    public User register(String username, String email, String password,
                         String confirmPassword, boolean ageConfirmed) {

        String cleanName = Validator.username(username);
        String cleanEmail = Validator.email(email);

        Validator.password(password);

        if (!password.equals(confirmPassword)) {
            throw new ValidationException("The two passwords do not match.");
        }

        if (!ageConfirmed) {
            throw new ValidationException(
                    "YapSpace is for people aged 13 and above. Please confirm your age.");
        }

        if (repository.findByUsername(cleanName) != null) {
            throw new ValidationException("That username is already taken.");
        }

        if (repository.existsByEmail(cleanEmail)) {
            throw new ValidationException("That email address is already registered.");
        }

        String salt = PasswordHasher.newSalt();
        String passwordHash = PasswordHasher.hash(password, salt);

        try {
            int userId = repository.save(
                    cleanName,
                    cleanEmail,
                    passwordHash,
                    salt,
                    Role.USER
            );

            return repository.findById(userId);

        } catch (RuntimeException e) {
            throw new ValidationException(
                    "Unable to create account. Please check your details and try again.");
        }
    }

    public User login(String username, String password) {

        String name = username == null ? "" : username.trim();

        if (name.isEmpty() || password == null || password.isEmpty()) {
            throw new ValidationException(
                    "Please enter your username and password.");
        }

        User user = repository.findByUsername(name);

        if (user == null) {
            PasswordHasher.hash(password, DUMMY_SALT);
            throw new AuthenticationException(
                    "Incorrect username or password.");
        }

        if (!PasswordHasher.matches(
                password, user.getSalt(), user.getPasswordHash())) {

            throw new AuthenticationException(
                    "Incorrect username or password.");
        }

        return user;
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User findById(int id) {
        return repository.findById(id);
    }
}