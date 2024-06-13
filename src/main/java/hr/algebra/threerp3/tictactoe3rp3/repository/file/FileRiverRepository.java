package hr.algebra.threerp3.tictactoe3rp3.repository.file;

import hr.algebra.threerp3.tictactoe3rp3.model.River;
import hr.algebra.threerp3.tictactoe3rp3.repository.RiverRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class FileRiverRepository implements RiverRepository {
    @Override
    public Set<River> getRivers() throws Exception {
        Set<River> rivers = new TreeSet<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(FileRiverRepository
                        .class
                        .getResourceAsStream("/hr/algebra/threerp3/tictactoe3rp3/txtfiles/river.txt"))))) {
            String line;
            while ((line = br.readLine()) != null) {
                River river = new River(line.trim());
                rivers.add(river);
            }
        }
        return new TreeSet<>(rivers);
    }
}
