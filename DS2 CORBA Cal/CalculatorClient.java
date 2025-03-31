import CalculatorApp.*;
import CalculatorApp.CalculatorHelper;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class CalculatorClient {
	public static void main(String[] args) {
    	try {
        	// Initialize ORB
        	ORB orb = ORB.init(args, null);

        	// Get reference to naming service
        	org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        	NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        	// Resolve the Calculator object reference in the naming service
        	String name = "Calculator";
        	Calculator calculator = CalculatorHelper.narrow(ncRef.resolve_str(name));

        	// Call remote methods
        	System.out.println("Add: " + calculator.add(10.5f, 5.5f));
        	System.out.println("Subtract: " + calculator.subtract(10.5f, 5.5f));
        	System.out.println("Multiply: " + calculator.multiply(10.5f, 5.5f));
        	System.out.println("Divide: " + calculator.divide(10.5f, 5.5f));
    	} catch (Exception e) {
        	e.printStackTrace();
    	}
	}
}
//1st terminal
//idlj -fall Calculator.idl
//tnameserv -ORBInitialPort 1050

//2nd terminal
//javac *.java CalculatorApp/*.java
//java CalculatorServer -ORBInitialPort 1050 -ORBInitialHost localhost

//3rd terminal
//java CalculatorClient -ORBInitialPort 1050 -ORBInitialPort 1050 -ORBInitialHost localhost