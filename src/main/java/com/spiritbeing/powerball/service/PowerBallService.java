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
    Map<Integer, Long> findTop10WhiteBalls();
    Map<Integer, Long> findTop10RedBalls();
    Map<Integer, Long> whiteBall();
    Map<Integer, Long> redBall();
    List<BallHolder> drawnBalls();
    String getCurrentDate();
    PowerBall findById(Long id);
    PowerBall save(PowerBall powerBall);
    void savePowerBallDraw(PowerBallDraw powerBallDraw);
    void delete(PowerBall powerBall);
    List<List<Map<Object, Object>>> getCanvasJsChartData();
}
