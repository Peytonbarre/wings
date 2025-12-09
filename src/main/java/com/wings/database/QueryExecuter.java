package com.wings.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecuter {
        public static <T> T executeQuery(String sql, SQLConsumer<PreparedStatement> paramSetter, SQLFunction<ResultSet, T> mapper) throws SQLException {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            paramSetter.accept(pstmt);
            ResultSet rs = pstmt.executeQuery();
            T result = mapper.apply(rs);
            rs.close();
            pstmt.close();
            return result;
        }

        public static void executeUpdate(String sql, SQLConsumer<PreparedStatement> paramSetter) throws SQLException {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            paramSetter.accept(pstmt);
            pstmt.executeUpdate();
            pstmt.close();
        }
}