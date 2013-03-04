/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author filippia
 */

public class Handler extends Thread {
	private Socket clientSocket;

	public Handler(Socket clientSocket) {
                System.out.println("Connection Received");
		this.clientSocket = clientSocket;
               
	}

	public void run() {
		BufferedInputStream in;
		BufferedOutputStream out;

		try {
			in = new BufferedInputStream(clientSocket.getInputStream());
			out = new BufferedOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		}
                
                new Game(in, out);

		try {
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
