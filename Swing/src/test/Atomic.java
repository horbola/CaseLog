/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Saif Khan Faysal
 */
public class Atomic 
{
    public static void main(String...s) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        Date date1 = sdf.parse("10-10-2018");
        
        
        
        System.out.println(sdf.format(date1));
    }
}
