package com.spiritbeing.powerball.ServiceAddon;

import com.spiritbeing.powerball.model.BallsFrequency;
import com.spiritbeing.powerball.service.PowerBallService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class FrequencyServiceImpl implements FrequencyService {
    private final PowerBallService powerBallService;

    public FrequencyServiceImpl(PowerBallService powerBallService) {
        this.powerBallService = powerBallService;
    }

    private List<BallsFrequency> ballsFrequency() {
        List<BallsFrequency> ballsFrequencies = new LinkedList<>();
        Map<Integer, Long> whiteBallsFrequencies = powerBallService.whiteBall();
        Map<Integer, Long> redBallsFrequencies= powerBallService.redBall();

        whiteBallsFrequencies.forEach((key, value) -> {
            Long whiteBallValue = whiteBallsFrequencies.get(key);
            Long redBallValue = redBallsFrequencies.get(key);
            if(redBallValue == null){
                BallsFrequency ballsFrequency = new BallsFrequency(key, whiteBallValue, "N/A");
                ballsFrequencies.add(ballsFrequency);
            }else{
                BallsFrequency ballsFrequency = new BallsFrequency(key, whiteBallValue, String.valueOf(redBallValue));
                ballsFrequencies.add(ballsFrequency);
            }
        });
        return ballsFrequencies;
    }

    @Override
    public Page<BallsFrequency> findByOrderById(Pageable pageable) {
        return new PageImpl<>(ballsFrequency(), pageable, ballsFrequency().size());
    }
}
