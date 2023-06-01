package com.example.projectgui.recyclerViewWeekday;

import java.util.HashMap;
import java.util.Map;

public class DataClass {
    private final Map<Integer, String> undergrounds = new HashMap<>();

    public DataClass() {
        undergrounds.put(0, "Охотный Ряд");
        undergrounds.put(1, "Театральная");
        undergrounds.put(2, "Площадь Революции");
        undergrounds.put(3, "Лубянка");
        undergrounds.put(4, "Арбатская");
        undergrounds.put(5, "Курская");
        undergrounds.put(6, "Новокузнецкая");
        undergrounds.put(7, "Библиотека имени Ленина");
        undergrounds.put(8, "Чистые пруды");
        undergrounds.put(9, "Кузнецкий мост");
    }

    public boolean containsUnderground(String underground) {
        boolean ans = false;
        for (Map.Entry<Integer, String> pair: undergrounds.entrySet()) {
            if (pair.getValue().equals(underground)) {
                ans = true;
                break;
            }
        }
        return ans;
    }

    public String returnUndergroundById(int id) {
        String text = "";
        for (Map.Entry<Integer, String> pair: undergrounds.entrySet()) {
            if (pair.getKey() == id) {
                text = pair.getValue();
            }
        }
        return text;
    }

    public Map<Integer, String> getUndergrounds() {
        return undergrounds;
    }
}
