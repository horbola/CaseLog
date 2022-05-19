package com.caseview.caselog.sail;

import java.util.ArrayList;
import java.util.HashMap;

public class TempStore {

    public static HashMap<java.lang.String, java.lang.String> profileCache = null;

    public static ArrayList<PaymentRecord> payment;

    public static HashMap<java.lang.String, java.lang.String> getProfileCache(){
        HashMap<java.lang.String, java.lang.String> pCache;

        if (profileCache == null){
            pCache = new HashMap<>();
            pCache.put("name", "Name");
            pCache.put("userID", "ID ");
            pCache.put("password", "Password");
            pCache.put("phone", "Phone");
            pCache.put("email", "Email");
            profileCache = pCache;
        }
        return profileCache;
    }

    public static ArrayList<PaymentRecord> getPayment(){
        ArrayList<PaymentRecord> pay;

        if (payment == null){
            pay = new ArrayList<>();
            pay.add(new PaymentRecord("January 2019", "500"));
            pay.add(new PaymentRecord("February 2019", "500"));
            pay.add(new PaymentRecord("March 2019", "400"));
            pay.add(new PaymentRecord("April 2019", "200"));
            pay.add(new PaymentRecord("May 2019", "300"));
            pay.add(new PaymentRecord("June 2019", "300"));
            pay.add(new PaymentRecord("July 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));
            pay.add(new PaymentRecord("August 2019", "200"));


            payment = pay;
        }
        return payment;
    }


}
