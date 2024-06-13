package hr.algebra.threerp3.tictactoe3rp3.repository;

import hr.algebra.threerp3.tictactoe3rp3.model.Village;

import java.util.Set;

public interface VillageRepository {
    Set<Village> getVillages() throws Exception;
}
