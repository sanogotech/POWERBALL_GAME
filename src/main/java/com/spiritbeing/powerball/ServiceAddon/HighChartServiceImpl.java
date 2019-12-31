package com.spiritbeing.powerball.ServiceAddon;

import com.spiritbeing.powerball.abstractModel.Chart;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class HighChartServiceImpl extends Chart implements HighChartService {

    private final FrequencyService frequencyService;

    public HighChartServiceImpl(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    @Override
    public Map<Integer, Integer> getChartCoordinates() {

            Map<Integer, Integer> coordinates = new LinkedHashMap<>();
            Map<Integer, Integer> sortedMap = frequencyService.allBallsSortedMap();
            //15
            int condition = sortedMap.size()% NUMBER_OF_HISTOGRAM;
            int divisorValue = sortedMap.size() / NUMBER_OF_HISTOGRAM;

            int divisor =(condition != 0) ? divisorValue + 1 : divisorValue;

            for (int i = 0; i < NUMBER_OF_HISTOGRAM; i++) {

                int min = initial_Value + (i * divisor);
                int max = divisor * (i+1);
                int y = yCoordinate(sortedMap, min, max);
                int x = MULTIPLIER * max;
                coordinates.put(x, y);
            }

            return coordinates;

    }
}
