package com.redhat;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Product2;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class ProductService implements Processor {

	
	

	@Override
	public void process(Exchange exchange) throws Exception {
		
		LinkedCaseInsensitiveMap bodyproduct = (LinkedCaseInsensitiveMap) exchange.getIn().getBody();
		Product2 product = new Product2();
		
		product.setProductCode(bodyproduct.get("ID")+"");
		product.setName((String)bodyproduct.get("WINE"));
		product.setIsActive(true);
		
		
		exchange.getOut().setBody(product);
		exchange.getOut().setHeader("productid", bodyproduct.get("ID"));
		exchange.getOut().setHeader("productprice", bodyproduct.get("PRICE"));
		exchange.getOut().setHeader("pricebookid", exchange.getIn().getHeader("pricebookid"));
		
	}
}
