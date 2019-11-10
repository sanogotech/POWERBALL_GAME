package com.spiritbeing.powerball.abstractModel;

import com.spiritbeing.powerball.model.BallHolder;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public abstract class Generator {

    public static final int MAX_WHITE_BALL = 69;
    public static final int MIN = 1;
    public static final int MAX_RED_BALL = 26;


    protected List<BallHolder> randomNumberGenerator(int[] whiteBallArray, int[] whiteBallFrequency,
                                                      int[] redBallArray, int[] redBallFrequency){
        List<BallHolder> ballHolders = new ArrayList<>();
        BallHolder ballHolder = new BallHolder();
        ballHolder.setWhiteBall1(customizedRandom(whiteBallArray, whiteBallFrequency));
        ballHolder.setWhiteBall2(customizedRandom(whiteBallArray, whiteBallFrequency));
        ballHolder.setWhiteBall3(customizedRandom(whiteBallArray, whiteBallFrequency));
        ballHolder.setWhiteBall4(customizedRandom(whiteBallArray, whiteBallFrequency));
        ballHolder.setWhiteBall5(customizedRandom(whiteBallArray, whiteBallFrequency));
        ballHolder.setRedBall(customizedRandom(redBallArray, redBallFrequency));
        log.info(" THIS IS BALL 1>>>>>>>>>>>>>>>>>>>>>>" + ballHolder.getRedBall());
        ballHolders.add(ballHolder);
        return ballHolders;
    }

    protected int[] convertSetToArray(Set<Integer> mySet){
        int[] array = new int[mySet.size()];
        //Converting Set object to integer array
        int j = 0;
        for (Integer i: mySet) {
            array[j++] = i;
        }
       return array;
    }

    protected int[] convertCollectionToArray(Collection<Integer> myCollection){
        int[] array = new int[myCollection.size()];
        //Converting Set object to integer array
        int j = 0;
        for (Integer i: myCollection) {
            array[j++] = i;
        }
        return array;
    }


    private int findCeil(int[] prefix, int randomNumber, int low, int high){
        int mid;
        while(low < high){
            mid = low + (high - low) / 2;
            if(prefix[mid] < randomNumber){
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        return prefix[low] >= randomNumber ? low : -1;
    }

    private int customizedRandom(int[] myArray, int[] frequency){
        //Create and fill the prefix array
        int[] prefix = new int[myArray.length];
        prefix[0]  = frequency[0];
        for (int i = 1; i < myArray.length; i++) {
            prefix[i] = prefix[i - 1] + frequency[i];
        }
        int random = (int)(Math.random() * prefix[myArray.length - 1]) + 1;
        System.out.println("random " + random);
        //findCeil convert the random value to index of original array.
        int indexCeil = findCeil(prefix, random, 0, myArray.length-1);
        System.out.println("ceiliNDEX " + indexCeil);
        return myArray[indexCeil];
    }
    protected String getDate(){
        Date date = new Date();
        String pattern = "yyy-MM-dd HH:mm:ss";
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        System.out.println(sd.format(date));
        return sd.format(date);
    }
}
