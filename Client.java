import java.net.*;
import java.io.*;
import java.util.*;

public class Client implements Runnable {

	public static Socket s;

	public static void main(String[] args) {
		String ip;
		String name = null;
		int port;

		if(args.length > 0) {
			ip = args[0];

			if(args.length > 1) {
				port = Integer.valueOf(args[1]);
				if(args.length > 2) {
					name = args[2];
				}
			} else {
				System.out.println("Missing Port Number");
				return;
			}
		} else {
			System.out.println("Missing IP");
			return;
		}

		try {
			s = new Socket(ip, port);

			new Thread(new Client()).start();

			Scanner scan = new Scanner(System.in);
			name = (name == null ? s.getLocalAddress().getHostAddress() + ":" + s.getLocalPort() : name);

			PrintWriter pw = new PrintWriter(s.getOutputStream());
			while(true) {
				pw.println(name + "> " + scan.nextLine());
				pw.flush();
			}
		} catch(UnknownHostException e) {
			System.out.println("Unknown host: " + e);
		} catch(IOException e) {
			System.out.println("Error with IO: " + e);
		}
	}

	public void run() {
		try {
		BufferedReader br;
		String line;

		while(true) {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));

			OUTER: while(true) {
 				while ((line = br.readLine()) != null) {
					if(line.equals(""))
						break OUTER;
					System.out.println(line);
				}
			}
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
