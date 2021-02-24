
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

    private final static QName _GetAllStoredOrders_QNAME = new QName("http://pizzashopwebservice.macisdev.com/", "getAllStoredOrders");
    private final static QName _GetAllStoredOrdersResponse_QNAME = new QName("http://pizzashopwebservice.macisdev.com/", "getAllStoredOrdersResponse");
    private final static QName _GetOrders_QNAME = new QName("http://pizzashopwebservice.macisdev.com/", "getOrders");
    private final static QName _GetOrdersResponse_QNAME = new QName("http://pizzashopwebservice.macisdev.com/", "getOrdersResponse");
    private final static QName _GetStoredOrder_QNAME = new QName("http://pizzashopwebservice.macisdev.com/", "getStoredOrder");
    private final static QName _GetStoredOrderResponse_QNAME = new QName("http://pizzashopwebservice.macisdev.com/", "getStoredOrderResponse");
    private final static QName _GetStoredOrdersByPhoneNumber_QNAME = new QName("http://pizzashopwebservice.macisdev.com/", "getStoredOrdersByPhoneNumber");
    private final static QName _GetStoredOrdersByPhoneNumberResponse_QNAME = new QName("http://pizzashopwebservice.macisdev.com/", "getStoredOrdersByPhoneNumberResponse");
    private final static QName _SendOrder_QNAME = new QName("http://pizzashopwebservice.macisdev.com/", "sendOrder");
    private final static QName _SendOrderResponse_QNAME = new QName("http://pizzashopwebservice.macisdev.com/", "sendOrderResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.macisdev.pizzasfxclient.webservicereference
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllStoredOrders }
     * 
     */
    public GetAllStoredOrders createGetAllStoredOrders() {
        return new GetAllStoredOrders();
    }

    /**
     * Create an instance of {@link GetAllStoredOrdersResponse }
     * 
     */
    public GetAllStoredOrdersResponse createGetAllStoredOrdersResponse() {
        return new GetAllStoredOrdersResponse();
    }

    /**
     * Create an instance of {@link GetOrders }
     * 
     */
    public GetOrders createGetOrders() {
        return new GetOrders();
    }

    /**
     * Create an instance of {@link GetOrdersResponse }
     * 
     */
    public GetOrdersResponse createGetOrdersResponse() {
        return new GetOrdersResponse();
    }

    /**
     * Create an instance of {@link GetStoredOrder }
     * 
     */
    public GetStoredOrder createGetStoredOrder() {
        return new GetStoredOrder();
    }

    /**
     * Create an instance of {@link GetStoredOrderResponse }
     * 
     */
    public GetStoredOrderResponse createGetStoredOrderResponse() {
        return new GetStoredOrderResponse();
    }

    /**
     * Create an instance of {@link GetStoredOrdersByPhoneNumber }
     * 
     */
    public GetStoredOrdersByPhoneNumber createGetStoredOrdersByPhoneNumber() {
        return new GetStoredOrdersByPhoneNumber();
    }

    /**
     * Create an instance of {@link GetStoredOrdersByPhoneNumberResponse }
     * 
     */
    public GetStoredOrdersByPhoneNumberResponse createGetStoredOrdersByPhoneNumberResponse() {
        return new GetStoredOrdersByPhoneNumberResponse();
    }

    /**
     * Create an instance of {@link SendOrder }
     * 
     */
    public SendOrder createSendOrder() {
        return new SendOrder();
    }

    /**
     * Create an instance of {@link SendOrderResponse }
     * 
     */
    public SendOrderResponse createSendOrderResponse() {
        return new SendOrderResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllStoredOrders }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllStoredOrders }{@code >}
     */
    @XmlElementDecl(namespace = "http://pizzashopwebservice.macisdev.com/", name = "getAllStoredOrders")
    public JAXBElement<GetAllStoredOrders> createGetAllStoredOrders(GetAllStoredOrders value) {
        return new JAXBElement<GetAllStoredOrders>(_GetAllStoredOrders_QNAME, GetAllStoredOrders.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllStoredOrdersResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllStoredOrdersResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://pizzashopwebservice.macisdev.com/", name = "getAllStoredOrdersResponse")
    public JAXBElement<GetAllStoredOrdersResponse> createGetAllStoredOrdersResponse(GetAllStoredOrdersResponse value) {
        return new JAXBElement<GetAllStoredOrdersResponse>(_GetAllStoredOrdersResponse_QNAME, GetAllStoredOrdersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrders }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetOrders }{@code >}
     */
    @XmlElementDecl(namespace = "http://pizzashopwebservice.macisdev.com/", name = "getOrders")
    public JAXBElement<GetOrders> createGetOrders(GetOrders value) {
        return new JAXBElement<GetOrders>(_GetOrders_QNAME, GetOrders.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrdersResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetOrdersResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://pizzashopwebservice.macisdev.com/", name = "getOrdersResponse")
    public JAXBElement<GetOrdersResponse> createGetOrdersResponse(GetOrdersResponse value) {
        return new JAXBElement<GetOrdersResponse>(_GetOrdersResponse_QNAME, GetOrdersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoredOrder }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetStoredOrder }{@code >}
     */
    @XmlElementDecl(namespace = "http://pizzashopwebservice.macisdev.com/", name = "getStoredOrder")
    public JAXBElement<GetStoredOrder> createGetStoredOrder(GetStoredOrder value) {
        return new JAXBElement<GetStoredOrder>(_GetStoredOrder_QNAME, GetStoredOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoredOrderResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetStoredOrderResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://pizzashopwebservice.macisdev.com/", name = "getStoredOrderResponse")
    public JAXBElement<GetStoredOrderResponse> createGetStoredOrderResponse(GetStoredOrderResponse value) {
        return new JAXBElement<GetStoredOrderResponse>(_GetStoredOrderResponse_QNAME, GetStoredOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoredOrdersByPhoneNumber }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetStoredOrdersByPhoneNumber }{@code >}
     */
    @XmlElementDecl(namespace = "http://pizzashopwebservice.macisdev.com/", name = "getStoredOrdersByPhoneNumber")
    public JAXBElement<GetStoredOrdersByPhoneNumber> createGetStoredOrdersByPhoneNumber(GetStoredOrdersByPhoneNumber value) {
        return new JAXBElement<GetStoredOrdersByPhoneNumber>(_GetStoredOrdersByPhoneNumber_QNAME, GetStoredOrdersByPhoneNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoredOrdersByPhoneNumberResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetStoredOrdersByPhoneNumberResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://pizzashopwebservice.macisdev.com/", name = "getStoredOrdersByPhoneNumberResponse")
    public JAXBElement<GetStoredOrdersByPhoneNumberResponse> createGetStoredOrdersByPhoneNumberResponse(GetStoredOrdersByPhoneNumberResponse value) {
        return new JAXBElement<GetStoredOrdersByPhoneNumberResponse>(_GetStoredOrdersByPhoneNumberResponse_QNAME, GetStoredOrdersByPhoneNumberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendOrder }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SendOrder }{@code >}
     */
    @XmlElementDecl(namespace = "http://pizzashopwebservice.macisdev.com/", name = "sendOrder")
    public JAXBElement<SendOrder> createSendOrder(SendOrder value) {
        return new JAXBElement<SendOrder>(_SendOrder_QNAME, SendOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendOrderResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SendOrderResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://pizzashopwebservice.macisdev.com/", name = "sendOrderResponse")
    public JAXBElement<SendOrderResponse> createSendOrderResponse(SendOrderResponse value) {
        return new JAXBElement<SendOrderResponse>(_SendOrderResponse_QNAME, SendOrderResponse.class, null, value);
    }

}
