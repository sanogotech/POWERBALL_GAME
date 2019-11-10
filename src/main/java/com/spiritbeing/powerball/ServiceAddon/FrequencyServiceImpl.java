package com.spiritbeing.powerball.ServiceAddon;

import com.spiritbeing.powerball.abstractModel.Constants;
import com.spiritbeing.powerball.model.BallsFrequency;
import com.spiritbeing.powerball.service.PowerBallService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FrequencyServiceImpl extends Constants implements FrequencyService {

    private final PowerBallService powerBallService;

    public FrequencyServiceImpl(PowerBallService powerBallService) {
        this.powerBallService = powerBallService;
    }

    private List<BallsFrequency> ballsFrequency() {
        List<BallsFrequency> ballsFrequencies = new LinkedList<>();
        Map<Integer, Integer> whiteBallsFrequencies = powerBallService.whiteBall();
        Map<Integer, Integer> redBallsFrequencies= powerBallService.redBall();

        whiteBallsFrequencies.forEach((key, value) -> {
            int whiteBallValue = whiteBallsFrequencies.get(key);
            Integer redBallValue = redBallsFrequencies.get(key);
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

    @Override
    public Page<BallsFrequency> findPaginated(Pageable pageable) {
        List<BallsFrequency> ballsFrequencyList = ballsFrequency();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<BallsFrequency> list;

        if (ballsFrequency().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ballsFrequency().size());
            list = ballsFrequencyList.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), ballsFrequencyList.size());
    }

    @Override
    public List<BallsFrequency> top10() {

        Map<Integer, Integer> top10White = sortedHashMapByValueDescOrder(powerBallService.findTop10WhiteBalls());
        Map<Integer, Integer> top10Red = sortedHashMapByValueDescOrder(powerBallService.findTop10RedBalls());

        return getTop10Mapping(top10White, top10Red);
    }



    private List<BallsFrequency> getTop10Mapping(Map<Integer, Integer> top10White, Map<Integer, Integer> top10Red) {
        List<BallsFrequency> top10 = new LinkedList<>();
        List<Integer> whiteValueList = new LinkedList<>(top10White.values());
        List<Integer> redValueList = new LinkedList<>(top10Red.values());

        for (int i = 0; i < whiteValueList.size(); i++) {
            BallsFrequency ballsFrequency = new BallsFrequency((i+1), whiteValueList.get(i),
                    String.valueOf(redValueList.get(i)));

            top10.add(ballsFrequency);
        }
        return top10;
    }


    private List<Integer> getAllBalls(){
        List<Integer> whiteBallsCollection = new ArrayList<>();
        powerBallService.findAll().forEach(powerBall -> {
            whiteBallsCollection.add(powerBall.getBall_1());
            whiteBallsCollection.add(powerBall.getBall_2());
            whiteBallsCollection.add(powerBall.getBall_3());
            whiteBallsCollection.add(powerBall.getBall_4());
            whiteBallsCollection.add(powerBall.getBall_5());
            whiteBallsCollection.add(powerBall.getBall_6());
        });

        return whiteBallsCollection;
    }

    @Override
    public Map<Integer, Integer> allBallsSortedMap() {
        Map<Integer, Integer> allBalls = new HashMap<>();
        getAllBalls().forEach(ball -> {
            if(!allBalls.containsKey(ball)){
                allBalls.put(ball, INIT_VALUE);
            }else{
                int value = allBalls.get(ball);
                allBalls.put(ball, value + INIT_VALUE);
            }
        });


        return sortedHashMapByKeyAscOrder(allBalls);
    }

    @Override
    public Long whiteTotalValue() {
        Map<Integer, Integer> whiteBalls = powerBallService.whiteBall();
        return whiteBalls.values().stream().reduce(Integer::sum).orElse(null).longValue();
    }

    @Override
    public Long redTotalValue() {
        Map<Integer, Integer> redBalls = powerBallService.redBall();
        return redBalls.values().stream().reduce(Integer::sum).orElse(null).longValue();
    }


}
