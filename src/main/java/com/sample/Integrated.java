package com.sample;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * A class that does something with an external resource and needs an integratino test
 */
public class Integrated {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class Static {
        public static Integrated importPageByUrl(String urlString) {
            try {
                Scanner scanner = new Scanner(new URL(urlString).openStream(), "UTF-8");
                // See: https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner.html
                String out = scanner.useDelimiter("\\A").next();
                scanner.close();

                // Ok, never do this.. but we just need a quick thing to do that we can test
                String title = out.replaceAll("^(?s)(?i).*(<title.*?>)(.+?)(</title>).*$", "$2");

                Integrated result = new Integrated();
                result.setTitle(title);
                return result;
            } catch(MalformedURLException ex) {
                return null;
            } catch(IOException ex) {
                return null;
            }
        }
    }
}
