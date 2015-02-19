/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class Test
{
    public static void main(String[] args)
    {
        List<String> list = new ArrayList<>();
        
        list.add("A");
        list.add("b");
        list.add("c");
        list.add("d");
        
        for(int i = 0; i < list.size()-1;i++)
        {
            System.out.println(list.get(i));
        }
    }
}
