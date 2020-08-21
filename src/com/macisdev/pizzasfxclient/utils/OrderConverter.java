package com.macisdev.pizzasfxclient.utils;

import com.macisdev.orders.Order;
import com.macisdev.orders.OrderElement;

import java.util.ArrayList;
import java.util.List;

/**Utility class that converts the class "Order" from the webService into the "Order" class from
 * the desktop app. They are the same class in code, but with a different namespace."
 */
public class OrderConverter {
	public static Order convertOrder (com.macisdev.pizzasfxclient.webservicereference.Order order) {
		if (order == null) {
			return null;
		}

		Order convertedOrder = new Order();

		convertedOrder.setCustomerAddress(order.getCustomerAddress());
		convertedOrder.setCustomerName(order.getCustomerName());
		convertedOrder.setCustomerPhone(order.getCustomerPhone());
		convertedOrder.setDeliveryMethod(order.getDeliveryMethod());
		convertedOrder.setOrderDateTime(order.getOrderDateTime());
		convertedOrder.setOrderId(order.getOrderId());
		convertedOrder.setPaymentMethod(order.getPaymentMethod());
		convertedOrder.setTotalPrice(order.getTotalPrice());

		List<OrderElement> orderElements = new ArrayList<>();

		for (com.macisdev.pizzasfxclient.webservicereference.OrderElement element: order.getOrderElements()) {
			orderElements.add(convertOrderElements(element));
		}

		convertedOrder.setOrderElements(orderElements);

		return convertedOrder;
	}

	private static OrderElement convertOrderElements (com.macisdev.pizzasfxclient.webservicereference.OrderElement element) {
		OrderElement convertedElement = new OrderElement();
		convertedElement.setCode(element.getCode());
		convertedElement.setName(element.getName());
		convertedElement.setPrimaryKey(element.getPrimaryKey());
		convertedElement.setSize(element.getSize());
		convertedElement.setExtras(element.getExtras());
		convertedElement.setPrice(element.getPrice());

		return convertedElement;
	}

	public static List<Order> convertOrderList(List<com.macisdev.pizzasfxclient.webservicereference.Order> orderList) {
		List<Order> ordersConverted = new ArrayList<>();
		for (com.macisdev.pizzasfxclient.webservicereference.Order order : orderList) {
			ordersConverted.add(convertOrder(order));
		}
		return ordersConverted;
	}
}
