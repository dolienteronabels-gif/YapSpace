package yapspace.web;

public final class Session {

    public record Flash(String message, boolean error) {
    }

    private final String token;
    private final int userId;
    private volatile Flash flash;
    private volatile long lastSeen = System.currentTimeMillis();

    Session(String token, int userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public int getUserId() {
        return userId;
    }

    long getLastSeen() {
        return lastSeen;
    }

    void touch() {
        lastSeen = System.currentTimeMillis();
    }

    public void setFlash(String message) {
        this.flash = new Flash(message, false);
    }

    public void setErrorFlash(String message) {
        this.flash = new Flash(message, true);
    }

    public Flash takeFlash() {
        Flash message = flash;
        flash = null;
        return message;
    }
}
