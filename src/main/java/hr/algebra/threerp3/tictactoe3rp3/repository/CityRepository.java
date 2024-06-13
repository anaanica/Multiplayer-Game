package hr.algebra.threerp3.tictactoe3rp3.repository;

import hr.algebra.threerp3.tictactoe3rp3.model.City;

import java.util.Set;

public interface CityRepository {
    Set<City> getCities() throws Exception;
}
