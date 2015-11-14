package com.nagu;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class RTCDataExtractor {
	public HtmlPage RTCPage;
	
	public void setRTCPage(HtmlPage RTCPage){
		this.RTCPage = RTCPage;
	}
	
	public void extractDataFromPage(){
		HtmlForm form = RTCPage.getFirstByXPath("//form[@id='bookingsForm']");
		HtmlHiddenInput fwTotalServices = form.getInputByName("fwTotalServices");
		HtmlHiddenInput fwTotalSeats = form.getInputByName("fwTotalSeats");
		System.out.println("Total Services available"+fwTotalServices.getValueAttribute());
		System.out.println("Total Seats available"+fwTotalSeats.getValueAttribute());
		List<DomElement>  ServiceNo_list = (List<DomElement>) form.getByXPath("//div[@class='srvceNO']");
		
		for (DomElement domElement : ServiceNo_list) {
			System.out.println("Service No :"+domElement.getTextContent().trim());
		}
		
//		List<DomElement> DepartureTime_list = (List<DomElement>) form.getByXPath("//span[@class='StrtTm']");
//		List<DomElement> ArrivalTime_list = (List<DomElement>) form.getByXPath("//span[@class='ArvTm']");
//		
//		for (DomElement domElement1 : DepartureTime_list) {
//			System.out.println("in for");
//			System.out.println("Departure time : "+domElement1.getTextContent().trim());
//		}
//		
//		for (DomElement domElement2 : ArrivalTime_list) {
//			System.out.println("in 2 for");
//			System.out.println("Arrival time : "+domElement2.getTextContent().trim());
//		}
		
		List<DomElement>  BusType_list = (List<DomElement>) form.getByXPath("//div[@class='col3-left']");
		
		for (DomElement domElement : BusType_list) {
			System.out.println("Bus Type :"+domElement.getTextContent().trim());
		}
		List<DomElement>  SeatAvail_list = (List<DomElement>) form.getByXPath("//div[@class='col4']");
		
		for (DomElement domElement : SeatAvail_list) {
			for(DomElement childElement : domElement.getChildElements()){
				if(childElement.hasAttribute("class") && childElement.getAttribute("class").equals("availCs")){
					System.out.println("Available Seats :"+childElement.getTextContent().trim());
				}else {
					System.out.println(childElement.getTextContent().trim());
				}
			}
		}
//		List<DomElement>  Rate_div = (List<DomElement>) form.getByXPath("//div[@class='col5']");
//		
//		for (DomElement domElement : Rate_div) {
//			for(DomElement childElement : domElement.getChildElements()){
//				//System.out.println("class name : "+childElement.getAttribute("class"));
//				if(childElement.hasAttribute("class") && childElement.getAttribute("class").equals("TickRate rupeeIco")){
//					System.out.println("Fare  :"+childElement.getTextContent().trim());
//				}
//			}
//		}
		List<DomElement>  Rate_span = (List<DomElement>) form.getByXPath("//span[@class='TickRate rupeeIco']");
		for (DomElement domElement : Rate_span) {
			System.out.println("Fare :"+domElement.getTextContent().trim());
		}
	}
}
