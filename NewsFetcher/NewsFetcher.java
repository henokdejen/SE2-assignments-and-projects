/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsfetcher;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Henkok
 */
public class NewsFetcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        String path = "D:\\Henok Dejen\\Academic\\6th semister\\Projects and Assignments\\SE\\Browser Automation\\todayNews.html";
        String Cont = "<h1>ache</h1>";
        new NewsFetcher().InjectHtmlContent(path, Cont);
        
        
        System.setProperty("webdriver.chrome.driver", "D:\\Henok Dejen\\Academic\\6th semister\\SE 2\\Labs\\Selenium Tools\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        
       String url = "http://www.bbc.com/news";                

        driver.navigate().to(url);        
        sleep(2);
        
        WebElement NewsWrapper = driver.findElementByClassName("nw-c-top-stories--standard");
               
        // Actual scrapping to be done here.
        List<WebElement> allNews = NewsWrapper.findElements(By.className("nw-c-top-stories__secondary-item"));
        System.out.println(allNews.size());
        
        
        String todayNews = "D:\\Henok Dejen\\Academic\\6th semister\\Projects and Assignments\\SE\\Browser Automation\\todayNews.html";        
        String content = buildNews(allNews);
        
        InjectHtmlContent(todayNews, content);
    }
    
    
    public static void InjectHtmlContent(String path, String content) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(content.getBytes());            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewsFetcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewsFetcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public static String buildNews(List<WebElement> news) {
        StringBuilder builder = new StringBuilder("<head>"
                + "<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"index.css\" />\n" +
                    "</head><div id=\"newsWrapper\">");
        
        String newsHeader, newsImSrc, newsBody;
        WebElement currentNews;
        for (int i = 0; i < news.size(); i++) {
            currentNews = news.get(i);
            newsHeader = currentNews.findElement(By.className("gs-c-promo-heading__title")).getText();
            newsImSrc = currentNews.findElement(By.className("lazyloaded")).getAttribute("src");
            newsBody = currentNews.findElement(By.className("nw-c-promo-summary")).getText();
            // finiding out value of the above variable is to be done
            builder.append(createSingleNews(newsImSrc, newsHeader, newsBody));
        }   
        
        builder.append("</div>");
        return builder.toString();
    }
    
    public static String createSingleNews(String imageSrc, String newsHeader, String newsBody) {
        StringBuilder builder = new StringBuilder();
        
        builder.append("    <div class=\"singleNews\">\n" +
" <div class=\"newsImage\">\n" +
"            <img src=\"" + imageSrc + "\" />\n" +
"        </div>\n" +
"        <div class=\"newsText\">\n" +
"            <h3 class=\"newsHeader\">\n" +
                newsHeader +
"            </h3>\n" +
"            <p class=\"newsBody\">\n" +
                newsBody +
"            </p>\n" +
"        </div>\n" +
"\n" +
"    </div>");
        return builder.toString();
    }
}
