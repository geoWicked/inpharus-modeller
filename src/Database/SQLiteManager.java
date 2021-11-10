package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteManager {

	// ��� ����
    //   - Database ����
    private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String SQLITE_FILE_DB_URL = "jdbc:sqlite:quartz.db";
    private static final String SQLITE_MEMORY_DB_URL = "jdbc:sqlite::memory";
 
    //  - Database �ɼ� ����
    private static final boolean OPT_AUTO_COMMIT = false;
    private static final int OPT_VALID_TIMEOUT = 500;
 
    // ���� ����
    //   - Database ���� ���� ����
    private Connection conn = null;
    private String driver = null;
    private String url = null;
 
    // ������
    public SQLiteManager(){
        this(SQLITE_FILE_DB_URL);
    }
    public SQLiteManager(String url) {
        // JDBC Driver ����
        this.driver = SQLITE_JDBC_DRIVER;
        this.url = url;
    }
 
    // DB ���� �Լ�
    public Connection createConnection() {
        try {
            // JDBC Driver Class �ε�
            Class.forName(this.driver);
 
            // DB ���� ��ü ����
            this.conn = DriverManager.getConnection(this.url);
 
            // �α� ���
            System.out.println("CONNECTED");
 
            // �ɼ� ����
            //   - �ڵ� Ŀ��
            this.conn.setAutoCommit(OPT_AUTO_COMMIT);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return this.conn;
    }
 
    // DB ���� ���� �Լ�
    public void closeConnection() {
        try {
            if( this.conn != null ) {
                this.conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.conn = null;
 
            // �α� ���
            System.out.println("CLOSED");
        }
    }
 
    // DB �翬�� �Լ�
    public Connection ensureConnection() {
        try {
            if( this.conn == null || this.conn.isValid(OPT_VALID_TIMEOUT) ) {
                closeConnection();      // ���� ����
                createConnection();     // ����
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return this.conn;
    }
 
    // DB ���� ��ü ��������
    public Connection getConnection() {
        return this.conn;
    }

}
