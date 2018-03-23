/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesharingappmain;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Henkok
 */
public class ConnectionManager {
    public boolean isConnected;
    public String url;
    public Home displayer;
    Socket socket;
    InputStream inputstream;
    OutputStream outputstream;
    public static final int PORT = 3000;
    public static final int BUFFER_SIZE = 1024 * 1024;    
    public Thread mainThread;
    public String DESTINATION_FOLDER;
    
    public ConnectionManager(Home home, Thread t) {
        this.displayer = home;
        this.mainThread = t;
    }
    
    public void establishConnection(boolean isServer, String url) throws IOException {
        if (isServer) {
            ServerSocket server = new ServerSocket(this.PORT);
            System.out.println("server started");
            socket = server.accept();
        }
        else {
            socket = new Socket(url, this.PORT);
        }
        inputstream = socket.getInputStream();
        outputstream = socket.getOutputStream();
        this.displayer.showConnectedStatus();
        this.displayer.showSharingProgressPanel();
    }
    
    public void readAndSendFile(String path, long size) throws FileNotFoundException, IOException {
       
        InputStream f = new FileInputStream(path);
        double compeletedPercent = 0;        
        int readLength;
        byte[] buffer = new byte[1024 * 1024];
        while ((readLength = f.read(buffer)) > 0) {
            outputstream.write(buffer, 0, readLength);
            outputstream.flush();
            compeletedPercent += (readLength / (double)size) * 100;  
            this.displayer.updateProgressBar((int) Math.floor(compeletedPercent));            
        }
        this.displayer.setSharingAction("file successfully Sent");                    
        outputstream.write(0);
        outputstream.flush();
        inputstream.read();
        inputstream.close();
        f.close();
        System.out.println("hererer erer"); 
    }
    
    public boolean setUPReceiving(){
        
        try {
            //System.out.println(inputstream.read());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
            String fileName = reader.readLine();
            long filesize = Long.parseLong(reader.readLine());
            this.displayer.setSharingAction("Receving " + fileName);
            this.displayer.updateProgressBar(0);
            this.receiveAndWriteFile(this.DESTINATION_FOLDER + "\\" + fileName, filesize);
            System.out.println(fileName + " " + filesize);
        }
        
        catch (Exception e){
            this.displayer.showNoConnection();
            this.displayer.showSharingHome();
            return false;
        }
        return true;
    }
    
    public void setUpSending(String path) {
        try {            
            PrintWriter writer = new PrintWriter(outputstream);
            File file = new File(path);
            writer.println(file.getName());
            writer.println(file.length());            
            writer.flush();
            this.displayer.setSharingAction("Sending " + file.getName());
            this.displayer.updateProgressBar(0);
            readAndSendFile(path, file.length());
        }
        
        catch (Exception e){
            this.displayer.showNoConnection();
            this.displayer.showSharingHome();
        }        
    }
    
    public void receiveAndWriteFile(String file, long size) throws FileNotFoundException, IOException {
        System.out.println(size);
        OutputStream f = new FileOutputStream(file);
        int readLength;
        double compeletedPercent = 0;
        //boolean sharingStarted = false;
        byte[] buffer = new byte[1024];
        while ((readLength = inputstream.read(buffer)) > 0) {
            f.write(buffer, 0, readLength);           
            f.flush();
            compeletedPercent += (readLength / (double)size) * 100;  
            this.displayer.updateProgressBar((int) Math.floor(compeletedPercent));
        }
        this.displayer.setSharingAction("file successfully Received");                    
        outputstream.write(0);
        outputstream.flush();
        outputstream.close();
        f.close();                
    }    
}
