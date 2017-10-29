/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tikape.runko.domain.AineListaus;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaAine;
import tikape.runko.domain.RaakaAine;

public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {

    private Database database;

    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        //
        return null;
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    @Override
    public AnnosRaakaAine saveOrUpdate(AnnosRaakaAine object) throws SQLException {
        // simply support saving -- disallow saving if task with
        // same name exists
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO AnnosRaakaAine (annosId, raakaAineId, jarjestys, maara, ohje) VALUES (?,?,?,?,?)");
            stmt.setInt(1, object.getAnnosId());
            stmt.setInt(2, object.getRaakaAineId());
            stmt.setInt(3, object.getJarjestys());
            stmt.setString(4, object.getMaara());
            stmt.setString(5, object.getOhje());
           
            stmt.executeUpdate();
        }
 
        return findByAnnosIdAndRaakaAineId(object.getAnnosId(), object.getRaakaAineId());
    }
    
    private AnnosRaakaAine findByAnnosIdAndRaakaAineId(Integer annosId, Integer raakaAineId) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT annosId, raakaAineId, jarjestys, maara, ohje FROM AnnosRaakaAine WHERE annosId = ? AND raakaAineId = ?");
            stmt.setInt(1, annosId);
            stmt.setInt(2, raakaAineId);
           
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
 
            return new AnnosRaakaAine(result.getInt("annosId"), result.getInt("raakaAineId"), result.getInt("jarjestys"), result.getString("maara"), result.getString("ohje"));

        }
    }
    
    public List<AineListaus> findAineListaus(Integer annosKey) throws SQLException {
        String query = "SELECT AnnosRaakaAine.maara, AnnosRaakaAine.ohje, RaakaAine.nimi FROM AnnosRaakaAine\n"
                   + "      INNER JOIN Annos ON Annos.id = AnnosRaakaAine.annosId "
                   + "      INNER JOIN RaakaAine ON AnnosRaakaAine.raakaAineId = RaakaAine.id "
                   + "      WHERE Annos.id = ?"
                   + "      ORDER BY AnnosRaakaAine.jarjestys";

        List<AineListaus> tasks = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, annosKey);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                String ohje = result.getString("ohje");
                if (ohje.isEmpty()) {
                    ohje = " ";
                }
                tasks.add(new AineListaus(result.getString("nimi"), result.getString("maara"), ohje));
            }
        }

        return tasks;
    }

}
