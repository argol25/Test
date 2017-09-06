package pl.arek.claiming.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import pl.arek.claiming.ClaimDAO;
import pl.arek.claiming.domain.ClaimData;
import pl.arek.claiming.dto.ClaimDataDTO;
import pl.arek.claiming.DataProcessing;


@Controller
public class ClaimingController {
	
	@Autowired
	ClaimDAO cdao;
	
	@RequestMapping("/claimdata")
	public String addClaimData(HttpServletRequest request, @ModelAttribute("claimDto") @Valid ClaimDataDTO claimDto, BindingResult result){
		
		if(request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()){
			ClaimData cd = new ClaimData();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			try{
				cd.setStartTime(sdf.parse(claimDto.getStartTime()));
				cd.setEndTime(sdf.parse(claimDto.getEndTime()));
			}catch(Exception e){
				e.printStackTrace();
			}
			
			cd.setAccountName(claimDto.getAccountName());
			cd.setStartHour(claimDto.getStartTime());
			cd.setEndHour(claimDto.getEndTime());
			cd.setSb1Day(claimDto.getSb1Day());
			cd.setSb2Day(claimDto.getSb2Day());
			
			//wstawić tutaj wyliczenie pozostałych danych dla modelu ClaimData
			/*
			 * powiązać model ClaimData z metodą DataProcessing
			 * metodę DataProcessing z kolei powiązać z modelem i kolekcją, która uzupełni wymagane rezultaty
			 */
			
			
			return "/claimdata";
		}
		
		
		return "/claimdata";
	}
	
}
