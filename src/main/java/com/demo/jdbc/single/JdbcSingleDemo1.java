package com.demo.jdbc.single;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 1、jdbc 基本api使用：Connection、Statement、执行、结果集合、防止SQL注入
 * 2、进阶使用：批处理：addBatch、executeBatch、事务处理 需要设置：conn.setAutoCommit(false);
 * 3、jdbc 查询结果转换成 java class
 */
public class JdbcSingleDemo1 {
//    关注下这里的query、path（就是table名称-learnjdbc）
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "dyx123456";

    public static void main(String[] args) {
//        jbdc 基本api使用
//        baseApiLearn();

        // 数据库事务提交
        // insertStudents();

        // jbdc 查询结果和class转换
        try {
            List<Student> students = queryStudents();
            students.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    // 数据库事务提交
    static void insertStudents(String name, boolean gender, int grade, int score) throws SQLException{
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            boolean isAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false); // 关闭自动提交事务，这个是事务的核心
            try (PreparedStatement ps = conn
                    .prepareStatement("INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?)")) {
                ps.setString(1, name);
                ps.setBoolean(2, gender);
                ps.setInt(3, grade);
                ps.setInt(4, score);
                int n = ps.executeUpdate();
                System.out.println(n + " inserted.");
            }
            if (score > 100) {
                conn.rollback();
                System.out.println("rollback.");

            } else {
                conn.commit();
                System.out.println("commit.");
            }
            conn.setAutoCommit(isAutoCommit); // 恢复AutoCommit设置
        }
    }
    // 查询数据，转换成class
    static List<Student> queryStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
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
        }
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

    // 基本方法使用
    static void baseApiLearn() {
        // 类关系：Connection（连接） -> Statement(语句）.excuteQuery -> ResultSet（结果集）
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)){
            try (Statement stmt = conn.createStatement()) {
                try(ResultSet resultSet = stmt.executeQuery("SELECT id, grade, name, gender FROM students WHERE gender=1")) {
                    while (resultSet.next()) {
                        long id = resultSet.getLong(1); // 注意：索引从1开始
                        long grade = resultSet.getLong(2);
                        String name = resultSet.getString(3);
                        int gender = resultSet.getInt(4);
                        System.out.println("Statement resultSet is:" + id + " " + grade + " " + name + " " + gender);
                    }
                }
            }
            // 生产环境使用 PreparedStatement 替代 Statement，没有 SQL 注入的问题。这里使用了占位符的设计
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, grade, name, gender FROM students WHERE gender=? AND grade=?")) {
                // 替换占位符
                preparedStatement.setObject(1, "M"); // 注意：索引从1开始
                preparedStatement.setObject(2, 3);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        long id = resultSet.getLong(1); // 注意：索引从1开始
                        long grade = resultSet.getLong(2);
                        String name = resultSet.getString(3);
                        int gender = resultSet.getInt(4);
                        System.out.println("preparedStatement resultSet is: " + id + " " + grade + " " + name + " " + gender);
                    }
                }
            }
//            批处理：addBatch、executeBatch
//            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?)")) {
//                // 对同一个PreparedStatement反复设置参数并调用addBatch():
//                for (Student s : students) {
//                    ps.setString(1, s.name);
//                    ps.setBoolean(2, s.gender);
//                    ps.setInt(3, s.grade);
//                    ps.setInt(4, s.score);
//                    ps.addBatch(); // 添加到batch
//                }
//                // 执行batch:
//                int[] ns = ps.executeBatch();
//                for (int n : ns) {
//                    System.out.println(n + " inserted."); // batch中每个SQL执行的结果数量
//                }
//            }

            // 插入数据：执行 executeUpdate 而不是 executeQuery
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO students (id, grade, name, gender, score) VALUES (?,?,?,?,?)")) {
                ps.setObject(1, 999); // 注意：索引从1开始
                ps.setObject(2, 1); // grade
                ps.setObject(3, "Bob"); // name
                ps.setObject(4, 1); // gender
                ps.setObject(5, 90); // score
                int n = ps.executeUpdate(); // 1 返回插入的行数
                System.out.println("inserted " + n + " rows");
            }

//            id 其实可以不用插入，数据库已经设置了自增。如果想要获取自增主键，可以配合 Statement.RETURN_GENERATED_KEYS
//            try (PreparedStatement ps = conn.prepareStatement(
//                    "INSERT INTO students (grade, name, gender) VALUES (?,?,?)",
//                    Statement.RETURN_GENERATED_KEYS)) {
//                ps.setObject(1, 1); // grade
//                ps.setObject(2, "Bob"); // name
//                ps.setObject(3, "M"); // gender
//                int n = ps.executeUpdate(); // 1
//                try (ResultSet rs = ps.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        long id = rs.getLong(1); // 注意：索引从1开始
//                    }
//                }
//            }
        } catch (Exception e) {
            System.out.println("error occur: " + e);
        }
    }
}
