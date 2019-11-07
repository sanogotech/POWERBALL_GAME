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
    public Map<Integer, Long> getChartCoordinates() {

            Map<Integer, Long> coordinates = new LinkedHashMap<>();
            Map<Integer, Long> sortedMap = frequencyService.allBallsSortedMap();
            //15
            int condition = sortedMap.size()% NUMBER_OF_HISTOGRAM;
            int divisorValue = sortedMap.size() / NUMBER_OF_HISTOGRAM;

            int divisor =(condition != 0) ? divisorValue + 1 : divisorValue;

            for (int i = 0; i < NUMBER_OF_HISTOGRAM; i++) {

                int min = initial_Value + (i * divisor);
                int max = divisor * (i+1);
                Long y = yCoordinate(sortedMap, min, max);
                int x = MULTIPLIER * max;
                coordinates.put(x, y);
            }

            return coordinates;

    }

//    @Override
//    public Map<Integer, Long> getCanvasJsChartData() { // extends Chart
//        Map<Integer, Long> coordinates = frequencyService.getChartCoordinates();
//
//        Map<Object, Object> map = new LinkedHashMap<>();
//        for(Map.Entry entry : coordinates.entrySet()){
//
//            map.put("x", entry.getKey());
//            map.put("y", entry.getValue());
//            if(entry.getValue() == getMinValue(coordinates)){
//                map.put("indexLabel", "Lowest");
//            }
//            if(entry.getValue() == getMaxValue(coordinates)){
//                map.put("indexLabel", "Highest");
//            }
//        }
//
//        return map;
//    }
}
