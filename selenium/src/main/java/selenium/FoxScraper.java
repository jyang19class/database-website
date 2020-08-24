package selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FoxScraper {
	
	public static ArrayList<Website> getArticles() {
		ArrayList<Website> articles = new ArrayList<Website>();
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\jyang19\\Downloads\\geckodriver-v0.27.0-win64\\geckodriver.exe");
		
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		WebDriver driver = new FirefoxDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		driver.navigate().to("https://www.foxnews.com/category/person/donald-trump");
		
		String buttonXPath = "/html/body/div[1]/div/div/div[2]/div[3]/div/main/section/footer/div/a";
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonXPath)));
		driver.findElement(By.xpath(buttonXPath)).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		String pageHtml = driver.getPageSource();
		driver.quit();
		Document doc = Jsoup.parse(pageHtml);
				
		
		Elements elements = doc.select(".main-content").select(".article");
		
		for (Element e:elements) {
			if (e.hasClass("vendor-unit")) {
				continue;
			}
			Website article = new Website();
			article.setWebsite("Fox News");
			article.setUrl("https://www.foxnews.com/" + e.select(".title").select("a").attr("href"));
			article.setTitle(e.select(".title").text());
			article.setAuthor("tbd");
			article.setDatePosted(e.select(".time").text());
			
			articles.add(article);
		}
		return articles;
	}
}