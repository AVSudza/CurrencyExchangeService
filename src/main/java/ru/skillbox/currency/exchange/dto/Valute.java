package ru.skillbox.currency.exchange.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
public class Valute {
    @XmlTransient
    public int id;
    @XmlAttribute(name = "ID")
    private String valuteId;
    @XmlElement(name = "NumCode")
    private String numCode;
    @XmlElement(name = "CharCode")
    private String charCode;
    @XmlElement(name = "Nominal")
    private int nominal;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Value")
    private String value;
    @XmlElement(name = "VunitRate")
    private String vunitRate;

    public String getValue() {
        return String.valueOf(value);
    }

    public void setValue(Double value) {
        this.value = String.valueOf(value);
    }

    public String getVunitRate() {
        return String.valueOf(vunitRate);
    }

    public void setVunitRate(double vunitRate) {
        this.vunitRate = String.valueOf(vunitRate);
    }

}