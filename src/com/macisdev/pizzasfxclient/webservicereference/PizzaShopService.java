
package com.macisdev.pizzasfxclient.webservicereference;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.0
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PizzaShopService", targetNamespace = "http://pizzashopwebservice.macisdev.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PizzaShopService {


    /**
     * 
     * @param arg0
     * @return
     *     returns int
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sendOrder", targetNamespace = "http://pizzashopwebservice.macisdev.com/", className = "com.macisdev.pizzasfxclient.webservicereference.SendOrder")
    @ResponseWrapper(localName = "sendOrderResponse", targetNamespace = "http://pizzashopwebservice.macisdev.com/", className = "com.macisdev.pizzasfxclient.webservicereference.SendOrderResponse")
    public int sendOrder(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws IOException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns com.macisdev.pizzasfxclient.webservicereference.Order
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getStoredOrder", targetNamespace = "http://pizzashopwebservice.macisdev.com/", className = "com.macisdev.pizzasfxclient.webservicereference.GetStoredOrder")
    @ResponseWrapper(localName = "getStoredOrderResponse", targetNamespace = "http://pizzashopwebservice.macisdev.com/", className = "com.macisdev.pizzasfxclient.webservicereference.GetStoredOrderResponse")
    public Order getStoredOrder(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOrders", targetNamespace = "http://pizzashopwebservice.macisdev.com/", className = "com.macisdev.pizzasfxclient.webservicereference.GetOrders")
    @ResponseWrapper(localName = "getOrdersResponse", targetNamespace = "http://pizzashopwebservice.macisdev.com/", className = "com.macisdev.pizzasfxclient.webservicereference.GetOrdersResponse")
    public List<String> getOrders(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

}