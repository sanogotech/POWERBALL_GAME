package com.spiritbeing.powerball.abstractModel;

import com.spiritbeing.powerball.model.BallHolder;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public abstract class Generator {

    public static final int MAX_WHITE_BALL = 69;
    public static final int MIN = 1;
    public static final int MAX_RED_BALL = 26;

    protected List<BallHolder> randomNumberGenerator(){
        List<BallHolder> ballHolders = new ArrayList<>();
        BallHolder ballHolder = new BallHolder();
        ballHolder.setWhiteBall1(internalGenerator(MAX_WHITE_BALL));
        ballHolder.setWhiteBall2(internalGenerator(MAX_WHITE_BALL));
        ballHolder.setWhiteBall3(internalGenerator(MAX_WHITE_BALL));
        ballHolder.setWhiteBall4(internalGenerator(MAX_WHITE_BALL));
        ballHolder.setWhiteBall5(internalGenerator(MAX_WHITE_BALL));
        ballHolder.setRedBall(internalGenerator(MAX_RED_BALL));
        log.info(" THIS IS BALL 1>>>>>>>>>>>>>>>>>>>>>>" + ballHolder.getRedBall());
        ballHolders.add(ballHolder);
        return ballHolders;
    }

    private int internalGenerator(int max){
       return ((int) (Math.random()*(max))) + Generator.MIN;
    }

    protected String getDate(){
        Date date = new Date();
        String pattern = "yyy-MM-dd HH:mm:ss";
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        System.out.println(sd.format(date));
        return sd.format(date);
    }
}
