module StringApp {

    interface StringService {

        string reverse(in string input);

    };

};

import StringApp.*;

import java.util.*;

import StringApp.StringServiceHelper;

import org.omg.CORBA.*;

import org.omg.CosNaming.*;

import org.omg.CosNaming.NamingContextExt;

import org.omg.CosNaming.NamingContextExtHelper;



public class StringClient

{

	public static void main(String [] args)

	{

	try

	{

		ORB orb = ORB.init(args,null);



		org.omg.CORBA.Object objref = orb.resolve_initial_references("NameService");

			NamingContextExt ncRef = NamingContextExtHelper.narrow(objref);		

			

			String name = "StringService";

			StringService stringservice = StringServiceHelper.narrow(ncRef.resolve_str(name));

			

			Scanner sc = new Scanner(System.in);

			System.out.println("Enter the string to reverse:");

			String rev = sc.nextLine();

			

			String result = stringservice.reverse(rev);

			

			System.out.println("Reversed string is:"+result);

			

			sc.close();

	}

	catch (Exception e)

	{

		e.printStackTrace();

	}

	}

} 

import StringApp.*;

import StringApp.StringServiceHelper;

import org.omg.CORBA.*;

import org.omg.PortableServer.*;

import org.omg.PortableServer.POA;

import org.omg.CosNaming.*;

import org.omg.CosNaming.NamingContextExt;

import org.omg.CosNaming.NamingContextExtHelper;



class StringServiceImpl extends StringServicePOA

{

	private ORB orb;

	

	public void setORB(ORB orb)

	{

		this.orb = orb;

	}

	

	public String reverse(String a)

	{

		return new StringBuilder(a).reverse().toString();

	

	}





}



public class StringServer

{

	public static void main(String [] args)

	{

		try

		{

			ORB orb = ORB.init(args,null);

			

			POA rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

			rootPoa.the_POAManager().activate();

			

			StringServiceImpl stringserviceImpl = new StringServiceImpl();

			stringserviceImpl.setORB(orb);

			

			org.omg.CORBA.Object ref = rootPoa.servant_to_reference(stringserviceImpl);

			StringService href = StringServiceHelper.narrow(ref);

			

			org.omg.CORBA.Object objref = orb.resolve_initial_references("NameService");

			NamingContextExt ncRef = NamingContextExtHelper.narrow(objref);		

			

			String name = "StringService";

			NameComponent [] path = ncRef.to_name(name);

			ncRef.rebind(path,href);

			

			System.out.println("String server ready...");

			orb.run();

		

		

		}

		catch(Exception e)

		{

			e.printStackTrace();
	}

	}

}

//1st terminal
//idlj -fall ReverseModule.idl
//javac *.java ReverseModule/*.java
//orbd -ORBInitialPort 1050

//2nd terminal
//java ReverseServer -ORBInitialPort 1050and -ORBInitialHost localhost
//-ORBInitialHost:

//3rd terminal
//java ReverseClient -ORBInitialPort 1050 -ORBInitialHost localhost

