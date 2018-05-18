package com.infosystem.springmvc.db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class V1_4__add_tariffs implements JdbcMigration {


    public void migrate(Connection connection) throws Exception {

        PreparedStatement statement = connection.prepareStatement("INSERT INTO `tariffs`" +
                "(`NAME`,`PRICE`,`TARIFF_STATUS`) " +
                " VALUES(?,?,?);");

        for (int i = 0; i < 4; i++) {
            statement.setString(1, "Tariff" + (i+1));
            statement.setDouble(2, (double)(i+1));
            statement.setString(3, "ACTIVE");
            statement.addBatch();
        }
        try {
            statement.executeBatch();
        } finally {
            statement.close();
        }
    }
}
