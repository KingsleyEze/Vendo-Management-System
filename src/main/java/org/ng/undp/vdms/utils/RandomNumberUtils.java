package org.ng.undp.vdms.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by macbook on 7/7/17.
 */


@Component
public class RandomNumberUtils {

    private Random random;

    public RandomNumberUtils(){
        random = new Random();
    }

    public String generateRandomChars(String pattern, int length) {
        StringBuilder sb = new StringBuilder();


        return random.ints(0, pattern.length())
                .mapToObj(i -> pattern.charAt(i))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public Integer generateRandonInteger(Integer integer){
        return random.ints(integer, 80)
                .findAny()
                .getAsInt();
    }
}



