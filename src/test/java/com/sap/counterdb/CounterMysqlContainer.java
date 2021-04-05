package com.sap.counterdb;

import org.testcontainers.containers.MySQLContainer;

public class CounterMysqlContainer extends MySQLContainer<CounterMysqlContainer> {

    private static CounterMysqlContainer container;

    private CounterMysqlContainer() {
        super();
    }

    public static CounterMysqlContainer getInstance() {
        if (container == null) {
            container = new CounterMysqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        // do nothing, JVM handles shut down
    }

}
