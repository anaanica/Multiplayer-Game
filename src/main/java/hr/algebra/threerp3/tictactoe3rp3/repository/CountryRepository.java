package hr.algebra.threerp3.tictactoe3rp3.repository;

import hr.algebra.threerp3.tictactoe3rp3.model.Country;

import java.util.Set;

public interface CountryRepository {
    Set<Country> getCountries() throws Exception;
}
