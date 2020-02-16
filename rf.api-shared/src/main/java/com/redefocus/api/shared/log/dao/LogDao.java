package com.redefocus.api.shared.log.dao;

import com.redefocus.common.shared.databases.mysql.dao.Table;
import com.redefocus.api.shared.log.data.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by @SrGutyerrez
 */
public class LogDao extends Table {
    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "logs";
    }

    @Override
    public void createTable() {
        for (Log.LogType logType : Log.LogType.values()) {
            if (!logType.isTable()) continue;

            this.execute(
                    String.format(
                            "CREATE TABLE IF NOT EXISTS %s " +
                                    "(" +
                                    "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                    "`user_id` INTEGER NOT NULL," +
                                    "`staff` BOOLEAN NOT NULL," +
                                    "`time` LONG NOT NULL," +
                                    "`network` VARCHAR(255) NOT NULL," +
                                    "`server_id` INTEGER NOT NULL," +
                                    "`type` VARCHAR(255) NOT NULL," +
                                    "`sub_type` VARCHAR(255) NOT NULL," +
                                    "`value` TEXT" +
                                    ");",
                            this.getTableName() + logType.getTableName()
                    )
            );
        }
    }

    public <T extends Log> void insert(T object) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`user_id`," +
                        "`staff`," +
                        "`time`," +
                        "`network`," +
                        "`server_id`," +
                        "`type`," +
                        "`sub_type`," +
                        "`value`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "%b," +
                        "%d," +
                        "'%s'," +
                        "%d," +
                        "'%s'," +
                        "'%s'," +
                        "'%s'" +
                        ");",
                this.getTableName() + object.getType().getTableName(),
                object.getUserId(),
                object.isStaff(),
                object.getTime(),
                object.getNetwork(),
                object.getServerId(),
                object.getType().toString(),
                object.getSubType().toString(),
                object.getValue()
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
