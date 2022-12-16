package com.mindtree.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.model.Player;

public class CovidAnalysisDao implements Dao {
	DataBaseConnection dataBaseConnection;
	Statement statement;
	PreparedStatement preparedStatement;
	List<Player> players;

	public CovidAnalysisDao() throws SQLException, IOException {
		dataBaseConnection = new DataBaseConnection();
	}

	@Override
	public List<Player> getStateCode() throws SQLException {
		players = new ArrayList<Player>();
		statement = dataBaseConnection.getConnection().createStatement();
		ResultSet rs = statement.executeQuery("select * from covid_data");
		while (rs.next()) {
			players.add(new Player(rs.getDate(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
					rs.getInt(6), rs.getInt(7)));
		}
		return players;
	}

	@Override
	public List<Player> getDistrictName(String stateCode) throws SQLException {
		players = new ArrayList<Player>();
		preparedStatement = dataBaseConnection.getConnection()
				.prepareStatement("select * from covid_data where state = ?");
		preparedStatement.setString(1, stateCode);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			players.add(new Player(rs.getDate(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
					rs.getInt(6), rs.getInt(7)));
		}
		return players;

	}

	@Override
	public List<Player> getDataForDateRange(Date sDate, Date eDate) throws SQLException {
		players = new ArrayList<Player>();
		preparedStatement = dataBaseConnection.getConnection().prepareStatement(
				"select date,state,sum(confirmed) from (select * from covid_data where date >? and date <? )as covid_data1 group by state,date ");
		preparedStatement.setDate(1, new java.sql.Date(sDate.getTime()));
		preparedStatement.setDate(2, new java.sql.Date(eDate.getTime()));
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			players.add(new Player(rs.getDate(1), rs.getString(2), null, 0, rs.getInt(3), 0, 0));
		}
		return players;

	}

	@Override
	public void end() {
		try {
			statement.close();
			preparedStatement.close();
			dataBaseConnection.getConnection().close();
		} catch (Exception e) {
		}
	}

}
