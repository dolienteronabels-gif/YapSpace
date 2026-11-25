package yapspace.web;


/**
 * YapSpace visual system: editorial social-club layout, responsive styles,
 * and the small client-side helpers used by the existing pages.
 */
public final class Assets {


    private Assets() {
    }


    public static final String CSS = """
            @import url('https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700;800&family=Playfair+Display:ital,wght@0,600;0,700;1,600;1,700&display=swap');


            :root {
              color-scheme: light;
              --bg: #f4f1eb;
              --paper: #fffefa;
              --paper-soft: #f8f6f1;
              --ink: #202b3c;
              --muted: #858781;
              --navy: #19283b;
              --navy-2: #263950;
              --terracotta: #bd765d;
              --terracotta-soft: #f3e5dc;
              --sage: #788c78;
              --gold: #c6a46a;
              --line: #e9e4da;
              --danger: #a94f52;
              --danger-soft: #fae9e7;
              --ok-soft: #e7f0e6;
              --shadow: 0 12px 35px rgba(35, 43, 52, .055);
              --radius: 18px;
            }


            * { box-sizing: border-box; }
            html { scroll-behavior: smooth; -webkit-text-size-adjust: 100%; }
            body {
              margin: 0; min-height: 100vh; background: var(--bg); color: var(--ink);
              font: 15px/1.65 "DM Sans", "Segoe UI", Arial, sans-serif;
              letter-spacing: -.015em;
            }
            a { color: var(--terracotta); text-underline-offset: 3px; }
            h1,h2,h3,p { margin-top: 0; }
            h1,h2,h3 { line-height: 1.15; letter-spacing: -.045em; }
            h1 { font-size: clamp(2rem, 4vw, 3.2rem); margin-bottom: .7rem; }
            h2 { font-size: 1.55rem; margin-bottom: .65rem; }
            h3 { font-size: 1.05rem; margin-bottom: .5rem; }
            p { margin-bottom: 1rem; }
            .muted { color: var(--muted); font-size: .9rem; }
            .sr-only { position:absolute; width:1px; height:1px; padding:0; margin:-1px; overflow:hidden; clip:rect(0,0,0,0); white-space:nowrap; border:0; }
            .skip { position:absolute; left:-999px; top:0; z-index:100; padding:.7rem 1rem; background:var(--navy); color:white; }
            .skip:focus { left:10px; top:10px; }
            :focus-visible { outline:3px solid var(--terracotta); outline-offset:3px; }
            .inline { display:inline; margin:0; }


            /* Main logged-in shell */
            .app-shell { min-height:100vh; display:grid; grid-template-columns:252px minmax(0,1fr); }
            .sidebar {
              position:sticky; top:0; height:100vh; padding:30px 20px 22px;
              display:flex; flex-direction:column; background:var(--navy); color:#fff;
              border-right:1px solid rgba(255,255,255,.08);
            }
            .brand { display:inline-flex; align-items:center; gap:10px; color:var(--navy); text-decoration:none; font-size:1.45rem; font-weight:800; letter-spacing:-.07em; }
            .sidebar .brand { color:#fff; margin:0 8px 58px; }
            .brand-mark { display:inline-flex; align-items:center; justify-content:center; width:35px; height:35px; border-radius:12px; background:var(--terracotta); color:#fff; font-family:"Playfair Display", Georgia, serif; font-size:1.35rem; letter-spacing:-.05em; }
            .brand-accent { color:var(--terracotta); }
            .sidebar .brand-accent { color:#e4a58c; }
            .side-caption,.panel-kicker,.section-kicker,.topline-kicker { display:block; font-size:.67rem; letter-spacing:.16em; font-weight:800; }
            .side-caption { color:#8793a0; padding:0 12px; margin-bottom:14px; }
            .side-nav { display:grid; gap:7px; }
            .side-link { position:relative; display:flex; align-items:center; gap:13px; padding:12px 13px; border-radius:12px; color:#c4cbd1; text-decoration:none; font-size:.92rem; font-weight:650; transition:.2s ease; }
            .side-link:hover { background:rgba(255,255,255,.08); color:#fff; }
            .side-link.active { background:rgba(255,255,255,.12); color:#fff; }
            .side-icon { width:24px; text-align:center; font-size:1.25rem; color:#d99a7f; }
            .side-indicator { position:absolute; right:12px; width:6px; height:6px; border-radius:50%; background:#e2a58b; }
            .side-compose { display:flex; align-items:center; justify-content:center; gap:8px; margin:30px 3px; padding:13px 12px; border-radius:12px; background:var(--terracotta); color:#fff; text-decoration:none; font-weight:750; box-shadow:0 8px 20px rgba(0,0,0,.14); transition:.2s ease; }
            .side-compose:hover { background:#cc846a; transform:translateY(-2px); }
            .side-compose span { font-size:1.25rem; line-height:1; }
            .sidebar-bottom { margin-top:auto; border-top:1px solid rgba(255,255,255,.12); padding-top:18px; }
            .side-profile { display:flex; align-items:center; gap:11px; padding:4px 5px 13px; }
            .side-profile-text { display:grid; min-width:0; }
            .side-profile-text strong { color:#fff; overflow:hidden; text-overflow:ellipsis; }
            .side-profile-text span { color:#aeb8c0; font-size:.78rem; }
            .side-logout { width:100%; text-align:left; border:0; background:transparent; color:#c4cbd1; padding:9px 8px; font:inherit; cursor:pointer; border-radius:9px; }
            .side-logout:hover { background:rgba(255,255,255,.08); color:#fff; }
            .app-main { min-width:0; }
            .mobile-topbar { display:none; }
            .workspace { width:min(1440px,100%); margin:0 auto; padding:27px clamp(22px,3.5vw,56px) 60px; }
            .workspace-topline { display:flex; align-items:center; justify-content:space-between; padding-bottom:20px; margin-bottom:30px; border-bottom:1px solid #e3ded4; }
            .topline-kicker { color:var(--terracotta); }
            .topline-user { color:var(--muted); font-size:.82rem; }
            .content-grid { display:grid; grid-template-columns:minmax(0,1fr) 270px; gap:30px; align-items:start; }
            .feed-column { min-width:0; }
            .community-panel { position:sticky; top:28px; display:grid; gap:16px; }
            .side-card { padding:20px; border:1px solid var(--line); border-radius:16px; background:var(--paper); box-shadow:var(--shadow); }
            .club-card { position:relative; overflow:hidden; background:#e9e8df; border-color:#deded2; }
            .club-card:after { content:""; position:absolute; width:130px; height:130px; right:-58px; bottom:-65px; border:1px solid rgba(25,40,59,.12); border-radius:50%; box-shadow:0 0 0 15px rgba(25,40,59,.025),0 0 0 31px rgba(25,40,59,.025); }
            .panel-kicker { color:var(--terracotta); margin-bottom:11px; }
            .club-symbol { width:38px; height:38px; display:grid; place-items:center; margin:17px 0 12px; border-radius:12px; background:var(--navy); color:#fff; font-size:1.35rem; }
            .club-card h3 { max-width:175px; font-family:"Playfair Display",Georgia,serif; font-size:1.35rem; font-weight:700; }
            .club-card p { color:#68716d; font-size:.84rem; margin:0; }
            .side-card-title { font-size:.68rem; letter-spacing:.13em; color:#858781; }
            .guideline-list { list-style:none; padding:0; margin:16px 0 0; display:grid; gap:13px; font-size:.82rem; }
            .guideline-list li { display:flex; gap:10px; color:#5e655f; }
            .guideline-list li span { color:var(--terracotta); font-size:.7rem; font-weight:800; }
            .panel-footnote { color:var(--muted); font-size:.76rem; padding:0 4px; }
            .panel-footnote span { color:var(--terracotta); }


            /* Public pages */
            .public-header { display:flex; align-items:center; justify-content:space-between; max-width:1240px; margin:auto; padding:25px 30px; }
            .public-nav { display:flex; align-items:center; gap:22px; }
            .public-nav > a:not(.btn) { color:var(--ink); text-decoration:none; font-weight:650; }
            .public-main { max-width:1240px; margin:auto; padding:20px 30px 70px; }
            .public-main.narrow { max-width:1100px; }
            .landing-hero { min-height:570px; display:grid; grid-template-columns:1fr 1fr; align-items:center; gap:30px; padding:50px 4.5% 65px; background:var(--paper); border:1px solid var(--line); border-radius:26px; overflow:hidden; box-shadow:var(--shadow); }
            .landing-copy { max-width:570px; }
            .eyebrow { display:inline-flex; align-items:center; gap:9px; padding:8px 12px; border-radius:999px; background:var(--terracotta-soft); color:#985d49; font-size:.67rem; letter-spacing:.11em; font-weight:800; }
            .eyebrow-dot { width:7px; height:7px; border-radius:50%; background:var(--terracotta); }
            .landing-hero h1 { margin:25px 0 20px; font-family:"Playfair Display",Georgia,serif; font-size:clamp(3.6rem,7vw,6.5rem); font-weight:700; line-height:.98; letter-spacing:-.065em; }
            .landing-hero h1 em,.landing-bottom h2 em,.auth-aside h1 em,.editor-intro h1 em { color:var(--terracotta); font-weight:600; }
            .lead { max-width:470px; color:#747a76; font-size:1.04rem; line-height:1.85; }
            .hero-cta { display:flex; align-items:center; gap:20px; flex-wrap:wrap; margin:28px 0 34px; }
            .text-link { color:var(--terracotta); font-weight:750; text-decoration:none; }
            .text-link:hover { text-decoration:underline; }
            .hero-proof { display:flex; align-items:center; gap:12px; color:var(--muted); font-size:.78rem; }
            .proof-stars { color:var(--gold); letter-spacing:.2em; }
            .hero-art { min-height:420px; position:relative; display:grid; place-items:center; }
            .art-orbit { position:absolute; width:330px; height:330px; border:1px solid #e7d9cb; border-radius:50%; }
            .orbit-two { width:420px; height:420px; border-style:dashed; border-color:#e9e4da; }
            .art-center { width:205px; height:205px; display:grid; place-items:center; border-radius:50%; background:var(--navy); box-shadow:0 24px 55px rgba(25,40,59,.22); transform:rotate(-8deg); }
            .art-center-inner { color:white; font-family:"Playfair Display",Georgia,serif; font-size:4.2rem; letter-spacing:-.1em; }
            .art-center-inner span { color:#e5a58b; }
            .art-note { position:absolute; z-index:2; display:grid; gap:2px; min-width:145px; padding:15px 18px; background:#fffefa; border:1px solid var(--line); border-radius:13px; box-shadow:0 12px 30px rgba(30,40,50,.09); transform:rotate(5deg); }
            .art-note span { color:var(--terracotta); font-size:1.25rem; }
            .art-note strong { color:var(--navy); font-family:"Playfair Display",Georgia,serif; font-size:1.1rem; }
            .art-note small { color:var(--muted); font-size:.73rem; }
            .note-one { top:65px; right:4px; }
            .note-two { bottom:55px; left:0; transform:rotate(-5deg); }
            .art-spark { position:absolute; color:var(--terracotta); font-size:2rem; }
            .spark-one { top:35px; left:65px; }
            .spark-two { right:45px; bottom:45px; color:var(--gold); }
            .landing-bottom { display:grid; grid-template-columns:.8fr 1.6fr; gap:50px; padding:85px 3% 20px; }
            .section-kicker { color:var(--terracotta); margin-bottom:14px; }
            .landing-bottom h2 { font-family:"Playfair Display",Georgia,serif; font-size:2.6rem; }
            .feature-grid { display:grid; grid-template-columns:repeat(3,1fr); gap:13px; }
            .feature-card { position:relative; min-height:190px; padding:22px 18px; background:var(--paper); border:1px solid var(--line); border-radius:15px; }
            .feature-number { display:block; color:var(--terracotta); font-size:.7rem; font-weight:800; letter-spacing:.12em; margin-bottom:28px; }
            .feature-card h3 { font-family:"Playfair Display",Georgia,serif; font-size:1.15rem; }
            .feature-card p { color:var(--muted); font-size:.82rem; margin:0; }
            .feature-arrow { position:absolute; right:17px; top:16px; color:#c6a46a; }


            /* Buttons and forms */
            .btn { display:inline-flex; align-items:center; justify-content:center; gap:9px; border:1px solid transparent; border-radius:10px; padding:11px 17px; font:inherit; font-weight:750; cursor:pointer; text-decoration:none; line-height:1.3; transition:transform .2s,background .2s,box-shadow .2s; }
            .btn:hover { transform:translateY(-2px); }
            .btn-primary { color:#fff; background:var(--navy); box-shadow:0 7px 17px rgba(25,40,59,.14); }
            .btn-primary:hover { color:#fff; background:var(--terracotta); box-shadow:0 9px 20px rgba(189,118,93,.2); }
            .btn-ghost { color:var(--ink); background:var(--paper); border-color:var(--line); }
            .btn-ghost:hover { background:var(--paper-soft); }
            .btn-sm { padding:8px 12px; font-size:.84rem; }
            .btn-lg { padding:14px 21px; }
            .btn-full { width:100%; margin-top:7px; }
            .alert { border-radius:11px; padding:12px 15px; margin:0 0 18px; font-weight:650; }
            .alert-ok { color:#477653; background:var(--ok-soft); border:1px solid #cde1d0; }
            .alert-error { color:var(--danger); background:var(--danger-soft); border:1px solid #ebcdcf; }
            .field { margin-bottom:17px; }
            .field label,legend { display:block; margin-bottom:7px; color:#424a4d; font-size:.87rem; font-weight:750; }
            input[type=text],input[type=email],input[type=password],input[type=search],select,textarea { width:100%; padding:12px 13px; border:1px solid #e2ded5; border-radius:10px; background:#fcfbf8; color:var(--ink); font:inherit; transition:.2s; }
            textarea { min-height:165px; resize:vertical; }
            textarea.short { min-height:85px; }
            input:focus,select:focus,textarea:focus { outline:none; border-color:var(--terracotta); box-shadow:0 0 0 3px rgba(189,118,93,.12); background:#fff; }
            .hint,.counter { color:var(--muted); font-size:.78rem; margin-top:5px; }
            .counter { text-align:right; }
            .check { display:flex!important; align-items:flex-start; gap:9px; font-weight:500!important; }
            .check input { margin-top:5px; accent-color:var(--terracotta); }
            fieldset { border:0; padding:0; margin:0 0 17px; }
            .feelings { display:flex; flex-wrap:wrap; gap:8px; }
            .feeling-opt span { display:inline-block; padding:8px 12px; border:1px solid var(--line); border-radius:999px; background:#fff; cursor:pointer; font-size:.84rem; font-weight:650; transition:.2s; }
            .feeling-opt input:checked + span { color:var(--navy); background:var(--terracotta-soft); border-color:var(--terracotta); }
            .feeling-opt:hover span { border-color:var(--terracotta); }
            .form-actions { display:flex; flex-wrap:wrap; align-items:center; gap:10px; margin-top:20px; }
            .auth-layout { display:grid; grid-template-columns:.9fr 1.1fr; max-width:950px; min-height:590px; margin:35px auto; background:var(--paper); border:1px solid var(--line); border-radius:22px; overflow:hidden; box-shadow:var(--shadow); }
            .auth-aside { position:relative; overflow:hidden; padding:55px 42px; color:white; background:var(--navy); }
            .auth-aside:after { content:""; position:absolute; width:300px; height:300px; right:-120px; bottom:-140px; border:1px solid rgba(255,255,255,.14); border-radius:50%; box-shadow:0 0 0 25px rgba(255,255,255,.025),0 0 0 55px rgba(255,255,255,.025); }
            .auth-aside .section-kicker { color:#e3a58c; }
            .auth-aside h1 { position:relative; z-index:1; margin:30px 0 20px; color:#fff; font-family:"Playfair Display",Georgia,serif; font-size:3.4rem; }
            .auth-aside p { max-width:330px; color:#c4cbd1; }
            .auth-aside-mark { position:absolute; left:40px; bottom:25px; color:rgba(255,255,255,.14); font-family:"Playfair Display",Georgia,serif; font-size:5rem; font-weight:700; }
            .auth-aside-mark span { color:#e3a58c; }
            .auth-card { align-self:center; padding:45px clamp(25px,5vw,60px); }
            .auth-card h2 { margin-bottom:7px; font-family:"Playfair Display",Georgia,serif; font-size:2rem; }
            .auth-switch { margin:22px 0 0; text-align:center; color:var(--muted); font-size:.86rem; }
            .auth-switch a { color:var(--terracotta); font-weight:750; text-decoration:none; }


            /* Feed and post cards */
            .page-heading { display:flex; align-items:flex-end; justify-content:space-between; gap:16px; flex-wrap:wrap; margin-bottom:28px; }
            .page-heading h1 { margin:0 0 5px; font-family:"Playfair Display",Georgia,serif; font-size:clamp(2.4rem,4vw,3.2rem); }
            .page-heading .muted { margin:0; }
            .heading-period { color:var(--terracotta); }
            .feed-toolbar { display:flex; align-items:center; gap:13px; margin:0 0 15px; color:var(--muted); font-size:.67rem; letter-spacing:.1em; font-weight:750; }
            .feed-count strong { color:var(--terracotta); }
            .toolbar-line { height:1px; flex:1; background:var(--line); }
            .toolbar-label { white-space:nowrap; }
            .post-card { margin-bottom:17px; padding:23px 24px 17px; background:var(--paper); border:1px solid var(--line); border-radius:16px; box-shadow:0 5px 18px rgba(35,43,52,.035); transition:box-shadow .2s,transform .2s; }
            .post-card:hover { box-shadow:var(--shadow); transform:translateY(-2px); }
            .post-head { display:flex; align-items:center; gap:12px; margin-bottom:17px; }
            .post-who { display:flex; flex-direction:column; flex:1; min-width:0; line-height:1.4; }
            .post-who strong { color:var(--ink); overflow:hidden; text-overflow:ellipsis; }
            .avatar { width:43px; height:43px; display:inline-flex; align-items:center; justify-content:center; flex:none; border-radius:50%; color:#fff; font-weight:800; box-shadow:0 0 0 3px #fff,0 2px 8px rgba(32,43,60,.12); }
            .avatar.sm { width:35px; height:35px; font-size:.85rem; }
            .a0 { background:#40536c; } .a1 { background:#718a78; } .a2 { background:#bd765d; } .a3 { background:#987b9b; }
            .a4 { background:#65859a; } .a5 { background:#8d9971; } .a6 { background:#927c70; } .a7 { background:#b27c69; }
            .feeling-tag { padding:6px 10px; border-radius:999px; background:#f5eee7; color:#98634f; font-size:.76rem; font-weight:750; white-space:nowrap; }
            .post-body { margin:0 0 17px; color:#39424a; font-size:1rem; line-height:1.85; overflow-wrap:anywhere; }
            .post-meta { display:flex; gap:7px; flex-wrap:wrap; margin-bottom:16px; }
            .chip { display:inline-block; padding:4px 10px; border-radius:999px; background:#e9edf0; color:#43566a; font-size:.73rem; font-weight:750; }
            .post-actions { display:flex; align-items:center; flex-wrap:wrap; gap:7px; border-top:1px solid #efebe4; padding-top:13px; }
            .react-btn { border:1px solid #e9e4da; border-radius:9px; padding:7px 10px; background:#fffefa; color:#59616a; font:inherit; font-size:.78rem; font-weight:650; cursor:pointer; transition:.2s; }
            .react-btn:hover { border-color:#c7b8a7; background:#f8f4ee; }
            .react-btn.active { border-color:#bd765d; background:#f5e7df; color:#8d513e; }
            .react-btn span { color:inherit; opacity:.75; }
            .spacer { flex:1; }
            .comment-link { border:0; background:none; padding:6px 7px; color:var(--terracotta); font:inherit; font-size:.8rem; font-weight:750; text-decoration:none; cursor:pointer; }
            .comment-link:hover { text-decoration:underline; }
            .comment-link.danger { color:var(--danger); }
            .empty-state { padding:55px 24px; margin:15px 0; text-align:center; background:var(--paper); border:1px dashed #d9d1c5; border-radius:17px; }
            .empty-icon { width:55px; height:55px; display:grid; place-items:center; margin:0 auto 18px; border-radius:17px; background:var(--terracotta-soft); color:var(--terracotta); font-size:1.7rem; }
            .empty-state h2 { font-family:"Playfair Display",Georgia,serif; }
            .empty-state p { max-width:410px; margin:0 auto 20px; color:var(--muted); font-size:.9rem; }
            .profile-summary { display:flex; align-items:center; gap:15px; padding:17px 20px; margin-bottom:20px; background:#e9e8df; border:1px solid #ddded3; border-radius:14px; }
            .profile-summary > div:nth-child(2) { display:grid; }
            .profile-summary > div:nth-child(2) strong { color:var(--navy); }
            .summary-decoration { margin-left:auto; color:#b7a17d; font-size:2rem; }
            .detail-heading { align-items:center; }
            .comments-panel { padding:24px; background:var(--paper); border:1px solid var(--line); border-radius:16px; }
            .comments-heading { display:flex; align-items:center; justify-content:space-between; margin-bottom:14px; }
            .comments-heading h2 { margin:0; font-family:"Playfair Display",Georgia,serif; }
            .comment-count { display:grid; place-items:center; width:32px; height:32px; border-radius:50%; background:var(--terracotta-soft); color:var(--terracotta); font-weight:800; }
            .comments { list-style:none; padding:0; margin:0 0 20px; }
            .comment { display:flex; gap:12px; padding:15px 0; border-top:1px solid var(--line); }
            .comment:first-child { border-top:0; }
            .comment-body { flex:1; min-width:0; }
            .comment-author { display:flex; align-items:center; gap:9px; flex-wrap:wrap; }
            .comment-body p { margin:5px 0; overflow-wrap:anywhere; }
            .comment-empty { padding:15px 0; }
            .comment-form { padding-top:18px; border-top:1px solid var(--line); }


            /* Editor and errors */
            .editor-layout { display:grid; grid-template-columns:.7fr 1.3fr; gap:35px; align-items:start; max-width:1050px; margin:25px auto; }
            .editor-intro { padding:25px 10px; }
            .editor-intro h1 { margin:24px 0 17px; font-family:"Playfair Display",Georgia,serif; font-size:3.4rem; }
            .editor-intro p { color:var(--muted); max-width:300px; }
            .editor-quote { margin-top:45px; padding-left:15px; border-left:2px solid var(--terracotta); color:#7c817b; font-family:"Playfair Display",Georgia,serif; font-style:italic; }
            .editor-card { padding:30px; background:var(--paper); border:1px solid var(--line); border-radius:18px; box-shadow:var(--shadow); }
            .editor-card h2 { font-family:"Playfair Display",Georgia,serif; }
            .error-page { max-width:600px; margin:70px auto; padding:55px 25px; text-align:center; background:var(--paper); border:1px solid var(--line); border-radius:20px; }
            .error-symbol { display:block; color:var(--terracotta); font-size:2.5rem; margin-bottom:15px; }
            .error-page h1 { font-family:"Playfair Display",Georgia,serif; }
            .error-page p { color:var(--muted); }


            /* Footer */
            .footer { padding:23px 18px 27px; text-align:center; color:#969890; background:#f8f6f1; border-top:1px solid var(--line); font-size:.76rem; }
            .footer p { max-width:650px; margin:0 auto 5px; }


            @media (max-width:1100px) {
              .content-grid { grid-template-columns:minmax(0,1fr) 235px; gap:20px; }
              .workspace { padding-left:25px; padding-right:25px; }
              .landing-bottom { grid-template-columns:1fr; gap:25px; }
            }
            @media (max-width:850px) {
              .app-shell { grid-template-columns:78px minmax(0,1fr); }
              .sidebar { padding:23px 11px 17px; align-items:center; }
              .sidebar .brand { margin:0 0 48px; }
              .sidebar .brand > span:last-child,.side-caption,.side-link > span:not(.side-icon),.side-compose:not(span),.side-profile-text,.side-logout { display:none; }
              .side-link { justify-content:center; width:52px; height:50px; padding:0; }
              .side-icon { font-size:1.4rem; }
              .side-compose { width:52px; height:50px; padding:0; font-size:0; }
              .side-compose span { display:inline; font-size:1.5rem; }
              .side-profile { justify-content:center; }
              .community-panel { display:none; }
              .content-grid { grid-template-columns:1fr; }
              .landing-hero { grid-template-columns:1fr  .8fr; }
              .hero-art { transform:scale(.85); }
            }
            @media (max-width:620px) {
              .app-shell { display:block; }
              .sidebar { display:none; }
              .mobile-topbar { position:sticky; top:0; z-index:20; display:flex; align-items:center; justify-content:space-between; padding:12px 17px; background:rgba(255,254,250,.95); border-bottom:1px solid var(--line); backdrop-filter:blur(14px); }
              .mobile-topbar .brand { font-size:1.25rem; }
              .workspace { padding:20px 15px 42px; }
              .workspace-topline { margin-bottom:23px; padding-bottom:13px; }
              .topline-user { display:none; }
              .public-header { padding:17px; }
              .public-main { padding:10px 15px 45px; }
              .public-nav { gap:12px; }
              .public-nav .btn { padding:8px 10px; font-size:.78rem; }
              .landing-hero { display:block; min-height:0; padding:35px 25px 15px; }
              .landing-hero h1 { font-size:3.8rem; }
              .hero-art { min-height:300px; transform:scale(.75); margin:-10px 0 -35px; }
              .art-orbit { width:270px; height:270px; }
              .orbit-two { width:340px; height:340px; }
              .landing-bottom { padding:55px 5px 10px; }
              .feature-grid { grid-template-columns:1fr; }
              .feature-card { min-height:0; }
              .feature-number { margin-bottom:12px; }
              .auth-layout { display:block; margin:15px auto; }
              .auth-aside { padding:30px 27px; min-height:260px; }
              .auth-aside h1 { font-size:2.7rem; margin:18px 0 10px; }
              .auth-aside-mark { font-size:3rem; left:auto; right:20px; bottom:12px; }
              .auth-card { padding:30px 24px; }
              .editor-layout { display:block; margin:0; }
              .editor-intro { padding:10px 3px 20px; }
              .editor-intro h1 { font-size:2.6rem; margin:12px 0; }
              .editor-quote { display:none; }
              .editor-card { padding:21px 17px; }
              .page-heading { align-items:flex-start; }
              .page-heading > .btn { width:100%; }
              .post-card { padding:18px 15px 14px; }
              .post-actions { gap:5px; }
              .react-btn { padding:7px 8px; font-size:.72rem; }
              .feeling-tag { font-size:.68rem; padding:5px 8px; }
              .comments-panel { padding:19px 15px; }
            }
            @media (max-width:390px) {
              .post-head { gap:8px; }
              .avatar { width:38px; height:38px; }
              .feeling-tag { white-space:normal; text-align:center; }
              .toolbar-label { display:none; }
              .landing-hero h1 { font-size:3.2rem; }
            }
            @media (prefers-reduced-motion:reduce) {
              *,*::before,*::after { scroll-behavior:auto!important; transition-duration:.01ms!important; animation-duration:.01ms!important; }
            }
            """;


    public static final String JS = """
            (function () {
              document.addEventListener('submit', function (e) {
                var msg = e.target.getAttribute('data-confirm');
                if (msg && !window.confirm(msg)) {
                  e.preventDefault();
                }
              });


              document.querySelectorAll('textarea[data-counter]').forEach(function (box) {
                var out = document.getElementById(box.getAttribute('data-counter'));
                if (!out) return;


                var max = box.getAttribute('maxlength');
                function update() {
                  out.textContent = box.value.length + ' / ' + max;
                }
                box.addEventListener('input', update);
                update();
              });
            })();
            """;
}
