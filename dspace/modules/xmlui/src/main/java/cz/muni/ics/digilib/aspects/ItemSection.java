/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.aspects;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ItemSection implements Comparable<ItemSection>
{
    private String sectionName;
    private int position;
    private boolean noSection;

    public String getSectionName()
    {
        return sectionName;
    }

    public void setSectionName(String sectionName)
    {
        this.sectionName = sectionName;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    @Override
    public int compareTo(ItemSection o)
    {
        return getPosition()-o.getPosition();
    }

    public boolean isNoSection()
    {
        return noSection;
    }

    public void setNoSection(boolean noSection)
    {
        this.noSection = noSection;
    }

    @Override
    public String toString()
    {
        return "ItemSection{" + "sectionName=" + sectionName + ", position=" + position + ", noSection=" + noSection + '}';
    }
}
