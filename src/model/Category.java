package yapspace.model;

import yapspace.exception.ValidationException;

public enum Category {
    SCHOOL("School"),
    FAMILY("Family"),
    FRIENDSHIP("Friendship"),
    RELATIONSHIP("Relationship"),
    MENTAL_HEALTH("Mental Health"),
    CAREER("Career"),
    HOBBIES("Hobbies"),
    PERSONAL("Personal"),
    RANDOM_THOUGHTS("Random Thoughts");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Category parse(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Please choose a category.");
        }
        try {
            return valueOf(code.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("That category does not exist.");
        }
    }

    public static Category parseOrNull(String code) {
        try {
            return (code == null || code.isBlank()) ? null : parse(code);
        } catch (ValidationException e) {
            return null;
        }
    }
}
