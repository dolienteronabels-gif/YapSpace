package yapspace.web;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Html {

    private static final DateTimeFormatter FULL = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a", Locale.ENGLISH);
    private static final DateTimeFormatter SHORT = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

    private Html() {
    }


    public static String esc(String text) {
        if (text == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(text.length() + 16);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
                case '&' -> sb.append("&amp;");
                case '<' -> sb.append("&lt;");
                case '>' -> sb.append("&gt;");
                case '"' -> sb.append("&quot;");
                case '\'' -> sb.append("&#39;");
                default -> sb.append(c);
            }
        }
        return sb.toString();
    }

    
    public static String multiline(String text) {
        return esc(text).replace("\n", "<br>");
    }

    public static String url(String text) {
        return URLEncoder.encode(text == null ? "" : text, StandardCharsets.UTF_8);
    }

    
    public static String timeAgo(LocalDateTime time) {
        Duration d = Duration.between(time, LocalDateTime.now());
        long minutes = d.toMinutes();
        if (minutes < 1) {
            return "just now";
        }
        if (minutes < 60) {
            return minutes + " min ago";
        }
        long hours = d.toHours();
        if (hours < 24) {
            return hours + " h ago";
        }
        long days = d.toDays();
        if (days < 7) {
            return days + " d ago";
        }
        return time.format(SHORT);
    }

    public static String fullDate(LocalDateTime time) {
        return time.format(FULL);
    }

    
    public static String timeTag(LocalDateTime time) {
        return "<time datetime=\"" + time + "\" title=\"" + esc(fullDate(time)) + "\">" + esc(timeAgo(time)) + "</time>";
    }

    
    public static String safeNext(String next, String fallback) {
        if (next == null || next.isEmpty() || next.length() > 300) {
            return fallback;
        }
        if (!next.startsWith("/") || next.startsWith("//") || next.contains("\\")) {
            return fallback;
        }
        for (int i = 0; i < next.length(); i++) {
            if (next.charAt(i) < 0x20 || next.charAt(i) == 0x7f) {
                return fallback;
            }
        }
        return next;
    }

   
    public static int avatarSlot(String username) {
        return Math.floorMod(username.toLowerCase(Locale.ROOT).hashCode(), 8);
    }

    public static String initial(String username) {
        return username.isEmpty() ? "?" : username.substring(0, 1).toUpperCase(Locale.ROOT);
    }
}
