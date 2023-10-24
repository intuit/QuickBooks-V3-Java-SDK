//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.02.07 at 12:34:12 PM IST 
//


package com.intuit.ipp.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import com.intuit.ipp.core.IEntity;
import org.jvnet.jaxb2_commons.lang.Equals2;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy2;
import org.jvnet.jaxb2_commons.lang.HashCode2;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy2;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * Describes list of OLBAccounts that needs to be
 * 				enabled or disabled
 * 
 * <p>Java class for OLBStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OLBStatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OLBAccount" type="{http://schema.intuit.com/finance/v3}OLBAccount" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OLBStatus", propOrder = {
    "olbAccount"
})
public class OLBStatus implements Serializable, IEntity, Equals2, HashCode2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "OLBAccount")
    protected List<OLBAccount> olbAccount;

    /**
     * Gets the value of the olbAccount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the olbAccount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOLBAccount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OLBAccount }
     * 
     * 
     */
    public List<OLBAccount> getOLBAccount() {
        if (olbAccount == null) {
            olbAccount = new ArrayList<OLBAccount>();
        }
        return this.olbAccount;
    }

    /**
     * Sets the value of the olbAccount property.
     * 
     * @param olbAccount
     *     allowed object is
     *     {@link OLBAccount }
     *     
     */
    public void setOLBAccount(List<OLBAccount> olbAccount) {
        this.olbAccount = olbAccount;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy2 strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final OLBStatus that = ((OLBStatus) object);
        {
            List<OLBAccount> lhsOLBAccount;
            lhsOLBAccount = (((this.olbAccount!= null)&&(!this.olbAccount.isEmpty()))?this.getOLBAccount():null);
            List<OLBAccount> rhsOLBAccount;
            rhsOLBAccount = (((that.olbAccount!= null)&&(!that.olbAccount.isEmpty()))?that.getOLBAccount():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "olbAccount", lhsOLBAccount), LocatorUtils.property(thatLocator, "olbAccount", rhsOLBAccount), lhsOLBAccount, rhsOLBAccount, ((this.olbAccount!= null)&&(!this.olbAccount.isEmpty())), ((that.olbAccount!= null)&&(!that.olbAccount.isEmpty())))) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy2 strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy2 strategy) {
        int currentHashCode = 1;
        {
            List<OLBAccount> theOLBAccount;
            theOLBAccount = (((this.olbAccount!= null)&&(!this.olbAccount.isEmpty()))?this.getOLBAccount():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "olbAccount", theOLBAccount), currentHashCode, theOLBAccount, ((this.olbAccount!= null)&&(!this.olbAccount.isEmpty())));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
