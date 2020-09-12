package newspapercrawler;

import java.lang.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.util.ArrayList;

public class crawler {

//    Main was used for debugging purposes
//    public static void main(String[] args) {
//        ArrayList<News> newss = crawlDailyStar("https://www.thedailystar.net/top-news");
//
//        for (News news : newss) {
//           System.out.println(news.toString());
//        }
//    }


    public static ArrayList<News> crawlDailyStar(String initialUrl) {
        ArrayList<News> newsList = new ArrayList<>();                                               // this is the output arraylist
        //String initialUrl;

        initialUrl = "https://www.thedailystar.net/top-news";                                       // this is the url that we are crawling from

        Document DailyStarDoc = null;
        try {
            DailyStarDoc = Jsoup.connect(initialUrl).get();
        } catch (Exception ex) {
            System.out.println("Debug: Couldn't connect with the website 1");
        }

        //System.out.println("Finding elements from The Daily Star");

        Elements url = DailyStarDoc.select("h3");
        String finalUrl="https://www.thedailystar.net";
        //System.out.println("run 1");

        for(Element  singleUrl : url) {
            String headline = singleUrl.select("a").text();                                // parsing the headline of the top news
            //System.out.println(headline);
            String link = singleUrl.select("a").attr("href");
            String theLink = finalUrl + link;                                                       // this is the LINK of the top news
            //System.out.println(theLink);

            Document DailyStarBody = null;
            String message;
            String Image = null;
            try {
                DailyStarBody = Jsoup.connect(theLink).timeout(1000).get();                         // the timeout was used so that the website doesn't disconnect us and throw an exception
                Elements body = DailyStarBody.select("div.field-body");                    // parsing the body of the news
                message = body.select("p").text();
                //System.out.println(message);
                Elements image = DailyStarBody.select("div.field-body");
                Image = image.select("img").attr("src");                                // parsing image from the news
                //System.out.println(Image);
            } catch (Exception ex) {
                System.out.println("Debug: Couldn't connect with the website 2");
                message = "No Text Found!!!";
                Image = "No Image Found!!!";
            }
            //System.out.println("run 2");

            News n = new News (headline, Image, message, theLink);                                  // creating a news object

            newsList.add(n);                                                                        // adding the object in the arraylist
        }
        //System.out.println("run 3");
        return newsList;                                                                            // returning the arraylist to the GUI
    }
}