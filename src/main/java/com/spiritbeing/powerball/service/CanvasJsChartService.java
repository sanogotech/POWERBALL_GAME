package com.spiritbeing.powerball.service;

        import java.util.List;
        import java.util.Map;

public interface CanvasJsChartService {
    //use map for occurrences >>>Map<Byte, Integer>
    //stack all divs in a row
    //<div><div> OR JUST USE TABLE
    List<List<Map<Object, Object>>> getCanvasJsChartData();
}

