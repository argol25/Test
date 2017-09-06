package pl.arek.claiming.dto;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class ClaimDataDTO {
	
		//nazwa klienta, dla którego wykonano nadgodziny
		@NotBlank
		private String accountName;

		//data rozpoczęcia nadgodzin
		@Pattern(regexp="[1-2][0-9]{3}\\/[0-1]?[0-9]\\/[0-3]?[0-9]")
		private String startTime;
		
		//data zakończenia nadgodzin
		@Pattern(regexp="[1-2][0-9]{3}\\/[0-1]?[0-9]\\/[0-3]?[0-9]")
		private String endTime;
		
		//godziny rozpoczęcia nadgodzin
		@NotBlank
		private String stHour;
		
		//godzina zakończenia nadgodzin
		@NotBlank
		private String endHour;
		
		@NotBlank
		//ilość godzin standby'a pierwszego dnia
		private String sb1Day;
		
		@NotBlank
		//ilość godzin stanby'a drugiego dnia
		private String sb2Day;
		
		private String WBSel;
		private String OType;
		Float sat,sun,mon,tue,wen,thu,fri;
		
		//gettery i settery
		public void setStartTime(String stTime){
			this.startTime = stTime;
		}
		
		public String getStartTime(){
			return startTime;
		}
		
		public void setEndTime(String eTime){
			this.endTime = eTime;
		}
		
		public String getEndTime(){
			return endTime;
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
