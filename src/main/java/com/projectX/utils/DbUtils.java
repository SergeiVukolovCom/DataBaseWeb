package com.projectX.utils;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import com.projectX.models.CredentialsData;
import com.projectX.models.HostsData;
import com.projectX.models.TestsData;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class DbUtils {
    private final CredentialsData credentialsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToCredentials"), CredentialsData.class);
    private final TestsData testsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToTestsData"), TestsData.class);
    private final HostsData hostsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToHosts"), HostsData.class);
    private final Logger logger = AqualityServices.getLogger();

    @SneakyThrows
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            return DriverManager.getConnection(String.format(testsData.getDbLink(), hostsData.getHostDb(), testsData.getNameDataBase()),
                    credentialsData.getLoginDb(), credentialsData.getPasswordDb());
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException | InvocationTargetException e) {
            logger.error("Error connecting to database: " + e.getMessage());
            throw new SQLException("Unable to establish connection to database: " + e.getMessage());
        }
    }

    @SneakyThrows
    public List<ArrayList<String>> getDataFromDatabase(String query) {
        List<ArrayList<String>> data = new ArrayList<>();
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int numColumns = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (int i = 1; i <= numColumns; i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            logger.error("Error connecting to database: " + e.getMessage());
            throw new SQLException("Unable to establish connection to database: " + e.getMessage());
        }
        return data;
    }

}