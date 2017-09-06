package pl.arek.claiming;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;

public class DataProcessing {
	
	//łączna liczba nadgodzin
	float otAmountInSummary = 0;
	
	//nazwy dni w formacie Mon, Tue...
	String day1Name = null;
	String day2Name = null;
	
	//różnica pomiędzy podanymi datami;
	int dayDifference = 0;
	
	//data roczpoczęcia i zakończenia OT yyyy/MM/dd
	String otStartDate = null;
	String otEndDate = null;
	
	//godziny rozpoczęcia OT HH:mm
	String otStartTime = null;
	String otEndTime = null;
	
	//liczba godzin SB
	String sb1Day = null;
	String sb2Day = null;
	
	//sformatowana data startu i końca OT yyyy/
	String fStartDate = null;
	String fEndDate = null;
	
	//formaty dat, które zostaną użyte
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	SimpleDateFormat fDays = new SimpleDateFormat("yyyy/MM/dd");
	DecimalFormat df = new DecimalFormat("#.##");
	
	//daty startu i zakończenia w formacie Date yyyy/MM/dd HH:mm
	Date dStartDate = null;
	Date dEndDate = null;
	
	//daty startu i zakończenia w formacie DAte yyyy/MM/dd
	Date ddStartDate = null;
	Date ddEndDate = null;
	
	//daty startu i zakończenia w formacie DateTime
	DateTime dtStartDate = null;
	DateTime dtEndDate = null;
	DateTime ddtStartDate = null;
	DateTime ddtEndDate = null;
	
	//czasy ses3 i  seot
	float ses30 = 0.00f;
	float seot0 = 0.00f;
	
	//dodatkowe zmienne w przypadku różnicy dnia, żeby policzyć OT dla obu dni 
	float ses31 = 0.00f;
	float seot1 = 0.00f;
	
	//kolekcja z nazwami dni
	final HashMap<Integer,String> weekdayNames = new HashMap<>();
	
	//kolekcja z kodami
	final HashMap<String,String> accCodes = new HashMap<>();
	
	//kod danego klienta
	String cCode = null;
	
	public DataProcessing(String otd, String oed, String ost, String oet, String s1d, String s2d, String aN){
		otStartDate = otd;
		otEndDate = oed;
		otStartTime = ost;
		otEndTime = oet;
		sb1Day = s1d;
		sb2Day = s2d;
		
		//uzyskujemy format yyyy/MM/dd HH:mm w formie String
		fStartDate = otd + " " + ost;
		fEndDate = oed + " " + oet;
		
		//zamieniamy String na Date w odpowiedniej formie
		try{
			
			//yyyy/MM/dd HH:mm
			dStartDate = format.parse(fStartDate);
			dEndDate = format.parse(fEndDate);
			
			//yyyy/MM/dd
			ddStartDate = fDays.parse(fStartDate);
			ddEndDate = fDays.parse(fEndDate);
			
			//DateTime yyyy/MM/dd HH:mm
			dtStartDate = new DateTime(dStartDate);
			dtEndDate = new DateTime(dEndDate);
			
			//DateTime yyyy/MM/dd
			ddtStartDate = new DateTime(ddStartDate);
			ddtEndDate = new DateTime(ddEndDate);
			
			//wypełnia kolekcję tak, żeby na podstawie daty wskazać odpowiednio dzień tygodnia
			weekdayNames.put(1, "Mon");
			weekdayNames.put(2, "Tue");
			weekdayNames.put(3, "Wen");
			weekdayNames.put(4, "Thu");
			weekdayNames.put(5, "Fri");
			weekdayNames.put(6, "Sat");
			weekdayNames.put(7, "Sun");
			
			//wypełnia kolekcję kodami klientów
			accCodes.put("customer#1", "wbbCust1");
			accCodes.put("customer#2", "wbbCust2");
			accCodes.put("customer#3", "wbbCust3");
			accCodes.put("customer#4", "wbbCust4");
			accCodes.put("customer#15", "wbbCust5");
					
			//wylicza różnicę dni pomiędzy końcem a początkiem OT
			this.dayDifference = Days.daysBetween(ddtStartDate, ddtEndDate).getDays();
			
			//wstawia kod dla podanego klienta
			cCode = accCodes.get(aN);
			
			claimDataCount();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	void claimDataCount(){
		
		
		
		//ile jest w sumie minut różnicy pomiędzy podanymi datami
		int minDiff = 0;
		
		//numer dnia tygodnia, pozwoli na wyznaczenie jego nazwy
		int dayOfWeekNo0 = 0;
		int dayOfWeekNo1 = 0;
		
		minDiff = Minutes.minutesBetween(dtStartDate, dtEndDate).getMinutes();
		otAmountInSummary = (float)minDiff / 60;
		System.out.println("Całkowita liczba nadgodzin wynosi: " + otAmountInSummary);
		
		if(dayDifference == 0){
			
			try{
				//data referencyjna potrzebna do wyliczeń yyyy/MM/dd 00:00
				String sRefDate = otStartDate + " 00:00";
				Date sdRefDate = format.parse(sRefDate);
				DateTime sddRefDate = new DateTime(sdRefDate);
				
				//różnica minut między północą a czasem startu
				int iMinDiff0 = 0;
				float fMinDiff0 = 0;
				
				//podaje który to jest dzień tygodnia jako klucz i pobiera nazwę dnia z kolekcji weekdayNames
				dayOfWeekNo0 = dtStartDate.getDayOfWeek();
				day1Name = weekdayNames.get(dayOfWeekNo0);
				
				iMinDiff0 = Minutes.minutesBetween(sddRefDate, dtStartDate).getMinutes();
				fMinDiff0 = (float)iMinDiff0 / 60;
				
				if(Float.parseFloat(sb1Day) < fMinDiff0){
					//jako, że godzina rozpoczęcia OT to czas już poza ramami stanby'a, liczymy tylko SEOT
					seot0 = otAmountInSummary;
				}
				else if(Float.parseFloat(sb1Day) >= fMinDiff0){
					//ses3 jest równe różnicy ilości standby'a i godziny rozpoczęcia OT 
					ses30 = Float.parseFloat(sb1Day) - fMinDiff0;
					seot0 = otAmountInSummary - ses30;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//w przypadku gdy różnica dni wynosi 1
		else if(dayDifference > 0){
			try{
				//data referencyjna potrzebna do wyliczeń yyyy/MM/dd 23:59
				String sRefDate = otStartDate + " 23:59";
				Date sdRefDate = format.parse(sRefDate);
				DateTime sddRefDate = new DateTime(sdRefDate);
				
				//różnica minut między północą na koniec pierwszego dnia a czasem startu
				int iMinDiff0 = 0;
				float fMinDiff0 = 0;
				
				//czas referejcyjny symbolizujący ilość godzin w ciągu doby
				final float dayHoursAmount = 24;
				
				//zmienna pozwalająca wyliczyć ile nadgodzin znajduje się w godzinach standby'a
				float sbHtoMidnight = 0;
				
				//zmienna referencyjna zawierająca liczbę zaplanowanych godzin stanby'a w postaci float
				float sb1DayRef = Float.parseFloat(sb1Day);
				float sb2DayRef = Float.parseFloat(sb2Day);
				
				//podaje który to jest dzień tygodnia jako klucz i pobiera nazwę dnia z kolekcji weekdayNames
				dayOfWeekNo0 = dtStartDate.getDayOfWeek();
				day1Name = weekdayNames.get(dayOfWeekNo0);
				
				iMinDiff0 = Minutes.minutesBetween(dtStartDate, sddRefDate).getMinutes();
				//dodaję brakującą minutę do północy
				iMinDiff0 = iMinDiff0 + 1;
				fMinDiff0 = (float)iMinDiff0 / 60;
								
				sbHtoMidnight = dayHoursAmount - fMinDiff0;
								
				//wyliczenia dla pierwszego dnia
				if(sb1DayRef > sbHtoMidnight){
					ses30 = sb1DayRef - sbHtoMidnight;
					seot0 = fMinDiff0 - ses30;
				}
				else if(sb1DayRef <= sbHtoMidnight){
						seot0 = fMinDiff0;
				}
				
				//ilość nadgodzin drugiego dnia
				float day2OT = 0;
				
				day2OT = otAmountInSummary - (ses30 + seot0);
				
				if(sb2DayRef < day2OT){
					ses31 = sb2DayRef;
					seot1 = day2OT - sb2DayRef;
				}else if(sb2DayRef >= day2OT){
					ses31 = day2OT;
				}
				
				//podaje który to jest dzień tygodnia jako klucz i pobiera nazwę dnia z kolekcji weekdayNames
				dayOfWeekNo1 = dtEndDate.getDayOfWeek();
				day2Name = weekdayNames.get(dayOfWeekNo1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}