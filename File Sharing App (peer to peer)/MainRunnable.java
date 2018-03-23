/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesharingappmain;

import java.io.IOException;

/**
 *
 * @author Henkok
 */
public class MainRunnable implements Runnable {

    private ConnectionManager conn;
    private boolean isServer;
    private short purpose;
    public static final short SERVER = 0;
    public static final short CLIENT = 1;

    public MainRunnable(ConnectionManager conn, boolean isServer) {
        this.conn = conn;
        this.isServer = isServer;
    }

    @Override
    public void run() {
        if (isServer) {
            try {
                this.conn.establishConnection(true, this.conn.url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.conn.establishConnection(false, this.conn.url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            //this.conn.receiveAndWriteFile("C:\\Users\\Henkok\\Music\\nati\\ff.mp3");            
            readyToReceive();
        } catch (Exception e) {
            //this.conn.socket = null;
            e.printStackTrace();            
        }

    }
    
    public void readyToReceive() throws IOException {
        if (this.conn.setUPReceiving()){
            readyToReceive();            
        }
    }

}
