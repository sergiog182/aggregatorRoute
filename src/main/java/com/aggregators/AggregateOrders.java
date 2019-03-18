package com.aggregators;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class AggregateOrders implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		// Utilizar esta cabecera para que al detener la ruta, 
		// si hay aggregator que aun no se han completado el sistema no se bloquee 
		newExchange.getIn().setHeader(Exchange.AGGREGATION_COMPLETE_ALL_GROUPS, true);
		
		if (oldExchange != null) {
			String cuerpoViejo = oldExchange.getIn().getBody(String.class);
			String cuerpoNuevo = newExchange.getIn().getBody(String.class);
			
			cuerpoNuevo = cuerpoNuevo.concat("\n" + cuerpoViejo);
			
			newExchange.getIn().setBody(cuerpoNuevo);
		}
		
		return newExchange;
	}

}
