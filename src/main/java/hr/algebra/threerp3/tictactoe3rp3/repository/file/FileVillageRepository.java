package hr.algebra.threerp3.tictactoe3rp3.repository.file;

import hr.algebra.threerp3.tictactoe3rp3.model.Village;
import hr.algebra.threerp3.tictactoe3rp3.repository.VillageRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class FileVillageRepository implements VillageRepository {
    @Override
    public Set<Village> getVillages() throws Exception {
        Set<Village> villages = new TreeSet<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(FileVillageRepository
                        .class
                        .getResourceAsStream("/hr/algebra/threerp3/tictactoe3rp3/txtfiles/village.txt"))))) {
            String line;
            while ((line = br.readLine()) != null) {
                Village village = new Village(line.trim());
                villages.add(village);
            }
        }
        return new TreeSet<>(villages);
    }
}
