
package com.macisdev.pizzasfxclient.webservicereference;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.macisdev.pizzasfxclient.webservicereference package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendOrder_QNAME = new QName("http://pizzawebservice.macisdev.com/", "sendOrder");
    private final static QName _GetOrdersResponse_QNAME = new QName("http://pizzawebservice.macisdev.com/", "getOrdersResponse");
    private final static QName _IOException_QNAME = new QName("http://pizzawebservice.macisdev.com/", "IOException");
    private final static QName _GetOrders_QNAME = new QName("http://pizzawebservice.macisdev.com/", "getOrders");
    private final static QName _SendOrderResponse_QNAME = new QName("http://pizzawebservice.macisdev.com/", "sendOrderResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.macisdev.pizzasfxclient.webservicereference
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetOrdersResponse }
     * 
     */
    public GetOrdersResponse createGetOrdersResponse() {
        return new GetOrdersResponse();
    }

    /**
     * Create an instance of {@link SendOrder }
     * 
     */
    public SendOrder createSendOrder() {
        return new SendOrder();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link GetOrders }
     * 
     */
    public GetOrders createGetOrders() {
        return new GetOrders();
    }

    /**
     * Create an instance of {@link SendOrderResponse }
     * 
     */
    public SendOrderResponse createSendOrderResponse() {
        return new SendOrderResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pizzawebservice.macisdev.com/", name = "sendOrder")
    public JAXBElement<SendOrder> createSendOrder(SendOrder value) {
        return new JAXBElement<SendOrder>(_SendOrder_QNAME, SendOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrdersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pizzawebservice.macisdev.com/", name = "getOrdersResponse")
    public JAXBElement<GetOrdersResponse> createGetOrdersResponse(GetOrdersResponse value) {
        return new JAXBElement<GetOrdersResponse>(_GetOrdersResponse_QNAME, GetOrdersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pizzawebservice.macisdev.com/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrders }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pizzawebservice.macisdev.com/", name = "getOrders")
    public JAXBElement<GetOrders> createGetOrders(GetOrders value) {
        return new JAXBElement<GetOrders>(_GetOrders_QNAME, GetOrders.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pizzawebservice.macisdev.com/", name = "sendOrderResponse")
    public JAXBElement<SendOrderResponse> createSendOrderResponse(SendOrderResponse value) {
        return new JAXBElement<SendOrderResponse>(_SendOrderResponse_QNAME, SendOrderResponse.class, null, value);
    }

}
