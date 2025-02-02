package splitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class chronos implements ActionListener {
	
	private int milis;
	private int sec;
	private int min;
	private int hrs;
	private static boolean paused;
	private Timer chronometer;
	
	public chronos(){
		chronometer = new Timer(10, this);
		chronometer.start();
	}
	
	public static void main(String[] args) {
			new chronos();
			while(true) {
				
			}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (!paused) {
		milis++;
		}
			if (milis == 100) {
				milis = 0;
				sec++;
				if(sec == 60) {
					sec = 0;
					min++;
					if(min == 60) {
						min = 0;
						hrs++;
					}
				}
			}
		}
		
		//System.out.println(hrs + ":" + min + ":" + sec + "." + milis );

	
	public int getMilis() {
		return milis;
	}
	
	public int getSec() {
		return sec;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getHrs() {
		return hrs;
	}
	
	public void resetTimer() {
		hrs = 0;
		min = 0;
		sec = 0;
		milis = 0;
	}
	
	public static void pauseTimer() {
		paused = true;
	}
	
	public static void continueTimer() {
		paused = false;
	}
	
}
