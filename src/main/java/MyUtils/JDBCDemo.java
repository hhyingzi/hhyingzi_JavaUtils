package MyUtils;

import java.sql.*;

/**
 * JDBC 增删改查示例。
 * 环境要求（建议）：
 *      mysql版本：5.7.42
 *      pom：mysql-connector-java  5.1.49
 *      host: 47.110.147.155:3306
 *      用户名: hhyingzi
 *      密码：1****6
 *      数据库：mybatis
 *      表名：temp_users
 */
class JDBCDemo {
    // JDBC连接信息
    private static final String JDBC_URL = "jdbc:mysql://myhost/mybatis";
    private static final String JDBC_USERNAME = "";
    private static final String JDBC_PASSWORD = "";

    public static void main(String[] args) {
//        // 加载JDBC驱动程序
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("无法加载JDBC驱动程序");
            e.printStackTrace();
            return;
        }

        // 建立数据库连接
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            System.out.println("成功连接到数据库");

            //建表
            createTempTable(conn);

            // 插入数据
            insertData(conn, "Alice", 25);

            // 更新数据
            updateData(conn, "Alice", 30);

            // 查询数据
            queryData(conn, "Alice");

            // 删除数据
            deleteData(conn, "Alice");

            // 再次查询数据，确认数据已删除
            queryData(conn, "Alice");
        } catch (SQLException e) {
            System.err.println("数据库操作失败");
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("关闭数据库连接失败");
                    e.printStackTrace();
                }
            }
        }
    }

    //建表
    private static void createTempTable(Connection conn){
        String createTableQuery = "CREATE TABLE temp_users ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(255) NOT NULL,"
                + "age INT NOT NULL"
                + ")";

        try (
                //用Statement 对象执行 DDL（数据定义语言），如 CREATE TABLE
                Statement statement = conn.createStatement()) {
            //检查表是否已存在
            ResultSet resultSet = conn.getMetaData().getTables(null, null, "temp_users", null);
            if (resultSet.next()) {
                System.out.println("Table already exists.");
            } else {
                statement.execute(createTableQuery);
                System.out.println("Table created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 插入数据
    private static void insertData(Connection conn, String name, int age) throws SQLException {
        String sql = "INSERT INTO temp_users (name, age) VALUES (?, ?)";
        //用 PreparedStatement 执行 SQL 查询和更新操作
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, name);
        statement.setInt(2, age);
        statement.executeUpdate();
        System.out.println("成功插入数据：" + name + ", " + age);
    }

    // 更新数据
    private static void updateData(Connection conn, String name, int age) throws SQLException {
        String sql = "UPDATE temp_users SET age = ? WHERE name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, age);
        statement.setString(2, name);
        int rowsUpdated = statement.executeUpdate();
        System.out.println("成功更新 " + rowsUpdated + " 条数据");
    }

    // 查询数据
    private static void queryData(Connection conn, String name) throws SQLException {
        String sql = "SELECT * FROM temp_users WHERE name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            String userName = rs.getString("name");
            int age = rs.getInt("age");
            System.out.println("查询到数据：" + userName + ", " + age);
        } else {
            System.out.println("未找到数据：" + name);
        }
    }

    // 删除数据
    private static void deleteData(Connection conn, String name) throws SQLException {
        String sql = "DELETE FROM temp_users WHERE name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, name);
        int rowsDeleted = statement.executeUpdate();
        System.out.println("成功删除 " + rowsDeleted + " 条数据");
    }
}
