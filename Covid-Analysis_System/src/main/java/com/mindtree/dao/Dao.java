package com.mindtree.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.mindtree.model.Player;

public interface Dao {

	public List<Player> getStateCode() throws SQLException;

	public List<Player> getDistrictName(String stateCode) throws SQLException;

	public List<Player> getDataForDateRange(Date sDate, Date eDate) throws SQLException;

	public void end();

}
