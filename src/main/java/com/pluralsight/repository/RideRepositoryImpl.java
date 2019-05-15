package com.pluralsight.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl extends CachedResultSetMetaData implements RideRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTemplate.query("select * from ride", new RowMapper<Ride>() {
			@Override
			public Ride mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ride ride = new Ride();
				ride.setId(rs.getInt("id"));
				ride.setName(rs.getString("name"));
				ride.setDuration(rs.getInt("duration"));
				return ride;
			}
		});
		return rides;
	}

	@Override
	public Ride createRide(Ride ride) {
		jdbcTemplate.update("Insert into ride (name, duration) values (?,?)",ride.getName(),ride.getDuration());
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ride getRide(int id) {
		Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?",new RowMapper<Ride>() {
			@Override
			public Ride mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ride ride = new Ride();
				ride.setId(rs.getInt("id"));
				ride.setName(rs.getString("name"));
				ride.setDuration(rs.getInt("duration"));
				return ride;
			}
		},id);
		return ride;
	}

	@Override
	public Ride updateRide(Ride ride) {
		jdbcTemplate.update("update ride set name=?, duration=? where id=?",ride.getName(),ride.getDuration(),ride.getId());
		return ride;
	}

	@Override
	public void updateRides(List<Object[]> pairs) {
		jdbcTemplate.batchUpdate("update ride set ride_date=? where id=?",pairs);
		
	}

	
}
