package pl.arek.claiming.domain;

import java.util.Date;

//klasa zawierająca informacje na temat danych, które muszą być dostarczone przez użytkownika i które użytkownik otrzymuje po przetworzeniu

public class ClaimData {
	
	private String accountName;
	//data rozpoczęcia nadgodzin
	private Date startTime;
	//data zakończenia nadgodzin
	private Date endTime;
	//nazwa klienta, dla którego wykonano nadgodziny
	//godziny rozpoczęcia nadgodzin
	private String stHour;
	
	//godzina zakończenia nadgodzin
	private String endHour;
	
		//ilość godzin standby'a pierwszego dnia
	private String sb1Day;
	//ilość godzin stanby'a drugiego dnia
	private String sb2Day;
	
	private String WBSel;
	private String OType;
	Float sat,sun,mon,tue,wen,thu,fri;
	
	public ClaimData(){
		WBSel = null;
		OType = null;
		
		sat = null;
		sun = null;
		mon = null;
		tue = null;
		wen = null;
		thu = null;
		fri = null;
	}
	
	//gettery i settery
	public void setStartTime(Date stTime){
		this.startTime = stTime;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public void setEndTime(Date eTime){
		this.endTime = eTime;
	}
	
	public Date getEndTime(){
		return endTime;
	}
	
	public void setStartHour(String sh){
		this.stHour = sh;
	}
	
	public String getStartHour(){
		return stHour;
	}
	
	public void setEndHour(String eh){
		this.endHour = eh;
	}
	
	public String getEndHour(){
		return endHour;
	}
	
	public void setAccountName(String aName){
		this.accountName = aName;
	}
	
	public String getAccountName(){
		return accountName;
	}
	
	public void setSb1Day(String sbOn){
		this.sb1Day = sbOn;
	}
	
	public String getSb1Day(){
		return sb1Day;
	}
	
	public void setSb2Day(String sbD){
		this.sb2Day = sbD;
	}
	
	public String getSb2Day(){
		return sb2Day;
	}
	
	public void setWBS(String wbs){
		this.WBSel = wbs;
	}
	
	public String getWBS(){
		return WBSel;		
	}
	
	public void setOtype(String ot){
		this.OType = ot;
	}
	
	public String getOtype(){
		return OType;
	}
	
	public void setMon(float mo){
		this.mon = mo;
	}
	
	public float getMon(){
		return mon;
	}
	
	public void setTue(float tu){
		this.tue = tu;
	}
	
	public float getTue(){
		return tue;
	}
	
	public void setWen(float we){
		this.wen = we;
	}
	
	public float getWen(){
		return wen;
	}
	
	public void setThu(float th){
		this.thu = th;
	}
	
	public float getThu(){
		return thu;
	}
	
	public void setFri(float fr){
		this.fri = fr;
	}
	
	public float getFri(){
		return fri;
	}
	
	public void setSat(float st){
		this.sat = st;
	}
	
	public float getSat(){
		return sat;
	}
	
	public void setSun(float su){
		this.sun = su;
	}
	
	public float getSun(){
		return sun;
	}
}
