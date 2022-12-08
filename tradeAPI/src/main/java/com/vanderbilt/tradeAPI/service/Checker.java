package com.vanderbilt.tradeAPI.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker {

    public static void main (String[] args){
        Random rand = new Random();
        double x = rand.nextDouble();
        System.out.println(Double.toString(x).substring(2,6));
        System.out.println(x);
        BigDecimal bd = new BigDecimal(x).setScale(2, RoundingMode.HALF_EVEN);
        System.out.println(bd.doubleValue());
    }
}
