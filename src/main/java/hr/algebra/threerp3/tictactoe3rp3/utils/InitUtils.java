package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.controller.GameController;
import hr.algebra.threerp3.tictactoe3rp3.repository.RepositoryFactory;

public final class InitUtils {
    private InitUtils() {
    }

    public static void initRepository() {
        GameController.countryRepository = RepositoryFactory.getCountryRepository();
        GameController.cityRepository = RepositoryFactory.getCityRepository();
        GameController.villageRepository = RepositoryFactory.getVillageRepository();
        GameController.riverRepository = RepositoryFactory.getRiverRepository();
    }

    public static void initSets() throws Exception {
        GameController.countries = GameController.countryRepository.getCountries();
        GameController.cities = GameController.cityRepository.getCities();
        GameController.villages = GameController.villageRepository.getVillages();
        GameController.rivers = GameController.riverRepository.getRivers();
    }

}
