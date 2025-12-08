package com.wings.database;

import java.sql.SQLException;

//Functional contracts to pass exceptions to the repository implementation layer
@FunctionalInterface
public interface SQLConsumer<T> {
    void accept(T t) throws SQLException;
}
