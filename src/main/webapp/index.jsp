<%
new com.psddev.dari.util.DebugFilter.PageWriter(application, request, response) {{
    startPage("Welcome to Brightspot CMS!");
        start("div", "class", "hero-unit", "style", "background: transparent; margin: 0 auto; padding-left: 0; padding-right: 0; width: 50em;");
            start("h2", "style", "margin-bottom: 20px;").html("Congratulations on installing Brightspot CMS!").end();
            start("p");
                html("Brightspot CMS is an open source content management system built on top of the ");
                start("a", "href", "http://www.dariframework.org/").html("Dari Framework").end();
                html(". Each instance is uniquely tailored to the application it powers, as its user interface is automatically synchronized to the back-end models.");
            end();
            start("p", "style", "margin-top: 30px;");
                start("a",
                        "class", "btn btn-large",
                        "href", "http://perfectsense.github.com/brightspot-cms/");
                    html("Let's get started \u2192");
                end();
            end();
        end();
    endPage();
}};
%>
