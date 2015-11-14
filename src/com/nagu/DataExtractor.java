package com.nagu;

//import javax.faces.component.html.HtmlInputText;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement;

@Path("/extract")
public class DataExtractor {
	@GET
	@Path("/rtc")
	public Response extractData(@QueryParam("from") String from , @QueryParam("to") String to,@QueryParam("date") String date){
		WebClient client = new WebClient(BrowserVersion.CHROME);
		String html = "";
		try{
			HtmlPage page = client.getPage("http://www.apsrtconline.in/oprs-web/");
			
			
			//html = page.asXml();
			
			HtmlForm form = page.getFormByName("bookingsForm");
			
			HtmlHiddenInput  Page_hidden_from = form.getInputByName("startPlaceId");
			HtmlHiddenInput  Page_hidden_to =  form.getInputByName("endPlaceId");
			HtmlTextInput  page_date 		= form.getInputByName("txtJourneyDate");
			HtmlTextInput  page_from 		= form.getInputByName("fromPlaceName");
			HtmlTextInput  page_to 			= form.getInputByName("toPlaceName");
			HtmlHiddenInput  page_ajaxAction 			= form.getInputByName("ajaxAction");
//			HtmlElement newele = (HtmlElement)page.createElement("input");
//			newele.setAttribute("type", "hidden");
//			newele.setAttribute("name", "actionType");
//			newele.setAttribute("value", "bookTickets");
//			form.appendChild(newele);
//			HtmlElement newele1 = (HtmlElement)page.createElement("input");
//			newele1.setAttribute("type", "hidden");
//			newele1.setAttribute("name", "X");
//			newele1.setAttribute("value", "1445456887296");
//			form.appendChild(newele1);
//			HtmlElement newele2 = (HtmlElement)page.createElement("input");
//			newele2.setAttribute("type", "hidden");
//			newele2.setAttribute("name", "parameter1");
//			newele2.setAttribute("value", "1445456887296");
//			form.appendChild(newele2);
//			HtmlElement newele3 = (HtmlElement)page.createElement("input");
//			newele3.setAttribute("type", "hidden");
//			newele3.setAttribute("name", "qryType");
//			newele3.setAttribute("value", "0");
//			form.appendChild(newele3);
			
			
			Page_hidden_from.setValueAttribute("15941");
			Page_hidden_to.setValueAttribute("15881");
			page_from.setValueAttribute("visakapatnam");
			page_to.setValueAttribute("vijayawada");
			page_date.setValueAttribute("23/10/2015");
			page_ajaxAction.setValueAttribute("fw");
			HtmlButtonInput button = form.getInputByName("searchBtn");
			HtmlPage page2 = button.click();
			client.waitForBackgroundJavaScript(2000);
			
			RTCDataExtractor rtcExtractor = new RTCDataExtractor();
			rtcExtractor.setRTCPage(page2);
			rtcExtractor.extractDataFromPage();
			
			
			
			
			
			html = page2.asXml();
			//String script_data = page.executeJavaScript("return jsondata").toString();
			//html += script_data;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return Response.status(200).entity(html).build();
	}
	@GET
	@Path("/redbus")
	public Response getRedBusData(@QueryParam("from")String from,@QueryParam("to")String to,@QueryParam("date")String date){
		WebClient client = new WebClient(BrowserVersion.CHROME);
		String html = "";
		try{
//			client.getOptions().setUseInsecureSSL(true);
//			client.se
			client.getOptions().setJavaScriptEnabled(true);
			client.getOptions().setThrowExceptionOnScriptError(false);
			new InterceptWebConnection(client);
			//client.addRequestHeader("Host", "www.redbus.in");
			//client.addRequestHeader("Referer", "https://www.redbus.in/");
			HtmlPage page = client.getPage("https://www.redbus.in/");
			client.waitForBackgroundJavaScript(10000);
			
			List<DomElement> inputs = (List<DomElement>) page.getByXPath("//input[@class='XXinput']");
			HtmlTextInput source=null,destination=null,date1=null;
			for (DomElement domElement : inputs) {
				System.out.println("Coming  into sourrce");
				if(domElement.getAttribute("id").equals("txtSource")){
					System.out.println("Coming  into sourrce");
					source = (HtmlTextInput) domElement;
				}else if (domElement.getAttribute("id").equals("txtDestination")){
					System.out.println("Coming  into destination");
					destination =(HtmlTextInput) domElement;
				}
			}
			List<DomElement> Cal_inputs = (List<DomElement>) page.getByXPath("//input[@class='XXinput calendar']");
			HtmlElement searchButton = (HtmlElement) page.getFirstByXPath("//button[@class='RB Xbutton']");
			for (DomElement domElement : Cal_inputs) {
				if(domElement.getAttribute("id").equals("txtOnwardCalendar")){
					System.out.println("Coming  into calandar");
					date1 =(HtmlTextInput) domElement;
				}
			}
			source.setValueAttribute("Visakapatnam");
			destination.setValueAttribute("Vijayawada");
			date1.setValueAttribute("27-10-2015");
			client.getOptions().setThrowExceptionOnScriptError(false);
			client.getOptions().setThrowExceptionOnFailingStatusCode(false);
			
			HtmlPage page1 = searchButton.click();
			client.waitForBackgroundJavaScript(2000);
			html = page.asXml();
		}catch(Exception e){
			e.printStackTrace();
		}
		return Response.status(200).entity(html).build();
	}
	@GET
	@Path("/abhbus")
	public Response getAbhiBusData(@QueryParam("from")String from,@QueryParam("to")String to,@QueryParam("date")String date){
		WebClient client = new WebClient(BrowserVersion.CHROME);
		String html = "";
		try{
			HtmlPage abhi_page = client.getPage("http://www.abhibus.com/");
			client.waitForBackgroundJavaScriptStartingBefore(200);
			client.waitForBackgroundJavaScript(1000);
			HtmlForm form = abhi_page.getFormByName("frmFinal");
			HtmlTextInput source = form.getInputByName("source");
			HtmlTextInput destination = form.getInputByName("destination");
			HtmlHiddenInput source_id = form.getInputByName("source_id");
			HtmlHiddenInput destination_id = form.getInputByName("destination_id");
			HtmlTextInput journey_date = form.getInputByName("journey_date");
			
			HtmlElement searchbutton =  form.getFirstByXPath("//a[@class='btnab icosearch']");
			ScriptResult scrp = abhi_page.executeJavaScript("stationslist");
			System.out.println("json data : "+scrp.getJavaScriptResult().toString());
			html = abhi_page.asXml();
		}catch(Exception e){
			e.printStackTrace();
		}
		return Response.status(200).entity(html).build();
	}
	
}
