package httpparser;

import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DB {

    private static final Logger logger = LogManager.getLogger(DB.class);

    private Connection getDBConnection() {
        Connection dbcon = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.info(e.getMessage());
        }

        try {
            dbcon = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "empty");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return dbcon;
    }

    private String CurrentDate() {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return (format.format(d));
    }

    public void insertDB(String ip, int port, String type) {
        Connection dbConnection = null;
        Statement statement = null;
        String SQL1 = "INSERT INTO proxy (ip, port, type, date) SELECT " + "'" + ip + "', " + port + ", " + "'" + type + "', " + "to_date('" + CurrentDate() + "', 'DD MM YYYY HH24 MI') WHERE NOT EXISTS (SELECT 1 FROM proxy WHERE ip=" + "'" + ip + "'" + " AND port=" + port + ")";
        String SQL2 = "UPDATE proxy SET type='" + type + "', date=to_date('" + CurrentDate() + "', 'DD MM YYYY HH24 MI') WHERE ip='" + ip + "'";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            int rowcount = statement.executeUpdate(SQL1);
            if (rowcount == 0) {
                statement.execute(SQL2);
            };
        } catch (SQLException e) {
            logger.info(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.info(e.getMessage());
                }
            }
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    logger.info(e.getMessage());
                }
            }
        }
    }
}
