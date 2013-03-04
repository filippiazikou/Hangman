/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author filippia
 */
public class Server {

    public static void main(String[] args) throws IOException {
        boolean listening = true;
        
        /*localhost:4444 is default if there are no arguments*/
        int port = 4444;
        String host = "localhost";
        
        /*Read host and port from arguments*/
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("USAGE: java Server [hostname] [port] ");
                System.exit(0);
            }
            host = args[0];

            if (args[0].equalsIgnoreCase("-h") || args[0].equalsIgnoreCase("-help")) {
                System.out.println("USAGE: java Server [hostname] [port] ");
                System.exit(1);
            }
        }

        try {

            //create an IP address and the server's socket to this address and port 2222
            InetAddress addr = InetAddress.getByName(host);
            ServerSocket serversocket = new ServerSocket(port, 1000, addr);

            while (listening) {    // the main server's loop
                System.out.println("Listening to connections on "+host+" : "+port+"...");
                Socket clientsocket = serversocket.accept();
               (new Handler(clientsocket)).start();
            }
            serversocket.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }

    }
}
