package com.redecommunity.api.shared.commands.defaults.disable.dao;

import com.google.common.collect.Sets;
import com.redecommunity.api.shared.commands.defaults.disable.data.DisabledCommand;
import com.redecommunity.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import com.redecommunity.common.shared.databases.mysql.dao.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class DisabledCommandDao extends Table {
    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_disabled_commands";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`name` VARCHAR(255) NOT NULL," +
                                "`user_id` INTEGER NOT NULL," +
                                "`time` LONG NOT NULL" +
                                ");",
                        this.getTableName()
                )
        );
    }

    public <T extends DisabledCommand> void insert(T object) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`name`," +
                        "`user_id`," +
                        "`time`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "'%s'," +
                        "%d," +
                        "%d" +
                        ");",
                this.getTableName(),
                object.getName(),
                object.getUserId(),
                object.getTime()
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.execute();

            DisabledCommand disabledCommand = this.findOne("name", object.getName());

            DisabledCommandManager.getDisabledCommands().add(disabledCommand);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public <K, V extends Integer> void delete(K key, V value) {
        String query = String.format(
                "DELETE FROM %s WHERE %s=%d",
                this.getTableName(),
                key,
                value
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

    @Override
    public <K, V, T> T findOne(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE %s='%s'",
                this.getTableName(),
                key,
                value
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            if (resultSet.next()) {
                DisabledCommand disabledCommand = DisabledCommandManager.toDisabledCommand(resultSet);

                return (T) disabledCommand;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public <T> Set<T> findAll() {
        String query = String.format(
                "SELECT * FROM %s",
                this.getTableName()
        );

        Set<T> disabledCommands = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                DisabledCommand disabledCommand = DisabledCommandManager.toDisabledCommand(resultSet);

                disabledCommands.add((T) disabledCommand);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return disabledCommands;
    }
}
