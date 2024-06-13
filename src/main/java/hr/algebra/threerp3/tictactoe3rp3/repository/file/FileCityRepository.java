package hr.algebra.threerp3.tictactoe3rp3.repository.file;

import hr.algebra.threerp3.tictactoe3rp3.model.City;
import hr.algebra.threerp3.tictactoe3rp3.repository.CityRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class FileCityRepository implements CityRepository {
    @Override
    public Set<City> getCities() throws Exception {
        Set<City> cities = new TreeSet<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(FileCityRepository
                        .class
                        .getResourceAsStream("/hr/algebra/threerp3/tictactoe3rp3/txtfiles/city.txt"))))) {
            String line;
            while ((line = br.readLine()) != null) {
                City city = new City(line.trim());
                cities.add(city);
            }
        }
        return new TreeSet<>(cities);
    }
}
