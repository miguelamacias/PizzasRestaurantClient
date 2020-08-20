
package com.macisdev.pizzasfxclient.webservicereference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para orderElement complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="orderElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="extras" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="primaryKey" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderElement", propOrder = {
    "code",
    "extras",
    "name",
    "price",
    "primaryKey",
    "size"
})
public class OrderElement {

    protected String code;
    protected String extras;
    protected String name;
    protected double price;
    protected int primaryKey;
    protected String size;

    /**
     * Obtiene el valor de la propiedad code.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Define el valor de la propiedad code.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Obtiene el valor de la propiedad extras.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtras() {
        return extras;
    }

    /**
     * Define el valor de la propiedad extras.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtras(String value) {
        this.extras = value;
    }

    /**
     * Obtiene el valor de la propiedad name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad price.
     * 
     */
    public double getPrice() {
        return price;
    }

    /**
     * Define el valor de la propiedad price.
     * 
     */
    public void setPrice(double value) {
        this.price = value;
    }

    /**
     * Obtiene el valor de la propiedad primaryKey.
     * 
     */
    public int getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Define el valor de la propiedad primaryKey.
     * 
     */
    public void setPrimaryKey(int value) {
        this.primaryKey = value;
    }

    /**
     * Obtiene el valor de la propiedad size.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSize() {
        return size;
    }

    /**
     * Define el valor de la propiedad size.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSize(String value) {
        this.size = value;
    }

}
