package com.example;

/**
 * Created by brank12345 on 16/6/12.
 */

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class Server extends JFrame implements Runnable{
    private Thread thread;
    private ServerSocket servSock;
    private JTextArea textArea = new JTextArea();

    public Server(){
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setResizable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(textArea);
        this.setVisible(true);


        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            System.out.println("IP of my system is := "+IP.getHostAddress());
            System.out.println("Waitting to connect......");
            textArea.append("IP of my system is := "+IP.getHostAddress() + "\n");

            // Create server socket
            servSock = new ServerSocket(8888);

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
    }

    @Override
    public void run(){
        System.out.println("hello");
        // Running for waitting multiple client
        while(true){
            try{
                System.out.println("D");
                // After client connected, create client socket connect with client
                Socket clntSock = servSock.accept();
                System.out.println("A");
                InputStream in = clntSock.getInputStream();
                System.out.println("Connected!!");

                // Transfer data
                byte[] b = new byte[1024];
                int length;

                length = in.read(b);
                String s = new String(b);
                System.out.println("The result from app:" + s);
                textArea.append(s+"\n");

            }
            catch(Exception e){
                //System.out.println("Error: "+e.getMessage());
            }
        }
    }
}
