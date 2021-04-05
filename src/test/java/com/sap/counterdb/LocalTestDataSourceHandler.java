package com.sap.counterdb;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.MySQLContainer;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile("testlocal")
@Slf4j
public class LocalTestDataSourceHandler implements TestDataSourceHandler {
    private static final String DB_BACKUP_FILENAME = "tmp/db_back_up.sql";
    private MySQLContainer<CounterMysqlContainer> mysql;

    public LocalTestDataSourceHandler() {
        this.mysql = CounterMysqlContainer.getInstance();
    }

    @PostConstruct
    public void initDatabase() {
        mysql.start();
        Flyway flyway = Flyway.configure()
            .dataSource(mysql.getJdbcUrl(), mysql.getUsername(), mysql.getPassword())
            .load();
        flyway.migrate();
    }

    @Override
    public void saveDbState() throws IOException, InterruptedException {
        log.debug("Creating database backup.");
        mysql.execInContainer("mysqldump", "--password=" + mysql.getPassword(), "--databases", mysql.getDatabaseName(), "--result-file=" + DB_BACKUP_FILENAME);
    }

    @Override
    public void restoreDbState() throws IOException, InterruptedException {
        log.debug("Restoring database backup.");
        mysql.execInContainer("mysql", "--password=" + mysql.getPassword(), "-e", "source " + DB_BACKUP_FILENAME);
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
            .create()
            .url(mysql.getJdbcUrl())
            .username(mysql.getUsername())
            .password(mysql.getPassword())
            .build();
    }

}
