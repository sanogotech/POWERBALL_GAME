package com.spiritbeing.powerball.service;

import com.spiritbeing.powerball.model.BallHolder;
import com.spiritbeing.powerball.model.PowerBall;
import com.spiritbeing.powerball.model.PowerBallDraw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PowerBallService {
    Set<PowerBall> findAll();
    Page<PowerBall> findByOrderByCreatedDateAsc(Pageable pageable);
    Map<Integer, Integer> findTop10WhiteBalls();
    Map<Integer, Integer> findTop10RedBalls();
    Map<Integer, Integer> whiteBall();
    Map<Integer, Integer> redBall();
    List<BallHolder> drawnBalls();
    String getCurrentDate();
    PowerBall findById(Long id);
    PowerBall save(PowerBall powerBall);
    void savePowerBallDraw(PowerBallDraw powerBallDraw);
    void delete(PowerBall powerBall);

    Double sumOfEntryNative();
    Double sumOfEntryJPQL();
}
