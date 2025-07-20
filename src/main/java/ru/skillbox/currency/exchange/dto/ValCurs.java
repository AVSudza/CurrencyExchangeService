package ru.skillbox.currency.exchange.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValCurs {
    @XmlAttribute(name = "Date")
    private String date;
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "Valute")
    private List<Valute> valuteList;
}
