package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String queryVacancySql = "select vacancy from acc_occupancy where acc_id = ?";
    private final String insertReservationSql = "insert into reservations(resv_id, name, email, acc_id, arrival_date, duration) values (?, ?, ?, ?, ?, ?)";
    private final String updateVacancySql = "update acc_occupancy set vacancy = ? where acc_id = ?";

    public Integer getVacancyById(String id){
        return jdbcTemplate.queryForObject(queryVacancySql, Integer.class, id);  
    }

    public Integer insertReservation(String resvId, String name, String email, String lid, Date arrival, Integer duration) {

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                
                PreparedStatement ps = con.prepareStatement(insertReservationSql);
                ps.setString(1,resvId);
                ps.setString(2, name);
                ps.setString(3, email);
                ps.setString(4, lid);
                ps.setDate(5, arrival);
                ps.setInt(6, duration);
                return ps;
            }
        };

        Integer iResult = jdbcTemplate.update(psc);
        return iResult;
    }
    
    public Integer updateVacancy(String lid, Integer duration){
        Integer vacancy = getVacancyById(lid);
        Integer newVacancy = vacancy - duration;

        Integer iResult = jdbcTemplate.update(updateVacancySql, newVacancy, lid);
        return iResult;
    }
}
