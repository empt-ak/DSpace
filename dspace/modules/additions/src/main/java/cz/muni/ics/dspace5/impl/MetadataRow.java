/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MetadataRow
{
    private String schema;
    private String element;
    private String qualifier;
    private String language;
    private String value;

    public MetadataRow(String schema, String element, String qualifier, String language, String value)
    {
        this.schema = schema;
        this.element = element;
        this.qualifier = qualifier;
        this.language = language;
        this.value = value;
    }

    public MetadataRow()
    {
    }

    public String getSchema()
    {
        return schema;
    }

    public void setSchema(String schema)
    {
        this.schema = schema;
    }

    public String getElement()
    {
        return element;
    }

    public void setElement(String element)
    {
        this.element = element;
    }

    public String getQualifier()
    {
        return qualifier;
    }

    public void setQualifier(String qualifier)
    {
        this.qualifier = qualifier;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "MetadataRow{" + "schema=" + schema + ", element=" + element + ", qualifier=" + qualifier + ", language=" + language + ", value=" + value + '}';
    }
}
