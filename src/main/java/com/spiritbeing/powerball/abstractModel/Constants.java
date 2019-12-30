package com.spiritbeing.powerball.abstractModel;

import java.util.*;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

public abstract class Constants {
    protected final int INIT_VALUE = 1;
    protected final int BUTTONS_TO_SHOW = 3;
    protected final int INITIAL_PAGE = 0;
    protected final int INITIAL_PAGE_SIZE = 5;
    protected final int[] PAGE_SIZES = { 5, 10};

    protected Map<Integer, Integer> sortedHashMapByValueDescOrder(Map<Integer, Integer> myMap){
       return myMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
    }

    protected Map<Integer, Integer> sortedHashMapByKeyAscOrder(Map<Integer, Integer> myMap){
        return myMap.entrySet()
                .stream()
                .sorted(comparingByKey())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
    }



}
