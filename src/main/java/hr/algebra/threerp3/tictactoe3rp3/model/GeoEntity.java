package hr.algebra.threerp3.tictactoe3rp3.model;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;

@Author(name = "Ana")
public abstract class GeoEntity implements Comparable<GeoEntity>{
    protected String name;
    protected Integer scoreValue;

    public GeoEntity(String name) {
        this.name = name;
        scoreValue = 5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScoreValue() {
        return scoreValue;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(GeoEntity o) { return name.compareTo(o.getName()); }
}
