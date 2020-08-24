package selenium;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NYTScraper {
	
	public static ArrayList<Website> getArticles(){
		ArrayList<Website> articlesPass = new ArrayList<Website>();
		try {
			Document doc = Jsoup.connect("https://www.nytimes.com/search?query=trump").get();
			Elements articles = doc.select(".css-1l4w6pd");
			for (Element e: articles) {
				Website article = new Website();
				article.setWebsite("NY Times");
				article.setUrl("https://www.nytimes.com" + e.select(".css-e1lvw9").select("a").attr("href"));
				article.setTitle(e.select(".css-2fgx4k").text());
				article.setAuthor(e.select(".css-15w69y9").text());
				article.setDatePosted("n/a"/*e.select("div.css-17ubb9w").text()*/);
				
				articlesPass.add(article);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return articlesPass;
	}
}