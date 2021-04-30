package com.example.jeudelavie;

import com.example.jeudelavie.model.daos.DataSourceFactory;
import com.example.jeudelavie.model.daos.ExampleDao;
import com.example.jeudelavie.model.entities.Example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JeuDeLaVieApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(JeuDeLaVieApplication.class, args);
    }

}
