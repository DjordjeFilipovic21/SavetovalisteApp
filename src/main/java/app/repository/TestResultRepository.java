package app.repository;

import app.model.Session;
import app.model.Test;
import app.model.TestResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestResultRepository extends CustomRepository{
    public List<TestResult> getResultsForSession(int sessionId) {
        List<TestResult> results = new ArrayList<>();
        String sql = """
        SELECT 
            tr.id AS tr_id,
            s.ses_id AS session_id,
            s.ses_title AS session_title,
            s.ses_desc AS session_desc,
            t.id AS test_id,
            t.name AS test_name,
            tr.result_value
        FROM test_result tr
        JOIN sessions s ON tr.session_id = s.ses_id
        JOIN test t ON tr.test_id = t.id
        WHERE s.ses_id = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Test test = new Test();
                test.setId(rs.getInt("test_id"));
                test.setName(rs.getString("test_name"));

                Session session = new Session(
                        rs.getInt("session_id"),
                        rs.getString("session_title"),
                        rs.getString("session_desc")
                );

                TestResult tr = new TestResult();
                tr.setId(rs.getInt("tr_id"));
                tr.setTest(test);
                tr.setSession(session);
                tr.setResultValue(rs.getString("result_value"));

                results.add(tr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
