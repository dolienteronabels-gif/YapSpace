package yapspace.web;

import yapspace.model.Category;
import yapspace.model.Comment;
import yapspace.model.Feeling;
import yapspace.model.Post;
import yapspace.model.ReactionType;
import yapspace.model.User;
import yapspace.service.PostService;
import yapspace.service.Validator;

import java.util.List;
import java.util.Map;

public class Views {

    private enum CardMode { FEED, MINE, DETAIL }

    private final PostService posts;

    public Views(PostService posts) {
        this.posts = posts;
    }

    private static String esc(String text) {
        return Html.esc(text);
    }

    private static String hidden(String name, String value) {
        return "<input type=\"hidden\" name=\"" + name + "\" value=\"" + esc(value) + "\">";
    }

    private static String plural(int n, String word) {
        return n + " " + word + (n == 1 ? "" : "s");
    }

    // ------------------------------------------------------------------ layout

    private String layout(WebRequest req, String title, String active, String width, String body) {
        User me = req.getUser();
        StringBuilder sb = new StringBuilder(8000);

        sb.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"utf-8\">")
          .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">")
          .append("<meta name=\"color-scheme\" content=\"light\">")
          .append("<meta name=\"theme-color\" content=\"#172538\">")
          .append("<title>").append(esc(title)).append(" · YapSpace</title>")
          .append("<link rel=\"icon\" href=\"data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'%3E%3Ctext y='.9em' font-size='90'%3E%F0%9F%92%AC%3C/text%3E%3C/svg%3E\">")
          .append("<link rel=\"stylesheet\" href=\"/static/style.css\"></head><body>")
          .append("<a class=\"skip\" href=\"#main\">Skip to content</a>");

        Session session = req.getSession();
        Session.Flash flash = session == null ? null : session.takeFlash();

        if (me != null) {
            sb.append("<div class=\"app-shell\">")
              .append("<aside class=\"sidebar\">")
              .append("<a class=\"brand\" href=\"/feed\"><span class=\"brand-mark\">Y</span><span>Yap<span class=\"brand-accent\">Space</span></span></a>")
              .append("<div class=\"side-caption\">YOUR SPACE</div>")
              .append("<nav class=\"side-nav\" aria-label=\"Main navigation\">")
              .append(sideLink("/feed", "⌂", "Community feed", "feed".equals(active)))
              .append(sideLink("/my-posts", "▤", "My posts", "mine".equals(active)))
              .append("</nav>")
              .append("<a class=\"side-compose\" href=\"/posts/new\"><span>＋</span> Create a post</a>")
              .append("<div class=\"sidebar-bottom\">")
              .append("<div class=\"side-profile\">")
              .append(avatar(me.getUsername(), true))
              .append("<div class=\"side-profile-text\"><strong>").append(esc(me.getUsername()))
              .append("</strong><span>Member</span></div></div>")
              .append("<form method=\"post\" action=\"/logout\"><button class=\"side-logout\" type=\"submit\">↪ Log out</button></form>")
              .append("</div></aside>")
              .append("<div class=\"app-main\">")
              .append("<header class=\"mobile-topbar\"><a class=\"brand\" href=\"/feed\"><span class=\"brand-mark\">Y</span> Yap<span class=\"brand-accent\">Space</span></a>")
              .append("<a class=\"btn btn-primary btn-sm\" href=\"/posts/new\">＋ Post</a></header>")
              .append("<main id=\"main\" class=\"workspace ").append(esc(width)).append("\">")
              .append("<div class=\"workspace-topline\"><span class=\"topline-kicker\">THE SOCIAL CLUB</span><span class=\"topline-user\">A little space to be yourself.</span></div>");
        } else {
            sb.append("<header class=\"public-header\"><a class=\"brand\" href=\"/\"><span class=\"brand-mark\">Y</span> Yap<span class=\"brand-accent\">Space</span></a>")
              .append("<nav class=\"public-nav\" aria-label=\"Main navigation\"><a href=\"/login\">Log in</a><a class=\"btn btn-primary btn-sm\" href=\"/register\">Join the community</a></nav></header>")
              .append("<main id=\"main\" class=\"public-main ").append(esc(width)).append("\">");
        }

        if (flash != null) {
            sb.append("<div class=\"alert ").append(flash.error() ? "alert-error\" role=\"alert\"" : "alert-ok\" role=\"status\"")
              .append(">").append(esc(flash.message())).append("</div>");
        }

        sb.append(body).append("</main>");

        if (me != null) {
            sb.append("</div></div>");
        }

        sb.append("<footer class=\"footer\"><p>YapSpace is a place for general community sharing. It does not provide ")
          .append("professional counseling, medical advice or emergency services. If you or someone you know is in ")
          .append("danger, contact your local emergency services or a trusted adult right away.</p>")
          .append("<p>OOP 101 project · Team 4, Ro Ro Row Your Code</p></footer>")
          .append("<script src=\"/static/app.js\" defer></script></body></html>");

        return sb.toString();
    }

    private static String sideLink(String href, String icon, String label, boolean active) {
        return "<a class=\"side-link" + (active ? " active" : "") + "\" href=\"" + href + "\""
                + (active ? " aria-current=\"page\"" : "") + "><span class=\"side-icon\" aria-hidden=\"true\">"
                + icon + "</span><span>" + label + "</span>" + (active ? "<span class=\"side-indicator\"></span>" : "") + "</a>";
    }

    private static String errorBox(String message) {
        return message == null || message.isEmpty() ? ""
                : "<div class=\"alert alert-error\" role=\"alert\">" + esc(message) + "</div>";
    }

    private static String avatar(String username, boolean small) {
        return "<span class=\"avatar " + (small ? "sm " : "") + "a" + Html.avatarSlot(username)
                + "\" aria-hidden=\"true\">" + esc(Html.initial(username)) + "</span>";
    }

    private static String communityPanel() {
        return "<aside class=\"community-panel\">"
             + "<section class=\"side-card club-card\"><span class=\"panel-kicker\">A NOTE FROM YAPSPACE</span>"
             + "<div class=\"club-symbol\">✳</div><h3>Make room for every voice.</h3>"
             + "<p>Share thoughtfully, listen kindly, and help keep this community welcoming.</p></section>"
             + "<section class=\"side-card\"><h3 class=\"side-card-title\">COMMUNITY GUIDELINES</h3>"
             + "<ul class=\"guideline-list\"><li><span>01</span> Be respectful and kind.</li>"
             + "<li><span>02</span> Think before you post.</li><li><span>03</span> Keep personal details private.</li></ul>"
             + "</section><div class=\"panel-footnote\">A thoughtful community starts with you. <span>✦</span></div></aside>";
    }

    private static String workspaceGrid(String mainContent) {
        return "<div class=\"content-grid\"><section class=\"feed-column\">" + mainContent + "</section>"
                + communityPanel() + "</div>";
    }

    // ------------------------------------------------------------ public pages

    public String landing(WebRequest req) {
        StringBuilder b = new StringBuilder();
        b.append("<section class=\"landing-hero\"><div class=\"landing-copy\">")
         .append("<span class=\"eyebrow\"><span class=\"eyebrow-dot\"></span> A COMMUNITY SPACE FOR AGES 13 AND UP</span>")
         .append("<h1>Your thoughts<br>have a <em>place</em><br>here.</h1>")
         .append("<p class=\"lead\">A little corner of the internet to share what’s on your mind, express how you feel, and connect through everyday stories.</p>")
         .append("<div class=\"hero-cta\">");
        if (req.isLoggedIn()) {
            b.append("<a class=\"btn btn-primary btn-lg\" href=\"/feed\">Explore the feed <span>↗</span></a>");
        } else {
            b.append("<a class=\"btn btn-primary btn-lg\" href=\"/register\">Find your space <span>↗</span></a>")
             .append("<a class=\"text-link\" href=\"/login\">I already have an account</a>");
        }
        b.append("</div><div class=\"hero-proof\"><span class=\"proof-stars\">✳ ✳ ✳</span><span>A space for real thoughts and kind conversations.</span></div>")
         .append("</div><div class=\"hero-art\" aria-hidden=\"true\"><div class=\"art-orbit orbit-one\"></div><div class=\"art-orbit orbit-two\"></div>")
         .append("<div class=\"art-note note-one\"><span>✦</span><strong>your voice</strong><small>matters here</small></div>")
         .append("<div class=\"art-note note-two\"><span>“</span><strong>Share a little.</strong><small>Connect a lot.</small></div>")
         .append("<div class=\"art-center\"><div class=\"art-center-inner\">Y<span>·</span>S</div></div>")
         .append("<div class=\"art-spark spark-one\">✳</div><div class=\"art-spark spark-two\">✦</div></div></section>")
         .append("<section class=\"landing-bottom\"><div><span class=\"section-kicker\">WHAT YOU CAN DO HERE</span><h2>Small moments.<br><em>Real connections.</em></h2></div>")
         .append("<div class=\"feature-grid\">")
         .append(featureCard("01", "Share your story", "Put your thoughts into words and choose a category that fits."))
         .append(featureCard("02", "Show some support", "React with Support, Relate or Encourage to make someone feel heard."))
         .append(featureCard("03", "Keep it kind", "Leave thoughtful comments and help conversations grow."))
         .append("</div></section>");
        return layout(req, "Welcome", "", "wide", b.toString());
    }   

    private static String featureCard(String number, String title, String text) {
        return "<article class=\"feature-card\"><span class=\"feature-number\">" + number + "</span><h3>" + title
                + "</h3><p>" + text + "</p><span class=\"feature-arrow\">↗</span></article>";
    }

    public String register(WebRequest req, String error, String username, String email, boolean ageChecked) {
        String body = "<section class=\"auth-layout\"><div class=\"auth-aside\"><span class=\"section-kicker\">START WITH A HELLO</span>"
                + "<h1>Make this<br>space <em>yours.</em></h1><p>Join a community built around sharing, listening, and treating one another with care.</p>"
                + "<div class=\"auth-aside-mark\">Y<span>·</span>S</div></div><div class=\"auth-card\"><span class=\"panel-kicker\">CREATE YOUR ACCOUNT</span>"
                + "<h2>Welcome to YapSpace</h2><p class=\"muted\">YapSpace is for people aged 13 and above.</p>" + errorBox(error)
                + "<form method=\"post\" action=\"/register\">"
                + "<div class=\"field\"><label for=\"username\">Username</label><input type=\"text\" id=\"username\" name=\"username\" value=\"" + esc(username)
                + "\" required minlength=\"3\" maxlength=\"20\" pattern=\"[A-Za-z0-9_]{3,20}\" autocomplete=\"username\"><div class=\"hint\">3–20 letters, numbers or underscores.</div></div>"
                + "<div class=\"field\"><label for=\"email\">Email address</label><input type=\"email\" id=\"email\" name=\"email\" value=\"" + esc(email)
                + "\" required maxlength=\"100\" autocomplete=\"email\"></div>"
                + "<div class=\"field\"><label for=\"password\">Password</label><input type=\"password\" id=\"password\" name=\"password\" required minlength=\""
                + Validator.PASSWORD_MIN + "\" maxlength=\"" + Validator.PASSWORD_MAX + "\" autocomplete=\"new-password\"><div class=\"hint\">At least "
                + Validator.PASSWORD_MIN + " characters.</div></div>"
                + "<div class=\"field\"><label for=\"confirm\">Confirm password</label><input type=\"password\" id=\"confirm\" name=\"confirm\" required autocomplete=\"new-password\"></div>"
                + "<div class=\"field\"><label class=\"check\"><input type=\"checkbox\" name=\"age\" value=\"yes\" required" + (ageChecked ? " checked" : "")
                + "> I am 13 years old or above.</label></div><button class=\"btn btn-primary btn-full\" type=\"submit\">Create account <span>↗</span></button></form>"
                + "<p class=\"auth-switch\">Already have an account? <a href=\"/login\">Log in</a></p></div></section>";
        return layout(req, "Sign up", "", "narrow", body);
    }

    public String login(WebRequest req, String error, String username) {
        String body = "<section class=\"auth-layout\"><div class=\"auth-aside\"><span class=\"section-kicker\">GOOD TO HAVE YOU BACK</span>"
                + "<h1>Your space<br>is waiting <em>for you.</em></h1><p>Pick up where you left off. There’s always room for another thought and another kind conversation.</p>"
                + "<div class=\"auth-aside-mark\">Y<span>·</span>S</div></div><div class=\"auth-card\"><span class=\"panel-kicker\">MEMBER SIGN IN</span>"
                + "<h2>Welcome back.</h2><p class=\"muted\">Log in to continue to your community.</p>" + errorBox(error)
                + "<form method=\"post\" action=\"/login\"><div class=\"field\"><label for=\"username\">Username</label>"
                + "<input type=\"text\" id=\"username\" name=\"username\" value=\"" + esc(username) + "\" required maxlength=\"20\" autocomplete=\"username\"></div>"
                + "<div class=\"field\"><label for=\"password\">Password</label><input type=\"password\" id=\"password\" name=\"password\" required autocomplete=\"current-password\"></div>"
                + "<button class=\"btn btn-primary btn-full\" type=\"submit\">Log in <span>↗</span></button></form>"
                + "<p class=\"auth-switch\">New to YapSpace? <a href=\"/register\">Create an account</a></p></div></section>";
        return layout(req, "Log in", "", "narrow", body);
    }

    // ------------------------------------------------------------------- feed

    public String feed(WebRequest req, List<Post> list) {
        StringBuilder b = new StringBuilder();
        b.append("<div class=\"page-heading\"><div><span class=\"section-kicker\">YOUR COMMUNITY, YOUR MOMENTS</span><h1>The feed<span class=\"heading-period\">.</span></h1>")
         .append("<p class=\"muted\">A fresh collection of thoughts from the community.</p></div><a class=\"btn btn-primary\" href=\"/posts/new\">＋ Share a thought</a></div>")
         .append("<div class=\"feed-toolbar\"><span class=\"feed-count\"><strong>").append(list.size()).append("</strong> ")
         .append(list.size() == 1 ? "POST" : "POSTS").append(" IN THE COMMUNITY</span><span class=\"toolbar-line\"></span><span class=\"toolbar-label\">LATEST CONVERSATIONS</span></div>");
        if (list.isEmpty()) {
            b.append("<div class=\"empty-state\"><div class=\"empty-icon\">✳</div><h2>The conversation starts with you.</h2>")
             .append("<p>It’s quiet here for now. Share the first thought and give the community something to connect with.</p>")
             .append("<a class=\"btn btn-primary\" href=\"/posts/new\">Write the first post <span>↗</span></a></div>");
        }
        for (Post p : list) {
            b.append(postCard(req, p, CardMode.FEED));
        }
        return layout(req, "Community Feed", "feed", "", workspaceGrid(b.toString()));
    }

    public String myPosts(WebRequest req, List<Post> mine) {
        StringBuilder b = new StringBuilder();
        b.append("<div class=\"page-heading\"><div><span class=\"section-kicker\">YOUR PERSONAL COLLECTION</span><h1>My posts<span class=\"heading-period\">.</span></h1>")
         .append("<p class=\"muted\">Everything you’ve shared with the YapSpace community.</p></div><a class=\"btn btn-primary\" href=\"/posts/new\">＋ New post</a></div>")
         .append("<div class=\"profile-summary\"><div class=\"profile-summary-avatar\">").append(avatar(req.getUser().getUsername(), false))
         .append("</div><div><span class=\"panel-kicker\">YOUR CONTRIBUTIONS</span><strong>").append(esc(req.getUser().getUsername()))
         .append("</strong><span class=\"muted\">").append(plural(mine.size(), "published post")).append("</span></div><div class=\"summary-decoration\">✳</div></div>");
        if (mine.isEmpty()) {
            b.append("<div class=\"empty-state\"><div class=\"empty-icon\">✎</div><h2>Your story begins here.</h2>")
             .append("<p>You haven’t shared a post yet. Whenever you’re ready, your first post is just a click away.</p>")
             .append("<a class=\"btn btn-primary\" href=\"/posts/new\">Create your first post <span>↗</span></a></div>");
        }
        for (Post p : mine) {
            b.append(postCard(req, p, CardMode.MINE));
        }
        return layout(req, "My Posts", "mine", "", workspaceGrid(b.toString()));
    }

    // ------------------------------------------------------------- post cards

    private String postCard(WebRequest req, Post p, CardMode mode) {
        User me = req.getUser();
        int id = p.getId();
        String next = switch (mode) {
            case FEED -> "/feed";
            case MINE -> "/my-posts";
            case DETAIL -> "/posts/" + id;
        };
        String afterDelete = mode == CardMode.MINE ? "/my-posts" : "/feed";

        StringBuilder sb = new StringBuilder();
        sb.append("<article class=\"post-card\" id=\"post-").append(id).append("\"><header class=\"post-head\">")
          .append(avatar(p.getAuthor(), false))
          .append("<div class=\"post-who\"><strong>").append(esc(p.getAuthor())).append("</strong><span class=\"muted\">")
          .append(Html.timeTag(p.getCreatedAt())).append(p.isEdited() ? " · edited" : "").append("</span></div>")
          .append("<span class=\"feeling-tag\">").append(p.getFeeling().getIcon()).append(' ')
          .append(esc(p.getFeeling().getLabel())).append("</span></header>")
          .append("<p class=\"post-body\">").append(Html.multiline(p.getContent())).append("</p>")
          .append("<div class=\"post-meta\"><span class=\"chip\">").append(esc(p.getCategory().getLabel()))
          .append("</span></div><div class=\"post-actions\">");

        ReactionType mine = posts.reactionOf(id, me.getUsername());
        Map<ReactionType, Integer> counts = posts.reactionCounts(id);
        for (ReactionType t : ReactionType.values()) {
            boolean active = t == mine;
            sb.append("<form class=\"inline\" method=\"post\" action=\"/posts/").append(id).append("/react\">")
              .append(hidden("type", t.name())).append(hidden("next", next))
              .append("<button class=\"react-btn").append(active ? " active" : "").append("\" type=\"submit\" aria-pressed=\"")
              .append(active).append("\">").append(t.getIcon()).append(' ').append(esc(t.getLabel())).append(" <span>")
              .append(counts.get(t)).append("</span></button></form>");
        }
        sb.append("<span class=\"spacer\"></span>");
        if (mode != CardMode.DETAIL) {
            sb.append("<a class=\"comment-link\" href=\"/posts/").append(id).append("#comments\">✎ ")
              .append(plural(posts.commentCount(id), "comment")).append("</a>");
        }
        if (mode != CardMode.FEED && me.canEdit(p)) {
            sb.append("<a class=\"comment-link\" href=\"/posts/").append(id).append("/edit\">Edit</a>");
        }
        if (mode != CardMode.FEED && me.canDelete(p)) {
            sb.append(deleteForm("/posts/" + id + "/delete", afterDelete, "Delete this post? This cannot be undone."));
        }
        sb.append("</div></article>");
        return sb.toString();
    }

    private static String deleteForm(String action, String next, String confirmMessage) {
        return "<form class=\"inline\" method=\"post\" action=\"" + action + "\" data-confirm=\"" + esc(confirmMessage) + "\">"
                + hidden("next", next) + "<button class=\"comment-link danger\" type=\"submit\">Delete</button></form>";
    }

    // ------------------------------------------------------------ post screens

    public String postForm(WebRequest req, Post existing, String error, String content, String category, String feeling) {
        boolean editing = existing != null;
        String action = editing ? "/posts/" + existing.getId() + "/edit" : "/posts";
        StringBuilder b = new StringBuilder();
        b.append("<div class=\"editor-layout\"><div class=\"editor-intro\"><span class=\"section-kicker\">YOUR WORDS, YOUR SPACE</span>")
         .append("<h1>").append(editing ? "Shape your<br><em>thought.</em>" : "Put it into<br><em>words.</em>").append("</h1>")
         .append("<p>There’s no perfect way to express yourself. Be honest, be thoughtful, and make it yours.</p>")
         .append("<div class=\"editor-quote\">“Every voice adds something to the conversation.”</div></div>")
         .append("<div class=\"editor-card\"><span class=\"panel-kicker\">").append(editing ? "EDIT YOUR POST" : "CREATE SOMETHING NEW")
         .append("</span><h2>").append(editing ? "Make your changes" : "What’s on your mind?").append("</h2>")
         .append(errorBox(error)).append("<form method=\"post\" action=\"").append(action).append("\">")
         .append("<div class=\"field\"><label for=\"content\">Your message</label><textarea id=\"content\" name=\"content\" maxlength=\"")
         .append(Validator.POST_MAX).append("\" required data-counter=\"count\" placeholder=\"Start writing here…\">")
         .append(esc(content)).append("</textarea><div class=\"counter\" id=\"count\"></div></div>")
         .append("<div class=\"field\"><label for=\"category\">Choose a category</label><select id=\"category\" name=\"category\" required>")
         .append("<option value=\"\" disabled").append(category == null || category.isEmpty() ? " selected" : "").append(">Select a category</option>");
        for (Category c : Category.values()) {
            b.append("<option value=\"").append(c.name()).append("\"").append(c.name().equals(category) ? " selected" : "")
             .append(">").append(esc(c.getLabel())).append("</option>");
        }
        b.append("</select></div><fieldset><legend>How are you feeling?</legend><div class=\"feelings\">");
        for (Feeling f : Feeling.values()) {
            b.append("<label class=\"feeling-opt\"><input class=\"sr-only\" type=\"radio\" name=\"feeling\" value=\"")
             .append(f.name()).append("\" required").append(f.name().equals(feeling) ? " checked" : "")
             .append("><span>").append(f.getIcon()).append(' ').append(esc(f.getLabel())).append("</span></label>");
        }
        b.append("</div></fieldset><div class=\"form-actions\"><button class=\"btn btn-primary\" type=\"submit\">")
         .append(editing ? "Save changes" : "Publish post").append(" <span>↗</span></button><a class=\"btn btn-ghost\" href=\"")
         .append(editing ? "/my-posts" : "/feed").append("\">Cancel</a></div></form></div></div>");
        return layout(req, editing ? "Edit post" : "New post", editing ? "mine" : "", "", b.toString());
    }

    public String postDetail(WebRequest req, Post post, String commentError, String commentDraft) {
        User me = req.getUser();
        StringBuilder b = new StringBuilder();
        b.append("<div class=\"page-heading detail-heading\"><div><span class=\"section-kicker\">COMMUNITY CONVERSATION</span><h1>The discussion<span class=\"heading-period\">.</span></h1></div>")
         .append("<a class=\"text-link\" href=\"/feed\">← Back to feed</a></div>")
         .append(postCard(req, post, CardMode.DETAIL));

        List<Comment> comments = posts.commentsFor(post.getId());
        b.append("<section class=\"comments-panel\" id=\"comments\"><div class=\"comments-heading\"><div><span class=\"panel-kicker\">KEEP IT KIND</span><h2>")
         .append(plural(comments.size(), "comment")).append("</h2></div><span class=\"comment-count\">").append(comments.size()).append("</span></div>");
        if (comments.isEmpty()) {
            b.append("<p class=\"muted comment-empty\">No comments yet. Start the conversation with something kind.</p>");
        } else {
            b.append("<ul class=\"comments\">");
            for (Comment c : comments) {
                b.append("<li class=\"comment\" id=\"comment-").append(c.getId()).append("\">")
                 .append(avatar(c.getAuthor(), true)).append("<div class=\"comment-body\"><div class=\"comment-author\"><strong>")
                 .append(esc(c.getAuthor())).append("</strong><span class=\"muted\">").append(Html.timeTag(c.getCreatedAt()))
                 .append(c.isEdited() ? " · edited" : "").append("</span></div><p>").append(Html.multiline(c.getContent())).append("</p>");
                if (me.canEdit(c)) {
                    b.append("<a class=\"comment-link\" href=\"/comments/").append(c.getId()).append("/edit\">Edit</a>");
                }
                if (me.canDelete(c)) {
                    b.append(deleteForm("/comments/" + c.getId() + "/delete", "/posts/" + post.getId(), "Delete this comment?"));
                }
                b.append("</div></li>");
            }
            b.append("</ul>");
        }
        b.append(errorBox(commentError)).append("<form class=\"comment-form\" method=\"post\" action=\"/posts/").append(post.getId()).append("/comments\">")
         .append("<div class=\"field\"><label for=\"comment\">Add to the conversation</label><textarea class=\"short\" id=\"comment\" name=\"content\" maxlength=\"")
         .append(Validator.COMMENT_MAX).append("\" required data-counter=\"ccount\" placeholder=\"Write a thoughtful comment…\">")
         .append(esc(commentDraft)).append("</textarea><div class=\"counter\" id=\"ccount\"></div></div>")
         .append("<button class=\"btn btn-primary btn-sm\" type=\"submit\">Add comment ↗</button></form></section>");
        return layout(req, "Post by " + post.getAuthor(), "feed", "", workspaceGrid(b.toString()));
    }

    public String commentEdit(WebRequest req, Comment comment, String error, String draft) {
        String body = "<div class=\"editor-layout\"><div class=\"editor-intro\"><span class=\"section-kicker\">A KIND WORD MATTERS</span>"
                + "<h1>Polish your<br><em>comment.</em></h1><p>Make your message clear, thoughtful, and true to what you want to say.</p></div>"
                + "<div class=\"editor-card\"><span class=\"panel-kicker\">EDIT COMMENT</span><h2>Update your comment</h2>" + errorBox(error)
                + "<form method=\"post\" action=\"/comments/" + comment.getId() + "/edit\">"
                + "<div class=\"field\"><label for=\"content\">Your comment</label><textarea class=\"short\" id=\"content\" name=\"content\" maxlength=\""
                + Validator.COMMENT_MAX + "\" required data-counter=\"ccount\">" + esc(draft) + "</textarea><div class=\"counter\" id=\"ccount\"></div></div>"
                + "<div class=\"form-actions\"><button class=\"btn btn-primary\" type=\"submit\">Save changes ↗</button>"
                + "<a class=\"btn btn-ghost\" href=\"/posts/" + comment.getPostId() + "#comment-" + comment.getId() + "\">Cancel</a></div></form></div></div>";
        return layout(req, "Edit comment", "feed", "", body);
    }

    // ------------------------------------------------------------------ errors

    public WebResponse error(WebRequest req, int status, String title, String message) {
        String body = "<div class=\"error-page\"><span class=\"error-symbol\">✳</span><span class=\"section-kicker\">A LITTLE DETOUR</span><h1>"
                + esc(title) + "</h1><p>" + esc(message) + "</p><a class=\"btn btn-primary\" href=\""
                + (req.isLoggedIn() ? "/feed" : "/") + "\">Go back home ↗</a></div>";
        return WebResponse.html(status, layout(req, title, "", "", body));
    }
}
