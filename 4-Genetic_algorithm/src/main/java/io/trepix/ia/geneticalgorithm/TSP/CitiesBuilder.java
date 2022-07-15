package io.trepix.ia.geneticalgorithm.TSP;

import java.util.*;
import java.util.stream.Collectors;

import static io.trepix.ia.geneticalgorithm.TSP.City.*;

public class CitiesBuilder {
    Set<CityBuilder> cityBuilders;

    public CitiesBuilder() {
        cityBuilders = new HashSet<>();
    }
    public CitiesBuilder with(CityBuilder cityBuilder) {
        cityBuilders.add(cityBuilder);
        return this;
    }
    public List<City> build() {

        List<String> missingCities = cityBuilders.stream()
                .flatMap(x -> x.distances.keySet().stream())
                .distinct()
                .filter(name -> cityBuilders.stream().noneMatch(x -> x.name.equals(name)))
                .toList();

        missingCities.stream().map(CityBuilder::city).forEach(cityBuilders::add);

        for (CityBuilder builder : cityBuilders) {

            builder.distances.forEach((cityName, distance) -> addDistanceToCityBuilders(builder.name, cityName, distance));
        }
        return cityBuilders.stream().map(x -> new City(x.name, x.cityDistances())).toList();
    }

    private void addDistanceToCityBuilders(String origin, String destination, int distance) {
        for (CityBuilder builder : cityBuilders) {
            if (builder.name.equals(destination) && !builder.distances.containsKey(destination)) {
                builder.distances.put(origin, distance);
            }
        }
    }

    public static class CityBuilder {
        private String name;
        private Map<String, Integer> distances;

        public static CityBuilder city(String name) {
            CityBuilder builder = new CityBuilder();
            builder.name = name;
            builder.distances = new HashMap<>();
            return builder;
        }

        public CityBuilder distanceTo(String name, int distance) {
            this.distances.put(name, distance);
            return this;
        }

        private Set<CityDistance> cityDistances() {
            return distances.entrySet().stream()
                    .map(entry -> new CityDistance(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toSet());
        }
    }

}
