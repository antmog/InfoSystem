package com.infosystem.springmvc.db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class V1_5__add_contracts implements JdbcMigration {

    public void migrate(Connection connection) throws Exception {

        PreparedStatement statement = connection.prepareStatement("INSERT INTO `contracts`" +
                "(`PHONE_NUMBER`,`CONTRACT_PRICE`,`CONTRACT_STATUS`,`CONTRACT_TARIFF_ID`,`CONTRACT_USER_ID`)\n" +
                "VALUES( ?,?,?,?,?);");

        for (int i = 0; i < 4; i++) {
            statement.setString(1, "+79811337"+(i+1)*100);
            statement.setDouble(2, (double)(i+1));
            statement.setString(3, "ACTIVE");
            statement.setInt(4, (i+1));
            statement.setInt(5, 2);
            statement.addBatch();
        }
        try {
            statement.executeBatch();
        } finally {
            statement.close();
        }
    }
}
