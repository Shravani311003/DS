#include <stdio.h>

#include <stdlib.h>

#include <mpi.h>

#include <math.h>

int a[20];

int a2[20];

int main(int argc,char* argv[])

{	

	int pid,np,elements_per_process,n_elements_received,no;

	MPI_Status status;

	MPI_Init(&argc,&argv);

	MPI_Comm_rank (MPI_COMM_WORLD,&pid);

	MPI_Comm_size(MPI_COMM_WORLD,&np);

	if(pid==0)

	{

	printf("Enter the number of elements:");

	scanf("%d",&no);

	printf("Enter the %d elements\n:",no);

	for(int i = 0 ; i<no ;i++)

	{

		scanf("%d",&a[i]);

	}
 elements_per_process = (int) ceil((double) no / np);

	for (int i = 1; i < np; i++) 

	{

            int index = i * elements_per_process;

            int elements_to_send = elements_per_process;



		    if (i == np - 1) 

		    {

		        elements_to_send = no - index;

		    }



		    MPI_Send(&elements_to_send, 1, MPI_INT, i, 0, MPI_COMM_WORLD);

		    MPI_Send(&a[index], elements_to_send, MPI_INT, i, 0, MPI_COMM_WORLD);



		    if (i == np - 1)

		        printf("Sending remaining elements to: %d\n", i);

		    else

		        printf("Sending elements to the process: %d\n", i);

        }

	int sum=0;

	for(int i = 0 ; i < elements_per_process;i++)

	{

		sum+=a[i];

	}

	

	printf("The partial sum of master process is :%d\n",sum);

	

	int temp = 0 ;

	for(int i = 1 ; i<np;i++)

	{

		MPI_Recv(&temp,1,MPI_INT,MPI_ANY_SOURCE,0,MPI_COMM_WORLD,&status);

		printf("Partial sum for process %d is : %d\n",i,temp);

		sum+=temp;

	}
	printf("Final sum is %d\n:",sum);

        }

        

        else

        {

        	MPI_Recv(&n_elements_received,1,MPI_INT,0,0,MPI_COMM_WORLD,&status);

        	MPI_Recv(&a2,n_elements_received,MPI_INT,0,0,MPI_COMM_WORLD,&status);

        	printf("Slave process %d has received the array.\n",pid);

        	int partial_sum=0;

        	for(int i = 0 ; i < n_elements_received;i++)

        	{

        		partial_sum+=a2[i];

        	}

        	MPI_Send(&partial_sum,1,MPI_INT,0,0,MPI_COMM_WORLD);

        }

        MPI_Finalize();

}
//mpicc mpi.c -o mpi -lm

//mpirun -np 4 ./mpi

//to run 
//mpicc -o DS3MPI_c DS3MPI.c

//mpirun -np 4 ./DS3MPI_c

