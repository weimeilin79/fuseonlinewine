package com.redhat;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.salesforce.dto.Product2;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class RemoveProductService implements Processor {

	
	

	@Override
	public void process(Exchange exchange) throws Exception {
		
		LinkedCaseInsensitiveMap bodyproduct = (LinkedCaseInsensitiveMap) exchange.getIn().getBody();
		Product2 product = new Product2();
		
		product.setId(bodyproduct.get("productcode")+"");
		
		exchange.getOut().setBody(product);
		exchange.getOut().setHeader("productcode", bodyproduct.get("productcode")+"");
	}
}
