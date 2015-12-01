package edu.rit.swen755.pool;

/**
 *
 * @author Joanna
 */
public class Main {

    /**
     * @param args URL of the Web page to be Crawled
     */
    public static void main(String[] args) throws Exception {
        WebCrawler crawler = new WebCrawler();
//        args = new String[]{"http://www.ufs.br"};
//        args = new String[]{"http://people.rit.edu/jds5109/645/lab01/start.html"};
//        args = new String[]{"http://people.rit.edu/jds5109/645/"};

//        args = new String[]{"http://www.rit.edu"};

        if (args.length == 0) {
            System.out.println("Please, provide sitebase as an argument");
        } else {
            crawler.execute(args[0]);
        }
    }

}
