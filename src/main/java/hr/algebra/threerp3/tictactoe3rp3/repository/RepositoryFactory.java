package hr.algebra.threerp3.tictactoe3rp3.repository;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import hr.algebra.threerp3.tictactoe3rp3.repository.file.FileCityRepository;
import hr.algebra.threerp3.tictactoe3rp3.repository.file.FileCountryRepository;
import hr.algebra.threerp3.tictactoe3rp3.repository.file.FileRiverRepository;
import hr.algebra.threerp3.tictactoe3rp3.repository.file.FileVillageRepository;

import java.io.FileReader;
import java.util.Properties;

@Author(name = "Ana")
public final class RepositoryFactory {
    private static CountryRepository countryRepository;
    private static CityRepository cityRepository;
    private static VillageRepository villageRepository;
    private static RiverRepository riverRepository;

    static {
        try {
            countryRepository = createRepository(CountryRepository.class, FileCountryRepository.class.getName());
            cityRepository = createRepository(CityRepository.class, FileCityRepository.class.getName());
            villageRepository = createRepository(VillageRepository.class, FileVillageRepository.class.getName());
            riverRepository = createRepository(RiverRepository.class, FileRiverRepository.class.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T createRepository(Class<T> repositoryClass, String repositoryClassName) throws Exception {
        return repositoryClass.cast(Class.forName(repositoryClassName)
                .getDeclaredConstructor()
                .newInstance());
    }

    public static CountryRepository getCountryRepository() {
        return countryRepository;
    }

    public static CityRepository getCityRepository() {
        return cityRepository;
    }

    public static VillageRepository getVillageRepository() {
        return villageRepository;
    }

    public static RiverRepository getRiverRepository() {
        return riverRepository;
    }

}
