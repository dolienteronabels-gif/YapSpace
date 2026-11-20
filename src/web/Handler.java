package yapspace.web;

@FunctionalInterface
public interface Handler {
    WebResponse handle(WebRequest request);
}
