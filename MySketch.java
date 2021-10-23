import processing.core.*;
import processing.video.*;


import javax.sound.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.io.PrintWriter;


import processing.sound.*;
import java.util.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.spi.*;
//import de.voidplus.leapmotion.*;

public class MySketch extends PApplet {


	//	Boolean tutorial = true;

	float yA = 100;
	float yB = 100;
	float yC = 100;
	float yD = 100;

	static int left = Color.green.getRGB();
	static int right = Color.yellow.getRGB();
	static int trackColor = left;
	static int trackColor2 = right;

	static float gap = 20;
	//	LeapMotion leap;
	Thread thread;
	static int master;

	float found = 200;

	Boolean played = false;




	int late;

	Capture video;
	PImage prevFrame;

	static float threshold = 10;

	int early;
	float skip;
	float place;
	float timer;
	float origin;
	String status;
	BeatDetector beat;
	String[]Url;
	float gradient = 0;
	boolean moved = false;
	boolean reset = true;
	boolean checked = false;
	static boolean spotify = true;
	String todo = "";
	float TimeStamp = 45;
	SoundFile song;

	//	boolean autoplay = true;

	float progress;
	AudioMetaData meta;
	int sleep = 2000;
	final int stateNormal = 0;
	final int stateInputBox = 1;
	int state = stateNormal;
	Minim minim;
	static Scanner in = new Scanner(System.in);
	Scanner in2;
	static String name;
	static String type;
	static float difficulty = (float)1.2;
	String download_path = "/Users/philippe/Documents/Processing/libraries/spotify-web-api-java";
	//	String tutorial_song = download_path+"/"+"tutorial.mp3";
	String finalname = "";
	String result = "";
	String path = "mp3";
	File file = new File(download_path);

	File file2;
	File absolute;
	float elapsed;
	float volume = 100;
	float resolution = 50;
	float frames;
	float row = 40;
	float speed = 0;
	float fin;
	float avg = 0;
	boolean done = false;
	boolean done2 = false;
	float[] averages;
	float[] averages2;
	float temp2 = 0;
	float intervall;
	float[][] spectra;
	float z = 0;
	float temp = 20;
	boolean caught = false;
	float bpm;
	float previous;
	float location = 200;
	PVector circlePos;
	PVector centerPos;
	static String send = "Day and Night";
	float number = 0; // combo counter
	int more = 0; // used to change catcher's appearance
	int moreTop = 0;

	// caught
	ArrayList<ParticleSystem> systems;

	//	AudioInputStream a;
	//


	Catcher catcher; // One catcher object
	Catcher catcherTop;
	Catcher time;
	float x = 0;
	float[] positions = new float[30000];

	float angleStep = TWO_PI / resolution;
	processing.sound.FFT fft;
	int bands = 5096;
	float r = (float) (height * 0.5); // used for the audio visualizer
	float[] spectrum = new float[bands];

	//	Dashboard box = new Dashboard();

	public void settings() {

		size(1000, 1000);
//		fullScreen();
		trackColor = color(0, 0, 255);
		trackColor2 = color(255, 255, 0);

		//				  fullScreen();
	}
	public static String ask() {
		//		if (reset == true) {
		//			reset = false;
		return send;
	}
	public static void receive(int c, String song, float diff, boolean fate, float sensitivity, float speed, int l, int r) {
		difficulty = diff;
		send = song;
		spotify = fate;
		master = c;
		threshold = sensitivity;
		gap = speed;
		trackColor = l;
		trackColor2 = r;
		//		if (reset == true) {
		//			reset = false;
	}
	public void captureEvent(Capture video) {
		// Save previous frame for motion detection!!
		prevFrame.copy(video, 0, 0, video.width, video.height, 0, 0, video.width, video.height);
		prevFrame.updatePixels();
		video.read();
	}



	public void setup() {

		//		if(tutorial==false) {
		//		System.out.println("Please type in your difficulty. (easy, normal, hard)");
		//		name = in.nextLine();
		//		if (name.indexOf(0) == 'e')

		//			difficulty = (float) 1.3;
		//		else if (name.indexOf(0) == 'h')
		//			difficulty = 1;
		//		else
		//			difficulty = (float) 1.2;
		//		System.out.println("Difficulty set to " + difficulty);
		//		System.out.println("Youtube or Spotify?");
		//		name = in.nextLine();
		//		if (name.equals("Spotify")) 
		//			spotify = true;
		//
		//		else
		//			spotify = false;
		//		System.out.println("Please type in your song.");
		//		send = in.nextLine();
		String[] cameras = Capture.list();

		if (cameras == null) {
			println("Failed to retrieve the list of available cameras, will try the default...");
			video = new Capture(this, width, height);
		} else if (cameras.length == 0) {
			println("There are no cameras available for capture.");
			exit();
		} else {
			println("Available cameras:");
			printArray(cameras);

	

			if(cameras[0].equals("FaceTime HD Camera (Built-in)"))
				video = new Capture(this, cameras[1]);
			else
				video = new Capture(this, cameras[0]);
					
//		    video = new Capture(this, "Built-in iSight");
			// Or, the settings can be defined based on the text in the list
			//cam = new Capture(this, 640, 480, "Built-in iSight", 30);

			// Start capturing the images from the camera
			video.start();
		}
		//
		//		video = new Capture(this, width, height);
		//		video.start();
		// Create an empty image the same size as the video
		prevFrame = createImage(video.width, video.height, RGB);

		if(spotify==true) {
			System.out.println("Downloading "+send+" from Spotify...");
		}






		if(spotify==false) {
			System.out.println("Searching Youtube for " + send + "...");

			for (int i = 0; i < send.length(); i++) {
				char c = send.charAt(i);
				if (c != ' ') {
					result += c;
				}

			}
			send = result;
		}
		String[] command =

			{ "sh", };

		Process p;
		if (spotify == false) {
			try {
				p = Runtime.getRuntime().exec(command);
				new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
				new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
				PrintWriter stdin = new PrintWriter(p.getOutputStream());

				stdin.println("cd \"" + download_path + "\"");
				//		                if(reset==true) {

				stdin.println(download_path
						+ "\\/youtube-dl --match-filter \"duration < 600 \" --max-downloads 2 \"ytsearch\":"
						+ send + " --ffmpeg-location /usr/local/Cellar/ffmpeg/4.4_2/bin/ffmpeg"
						+ " -x --audio-format " + path
						+ " -f 'bestaudio[ext=m4a]/mp3'");
				
				//							+ " --hls-prefer-ffmpeg");
				//							+ " -f \"mp4\"");
				//							+ " ffmpeg -i ");
				//							+" -f bestvideo+bestaudio");
				//							+" -f bestvideo,bestaudio -o '%titles-%format_id)s.%(ext)s'");
				//							+" -f best");
				//							+" -f 22");
				//							+" -F");
				//							);

				stdin.close();
				p.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else {
			Url = SearchTracksExample.searchItem_Sync(send);
			try {
				p = Runtime.getRuntime().exec(command);
				new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
				new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
				PrintWriter stdin = new PrintWriter(p.getOutputStream());
				stdin.println("export SPOTIPY_CLIENT_ID=2148b8ad70a346f79d855789069ddba5"); //credentials
				stdin.println("export SPOTIPY_CLIENT_SECRET=c8ba8c74ac5a4257b8ed404c23fd197e");
				stdin.println("export PATH=\"/usr/local/bin:$PATH\""); //ffmpeg location
				stdin.println("cd \"" + download_path + "\"");
				//			                stdin.print("ffmpeg -version");
				stdin.println(download_path + "\\/spotify_dl -l https://" + Url[1] + " -o " + download_path
						+ " -s yes -f 'bestaudio[ext=m4a]/mp3'");



				stdin.close();
				p.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		circlePos = new PVector(width / 2, height / 2);
		centerPos = new PVector(location, height / 2);
		systems = new ArrayList<ParticleSystem>();
		catcher = new Catcher(50); // Create the catcher with a radius of 32
		catcherTop = new Catcher(50);
		time = new Catcher(25);
		minim = new Minim(this);

		analyzeUsingAudioSample(); //defines finalname

		intervall = (float) (2 * r * sin(angleStep / 2));

		beat = new BeatDetector(this);

		if(spotify==false)

			song = new SoundFile(this, finalname);

		fft = new processing.sound.FFT(this);
		fft.input(song);
//		song.amp((float)1.4);
		beat.input(song);

	}



	void progressBar(float w, float h) {

		noFill();
		stroke(0,0,100);
		strokeWeight(4);
		line(location,h,w,h);
//		text(song.duration(),w,h);


	}

	void visualizeSpectrum() {


//		stroke(master);
		noStroke();
		fft.analyze();
		for (int i = 0; i < resolution; i++) {	
			// float move = map(song.position(), 0, song.duration(), 200, width - 200);
			float scale = map(fft.spectrum[i], 0, 1, 0, 200); // bars change size based on the combo counter
			float angle = map(i, 0, resolution, 0, TWO_PI);
			float now = map(number, 100, 0, 0, 360); // color changes based on the combo counter
			float y = 200 * sin(angle - PI / 2);
			float x = 200 * cos(angle - PI / 2);

			// text(song.position(), move - 20, location);

			pushMatrix();
			translate(width / 2 + x, height / 2 + y);
			rotate(angle);
			// fill(255, 0, now);
			fill(255, 127);

			rect(-intervall / 2, -scale, intervall, scale);
			popMatrix();

		}

	}

	void analyzeUsingAudioSample() {




		File file = new File(download_path);
		String[] paths;
		paths = file.list();


		if(spotify==false) {
			for (String two : paths) {
				if (two.contains("." + path)&&two.contains("tutorial")==false) {


					finalname = two;

				}
			}
			absolute = new File(download_path + "/" + finalname);
		}

		else
			if(spotify==false) {
				for (String two : paths) {
					if (two.contains("." + path)) {


						finalname = two;

					}
				}
				absolute = new File(download_path + "/" + finalname);
			}

			else {

				//		download_path=("/Users/philippe/Documents/Processing/libraries/spotify-web-api-java/"+Url[0]);
				download_path+="/"+Url[0];

				file2 = new File(download_path);
				file2.deleteOnExit();
				//		System.out.println(download_path);

				file = new File(download_path);
				//		file.deleteOnExit();
				paths = file.list();
				for (String two : paths) {

					if(two.contains(".txt")==false)
						finalname = two;


				}
				//		absolute = new File(Url[0]+"/"+download_path+".mp3");
				absolute = new File(download_path+"/"+finalname);
				file2 = new File(download_path+"/downloaded_songs.txt");
				file2.delete();
			}

		String filename = absolute.getAbsolutePath();
		if(spotify==true)
			song = new SoundFile(this,filename);

		absolute = new File(download_path + "/" + finalname);
		absolute.deleteOnExit();

		//		System.out.println(absolute);

		ddf.minim.AudioSample jingle = minim.loadSample(filename, 2048);
		played=false;
		meta = jingle.getMetaData();
		frames = jingle.length();
		// get the left channel of the audio as a float array
		// getChannel is defined in the interface BuffereAudio,
		// which also defines two constants to use as an argument
		// BufferedAudio.LEFT and BufferedAudio.RIGHT
		float[] leftChannel = jingle.getChannel(ddf.minim.AudioSample.LEFT);

		// then we create an array we'll copy sample data into for the FFT object
		// this should be as large as you want your FFT to be. generally speaking, 1024
		// is probably fine.
		int fftSize = 1024;
		float[] fftSamples = new float[fftSize];
		ddf.minim.analysis.FFT fft = new ddf.minim.analysis.FFT(fftSize, jingle.sampleRate());

		// now we'll analyze the samples in chunks
		int totalChunks = (leftChannel.length / fftSize);

		// allocate a 2-dimentional array that will hold all of the spectrum data for
		// all of the chunks.
		// the second dimension if fftSize/2 because the spectrum size is always half
		// the number of samples analyzed.
		spectra = new float[totalChunks][fftSize / 2];
		averages = new float[spectra.length];

		for (int chunkIdx = 0; chunkIdx < totalChunks; ++chunkIdx) {
			int chunkStartIndex = chunkIdx * fftSize;

			// the chunk size will always be fftSize, except for the
			// last chunk, which will be however many samples are left in source
			int chunkSize = min(leftChannel.length - chunkStartIndex, fftSize);

			// copy first chunk into our analysis array
			System.arraycopy(leftChannel, // source of the copy
					chunkStartIndex, // index to start in the source
					fftSamples, // destination of the copy
					0, // index to copy to
					chunkSize // how many samples to copy
					);

			// if the chunk was smaller than the fftSize, we need to pad the analysis buffer
			// with zeroes
			if (chunkSize < fftSize) {
				// we use a system call for this
				Arrays.fill((float[]) fftSamples, chunkSize, fftSamples.length - 1, (float) 0.0);
			}

			// now analyze this buffer
			fft.forward(fftSamples);
			// and copy the resulting spectrum into our spectra array
			for (int i = 0; i < 512; i++) {
				spectra[chunkIdx][i] = fft.getBand(i);

			}
		}

		for (int s = 0; s < spectra.length; s++) {
			int i = 0;
			float total = 0;
			for (i = 0; i < spectra[s].length; i++) {
				if (spectra[s][i] != 0)
					total += spectra[s][i]; // current volume value

			}
			total = total / 10;
			if(total==0)
				skip++;
			averages[s] = total;

			avg += total;

			// if(total>0 && done ==false){

			// song.cue(s/60);
			// done = true;
			// }
		}
		origin = avg / (spectra.length-skip);
		fin = origin;
		text(origin, location, location);

		// Checking whether the first point is
		// local maxima or minima or none

		//if (averages[0] > averages[1])
		//mx.add(0);
		//
		//else if (averages[0] < averages[1])
		//mn.add(0);
		//
		//// Iterating over all points to check
		//// local maxima and local minima
		//for(int i = 1; i < averages.length - 1; i++)
		//{
		//// Condition for local minima
		//if ((averages[i - 1] > averages[i]) &&
		//(averages[i] < averages[i + 1]))
		//mn.add(i);
		//
		//// Condition for local maxima
		//else if ((averages[i - 1] < averages[i]) &&
		//(averages[i] > averages[i + 1]))
		//mx.add(i);
		//}
		//
		//// Checking whether the last point is
		//// local maxima or minima or none
		//if (averages[averages.length - 1] > averages[averages.length - 2])
		//mx.add(spectra.length - 1);
		//
		//else if (averages[averages.length - 1] < averages[averages.length-2])
		//mn.add(averages.length - 1);

	}


	public void draw() {

		saber L = new saber();
		saber R = new saber();

		// background(gradient,0,0);


		
		if (frameCount < 20) // if background is black
			elapsed = millis();

		speed = 1f * (millis() - elapsed) * gap*.043f; // changeable speed!

		background(master);
		

		text("Hit: "+ (int)number,width/2+200,height/2+200);
		textSize(more/2+20);
		//


		float worldRecord = 500;
		float worldRecord2 = 500;

		float angle1 = 0;
		float angle2 = 0;

		float segLength = 50;

		// XY coordinate of closest color
		int closestX = 0;
		//		  int previousX = 0;
		//		  int previousX2 = 0;
		int closestY = 0;
		//		  int previousY = 0;
		//		  int previousY2 = 0;
		int closestX2 = 0;
		int closestY2 = 0;
		//		  int totalMotion = 0;
		//		  int totalMotion2 = 0;
		float dy = closestY - yA;

		video.loadPixels();
		// Begin loop to walk through every pixel
		for (int x = 0; x < video.width; x ++ ) {
			for (int y = 0; y < video.height; y ++ ) {
				int loc = x + y*video.width;
				// What is current color
				int currentColor = video.pixels[loc];
				float r1 = red(currentColor);
				float g1 = green(currentColor);
				float b1 = blue(currentColor);
				float r2 = red(trackColor);
				float g2 = green(trackColor);
				float b2 = blue(trackColor);
				//		      float r3 = red(trackColor2);
				//		      float g3 = green(trackColor2);
				//		      float b3 = blue(trackColor2);


				// Using euclidean distance to compare colors
				float d = dist(r1, g1, b1, r2, g2, b2); // We are using the dist( ) function to compare the current color with the color we are tracking.
				//		      totalMotion+=d;
				//		      angle1 = atan2(dy, 0);  
				//		      
				//		      yA = dy - (sin(angle1) * segLength);

				//		      float d2 = dist(r1, g1, b1, r3, g3, b3);
				// If current color is more similar to tracked color than
				// closest color, save current location and current difference
				if (d < worldRecord) {
					worldRecord = d;
					closestX = x;
					closestY = y;
				}
				//		      if (d2 < worldRecord) {
				//			        worldRecord = d2;
				//			        closestX2 = x;
				//			        closestY2 = y;
				//			      }

			}
		}
		float dy2 = closestY2 - yB;
		for (int x2 = 0; x2 < video.width; x2 ++ ) {
			for (int y2 = 0; y2 < video.height; y2 ++ ) {
				int loc2 = x2 + y2*video.width;
				// What is current color
				int currentColor2 = video.pixels[loc2];
				float r3 = red(currentColor2);
				float g3 = green(currentColor2);
				float b3 = blue(currentColor2);
				float r4 = red(trackColor2);
				float g4 = green(trackColor2);
				float b4 = blue(trackColor2);
				//			      float r3 = red(trackColor2);
				//			      float g3 = green(trackColor2);
				//			      float b3 = blue(trackColor2);


				// Using euclidean distance to compare colors
				//			      float d = dist(r1, g1, b1, r2, g2, b2); // We are using the dist( ) function to compare the current color with the color we are tracking.
				float d2 = dist(r3, g3, b3, r4, g4, b4);
				//			      angle2 = atan2(dy2, 0);  
				//			      
				//			      yB = dy2 - (sin(angle2) * segLength);
				//			      totalMotion2+=d2;
				// If current color is more similar to tracked color than
				// closest color, save current location and current difference
				//			      if (d < worldRecord) {
				//			        worldRecord = d;
				//			        closestX = x;
				//			        closestY = y;
				//			      }
				if (d2 < worldRecord) {
					worldRecord = d2;
					closestX2 = x2;
					closestY2 = y2;
				}

			}
		}

		// We only consider the color found if its color distance is less than 10. 
		// This threshold of 10 is arbitrary and you can adjust this number depending on how accurate you require the tracking to be.
		if (worldRecord2 < found || worldRecord < found) { 

			// Draw a circle at the tracked pixel
			// Draw a circle at the tracked pixel
//			pushMatrix();
//			scale((float) .5);
//			translate(width, (float) (height*1.5));
//			pushMatrix();
//			translate(-250,0);
//			//			L.display(trackColor,width/2,height-200);
//
//			//			L.display(left, closestX, closestY);
//			L.display(trackColor, width/2+500, closestY);
//			popMatrix();
//			pushMatrix();
//			translate(250,0);
//			//			R.display(right, closestX2, closestY2);
//			R.display(trackColor2, width/2-200, closestY2);
//			//			R.display(trackColor2,width/2,height-200);
//			popMatrix();
//			popMatrix();

			strokeWeight(4);
			stroke(0);
			pushMatrix();

			scale((float) 0.25);
			translate((float) (2* width), 0);
			fill(trackColor2);

			ellipse(width/2+200,closestY2,80,80);
			
			fill(trackColor);

			ellipse(width/2-200,closestY,80,80);
			popMatrix();

			//		    previousX2 = closestX2;
			//		    previousY2 = closestY2;
		}

		dy = closestY - yA;

		angle1 = atan2(dy, 0);  

		yA = closestY - (sin(angle1) * segLength);

		yC = dy - (sin(angle1) * segLength);


		dy2 = closestY - yB;

		angle2 = atan2(dy2, 0);  

		yB = closestY2 - (sin(angle2) * segLength);

		yD = dy2 - (sin(angle2) * segLength);
		
//		int amt = 2;
//
//		if (yC > threshold)
//			L.swing(amt);
//		
//
//		if (yD > threshold)
//			R.swing(-amt);
	

		video.loadPixels();
		prevFrame.loadPixels();

		translate(width/2,height/2);
		rotate(radians(-90)); //rotate based on how many times draw() has been called
		pushMatrix();
		translate(-width/2,-height/2); //translate to center of the screen


		catcher.setLocation(location, height / 2 + row); // bottom
		catcher.display(more, 100);
		catcherTop.setLocation(location, height / 2 - row); // top
		catcherTop.display(moreTop, 200);
		//		
		//		if(tutorial==false)
		visualizeSpectrum();

		//		if(tutorial==true)
		//			buttons.display();

		for (int s = 0; s < spectra.length; s++) {

			if (averages[s] > 0 && averages[s]<fin*difficulty) {
				//			positions[s]=(2*width)+(s*20);
				//	        circlePos.x=(positions[s]-speed);
				//            ellipse(circlePos.x,circlePos.y, 20, 20);
				//			fill(0);
				//				fin--;
				if(done2==false) 
					song.cue((float) (s / (spectra.length / song.duration()))); // foolproof cue algorithm
			}

			if (averages[s] > fin * difficulty) {
				//	    if(s!=0 && s!=spectra.length)
				//		if((averages[s - 1] < averages[s]) &&
				//				(averages[s] > averages[s + 1])) {

				if (done2 == false) {
					done2 = true;
					System.out.println("Cueing to " + (float) (s / (spectra.length / song.duration())) + " seconds ("
							+ s + " frames) out of " + song.duration() + " seconds or " + spectra.length + " frames.");
					//
				}

				if (s != 0)
					previous = positions[s - 1];

				positions[s] = (2 * width) + (s * gap); // make a beat for it;

				circlePos.x = (positions[s] - speed);
				//				fill(master);

				if (circlePos.x <= location)
					if (song.isPlaying() == false) {
						//						if(spotify==false)
						//							song.rate((float) 1.1);

						song.play();

					}

				if (dist(previous, 0, positions[s], 0) <= gap) { // combines adjacent circles
					temp+=gap;
					fin++;
				} else {

					if (temp >= gap && temp <= 100) {
						moved = true;
					}

					circlePos.x -= temp;
					noStroke();
					
					if (moved == true) {
						circlePos.y = height / 2 + row;
						//	            centerPos.y=40;
						fill(right,circlePos.x-60);
						ellipse(circlePos.x, circlePos.y, 20, 20);
				
					} else {
						circlePos.y = height / 2 - row;
						//	            centerPos.y=-40;
						
						fill(left, circlePos.x-60);
						ellipse(circlePos.x, circlePos.y, 20, 20);
				
					}
					if (temp != 0) {
						noStroke();
						if (moved == true) {
							rect((circlePos.x) - previous, circlePos.y - 10, temp, 20);
						} else {
							rect((circlePos.x) - previous, circlePos.y - 10, temp, 20);
							noStroke();
						}
						if (moved == true) {
							ellipse(circlePos.x + temp, circlePos.y, 20, 20);
						} else {
							ellipse(circlePos.x + temp, circlePos.y, 20, 20);
						}

						temp = 0;
						moved = false;
						fin = origin;

					}

				}

				checked = false;

				if (keyPressed || yD > threshold) {

					//				if (avgMotion > threshold) {

					if (catcher.intersect(circlePos) < 80 && catcher.intersect(circlePos) > 20 && keyPressed) {
						checked = true;
						early += 1;
						status = "EARLY";

						if (catcher.intersect(circlePos) < -40) {
							status = "LATE";
						}
					} 
					else {
						//						number++;
						status = "Hit!";
					}

					// if(caught == true) //leap motion

					// caught = false; //leap motion

					if (catcher.intersect(circlePos) <= 20 || checked == true) {

						if (more < 18) {
							//	        systems.add(new ParticleSystem(1, row, new PVector(50, 50)));
							systems.add(new ParticleSystem(1, row, new PVector(circlePos.x, circlePos.y)));
							number++;
							more = 20;
						}
					}

					checked = false;


				}

				if (keyPressed || yC > threshold) {

					// caught = false; //leap motion
					if (catcherTop.intersect(circlePos) <= 20 || checked == true) {

						if (moreTop < 18) {
							//	        systems.add(new ParticleSystem(1, -row, new PVector(50, 50)));
							systems.add(new ParticleSystem(1, -row, new PVector(circlePos.x, circlePos.y)));
							number++;
							moreTop = 20;
						}
					}

					if (systems.size() > 5) {
						systems.remove(0); // helps reduce clutter
					}
				}
			}

		}
		//	    else
		//	    	if(done2==true)
		//	    		fin--;



		more -= 0.5;
		moreTop -= 0.5;

		for (ParticleSystem ps : systems) {
			ps.run();
			ps.addParticle();
		}
		popMatrix();
	}

	// New variable to keep track of total number of drops we want to use!

	class ParticleSystem {

		ArrayList<Particle> particles; // An arraylist for all the particles
		PVector origin; // An origin point for where particles are birthed

		ParticleSystem(int num, float row, PVector v) {
			particles = new ArrayList<Particle>(); // Initialize the arraylist
			origin = v.copy(); // Store the origin point
			for (int i = 0; i < num; i++) {
				particles.add(new Particle(origin, row)); // Add "num" amount of particles to the arraylist
			}
		}

		void run() {

			Particle p = particles.get(0);
			p.run();

			// if (p.isDead()) {
			// particles.remove(0);
			// }
		}

		void addParticle() {
			Particle p;
			// Add either a Particle or CrazyParticle to the system

			p = new Particle(origin, row);

			particles.add(p);
		}

		void addParticle(Particle p) {
			particles.add(p);
		}

		// A method to test if the particle system still has particles
		boolean dead() {
			return particles.isEmpty();
		}
	}

	// A subclass of Particle

	// A simple Particle class



	class Particle {
		PVector position;
		PVector velocity;
		PVector acceleration;
		float row2;
		float lifespan;

		Particle(PVector l, float ro) {
			velocity = new PVector(0, (ro / 8));
			position = l.copy();
			lifespan = 50.0f;
			//	    row2=ro;

		}

		void run() {
			update();
			display(status);
		}

		// Method to update position
		void update() {
			position.add(velocity);
			lifespan -= 2.0f;
		}

		// Method to display
		void display(String stat) {
			fill(master * lifespan, lifespan, 100 * lifespan);

			//	    ellipse(location, height/2+row2, position.x, position.y);
			text(stat, position.x, position.y, lifespan);

		}

		// Is the particle still useful?
		boolean isDead() {
			return (lifespan < 0.0f);
		}
	}
	class saber {

		float a = 0;
		float a2 = 0;
		float mx2, my2;

		void display(int side, float x, float y) {

			mouseX = (int) x;
			mouseY = (int) y;


			float dx = mouseX-pmouseX;  

			a-=(dx/2);
			a/=8;

			a2+=(a-a2)/16;
			float x2 = sin(a2);
			float y2 = cos(a2);


			mx2+=(mouseX-mx2)/4;
			//			mx2 = constrain(mx2,0,xBound);
			my2+=(mouseY-my2)/4;
			//			my2 = constrain(my2,height-200, height/2);

			handle(mx2, my2, mx2, my2+100);
			lazer(side,mx2, my2, mx2, my2-400);

		}

		void handle(float x1, float y1, float x2, float y2) {

			stroke(50);

			for (float i = 1; i< 7; i++) {
				if (i==3) {
					fill(140, 0, 0);
					strokeWeight(6);
				} else {
					strokeWeight(4);
					fill(80);
				}
				ellipse(lerp(x1, x2, i/7), lerp(y1, y2, i/7), 21, 21);
			}
		}

		float t = 0;
		void lazer(int side2, float x1, float y1, float x2, float y2) {
			t+=0.1;
			for (float n = 40; n>1; n--) {
				float m = n/40;
				//m*=m;
				m = 1- m;
				m*=m;
				stroke(lerpColor(side2, Color.white.getRGB(), m));
				strokeWeight(n);
				line(x1, y1, x2, y2);
			}
		}

		void swing(int amt) {
			a2=amt;
		}
	}


	class Catcher {
		float r; // radius
		float x, y;

		Catcher(float tempR) {
			r = tempR;
		}

		void setLocation(float tempX, float tempY) {
			x = tempX;
			y = tempY;
		}

		void display(int more, int location) {
			stroke(master);
			if(location<200)
				fill(trackColor2);
			else
				fill(trackColor);
			ellipse(x, y, more + 50, more + 50);

			//	    text(number,x-5,y);
			//	    text(number,x-5,y-50);
		}

		// A function that returns true or false based on
		// if the catcher intersects a raindrop
		float intersect(PVector d) {
			// Calculate distance
			float distance = dist(x, y, d.x, d.y);

			// Compare distance to sum of radii

			return distance;

		}
	}

	public static void main(String[] args) {

		PApplet.main(new String[] { MySketch.class.getName() });
		//		PApplet.main(new String[] {Dashboard.class.getName()});

		//
	}
}
