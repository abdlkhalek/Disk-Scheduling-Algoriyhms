package com.disk.Schedule;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		int n;
		int request;
		int head=0;
		int choice;Scanner getter= new Scanner(System.in); // which will get the user's choice. 
		List<Integer> queue = new ArrayList<Integer>();
		System.out.println("****************************** ");
		System.out.println("enter number of cylinders I/O requests");
		n = getter.nextInt();
		for(int i=0 ;i<n;i++) {
			int j=i+1;
		System.out.println("enter "+ j +" request");
		request = getter.nextInt();
		queue.add(request); 	// adding data to the list.
		
		}
		System.out.println("enter the disk head");
		head = getter.nextInt();  
	     
		// where the point of index of writer and reader is 
				// creating instance of DiskAlgorithms Class
				
		
		DiskSchedule disk = new DiskSchedule();
		do {
			System.out.println("****************************** ");
			System.out.print("Which Algorithm you wanna Run ? \n" 
					  + "1-fcfs.\n2-sstf.\n3-scan.\n4-c-scna\n5-look.\n6-c-look\n7-optimize algorithm\n8-Exit\n"
			);
			System.out.print("your choice : ");
			choice = getter.nextInt();// print out menu.
		switch(choice) {
			case 1:
				System.out.println("First come first Server Algorithm.");
				disk.fcfs(queue,head);				
				break;
			case 2:
				System.out.println("shortest seek time first.");
				disk.sstf(queue, head);
				break;
			case 3:
				disk.scanAndLook(head, queue); 
				break;
			case 4:
				disk.cScan(queue, head,199);
				break;
			case 5 : 
				disk.startLook(head, queue);
				break;
			case 6:
				disk.cLook(queue,head);
				break;
			case 7:
				disk.optimiseAlgorithm(head,queue); 
				break;
			case 8:
				System.out.println("good bye");
				break;
			default:
				
				System.out.println("No Algorithm exist such your choice");
		}// end of switch  
		}while(choice!=8);
		getter.close();
	} // end of main function
} // end of class.
