package jp.softrain.coinAPI.database;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jp.softrain.coinAPI.Main;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {

    private Main instance;
    private HikariDataSource dataSource;

    public DatabaseManager(Main instance){
        this.instance = instance;

        HikariConfig config = new HikariConfig();

        String host = instance.getDatabaseConfig().getConfig().getString("host");
        int port = instance.getDatabaseConfig().getConfig().getInt("port");
        String database = instance.getDatabaseConfig().getConfig().getString("database");
        String username = instance.getDatabaseConfig().getConfig().getString("username");
        String password = instance.getDatabaseConfig().getConfig().getString("password");
        int maxPoolSize = instance.getDatabaseConfig().getConfig().getInt("max-pool-size");

        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database );
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        config.setMaximumPoolSize(maxPoolSize);

        dataSource = new HikariDataSource(config);

        createTables();
    }

    public void createTables(){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS coins(uuid VARCHAR(36) PRIMARY KEY, balance DECIMAL(20, 2) NOT NULL DEFAULT 00.00)")){

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            instance.getLogger().severe(e.getMessage());
        }
    }

    public Connection getConnection(){
        try{
            return dataSource.getConnection();
        }catch (SQLException e){
            instance.getLogger().severe(e.getMessage());
        }
        return null;
    }

    //AddMoney

    public void addMoney(UUID uuid, BigDecimal amount){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO coins(uuid, balance) VALUES (?, ?) ON DUPLICATE KEY UPDATE balance = balance + VALUES(balance)")){

            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setBigDecimal(2, amount);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            instance.getLogger().severe(e.getMessage());
        }

    }
    //IsRegistered
    public boolean isRegistered(UUID uuid) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1 FROM coins WHERE uuid = ?")) {

            preparedStatement.setString(1, uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        } catch (SQLException e){
            instance.getLogger().severe(e.getMessage());
        }
        return false;
    }

    //InitializePlayer

    public void initializePlayer(UUID uuid){
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO coins(uuid, balance) VALUES (?,?)")){

            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setBigDecimal(2,new BigDecimal(0.00));

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            instance.getLogger().severe(e.getMessage());
        }
    }

    //RemoveMoney
    public void removeMoney(UUID uuid, BigDecimal amount){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO coins(uuid, balance) VALUES (?, ?) ON DUPLICATE KEY UPDATE balance = balance - VALUES(balance)")){

            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setBigDecimal(2, amount);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            instance.getLogger().severe(e.getMessage());
        }

    }
    //setMoney
    public void setMoney(UUID uuid, BigDecimal amount){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO coins(uuid, balance) VALUES (?, ?) ON DUPLICATE KEY UPDATE balance = VALUES(balance)")){

            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setBigDecimal(2, amount);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            instance.getLogger().severe(e.getMessage());
        }

    }
    //resetMoney
    public void resetMoney(UUID uuid){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO coins(uuid, balance) VALUES (?, 00.00) ON DUPLICATE KEY UPDATE balance = 00.00")){

            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            instance.getLogger().severe(e.getMessage());
        }

    }
    public BigDecimal getMoney(UUID uuid){
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM coins WHERE uuid = ?")){

            preparedStatement.setString(1, uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){

                return resultSet.getBigDecimal("balance");
            }
        }catch (SQLException e){
            instance.getLogger().severe(e.getMessage());
        }
        return BigDecimal.ZERO;
    }
}
