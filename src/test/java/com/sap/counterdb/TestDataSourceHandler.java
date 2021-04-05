package com.sap.counterdb;

import java.io.IOException;

public interface TestDataSourceHandler {

    void saveDbState() throws IOException, InterruptedException;

    void restoreDbState() throws IOException, InterruptedException;
}
