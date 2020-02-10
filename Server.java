import java.util.*;
import java.io.*;
import java.net.*;

public class Server implements Runnable {

	public static ArrayList<Socket> sockets;
	public Socket socket;

	public static void main(String[] args) throws Exception {
		if(args.length <= 0) {
			System.out.println("Please provide port in the arguments.");
			return;
		}

		ServerSocket server = new ServerSocket(Integer.valueOf(args[0]));
		sockets = new ArrayList<Socket>();

		Socket s;
		while(true) {
			s = server.accept();

			System.out.println("Connection made");
			sockets.add(s);
			new Thread(new Server(s)).start();
		}
	}

	public Server(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			String line;
			BufferedReader br;
			PrintWriter pw;
			while(true) {
				br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

				OUTER: while(true) {
					while ((line = br.readLine()) != null) {
						//if(line.equals(""))
						//	break OUTER;
						System.out.println(line);
						for(Socket t : sockets) {
							pw = new PrintWriter(t.getOutputStream());
							pw.println(line);
							pw.flush();
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
