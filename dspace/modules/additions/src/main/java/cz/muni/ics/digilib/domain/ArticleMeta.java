/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.domain;

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
@XmlType(name="",propOrder = {"section"})
@XmlRootElement(name="article")
public class ArticleMeta
{
    @XmlElement(name="section")
    protected String section;

    public String getSection()
    {
        return section;
    }

    public void setSection(String section)
    {
        this.section = section;
    }

    @Override
    public String toString()
    {
        return "ArticleMeta{" + "section=" + section + '}';
    }
}
