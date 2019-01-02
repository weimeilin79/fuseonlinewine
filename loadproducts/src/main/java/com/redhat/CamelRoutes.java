package com.redhat;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class CamelRoutes extends RouteBuilder {
	
	

	@Override
	
	public void configure() throws Exception {
		
		restConfiguration()
			.component("servlet")
			.port(8080)
			.bindingMode(RestBindingMode.json)
			.contextPath("/")
			.dataFormatProperty("prettyPrint", "true")
			.enableCORS(true)
			.apiContextPath("/api-doc")
			.apiProperty("api.title", "Location API")
			.apiProperty("api.version", "1.0.0")
		;
	
	
		rest("/wineproduct").description("Product to Salesforce ")
			.produces("application/json")
			.get("/addWine")
				.responseMessage().code(200).message("Wine added to Product in Salesforce").endResponseMessage()
				.to("direct:addWine")
			.get("/deleteWine")
				.responseMessage().code(200).message("Wine delete from Salesforce").endResponseMessage()
				.to("direct:deleteExisting")
		;
		
		RemoveProductService deleteProductservice = new RemoveProductService();
		ProductService productservice = new ProductService();
		PricebookService pricebookservice = new PricebookService();
		
		
		
		//bulk update the product table
		from("direct:deleteExisting")
			.to("sql:SELECT * FROM winelist where productcode is NOT NULL;")
			.split(body())
				.log("${body}")
				.process(deleteProductservice)
				.to("salesforce:deleteSObject?sObjectName=Product2")
				.to("sql:UPDATE winelist SET productcode = NULL where productcode = :#productcode ; ")
		;
		
		
		 from("direct:addWine")
		 	.to("salesforce:query?sObjectQuery=SELECT Id,NAME,isActive FROM PRICEBOOK2 where IsActive=true&sObjectClass=org.apache.camel.salesforce.dto.QueryRecordsPricebook2")
	 		.setHeader("pricebookid",simple("${body.records[0].id}"))
		 	.to("sql:select ID,WINE,PRICE from winelist where productcode is NULL;")
		 	.split(body())
		 		.log("${body}")
		 		.process(productservice)
		 		.to("salesforce:createSObject?sObjectName=Product2")
		 		.process(pricebookservice)
		 		.to("salesforce:createSObject?sObjectName=PricebookEntry")
		 		.log("${body}")
		 		.to("sql:UPDATE winelist SET productcode = :#productsfid, pricebookentryid = :#${body.id} where id = :#productid ; ")

		;
		
	
	}
	
	

}
