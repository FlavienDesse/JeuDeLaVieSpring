package com.example.jeudelavie.daos;

import com.example.jeudelavie.model.daos.DataSourceFactory;
import com.example.jeudelavie.model.daos.ExampleDao;
import com.example.jeudelavie.model.entities.Example;

import org.assertj.core.api.AbstractFileAssert;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Assert;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleDaoTestCase {

    private ExampleDao exampleDao = new ExampleDao();

    private List<Example> allExamples = new ArrayList<>();

    @Before
    public void initDb() throws Exception {
        Connection connection = DataSourceFactory.getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE example");

        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS example" +
                        " (" +
                        "id    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "name    TEXT NOT NULL," +
                        "board BLOB " +
                        ");"
        );


        byte[] ship = {
                3,3,0,1,0,0,0,1,1,1,1
        };

        String query = "INSERT INTO example(id,name, board) VALUES (?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, 0);pstmt.setString(2, "Ship");pstmt.setBytes(3, ship);
        pstmt.execute();

        allExamples.add(new Example(0,new int[][]{
                {0,1,0},
                {0,0,1},
                {1,1,1},
        },"Ship"));


        stmt.close();
        connection.close();
    }


    @Test
    public void shouldGetAllExamples() {
        List<Example> res= exampleDao.getAllExample();
        assertThat(res).hasSize(this.allExamples.size());
        Assert.assertEquals(res,this.allExamples);

    }


}
