package org.anothercreator.webapp.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DataSourceDefinition(
        name = "java:app/jdbc/itmd4515DS",
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        portNumber = 3306,
        serverName = "localhost",
        databaseName = "webapp",
        user = "webapp",
        password = "webapp",
        properties = {
                "zeroDateTimeBehavior=CONVERT_TO_NULL",
                "serverTimezone=America/Chicago",
                "useSSL=false"
        }
)

public class DatabaseConfig {

}
