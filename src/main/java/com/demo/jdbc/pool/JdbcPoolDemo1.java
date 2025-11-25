package com.demo.jdbc.pool;

import com.demo.jdbc.single.Student;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPoolDemo1 {

    //    关注下这里的query、path（就是table名称-learnjdbc）
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "dyx123456";

    public static void main(String[] args) throws IOException {
        // 创建连接池
        HikariConfig config = new HikariConfig();
        // 基本配置
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(JDBC_USER);
        config.setPassword(JDBC_PASSWORD);

        // hikari cachePrepStmts 是一个用于启用预编译SQL语句缓存的HikariCP配置，当设置为 true 时，HikariCP会缓存预编译语句，以提高性能。
        config.addDataSourceProperty("cachePrepStmts", "true");
        // prepStmtCacheSize 用于指定每个数据库连接可缓存的预编译语句（PreparedStatement）的数量，250常见配置
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        // prepStmtCacheSqlLimit 用于指定每个数据库连接可缓存的预编译语句（PreparedStatement）的长度限制，2048常见配置
        // prepStmtCacheSize 和 prepStmtCacheSqlLimit 区别是：
        //  prepStmtCacheSize 是指每个数据库连接可缓存的预编译语句（PreparedStatement）的数量，
        //  而 prepStmtCacheSqlLimit 是指每个数据库连接可缓存的预编译语句（PreparedStatement）的长度限制。
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        DataSource datasource = new HikariDataSource( config);
        try {
            List<Student> students = queryStudents(datasource);
            students.forEach(System.out::println);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    static List<Student> queryStudents(DataSource ds) throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = ds.getConnection()) { // 在此获取连接
            try (PreparedStatement ps = conn
                    .prepareStatement("SELECT * FROM students WHERE grade = ? AND score >= ?")) {
                ps.setInt(1, 3); // 第一个参数grade=?
                ps.setInt(2, 90); // 第二个参数score=?
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        students.add(extractRow(rs));
                    }
                }
            }
        } // 在此“释放”连接
        return students;
    }

    static Student extractRow(ResultSet rs) throws SQLException {
        var std = new Student();
        std.setId(rs.getLong("id"));
        std.setName(rs.getString("name"));
        std.setGender(rs.getBoolean("gender"));
        std.setGrade(rs.getInt("grade"));
        std.setScore(rs.getInt("score"));
        return std;
    }
}
