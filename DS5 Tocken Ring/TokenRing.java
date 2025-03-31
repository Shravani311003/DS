import java.util.*;

public class TokenRing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  // Initialize Scanner
        
        System.out.println("Enter the message: ");
        String message = sc.nextLine();
        
        System.out.println("Enter number of processes: ");
        int n = sc.nextInt();
        
        System.out.println("The processes are: ");
        for (int i = 1; i <= n; i++) {
            System.out.print(i + " ");
        }
        System.out.println("1\n");
        
        System.out.println("Enter the sender: ");
        int sender = sc.nextInt();
        
        System.out.println("Enter the receiver: ");
        int receiver = sc.nextInt();
        
        // Sender sends the message
        System.out.println("Sender process -> " + sender);
        System.out.println("The token is received by the sender");
        System.out.println("The message to be sent: " + message);

        // Token passing
        int current = sender;
        while (true) {
            current = (current % n) + 1;  // Circular token passing

            System.out.println("Token passed to process " + current);

            if (current == receiver) {
                System.out.println("\nReceiver process -> " + receiver);
                System.out.println("The message is received: " + message);
                break;
            }
        }
        
        sc.close();  // Close the scanner to prevent memory leaks
    }
}
