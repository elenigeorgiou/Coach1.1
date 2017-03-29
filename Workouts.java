/*
 * Workouts.java
 * 
 * Copyright 2017 Eleni Georgiou <2017egeorgio@mario>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
import java.io.File;
import java.util.Scanner; 
import java.util.Timer; 
import java.lang.Integer;
import java.util.TimerTask;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException; 
import java.io.OutputStreamWriter; 
import java.io.IOException;
//Beginner
//Intermediate
//Advanced
public class Workouts{
	private static File file; 
	public static void main (String[] args) throws IOException{
		createWorkout(15*60, 30, 60); //in seconds
	}
	public static void createWorkout(int total,int on, int off) throws IOException{ // in seconds
		//create a new file
	
		
	       file = new File("workout1.txt");
			 
		    FileOutputStream fos = new FileOutputStream(file);
 
			OutputStreamWriter bw = new OutputStreamWriter(fos);
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write("4,2");
			for(int x = 0;  x<=total/(on*1.0 +off*1.0); x++) {	
				for(int i=0;i<=(on/5); i++){
					bw.write('\n'+"4");
	
					
				}
				for( int i=0; i<=(off/5); i++){
					bw.write('\n'+"2");
						 
				}
				}
					bw.close();
			}

}

