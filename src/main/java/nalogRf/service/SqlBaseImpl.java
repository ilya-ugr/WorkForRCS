package nalogRf.service;

import nalogRf.entity.NalogTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service
public class SqlBaseImpl {
    private static String QUERY_TEMPLATE = "SELECT %s row, %s col, v val FROM source_data GROUP BY 1, 2";
    private static String DRIVER = "org.sqlite.JDBC";
    private static String URL = "jdbc:sqlite:src/main/resources/data.sqlite";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private List<NalogTable> nalogTableList;

    @Autowired
    public SqlBaseImpl(List<NalogTable> nalogTableList) {
        this.nalogTableList = nalogTableList;
    }

    public List<NalogTable> getResponse(String row, String col) {
        if (connection == null) {
            connect();
        }
        readDb(row, col);
        closeConnect();
        return nalogTableList;
    }

    private Connection connect() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void readDb(String row, String col) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(QUERY_TEMPLATE, row, col));
            while (resultSet.next()) {
                nalogTableList.add(NalogTable.builder()
                        .row(resultSet.getString(1))
                        .col(resultSet.getString(2))
                        .value(resultSet.getLong(3))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnect() {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
