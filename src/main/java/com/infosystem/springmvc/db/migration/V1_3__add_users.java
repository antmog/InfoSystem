package com.infosystem.springmvc.db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class V1_3__add_users implements JdbcMigration {

    public void migrate(Connection connection) throws Exception {

        PreparedStatement statement = connection.prepareStatement("INSERT INTO `users` " +
                "(`ADRESS`,`BALANCE`,`BIRTH_DATE`,`FIRST_NAME`,`LAST_NAME`,`LOGIN`,`MAIL`,`PASSPORT_ID`, " +
                "`PASSWORD`,`ROLE`, `STATUS`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

        statement.setString(1, "Address1");
        statement.setDouble(2, 0.0);
        statement.setString(3, "1991-06-20");
        statement.setString(4, "Moguyanov");
        statement.setString(5, "Abdul");
        statement.setString(6, "antmog");
        statement.setString(7, "obouuzi@t-systems.kz");
        statement.setInt(8, 225194338);
        statement.setString(9, "$2a$10$B7jg5.qngCxHJ1xcCVOBpOatk8APp/AR4HvMTLnAqVOEVFvrAoTM6");
        statement.setString(10, "ADMIN");
        statement.setString(11, "ACTIVE");
        statement.addBatch();

        statement.setString(1, "Address2");
        statement.setDouble(2, 0.0);
        statement.setString(3, "1993-04-12");
        statement.setString(4, "Katharina");
        statement.setString(5, "Adele");
        statement.setString(6, "kadele");
        statement.setString(7, "sohot@t-systems.kz");
        statement.setInt(8, 157426725);
        statement.setString(9, "$2a$10$B7jg5.qngCxHJ1xcCVOBpOatk8APp/AR4HvMTLnAqVOEVFvrAoTM6");
        statement.setString(10, "CUSTOMER");
        statement.setString(11, "ACTIVE");
        statement.addBatch();


        try {
            statement.executeBatch();
        } finally {
            statement.close();
        }
    }
}
