package dimcho.proj.stopwatch;

public class TimeFormatUtil {
	
	public static String toDisplayString(int timeHundreds){
		int hundreds,seconds,minutes;
		String formatedSeconds,formatedMinutes;
		
		hundreds=timeHundreds%10;
		seconds=(timeHundreds/=10)%60;
		minutes=(timeHundreds/=60)%60;
		
		//format output
		formatedSeconds=Integer.toString(seconds/10)+
				Integer.toString(seconds%10);
		formatedMinutes=Integer.toString(minutes/10)+
				Integer.toString(minutes%10);		
		
		String timeString = 
				formatedMinutes+":"+
				formatedSeconds+"."+
				Integer.toString(hundreds);
		
		
		return timeString;
	}

}
