import java.util.*;

public class BullyAlgo {
    static int np; // Number of processes
    static int old_lead; // The failed coordinator or leader
    static int new_lead; // The newly elected leader
    static int curr_elec; // The current process that is holding the election
    static int fail_pr;
    static int isActive[];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of processes: ");
        np = sc.nextInt();

        isActive = new int[np + 1];

        for (int i = 1; i <= np; i++) {
            isActive[i] = 1;
        }

        old_lead = np;
        isActive[old_lead] = 0; // The leader has failed

        System.out.println("Enter the process that initiates the election process: ");
        curr_elec = sc.nextInt();

        System.out.println("The leader process that failed is: " + old_lead);

        System.out.println("Enter the process that fails other than the leader process: ");
        fail_pr = sc.nextInt();
        
        if (fail_pr != old_lead && fail_pr > 0 && fail_pr <= np) {
            isActive[fail_pr] = 0;
        } else {
            System.out.println("Invalid process failure input. Continuing without it.");
        }

        // Start the election process
        new_lead = election_process(isActive, old_lead, curr_elec);

        System.out.println("Finally, process " + new_lead + " became the new leader");

        // Inform all processes about the new leader
        for (int i = 1; i <= np; i++) {
            if (isActive[i] == 1 && i != new_lead) {
                System.out.println("Process " + new_lead + " passes a Coordinator (" + new_lead + ") message to process " + i);
            }
        }
        
        sc.close();  // Closing the Scanner
    }

    public static int election_process(int isActive[], int old_lead, int curr_elec) {
        int max_process = curr_elec;

        for (int i = curr_elec; i <= np; i++) {
            if (isActive[i] == 1) {
                for (int j = i + 1; j <= np; j++) {
                    if (isActive[j] == 1) {
                        System.out.println("Process " + i + " passes Election(" + i + ") message to process " + j);
                    }
                }

                boolean higherResponse = false;

                for (int j = i + 1; j <= np; j++) {
                    if (isActive[j] == 1) {
                        System.out.println("Process " + j + " passes Ok(" + j + ") message to process " + i);
                        higherResponse = true;
                    }
                }

                if (!higherResponse) {
                    max_process = i;
                    break;
                }
            }
        }

        return max_process;
    }
}
