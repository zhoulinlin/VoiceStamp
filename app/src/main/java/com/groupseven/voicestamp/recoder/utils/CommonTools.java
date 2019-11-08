package com.groupseven.voicestamp.recoder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CommonTools {

    private static Random randGen = new Random();

    private static long id = 0L;

    public static String getDate(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");// HH:mm:ss

        Date date = new Date(System.currentTimeMillis());

        return simpleDateFormat.format(date);

    }


    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    public static String getRandomId(){

        return randomString(8) + "-" + ++id;

    }

    public static String randomString(int length) {
        if (length < 1) {
            return null;
        }
        // Create a char buffer to put random letters and numbers in.
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }




}
