package yapspace.model;

import yapspace.exception.ValidationException;

public enum Feeling {
    HAPPY("Happy", "&#128522;"),
    SAD("Sad", "&#128546;"),
    ANGRY("Angry", "&#128544;"),
    STRESSED("Stressed", "&#128560;"),
    EXCITED("Excited", "&#129321;"),
    TIRED("Tired", "&#128564;"),
    CONFUSED("Confused", "&#128533;"),
    NEUTRAL("Neutral", "&#128528;");

    private final String label;
    private final String icon;

    Feeling(String label, String icon) {
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }

    public static Feeling parse(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Please select how you are feeling.");
        }
        try {
            return valueOf(code.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("That feeling does not exist.");
        }
    }

    public static Feeling parseOrNull(String code) {
        try {
            return (code == null || code.isBlank()) ? null : parse(code);
        } catch (ValidationException e) {
            return null;
        }
    }
}
