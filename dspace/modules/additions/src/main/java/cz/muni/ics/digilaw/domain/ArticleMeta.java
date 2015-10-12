/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="",propOrder = {"section","number"})
@XmlRootElement(name="article")
public class ArticleMeta
{
    @XmlElement(name="section")
    protected String section;
    
    @XmlElement(name="number")
    protected String number;

    public String getSection()
    {
        return section;
    }

    public void setSection(String section)
    {
        this.section = section;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return "ArticleMeta{" + "section=" + section + ", number=" + number + '}';
    }
}
