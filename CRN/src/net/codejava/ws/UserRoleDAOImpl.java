package net.codejava.ws;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAOImpl implements UserRoleDAO{
	
	private static UserRoleDAOImpl instance;
	
	private static List<UserRole> data = new ArrayList<>();
	
	static {
		data.add(new UserRole(1001, 1, 1, 11, 101, "2019-01-02T00:00:00.00Z", "2019-12-31T23:59:59.00Z"));
		data.add(new UserRole(1002, 2, 1, 11, 104, "2019-01-02T00:00:00.00Z", "2019-12-31T23:59:59.00Z"));
		data.add(new UserRole(1003, 1, 1, 11, 105, "2019-06-11T00:00:00.00Z", "2019-12-31T23:59:59.00Z"));
		data.add(new UserRole(1004, 2, 2, 12, 101, "2020-01-28T00:00:00.00Z", null));
		data.add(new UserRole(1005, 1, 2, 12, 105, "2020-01-28T00:00:00.00Z", null));
		data.add(new UserRole(1006, 1, 2, 14, 101, "2020-01-28T00:00:00.00Z", null));
		data.add(new UserRole(1007, 1, 2, 14, 102, "2020-01-28T00:00:00.00Z", null));
		data.add(new UserRole(1008, 1, 1, 11, 101, "2020-02-01T07:00:00.00Z", null));
		data.add(new UserRole(1009, 1, 1, 11, 104, "2020-02-01T07:00:00.00Z", null));
	}
	
	public static UserRoleDAOImpl getInstance() {
		if(instance == null) {
			instance = new UserRoleDAOImpl();
		}
		return instance;
	}

	@Override
	public List<UserRole> listAll() {
		return new ArrayList<UserRole>(data);
	}
	
	@Override
	public List<UserRole> listAll(int userId, int unitId) {
		List<UserRole> ur = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			UserRole index = data.get(i);
			if(index.getUserId() == userId && index.getUnitId() == unitId) {
				if(index.getValidTo() == null) {
					index.setValidTo("(no value)");
				}
				ur.add(data.get(i));
			}
		}
		return ur;
	}

	@Override
	public List<UserRole> listValid(int userId, int unitId, String time) throws ParseException {
		List<UserRole> ur = new ArrayList<>();
		Instant instant = Instant.parse(time);
		Long date = instant.toEpochMilli();
		for (int i = 0; i < data.size(); i++) {
			UserRole index = data.get(i);
			Instant instantFrom = Instant.parse(index.getValidFrom());
			Long from = instantFrom.toEpochMilli();
			if(index.getUserId() == userId && index.getUnitId() == unitId) {
				if(date > from) {
					if(index.getValidTo() != null) {
						Instant instantTo = Instant.parse(index.getValidTo());
						Long to = instantTo.toEpochMilli();
						if(date < to) {
							ur.add(data.get(i));
						}
					}
					if(index.getValidTo() == null) {
						index.setValidTo("(no value)");
					}
					ur.add(data.get(i));
				}
			}
		}
		return ur;
	}

	@Override
	public int create(UserRole ur) {
		int newID =  data.get(data.size()-1).getId() + 1;
		ur.setId(newID);
		ur.setVersion(1);
		//an optional valid from timestamp (if not specified, default to the current date and time)
		if(ur.getValidFrom() == null) {
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			ur.setValidFrom(timeStamp);
		}
		//If a valid to timestamp is specified, it must be after the valid from
		//timestamp (or the current date and time if valid from timestamp is not specified in the request).
		if(ur.getValidTo() != null) {
			Instant instantTo = Instant.parse(ur.getValidTo());
			Long to = instantTo.toEpochMilli();
			if(ur.getValidFrom() != null) {
				Instant instantFrom = Instant.parse(ur.getValidFrom());
				Long from = instantFrom.toEpochMilli();
				if(from > to) {
					ur.setValidTo("(invalid input");
				}
			}
			if(ur.getValidFrom() == null && to < System.currentTimeMillis()) {
				ur.setValidTo("(invalid input");
			}	
		}
		//an optional valid to timestamp (if not specified, default to no timestamp).
		if(ur.getValidTo() == null) {
			ur.setValidTo("(no value)");
		}
		//At most one user role for a given combination of
		//user id, unit id and role id can be valid at any point in time.
		if(checkUserRoleCreationValidity(ur)) {
			data.add(ur);
		}
		return newID;
	}
	
	private boolean checkUserRoleCreationValidity(UserRole ur) {
		for(int i = 0; i < data.size(); i++) {
			if(ur.getUserId() == data.get(i).getUserId() && 
				ur.getUnitId() == data.get(i).getUnitId() && 
				ur.getRoleId() == data.get(i).getRoleId()){
				return false;
			}
		}
		return true;
	}


	@Override
	public boolean update(UserRole ur) {
		int userRoleIndex = data.indexOf(ur);
		int currentVersion = data.get(userRoleIndex).getVersion();
		int userRoleVersion = ur.getVersion();
		int currentUserId = data.get(userRoleIndex).getUserId();		
		int currentUnitId = data.get(userRoleIndex).getUnitId();	
		int currentRoleId = data.get(userRoleIndex).getRoleId();		
		//Check if the specified version matches the resource's current version.
		if(userRoleIndex >= 0 && currentVersion == userRoleVersion) {
			//the valid to timestamp, if specified, must come after the valid from timestamp
			Instant instantTo = Instant.parse(ur.getValidTo());
			Long to = instantTo.toEpochMilli();
			Instant instantFrom = Instant.parse(ur.getValidFrom());
			Long from = instantFrom.toEpochMilli();
			if(from < to) {
				//an update that would cause two user roles for the same user id, unit id and role id to be valid at the
				//same time must be rejected
				if(checkUserRoleCreationValidity(ur)) {
					ur.setVersion(++currentVersion);
					//Only the valid from and valid to timestamps can be changed.
					ur.setUserId(currentUserId);
					ur.setUnitId(currentUnitId);
					ur.setRoleId(currentRoleId);
					data.set(userRoleIndex, ur);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean delete(int id, int version) {
		UserRole ur = new UserRole(id, version);
		int userRoleIndex = data.indexOf(ur);
		int currentVersion = data.get(userRoleIndex).getVersion();
		int userRoleVersion = ur.getVersion();
		if(userRoleIndex >= 0 && currentVersion == userRoleVersion) {
			data.remove(userRoleIndex);
			return true;
		}
		return false;
	}

}
