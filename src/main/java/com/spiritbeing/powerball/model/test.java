package com.spiritbeing.powerball.model;

import com.spiritbeing.powerball.abstractModel.Generator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class test extends Generator {


    public static void main(String[] args) {
        Date date = new Date();
        String pattern = "yyy-MM-dd";
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        System.out.println(sd.format(date));

        String password = "password123";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pss = bCryptPasswordEncoder.encode(password);
        System.out.println(pss);


        int ball5 = ((int) (Math.random() * (5))) + 1;
        System.out.println(ball5);
        int b2 = ((int) (Math.random() * (3))) + 1;
        System.out.println(b2);

        List<Integer> list = new LinkedList<>();
        list.add(50);
        list.add(8);
        list.get(0);

        for (int k : list) {
            System.out.println("linked list:::::" + k + " " + list.get(0));
        }
    }

}

