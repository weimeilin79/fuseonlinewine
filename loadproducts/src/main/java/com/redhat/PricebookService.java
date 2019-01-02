package com.redhat;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.salesforce.api.dto.CreateSObjectResult;
import org.apache.camel.salesforce.dto.PricebookEntry;

public class PricebookService implements Processor {

	
	

	@Override
	public void process(Exchange exchange) throws Exception {
		CreateSObjectResult bodyproduct = (CreateSObjectResult) exchange.getIn().getBody();
		
		PricebookEntry pricebook = new PricebookEntry();
		
		Double price = Double.valueOf(exchange.getIn().getHeader("productprice")+"");
		String productid =  (String)bodyproduct.getId();
		String pricebookid = (String)exchange.getIn().getHeader("pricebookid");
		
		pricebook.setUnitPrice(price);
		pricebook.setProduct2Id(productid);
		pricebook.setIsActive(true);
		pricebook.setPricebook2Id(pricebookid.substring(0, 15));
		//pricebook.setUseStandardPrice(false);
		
		exchange.getOut().setBody(pricebook);
		exchange.getOut().setHeader("productsfid",productid);
		exchange.getOut().setHeader("productid", exchange.getIn().getHeader("productid"));
		exchange.getOut().setHeader("productprice", exchange.getIn().getHeader("productprice"));
		exchange.getOut().setHeader("pricebookid", exchange.getIn().getHeader("pricebookid"));
		
	}
}
