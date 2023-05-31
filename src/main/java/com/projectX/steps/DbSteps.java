package com.projectX.steps;

import com.projectX.utils.DbUtils;
import com.projectX.utils.Enum;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbSteps {
    public List<ArrayList<String>> getArrayListWithTests(String projectName, int limit) {
        String query =
                """
                SELECT test.name, test.method_name, status.name, test.start_time, test.end_time,
                DATE_FORMAT(TIMEDIFF(test.end_time, test.start_time), '%H:%i:%s:000') as duration
                FROM union_reporting.test
                JOIN union_reporting.status ON test.status_id = status.id
                JOIN union_reporting.project ON test.project_id = project.id
                WHERE project.name = '%s'
                GROUP BY test.name, test.method_name, status.name, test.start_time, test.end_time
                ORDER BY test.start_time LIMIT %s;
                """;
        return DbUtils.getDataFromDatabase(String.format(query, projectName, limit));
    }

    public void sendTestToDataBase(long testId, String name, String methodName, int projectId, int sessionId, String env) {
        String query = "INSERT INTO union_reporting.test (id, name, status_id, method_name, project_id, session_id, env) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DbUtils.getConnection().prepareStatement(query)) {
            stmt.setLong(Enum.ONE.getValue(), testId);
            stmt.setString(Enum.TWO.getValue(), name);
            stmt.setInt(Enum.THREE.getValue(), Enum.ONE.getValue());
            stmt.setString(Enum.FOUR.getValue(), methodName);
            stmt.setInt(Enum.FIVE.getValue(), projectId);
            stmt.setInt(Enum.SIX.getValue(), sessionId);
            stmt.setString(Enum.SEVEN.getValue(), env);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendLogToDataBase(long id, String logContent, long testId) {
        String query = "INSERT INTO union_reporting.log (id, content, is_exception, test_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = DbUtils.getConnection().prepareStatement(query)) {
            stmt.setLong(Enum.ONE.getValue(), id);
            stmt.setString(Enum.TWO.getValue(), logContent);
            stmt.setInt(Enum.THREE.getValue(), Enum.ZERO.getValue());
            stmt.setLong(Enum.FOUR.getValue(), testId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendAttachmentToDataBase(long id, byte[] content, String contentType, long testId) {
        String query = "INSERT INTO union_reporting.attachment (id, content, content_type, test_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = DbUtils.getConnection().prepareStatement(query)) {
            stmt.setLong(Enum.ONE.getValue(), id);
            stmt.setBytes(Enum.TWO.getValue(), content);
            stmt.setString(Enum.THREE.getValue(), contentType);
            stmt.setLong(Enum.FOUR.getValue(), testId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
