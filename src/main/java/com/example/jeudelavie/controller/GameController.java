package com.example.jeudelavie.controller;


import com.example.jeudelavie.model.daos.ExampleDao;
import com.example.jeudelavie.model.entities.Example;
import com.example.jeudelavie.model.entities.Game;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class GameController {


    @PostMapping("/game")
    public int[][] getTestData(Game model) {
        return model.calculateNextStep();
    }
    @PostMapping("/allExamples")
    public List<Example> getAllExamples() {


        ExampleDao exampleDao = new ExampleDao();


        return exampleDao.getAllExample();
    }

}
