package org.example;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Calculate {
    private final int[][] graph;

    private final int[] distance;

    boolean[] ifWas ;
    int[] huina;

    public Calculate(int[][] graph) {
        this.graph = graph;
        distance  = new int[graph.length];
        ifWas = new boolean[graph.length];
        huina = IntStream.rangeClosed(0,graph.length - 1).toArray();
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(ifWas, false);
    }


    private int[] find(int source, int drain) {
        int[] distance_source = Arrays.copyOf(calculate(source), graph.length);

        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(ifWas, false);

        int[] distance_drain = Arrays.copyOf(calculate(drain), graph.length);
        int min = Integer.MAX_VALUE, min_index = 0;
        for (int i=0; i < graph.length; i++) {
            distance_source[i] +=distance_drain[i];
            /*if (distance_source[i] < min) {
                min = distance_source[i];
                min_index = i;
            }*/
        }
        return distance_source;
    }

    private int[] calculate(int source) {
        distance[source] = 0;
        ifWas[source] = true;

        len(source, ifWas, huina);

        return distance;
    }
    private void len (int point, boolean[] ifWas, int[] huina) {

        for (int i=0; i < graph[point].length; i++) {
            if (graph[point][i] + distance[point] < distance[i] && !ifWas[i] && graph[point][i] != 0) {
                distance[i] = graph[point][i] + distance[point];
            }
        }
        ifWas[point] = true;
        for (int g : huina) {
            if (graph[point][g]!=0 && ifFalseExist() && !ifWas[g]) {
                len(g, ifWas, huina);
            }
        }
    }
    public boolean ifFalseExist() {
        for (boolean i : ifWas) if (!i) return true;
        return false;
    }

    public int[] out(int source, int drain) {
        return find(source, drain);
    }


}
