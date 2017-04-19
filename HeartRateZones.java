/*
 * HeartRateZones.java
 * 
 * Copyright 2017 Eleni Georgiou <2017egeorgio@mario>
 * 

 */

import java.io.File;
import java.util.Scanner; 
import java.util.Timer; 
import java.lang.*;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException; 
import java.io.OutputStreamWriter; 
import java.io.IOException;
import java.util.TimerTask;
import java.io.FileNotFoundException; 
import java.util.ArrayList; 
public class HeartRateZones {
	private static int maxHeartRate;
	private static File file;
	private static int yes, low,high; 
	private static int time; 
	private static String[] levels;
	private static ArrayList<Integer> workoutArray= new ArrayList<Integer>(); ; // every 5 seconds, has what zone to be in 
	private static Scanner scan, data;
	private static OutputStreamWriter bw ;
	public static void main (String[] args) {
		yes = 0;
		low = 0;
		high = 0;
		System.out.println("Welcome. Enter age");
		scan = new Scanner(System.in); 
		setMaxHeartRate(18 );//Integer.parseInt(scan.nextLine())); // sets max heart rate
		assignZones();
		//System.out.println("ASSIGNED ZONES"); 
	//	System.out.println(workoutArray);
		System.out.println(); 
		file = new File("data.txt"); 
		try{
		data = new Scanner(file); //takes in sample heart rate data from every 5 seconds
		}
		catch(FileNotFoundException e)
		{
		System.out.println("File not found.");
		}
		Timer timer = new Timer();
		time = 0; 
	//	System.out.print(levels);
		timer.schedule(new TimerTask() {
			public void run(){ 
				//int time = 0;
				System.out.println("Time interval in seconds " + time* 5); //prints out time interval
			
				if(data.hasNext())
				{
					String d = data.nextLine();
					int current = whatZone(Integer.parseInt(d));
					//System.out.println(current); // just states what zone 
					if(current> zone(time)) //time is every five seconds
							{
							System.out.println("Slow down");
						//bw.write('\n'+ "Slow Down");
							high++;
					    	}
							//is not in zone ... 
					else if(current< zone(time))
					{
							System.out.println("Speed up!");
							low++; 
							//	bw.write('\n'+ "Speed Up!");
						}
					else if (current == zone(time))
					{
						yes++;
						System.out.println("Steady here");
						//	bw.write('\n'+ "Steady Here");
						 }
				}
				else
				{
					timer.cancel();
					//System.out.println("So how fit are you?"); 
					double[] p = percentageAchieved();
					System.out.println(p[0] + "; "+ p[1] + ";  "+ p[2]); //percentageAchieved()); 
					if(p[0]>90 || p[2]<20)
						System.out.println("I think you should change your workout to be harder");
					else if(p[2]>90)
						System.out.println("Workout way too hard for you");
					/* FINISH*/
				}
				time++;
				//takes in 
			}}, 0, 1000); //change to 5 seconds for real time
			//make this wait until timer is done, reading the interval 
			
		
	}
	public static double[] percentageAchieved() // .98 ie
	{
		/* measures how good you are at keeping it in range. however, you need to figure out 
		 * how fast you decrease heart rate during rest period. 
		 * above a certain percentage, maybe recommend a faster program
		 * */
		double[] percent = {yes*100/(yes+low+high*1.0), low*100.0/(yes+low+high), high*100.0/(yes+low+high)}; //times in zone, times too low, times too high
		
		return percent; 
	}
	public static void setMaxHeartRate(int age) // sets max heart rate based on age
	{
		maxHeartRate = 220-age; 
	}
	public static void assignZones() //assign zones to time 
	{
		//int num = chooseWorkout();
		String filename = "workout1.txt";
		try{
			File file = new File(filename);
			Scanner workoutFile = new Scanner(file); 
			String i = workoutFile.nextLine();
			levels = i.split(",");
		
		//workoutArray = 
		while(workoutFile.hasNextLine())
		{
			String temp = workoutFile.nextLine();
			
		//	String[] a = temp.split("");
			//int num1 = Integer.parseInt(a[0]);
		//	System.out.println(workoutFile.nextLine());
			workoutArray.add(Integer.parseInt(temp)); // indices is every 5 seconds
			
		//	System.out.println("step 1 ");
		} 
		}
	 catch (Exception ex) {
            ex.printStackTrace();
        }
		
		return; 
	}
	public static int chooseWorkout() // chooseworkout from list of possible
	{
		System.out.println("What workout would you like to do?");
		System.out.println("Enter 1: 14 minutes, HITT: 30 seconds on, 1 minute off");
		String x = scan.next();
		System.out.println("X:   "+x); 
		
		int number = Integer.parseInt(x); //choose a list of options - enter 1,2,3
		return number;//get workout, arrayList
		
	}
	public static int zone(int t)
	{
		int y = workoutArray.get(t); // zone in which the given time interval should be in 
		//System.out.println("Zone:  "+y);
		return y; 
	}
	public static int whatZone(int beat){
		double percent = beat*1.00/maxHeartRate;
		if(percent >= .97)
			return 6; //too high
		else if(percent>= .9) // great for developing anaerobic capacity 
			return 5;
		else if(percent >= .8) // hard, sustainable, maintaining anaerobic capacity
			return 4;
		else if(percent >= .7) // improvemnets in aerobic capacity
			return 3;
		else if(percent >= .6) // average, maintain convo
			return 2;
		else if (percent >= .5) // Comfortable effort. Warm up, cooldown, recovery between high intense
			return 1;
		else
			return 0; // you are breathing really low.

	}
	//every 5 seconds call check--- take in data from input 
}

