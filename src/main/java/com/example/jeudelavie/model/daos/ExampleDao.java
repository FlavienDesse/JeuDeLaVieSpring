package com.example.jeudelavie.model.daos;

import com.example.jeudelavie.model.entities.Example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleDao {


    public Boolean addExample(Example example){
        try (Connection connection = DataSourceFactory.getConnection()) {
            String sqlQuery = "INSERT INTO example(board,name) VALUES(?,?)";
            try (PreparedStatement statement = connection.prepareStatement(
                    sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                int [][] cells = example.getCells();
                byte[] listOfBytes = new byte[cells.length * cells[0].length + 2];
                listOfBytes[0]=(byte) cells.length;
                listOfBytes[1]=(byte)cells[0].length;

                for(int i = 0 ; i < cells.length;i++){
                    for(int j = 0 ; j < cells[0].length;j++){
                        System.err.println(i*cells.length+j+2);
                        listOfBytes[i*cells.length+j+2]=(byte)cells[i][j];
                    }
                }
                statement.setBytes(1, listOfBytes);
                statement.setString(2, example.getName());

                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    keys.next();
                    return true;
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
    }

    public List<Example> getAllExample(){

        List<Example> exampleList = new ArrayList<>();

        try (Connection cnx = DataSourceFactory.getConnection()) {
            try (Statement stmt = cnx.createStatement()) {
                try (ResultSet res = stmt.executeQuery("select * from example")) {
                    while (res.next()) {
                        byte[] exampleByte = res.getBytes("board");
                        int[][] exampleInt =new int[(int)exampleByte[0]][(int)exampleByte[1]];


                        for(int i = 0;i<(int)exampleByte[0];i++){
                            for(int j = 0;j<(int)exampleByte[1];j++){
                                exampleInt[i][j]=exampleByte[i*(int)exampleByte[0]+j+2];
                            }
                        }
                        exampleList.add(new Example(res.getInt("id"),exampleInt,res.getString("name")));

                    }



                    return exampleList;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong with the db !",
                    e);
        }
    }


}
