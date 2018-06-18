package com.infosystem.springmvc.db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class V1_2__add_options_21 implements JdbcMigration {

    public void migrate(Connection connection) throws Exception {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO tariff_options(name, price,costofadd,description) VALUES (?,?,?,?);");
        for (int i = 0; i < 31; i++) {
            statement.setString(1, "Option" + (i + 1));
            statement.setDouble(2, (double) (i + 1));
            statement.setDouble(3, (double) (i * 2 + 2));
            statement.setString(4, "optionDescription" + (i + 1));
            statement.addBatch();
        }
        try {
            statement.executeBatch();
        } finally {
            statement.close();
        }
    }

}
