
package com.matson.vindecoding.webservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VehicleInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VehicleInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="vinNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="make" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="model" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="year" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="vehicleType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="bodyType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="assemblyPlant" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="length" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="height" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="width" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="weight" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleInfo")
public class VehicleInfo {

    @XmlAttribute(required = true)
    protected String vinNumber;
    @XmlAttribute(required = true)
    protected String make;
    @XmlAttribute(required = true)
    protected String model;
    @XmlAttribute(required = true)
    protected int year;
    @XmlAttribute(required = true)
    protected String vehicleType;
    @XmlAttribute(required = true)
    protected String bodyType;
    @XmlAttribute(required = true)
    protected String assemblyPlant;
    @XmlAttribute(required = true)
    protected double length;
    @XmlAttribute(required = true)
    protected double height;
    @XmlAttribute(required = true)
    protected double width;
    @XmlAttribute(required = true)
    protected int weight;

    /**
     * Gets the value of the vinNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVinNumber() {
        return vinNumber;
    }

    /**
     * Sets the value of the vinNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVinNumber(String value) {
        this.vinNumber = value;
    }

    /**
     * Gets the value of the make property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMake() {
        return make;
    }

    /**
     * Sets the value of the make property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMake(String value) {
        this.make = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the year property.
     * 
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     */
    public void setYear(int value) {
        this.year = value;
    }

    /**
     * Gets the value of the vehicleType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * Sets the value of the vehicleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleType(String value) {
        this.vehicleType = value;
    }

    /**
     * Gets the value of the bodyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBodyType() {
        return bodyType;
    }

    /**
     * Sets the value of the bodyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBodyType(String value) {
        this.bodyType = value;
    }

    /**
     * Gets the value of the assemblyPlant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssemblyPlant() {
        return assemblyPlant;
    }

    /**
     * Sets the value of the assemblyPlant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssemblyPlant(String value) {
        this.assemblyPlant = value;
    }

    /**
     * Gets the value of the length property.
     * 
     */
    public double getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     */
    public void setLength(double value) {
        this.length = value;
    }

    /**
     * Gets the value of the height property.
     * 
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     */
    public void setHeight(double value) {
        this.height = value;
    }

    /**
     * Gets the value of the width property.
     * 
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     */
    public void setWidth(double value) {
        this.width = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     */
    public void setWeight(int value) {
        this.weight = value;
    }

}
