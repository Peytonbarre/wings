package com.wings.database;

import java.sql.SQLException;

//Functional contracts to pass exceptions to the repository implementation layer
@FunctionalInterface
public interface SQLFunction<T, R> {
    R apply(T t) throws SQLException;
}