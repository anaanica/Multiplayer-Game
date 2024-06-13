package hr.algebra.threerp3.tictactoe3rp3.repository.file;

import hr.algebra.threerp3.tictactoe3rp3.model.Country;
import hr.algebra.threerp3.tictactoe3rp3.repository.CountryRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class FileCountryRepository implements CountryRepository {
    @Override
    public Set<Country> getCountries() throws Exception {
        Set<Country> countries = new TreeSet<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(FileCountryRepository
                        .class
                        .getResourceAsStream("/hr/algebra/threerp3/tictactoe3rp3/txtfiles/country.txt"))))) {
            String line;
            while ((line = br.readLine()) != null) {
                Country country = new Country(line.trim());
                countries.add(country);
            }
        }
        return new TreeSet<>(countries);
    }
}
