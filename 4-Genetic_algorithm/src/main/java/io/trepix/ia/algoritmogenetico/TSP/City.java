package io.trepix.ia.algoritmogenetico.TSP;

import io.trepix.ia.algoritmogenetico.Gene;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class City implements Gene {

    private final Map<String, Integer> cityDistances;
    private final String name;

    public City(String name, Set<CityDistance> distances) {
        this.name = name;
        this.cityDistances = distances.stream().collect(toMap(CityDistance::city, CityDistance::distance));
        this.cityDistances.put(name, 0);
    }

    public String name() {
        return this.name;
    }

    protected int distanceTo(City city) {
        return cityDistances.get(city.name);
    }

    @Override
    public void mutate() {
        throw new UnsupportedOperationException("City can't mutate");
    }

    @Override
    public String toString() {
        return name;
    }

    public record CityDistance(String city, int distance) {
    }
}
