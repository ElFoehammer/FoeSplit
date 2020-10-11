package splitter;

import java.sql.Time;

public class Segment {
	
	private static String name;
	private static Time latestRun;
	private static Time goldRun;
	private static Time PBRun;


	public Segment(String nickName, Time latest, Time gold, Time PB) {

		name = nickName;
		latestRun = latest;
		goldRun = gold;
		PBRun = PB;

	}
	
	//Setters
	public static void setName(String nickName) {
		name = nickName;
	}
	
	public static void setLatestRun(Time latest) {
		latestRun = latest;
	}
	
	public static void setGoldRun(Time gold) {
		goldRun = gold;
	}
	
	public static void setPBRun(Time PB) {
		PBRun = PB;
	}
	
	//Getters
	public static String getName() {
		return name;
	}
	
	public static Time getLatestRun() {
		return latestRun;
	}
	
	public static Time getGoldRun() {
		return goldRun;
	}
	
	public static Time getPBRun() {
		return PBRun;
	}
}
