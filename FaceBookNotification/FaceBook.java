/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automateone;

import java.sql.Driver;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Henkok
 */
public class FaceBook {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\Henok Dejen\\Academic\\6th semister\\SE 2\\Labs\\Selenium Tools\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        
        String url = "https://en-gb.facebook.com/login/";
                
        driver.navigate().to(url);
        
        sleep(2);
        
        WebElement username = driver.findElementById("email");
        WebElement password = driver.findElementById("pass");
        WebElement Submit = driver.findElementById("loginbutton");
        

        
        username.sendKeys("dawitgizaw51@gmail.com");
        password.sendKeys("01001000110000");
        Submit.click();
        
        showNumberNotifications(driver);
    }
    
    static void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SecondMukera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static void showNumberNotifications(ChromeDriver driver) {
        WebElement notCount = driver.findElementById("notificationsCountValue");
        System.out.println("Notifications: " + notCount.getText());
    }
    
}
