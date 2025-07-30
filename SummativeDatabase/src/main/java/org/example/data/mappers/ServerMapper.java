package org.example.data.mappers;

import org.example.model.Server;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class ServerMapper implements RowMapper<Server> {

    @Override
    public Server mapRow(ResultSet rs, int rowNum) throws SQLException {
        Server server = new Server();
        server.setServerID(rs.getInt("ServerID"));
        server.setFirstName(rs.getString("FirstName"));
        server.setLastName(rs.getString("LastName"));
        server.setHireDate(rs.getObject("HireDate", LocalDate.class));

        // Handle null TermDate
        if (rs.getObject("TermDate") != null) {
            server.setTermDate(rs.getObject("TermDate", LocalDate.class));
        }

        return server;
    }
}