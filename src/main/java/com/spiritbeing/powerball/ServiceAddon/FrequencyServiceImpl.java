package com.spiritbeing.powerball.ServiceAddon;

import com.spiritbeing.powerball.model.BallsFrequency;
import com.spiritbeing.powerball.service.PowerBallService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toMap;

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

        Map<Integer, Long> top10White = powerBallService.findTop10WhiteBalls()
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        Map<Integer, Long> top10Red = powerBallService.findTop10RedBalls()
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        return getTop10Mapping(top10White, top10Red);
    }

    private List<BallsFrequency> getTop10Mapping(Map<Integer, Long> top10White, Map<Integer, Long> top10Red) {
        List<BallsFrequency> top10 = new LinkedList<>();
        List<Long> whiteValueList = new LinkedList<>(top10White.values());
        List<Long> redValueList = new LinkedList<>(top10Red.values());

        for (int i = 0; i < whiteValueList.size(); i++) {
            BallsFrequency ballsFrequency = new BallsFrequency((i+1), whiteValueList.get(i),
                    String.valueOf(redValueList.get(i)));

            top10.add(ballsFrequency);
        }
        return top10;
    }
}
