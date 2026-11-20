package yapspace.web;

import yapspace.exception.AuthenticationException;
import yapspace.exception.NotFoundException;
import yapspace.model.User;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class WebRequest {

    private final String method;
    private final String path;
    private final Map<String, String> query;
    private final Map<String, String> form;
    private final Map<String, String> cookies;
    private Map<String, String> pathParams = Map.of();
    private User user;
    private Session session;

    public WebRequest(String method, String path, String rawQuery, Map<String, String> form,
                      Map<String, String> cookies) {
        this.method = method;
        this.path = path;
        this.query = parseUrlEncoded(rawQuery);
        this.form = form;
        this.cookies = cookies;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String cookie(String name) {
        return cookies.get(name);
    }

    public String param(String name) {
        String value = form.get(name);
        if (value == null) {
            value = query.get(name);
        }
        return value == null ? "" : value;
    }

    public boolean hasParam(String name) {
        return form.containsKey(name) || query.containsKey(name);
    }

    void setPathParams(Map<String, String> params) {
        this.pathParams = params;
    }

    public int pathInt(String name) {
        try {
            return Integer.parseInt(pathParams.getOrDefault(name, ""));
        } catch (NumberFormatException e) {
            throw new NotFoundException("That page does not exist.");
        }
    }

    void setLogin(Session session, User user) {
        this.session = session;
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public User getUser() {
        return user;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public User requireUser() {
        if (user == null) {
            throw new AuthenticationException("Please log in to continue.");
        }
        return user;
    }

    public static Map<String, String> parseUrlEncoded(String text) {
        Map<String, String> map = new HashMap<>();
        if (text == null || text.isEmpty()) {
            return map;
        }
        for (String pair : text.split("&")) {
            if (pair.isEmpty()) {
                continue;
            }
            int eq = pair.indexOf('=');
            String key = eq < 0 ? pair : pair.substring(0, eq);
            String value = eq < 0 ? "" : pair.substring(eq + 1);
            try {
                map.put(URLDecoder.decode(key, StandardCharsets.UTF_8), URLDecoder.decode(value, StandardCharsets.UTF_8));
            } catch (IllegalArgumentException e) {
                
            }
        }
        return map;
    }
}
