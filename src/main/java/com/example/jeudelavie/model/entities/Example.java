package com.example.jeudelavie.model.entities;

import java.util.Arrays;
import java.util.Objects;

public class Example {
    int id;
    int [][] cells;
    String name;

    public Example(int id ,int[][] cells,String name) {
        this.id = id;
        this.name = name;
        this.cells = cells;
    }


    public int[][] getCells() {
        return cells;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Example)) return false;
        Example example = (Example) o;
        return id == example.id && Arrays.deepEquals(cells, example.cells) && name.equals(example.name);
    }



    @Override
    public String toString() {
        return "Example{" +
                "id=" + id +
                ", cells=" + Arrays.toString(cells) +
                ", name='" + name + '\'' +
                '}';
    }
}
