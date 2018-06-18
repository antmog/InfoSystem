package com.infosystem.springmvc.db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class V1_6__add_advProfiles implements JdbcMigration {
    public void migrate(Connection connection) throws Exception {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO adv_profile(name, status) VALUES (?,?);");
        for (int i = 0; i < 3; i++) {
            statement.setString(1, "Profile" + (i + 1));
            if(i==0){
                statement.setString(2, "ACTIVE");
            }else{
                statement.setString(2, "INACTIVE");
            }
            statement.addBatch();
        }
        try {
            statement.executeBatch();
        } finally {
            statement.close();
        }
    }
}
