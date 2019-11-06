package com.spiritbeing.powerball.service;

import com.spiritbeing.powerball.model.BallHolder;
import com.spiritbeing.powerball.abstractModel.Generator;
import com.spiritbeing.powerball.model.PowerBall;
import com.spiritbeing.powerball.model.PowerBallDraw;
import com.spiritbeing.powerball.repo.PowerBallRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service @Slf4j
public class PowerBallServiceImpl extends Generator implements PowerBallService {
    private final Long INIT_VALUE = 1L;
    private final int LIMIT = 10;
    private final PowerBallRepository powerBallRepository;

    public PowerBallServiceImpl(PowerBallRepository powerBallRepository) {
        this.powerBallRepository = powerBallRepository;
    }

    @Override
    public Set<PowerBall> findAll() {
        Set<PowerBall> powerBallSet = new HashSet<>();
        powerBallRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate")).forEach(powerBallSet ::add);
        return powerBallSet;
    }

    @Override
    public Page<PowerBall> findByOrderByCreatedDateAsc(Pageable pageable) {
        return powerBallRepository.findByOrderByCreatedDateDesc(pageable);
    }


    @Override
    public Map<Integer, Long> findTop10WhiteBalls() {
        //findAll().parallelStream().
        return whiteBall().entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(LIMIT).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    @Override
    public Map<Integer, Long> findTop10RedBalls() {
        return redBall().entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(LIMIT).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    @Override
    public Map<Integer, Long> whiteBall() {
        Map<Integer, Long> frequencies = new HashMap<>();
        getWhiteBalls().forEach(ball -> {
            if(!frequencies.containsKey(ball)){
                frequencies.put(ball, INIT_VALUE);
            }else{
                Long value = frequencies.get(ball);
                frequencies.put(ball, value + INIT_VALUE);
            }
        });
        return  frequencies;
    }

    private List<Integer> getWhiteBalls(){
        List<Integer> whiteBallsCollection = new ArrayList<>();
        findAll().forEach(powerBall -> {
                    whiteBallsCollection.add(powerBall.getBall_1());
                    whiteBallsCollection.add(powerBall.getBall_2());
                    whiteBallsCollection.add(powerBall.getBall_3());
                    whiteBallsCollection.add(powerBall.getBall_4());
                    whiteBallsCollection.add(powerBall.getBall_5());
                });

        return whiteBallsCollection;
    }

    @Override
    public Map<Integer, Long> redBall() {
        Map<Integer, Long> frequencies = new HashMap<>();
        findAll().forEach(powerBall -> {
            int redBall = powerBall.getBall_6();
            if(!frequencies.containsKey(redBall)){
                frequencies.put(redBall, INIT_VALUE);
            }else{
                Long value = frequencies.get(redBall);
                frequencies.put(redBall, value + INIT_VALUE);
            }
        });

        return frequencies;
    }

    @Override
    public List<BallHolder> drawnBalls() {
        log.info("Am in IMPL>>>>" + randomNumberGenerator());
        return randomNumberGenerator();
    }

    @Override
    public String getCurrentDate() {
        return getDate();
    }


    @Override
    public PowerBall findById(Long id) {
        return powerBallRepository.findById(id).orElse(null);
    }

    @Override
    public PowerBall save(PowerBall powerBall) {
        return powerBallRepository.save(powerBall);
    }

    @Override
    public void savePowerBallDraw(PowerBallDraw powerBallDraw) {
        log.info("hOPE ISSUE DID NOT OCCUR HERE >>>> i mean id things");
        PowerBall powerBall = new PowerBall(powerBallDraw.getId(), powerBallDraw.getBall_1(),
                powerBallDraw.getBall_2(),powerBallDraw.getBall_3(), powerBallDraw.getBall_4(),
                powerBallDraw.getBall_5(), powerBallDraw.getBall_6(), powerBallDraw.getCreatedDate());

        powerBallRepository.save(powerBall);
    }

    @Override
    public void delete(PowerBall powerBall) {
        powerBallRepository.delete(powerBall);
    }

    @Override
    public List<List<Map<Object, Object>>> getCanvasJsChartData() {
        return null;
    }
}
