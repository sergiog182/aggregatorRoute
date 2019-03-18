package com.router;

import org.apache.camel.builder.RouteBuilder;

import com.aggregators.AggregateOrders;

public class MainRouteBuilder extends RouteBuilder{
	@Override
	public void configure() {	
		from("file:files/incoming?noop=true")
		.setHeader("provider", xpath("/order/@provider"))
		// Obtiene el xml de la orden
		.setBody(xpath("(//*[local-name()='order'])[1]"))
		.aggregate(header("provider"), new AggregateOrders())
		.completionTimeout(5000)
		.completeAllOnStop()
		.to("file:files/outgoing?fileName=${headers.provider}.xml&fileExist=append");
	}
}
