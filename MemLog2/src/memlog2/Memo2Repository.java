package memlog2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Memo2Repository {
    private static final String URL = "jdbc:mysql://localhost:3306/memos2";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void saveMemo(Memo2 memo) {
        String sql = "INSERT INTO memos (title, content, date) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memo.getTitle());
            pstmt.setString(2, memo.getContent());
            pstmt.setDate(3, Date.valueOf(memo.getDate())); // LocalDate -> java.sql.Date
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Memo2> getAllMemos() {
        List<Memo2> memos = new ArrayList<>();
        String sql = "SELECT * FROM memos";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM memos")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                Date sqlDate = rs.getDate("date"); // java.sql.Date
                LocalDate date = sqlDate.toLocalDate(); // Convert to LocalDate
                Memo2 memo = new Memo2(id, title, content, date); // Correct constructor
                memos.add(memo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memos;
    }

    public void deleteMemo(int id) {
        String sql = "DELETE FROM memos WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
