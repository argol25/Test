package pl.arek.claiming;

//klasa będąca repozytorium danych użytych przy wprowadzaniu i wykorzystaniu danych

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import pl.arek.claiming.domain.ClaimData;

@Repository
public class ClaimDAO {
	List<ClaimData> clData = new ArrayList<ClaimData>();

	//dodajemy tylko dane od użytkownika
	public void addBasicData(ClaimData data){
		clData.add(data);
	}
	
	
	
	
}
