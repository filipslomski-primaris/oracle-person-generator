package eu.primaris;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseService {

    private static final String URL =
            "jdbc:oracle:thin:@vps-f8c88572.vps.ovh.net:1521/XEPDB1";

    private static final String USER = "dev";
    private static final String PASS = "dev";

    public void savePerson(PersonData p) throws Exception {

        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        Long partyId = insertParty(conn);

        String sql = """
                INSERT INTO person
                (party_id, first_name, last_name, pesel, birth_date,
                 citizenship, document_type, document_number,
                 phone_number, email)
                VALUES (?,?,?,?,?,?,?,?,?,?)
                """;

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setLong(1, partyId);
        ps.setString(2, p.firstName);
        ps.setString(3, p.lastName);
        ps.setString(4, p.pesel);
        ps.setDate(5, java.sql.Date.valueOf(p.birthDate));
        ps.setString(6, "PL");
        ps.setString(7, "ID_CARD");
        ps.setString(8, p.documentNumber);
        ps.setString(9, p.phone);
        ps.setString(10, p.email);

        ps.executeUpdate();

        conn.close();
    }

    private Long insertParty(Connection conn) throws Exception {

        String sql = "INSERT INTO party (party_type) VALUES ('PERSON')";

        PreparedStatement ps =
                conn.prepareStatement(sql, new String[]{"ID"});

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        rs.next();

        return rs.getLong(1);
    }
}
