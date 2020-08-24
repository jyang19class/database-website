package selenium;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class USATodayScraper {
	
	public static ArrayList<Website> getArticles() {
		ArrayList<Website> articles = new ArrayList<Website>();
		
		try {
			Document doc = Jsoup.connect("https://www.usatoday.com/search/?q=trump").get();
			Elements webPieces = doc.select(".gnt_se_a.gnt_se_a__hd.gnt_se_a__hi");
			for (Element e: webPieces) {
				Website article = new Website();
				article.setWebsite("USA Today");
				article.setUrl("https://www.usatoday.com" + e.attr("href"));
				article.setTitle(e.text());
				article.setAuthor(e.select(".gnt_se_th_by.gnt_sbt.gnt_sbt__ms.gnt_sbt__ts").text());
				article.setDatePosted(e.select(".gnt_se_th_by.gnt_sbt.gnt_sbt__ms.gnt_sbt__ts").attr("data-c-dt"));
				
				articles.add(article);
			}
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}
}