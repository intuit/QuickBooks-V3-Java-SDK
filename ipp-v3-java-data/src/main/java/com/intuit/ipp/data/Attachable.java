//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.18 at 04:20:25 PM IST 
//


package com.intuit.ipp.data;

import java.io.Serializable;

import com.intuit.ipp.core.IEntity;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 				Product: ALL
 * 				Description: Describes the details of
 * 				the attachment.
 * 			
 * 
 * <p>Java class for Attachable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Attachable"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schema.intuit.com/finance/v3}IntuitEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FileAccessUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TempDownloadUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Size" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="ContentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Lat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Long" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PlaceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Tag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ThumbnailFileAccessUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ThumbnailTempDownloadUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AttachableEx" type="{http://schema.intuit.com/finance/v3}IntuitAnyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Attachable", propOrder = {
    "fileName",
    "fileAccessUri",
    "tempDownloadUri",
    "size",
    "contentType",
    "category",
    "lat",
    "_long",
    "placeName",
    "note",
    "tag",
    "thumbnailFileAccessUri",
    "thumbnailTempDownloadUri",
    "attachableEx"
})
public class Attachable
    extends IntuitEntity
    implements Serializable, IEntity
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "FileName")
    protected String fileName;
    @XmlElement(name = "FileAccessUri")
    protected String fileAccessUri;
    @XmlElement(name = "TempDownloadUri")
    protected String tempDownloadUri;
    @XmlElement(name = "Size")
    protected Long size;
    @XmlElement(name = "ContentType")
    protected String contentType;
    @XmlElement(name = "Category")
    protected String category;
    @XmlElement(name = "Lat")
    protected String lat;
    @XmlElement(name = "Long")
    protected String _long;
    @XmlElement(name = "PlaceName")
    protected String placeName;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "Tag")
    protected String tag;
    @XmlElement(name = "ThumbnailFileAccessUri")
    protected String thumbnailFileAccessUri;
    @XmlElement(name = "ThumbnailTempDownloadUri")
    protected String thumbnailTempDownloadUri;
    @XmlElement(name = "AttachableEx")
    protected IntuitAnyType attachableEx;

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the fileAccessUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileAccessUri() {
        return fileAccessUri;
    }

    /**
     * Sets the value of the fileAccessUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileAccessUri(String value) {
        this.fileAccessUri = value;
    }

    /**
     * Gets the value of the tempDownloadUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTempDownloadUri() {
        return tempDownloadUri;
    }

    /**
     * Sets the value of the tempDownloadUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTempDownloadUri(String value) {
        this.tempDownloadUri = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSize(Long value) {
        this.size = value;
    }

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentType(String value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * Gets the value of the lat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLat() {
        return lat;
    }

    /**
     * Sets the value of the lat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLat(String value) {
        this.lat = value;
    }

    /**
     * Gets the value of the long property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLong() {
        return _long;
    }

    /**
     * Sets the value of the long property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLong(String value) {
        this._long = value;
    }

    /**
     * Gets the value of the placeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlaceName() {
        return placeName;
    }

    /**
     * Sets the value of the placeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlaceName(String value) {
        this.placeName = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the tag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTag(String value) {
        this.tag = value;
    }

    /**
     * Gets the value of the thumbnailFileAccessUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumbnailFileAccessUri() {
        return thumbnailFileAccessUri;
    }

    /**
     * Sets the value of the thumbnailFileAccessUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumbnailFileAccessUri(String value) {
        this.thumbnailFileAccessUri = value;
    }

    /**
     * Gets the value of the thumbnailTempDownloadUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumbnailTempDownloadUri() {
        return thumbnailTempDownloadUri;
    }

    /**
     * Sets the value of the thumbnailTempDownloadUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumbnailTempDownloadUri(String value) {
        this.thumbnailTempDownloadUri = value;
    }

    /**
     * Gets the value of the attachableEx property.
     * 
     * @return
     *     possible object is
     *     {@link IntuitAnyType }
     *     
     */
    public IntuitAnyType getAttachableEx() {
        return attachableEx;
    }

    /**
     * Sets the value of the attachableEx property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntuitAnyType }
     *     
     */
    public void setAttachableEx(IntuitAnyType value) {
        this.attachableEx = value;
    }

}
