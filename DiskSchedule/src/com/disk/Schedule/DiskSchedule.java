package com.disk.Schedule;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.lang.Math.abs;


/**
 * DiskAlgoritms Class which offer all disk Schedulingin os.
 * will work with request queue [0-199].
 * @author Abdulkhalek Alalimi, Osama Alrohani,Rokaya Adel,Nourhan Mohamed
 * @since 5-2021
 * @version 1.0
 * @see dr's Slides , chapter 10.
 **/ 
public class DiskSchedule {

	 private List<Integer>queueLess;
	 private List<Integer>queueBig;
	/**
	 *	a function will return the smallest number in the array everyTime
	 *  @param List<Integer>queue,int head
	 *  @return paths array
	 **/
	public DiskSchedule() {

        this.queueLess=new ArrayList<>();
        this.queueBig=new ArrayList<>();
    }

	private ArrayList<Integer> Paths(List<Integer>queue,int head) {
		ArrayList<Integer> paths = new ArrayList<Integer>();
		int n = queue.size();
		int result =0;
		for (int i=0;i<n;++i) {
			result=Math.abs(queue.get(i)-head);
			paths.add(result);
		} // end of loop 
		return paths;
	} // end of function.
	
	/**
	 *	getMinimum function
	 *	@param queue
	 *	@return index of minimum number 
	 **/ 
	private int getMinimum(List<Integer> queue) {
		int min = 200000000,index=0;
		for( int i=0;i<queue.size();i++){
			 if(queue.get(i) < min && queue.get(i)!=-1){ 
			      min = queue.get(i);
			      index=i;
			 } // end of statement
		} // end of loop
		return index;
	} // end of function.
	
	/**
	 * first come first serviced
	 * @param queue[] , head
	 * @author osama
	 **/
	public void fcfs(List<Integer> queue,int head){
		  int total=0;
	        total=Math.abs(head-queue.get(0));
	        System.out.print("Order Of Movments : " + head +"->"+queue.get(0) + " -> ");
	        for(int i=0;i<queue.size()-1;i++) {
	            System.out.print(queue.get(i+1) + " -> ");
	            head=queue.get(i);
	            total+=Math.abs(head-queue.get(i+1));

	        }
	        System.out.print("The end");
	        System.out.println("\nAll movments are done, with cylnder : " + total); // seek distance.
 // seek distance.
	} // end of fcfs function.
	
	/**
	 * shortest seek time first.
	 * @param queue[] , head
	 * @author osama
	 **/
	
	public void sstf(List<Integer> queue,int head) {
		
		ArrayList<Integer> paths = Paths(queue,head);
		int n=paths.size(),index=0,cylinder=0,helper=head;
		System.out.print("Paths will go throw not in order: ");
		for (int i=0;i<n;i++) {
			System.out.print(paths.get(i)+ " -> " );
		} // end of loop 
		System.out.println();
		System.out.println("indecies Order is : ");
		for (int i=0;i<queue.size();++i) {
			index = getMinimum(paths);
			System.out.print(queue.get(index) + " -> ");
			cylinder+=Math.abs(helper-queue.get(index));
			helper=queue.get(index);	
			paths.set(index,-1);
		} // end of loop 
		System.out.println("\nCylender = " + cylinder);
	} // end of sstf function.
	
	/**
	 * C-Scan Scheduler
	 * @param queue[] , head
	 * @author Abdulkhalek 
	 **/
	public void cScan(List<Integer>queue,int head,int last) {
		List<Integer> newQueue = new ArrayList<Integer>(queue);
		Collections.sort(newQueue,Collections.reverseOrder());
		List<Integer> paths = Paths(newQueue, head);
//		queue.forEach(System.out::println);
		int end=getMinimum(paths),start =getMinimum(newQueue),n = newQueue.size(),cylinder=0;
		System.out.println("Order to the left ==>> ");
		for (int i=n;i>0;--i) {
			System.out.print(head + " - "  + newQueue.get(end%n) + " , ");
			cylinder+=Math.abs(head-newQueue.get(end%n));
			head=newQueue.get(end%n); // 65 , 67 ,....etc to 14.
			end--;
			if (end==-1) {
				end=start;
				cylinder+=Math.abs(head-last);
				System.out.print(head + " - "  + last +  " , ");
				head=0;
				cylinder+=Math.abs(head-last);
			} // end of statement
		} // end of loop
		System.out.println("\nThe final cylinder is : " + cylinder); // seek distance
	} // end of cScan Function.
	
	/**
	 *	cLook scheduler
	 *	@param queue , head
	 *  @author Abdulkhalek
	 **/
	public void cLook(List<Integer>queue , int head) {
		List<Integer> newQueue = new ArrayList<Integer>(queue);
		Collections.sort(newQueue,Collections.reverseOrder());
		List<Integer> paths = Paths(newQueue, head);
		int end=getMinimum(paths),start =getMinimum(newQueue),n = queue.size(),cylinder=0;
		for (int i=n;i>0;--i) {
			System.out.print(head + " - "  + newQueue.get(end%n) + " , ");
			cylinder+=Math.abs(head-newQueue.get(end%n));
			head=newQueue.get(end%n); // 65 , 67 ,....etc to 14.
			end--;
			if (end==-1) {
				end=start;
			} // end of statement
		} // end of loop 
		System.out.println("\nThe final cylinder is : " + cylinder);// seek distance
	} // end of cLook function
	/**
	 *@author norhan  
	 **/
	public void startScan(int head, List<Integer> queue)
    {
        List<Integer>newQueue=new ArrayList<>();
        newQueue.addAll(queue);
        newQueue.add(0);
        Collections.sort(newQueue);
        //print(queue);
        scanAndLook( head, newQueue);
    }
	/**
	 *@author norhan  
	 **/
	 public void scanAndLook(int head, List<Integer> queue)
	    {
	        List<Integer>newQueue=new ArrayList<>();
	        newQueue.addAll(queue);

	        splitQueue( head,  newQueue);
	        int sum=0;
	        System.out.print("Order Of Movements : "+head+"->");
	        for(int i=queueLess.size()-1;i>=0;i--)
	        {
	            System.out.print(queueLess.get(i) + " -> ");

	            if(i==queueLess.size()-1)
	            {
	                sum+=head-queueLess.get(i);
	            }else {
	                sum += abs(queueLess.get(i+1) - queueLess.get(i ));
	            }
	        }
	        for(int i=0;i<queueBig.size();i++) {
	            System.out.print(queueBig.get(i) + " -> ");
	            if (i == 0) {
	                sum +=abs (queueLess.get(0)-queueBig.get(i));
	            } else {
	                sum += abs(queueBig.get(i) - queueBig.get(i-1));
	            }
	        }
	        System.out.print("The end");
	        System.out.println("\nAll movements are done, with cylinder : " +sum); // seek distance.

	    }
	 /**
		 *@author rokia  
		 **/
	 public void splitQueue(int head, List<Integer> queue)
	    {
	        List<Integer> newQueue=new ArrayList<>();
	        newQueue.addAll(queue);
	        this.queueLess=new ArrayList<>();
	        this.queueBig=new ArrayList<>();
	        for(int i=0;i<newQueue.size();i++)
	        {
	            if(newQueue.get(i)<head)
	            {
	                queueLess.add(newQueue.get(i));
	            }
	            else
	            {
	                queueBig.add(newQueue.get(i));
	            }
	        }
	    }

	 /**
		 *@author rokia  
		 **/
	    public void startLook(int head, List<Integer> queue)
	    {
	        List<Integer> newQueue=new ArrayList<>();
	        newQueue.addAll(queue);
	        Collections.sort(newQueue);
	        scanAndLook( head,  newQueue);
	    }
	    void print(List<Integer>queue)
	    {
	        for(int i=0;i<queue.size();i++)
	        {
	            System.out.println(queue.get(i));
	        }
	    }
	    /**
		 *@author norhan & rokia  
		 **/
	    public void optimiseAlgorithm(int head,List<Integer>queue)
	    {
	        List<Integer>newQueue=new ArrayList<>();
	        newQueue.addAll(queue);
	        if(abs(Collections.min(newQueue)-head)>abs(Collections.max(newQueue)-head))
	        {
	            Collections.sort(newQueue, Collections.reverseOrder());
	        }
	        else
	        {
	            Collections.sort(newQueue);
	        }
	        
	        int sum=0;
	        sum+=Math.abs(head-newQueue.get(0));
	        System.out.print("Order Of Movements : " + newQueue.get(0) + " -> ");
	        for(int i=0;i<newQueue.size()-1;i++) {
	            System.out.print(newQueue.get(i+1) + " -> ");
	            sum+=Math.abs(newQueue.get(i)-newQueue.get(i+1));
	            //System.out.print("("+newQueue.get(i+1)+"-");
	           // System.out.print(newQueue.get(i)+") +");
	        }
	        System.out.println("The end");
	        System.out.println("\nAll movements are done, with cylinder : " + sum);
	    }// seek distance.*/



} // end of class
