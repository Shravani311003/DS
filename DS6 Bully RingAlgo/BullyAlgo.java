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

import java.util.*;



public class Ringalgo

{

	static int old_lead;

	static int new_lead;

	static int fail_pr;

	static int np;

	static int isActive[];

	static int arr[];

	static int initiator;

	public static void main(String args[])

	{

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter the number of process:");

		np = sc.nextInt();

		isActive = new int[np+1];

		for(int i = 1 ; i <= np ; i++)

		{

			isActive[i]=1;

		}

		old_lead = np;

		isActive[old_lead] = 0;

		System.out.println("Enter the process that initiates the process:");

		initiator = sc.nextInt();

		

		System.out.println("The process that is failed is " + old_lead);

		System.out.println("Enter the process that fails(other than leader process) if none give 0:");

		fail_pr = sc.nextInt();

		if(fail_pr > 0 && fail_pr <= np)

		{

			isActive[fail_pr] = 0;

		}

		new_lead  = election_process(isActive,old_lead,initiator);

		System.out.println("Finally the process " + new_lead + " is selected as new lead..");

		for(int i = 1; i<np-1;i++)

		{

		if(isActive[i]==1)

		{

		System.out.println("Process "+new_lead+" passes the coordinator ( "+new_lead+" )new_lead to " +i);

		}

		}

		

		sc.close();

		}
	public static int election_process(int isActive[],int old_lead ,int initiator)
	{

		System.out.println("The election process is started by "+initiator);

		int index = 0 ;

		arr = new int[np+1];

		int i = initiator;

		int receiver = (i % np) + 1;
	while(index <= np-1)

		{

			if(isActive[i]==1 && i!= receiver)

			{

				if(isActive[receiver]==0)

				{

				receiver = (receiver % np)+1;

				}

		System.out.println(i + " sends the Election message to process "+receiver);

		arr[index] = i;

		printArray(arr,index + 1);

		}
		i = (i%np)+1;

		receiver = (i%np)+1;

		index++;
		}
		new_lead = 0;
		for(int j = 0 ; j<=np;j++)

		{

			if(new_lead < arr[j])

			{

				new_lead = arr[j];

			}
		}
		return new_lead;

}

public static void printArray(int arr[],int size)

{

	System.out.print("[");

	for(int i = 0 ; i <size ; i++)

	{

		if(arr[i]==0) continue;

		System.out.print(arr[i] + " ");
	}

	System.out.println("]");

}

}
