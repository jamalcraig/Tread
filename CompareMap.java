package assignment2;

import java.util.Comparator;
import java.util.Map;

public class CompareMap implements Comparator<String> {

    Map<String, Integer> map;

    //constructor
    public CompareMap(Map<String, Integer> map) {
        this.map = map;
    }

    //compares the scores of the map
    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
            return -1;
        } else if (map.get(a) < map.get(b)){
            return 1;
        } else{
            return 0;
        }
    }
}
