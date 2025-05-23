
import java.rmi.*;
import java.util.Scanner;

public class Client {
	public static void main(String args[]) {
    	try {
        	Scanner s = new Scanner(System.in);
        	System.out.println("Enter the Server address : ");
        	String server = s.nextLine();
        	ServerInterface si = (ServerInterface) Naming.lookup("rmi://" + server + "/Server");
        	System.out.println("Enter first string : ");
        	String first = s.nextLine();
        	System.out.println("Enter second string : ");
        	String second = s.nextLine();
        	System.out.println("Concatenated String : " + si.concat(first, second));
        	s.close();
    	} catch (Exception e) {
        	System.out.println(e);
    	}
	}
}

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.server.*;

public class Servant extends UnicastRemoteObject implements ServerInterface {
	protected Servant() throws RemoteException {
    	super();
	}

	@Override
	public String concat(String a, String b) throws RemoteException {
    	return a + b;
	}
}

import java.rmi.*;
import java.net.*;

public class Server {
	public static void main(String[] args) {
    	try {
        	Servant s = new Servant();
        	Naming.rebind("Server", s);
    	} catch (Exception e) {
        	System.out.println(e);
    	}
	}
} 

import java.rmi.*;

public interface ServerInterface extends Remote {
	String concat(String a, String b) throws RemoteException;
} 
