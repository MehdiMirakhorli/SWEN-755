package edu.rit.swen755.pool;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {

    // size of the thread pool
    private static final int MAX_POOL_SIZE = 10;
    // pool of threads
    private final ExecutorService threadPool;

    public WebCrawler() {
        // initializes thread pool
        threadPool = Executors.newFixedThreadPool(MAX_POOL_SIZE);
    }

    /**
     * Starts agent execution
     *
     * @param sitebase initial Web site to be analyzed
     */
    public void execute(String sitebase) throws IOException, URISyntaxException  {

        // Gets all links within a Web page
        System.out.println("Getting all links for " + sitebase);
        Set<String> foundLinks = getLinks(sitebase);
        System.out.println("Found " + foundLinks.size() + " links");

        // For each found link, creates a new task to be executed
        for (String nextURL : foundLinks) {
            threadPool.execute(new Task(new URL(nextURL)));
        }

        // Wait for tasks to complete
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {

        }
        
    }

    /**
     * It performs a HTTP GET request to a URL and get the links contained in
     * href attributes of anchor tags.
     *
     * @param url link to the Web page
     * @return a set of links contained in href attributes of anchor tags.
     * @throws IOException
     * @throws URISyntaxException
     */
    private Set<String> getLinks(String url) throws IOException, URISyntaxException {
        Set<String> foundLinks = new HashSet<String>();
        Document doc = Jsoup.connect(url).get();
        Elements linkElements = doc.select("a[href]");
        for (Element linkElement : linkElements) {
            String href = linkElement.attr("href");
            foundLinks.add(resolve(url, href));
        }
        return foundLinks;
    }

    /**
     * Constructs a new URL by parsing the given link and then resolving it
     * against the parent page.
     *
     * @param parentPage the parent Web page in which the link is contained
     * @param link the URL contained inside the href attribute
     * @return a new URL by parsing the given link and then resolving it against
     * the parent page.
     * @throws URISyntaxException
     * @throws MalformedURLException
     */
    private String resolve(String parentPage, String link) throws URISyntaxException, MalformedURLException {
        URI parentURI = new URI(parentPage);
        return parentURI.resolve(link).toString();
    }

}
