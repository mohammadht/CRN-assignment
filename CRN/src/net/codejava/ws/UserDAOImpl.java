package net.codejava.ws;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{
	
	private static UserDAOImpl instance;
	
	private static List<User> data = new ArrayList<>();
	
	UserRoleDAOImpl userRoleDao = UserRoleDAOImpl.getInstance();
	
	static {
		data.add(new User(1, 1, "Alice"));
		data.add(new User(2, 2, "Bob"));
		data.add(new User(3, 1, "Eve"));
	}
	
	public static UserDAOImpl getInstance() {
		if(instance == null) {
			instance = new UserDAOImpl();
		}
		return instance;
	}

	@Override
	public List<User> listAll() {
		return new ArrayList<User>(data);
	}
	
	//List all users with at least one valid user role at a given unit at a given time
	@Override
	public List<User> listValid(int unitId, String time) {
		List<UserRole> userRoleList = new ArrayList<>();
		userRoleList = userRoleDao.listAll();
		Instant instant = Instant.parse(time);
		Long date = instant.toEpochMilli();
		List<Integer> users = new ArrayList<>();
		List<User> usersList = new ArrayList<>();
		for(int i = 0; i < userRoleList.size(); i++) {
			UserRole index = userRoleList.get(i);
			Instant instantFrom = Instant.parse(index.getValidFrom());
			Long from = instantFrom.toEpochMilli();
			if(index.getUnitId() == unitId) {
				if(date > from) {
					if(index.getValidTo() != null) {
						Instant instantTo = Instant.parse(index.getValidTo());
						Long to = instantTo.toEpochMilli();
						if(date < to) {
							users.add(index.getUserId());
						}
					}
					if(index.getValidTo() == null) {
						index.setValidTo("(no value)");
					}
					users.add(index.getUserId());
				}
			}
		}
		for(int i = 0; i < data.size(); i++) {
			if(users.contains(data.get(i).getId())){
				usersList.add(data.get(i));
			}
		}
		return usersList;
	}
	
	//For a given unit id, list all users with at least one user role at that unit
	@Override
	public List<User> listAll(int unitId) {
		List<UserRole> userRoleList = new ArrayList<>();
		userRoleList = userRoleDao.listAll();
		List<Integer> users = new ArrayList<>();
		List<User> usersList = new ArrayList<>();
		for(int i = 0; i < userRoleList.size(); i++) {
			UserRole index = userRoleList.get(i);
			if(index.getUnitId() == unitId) {
				users.add(index.getUserId());
			}
		}
		for(int i = 0; i < data.size(); i++) {
			if(users.contains(data.get(i).getId())){
				usersList.add(data.get(i));
			}
		}
		return usersList;
	}
	
	
	@Override
	public int create(User u) {
		int newID = data.size() + 1;
		u.setId(newID);
		u.setVersion(1);
		data.add(u);
		return newID;
	}

	@Override
	public boolean update(User u) {
		int userIndex = data.indexOf(u);
		int currentVersion = data.get(userIndex).getVersion();
		int userVersion = u.getVersion();
		if(userIndex >= 0 && currentVersion == userVersion) {
			u.setVersion(++currentVersion);
			data.set(userIndex, u);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(int id, int version) {
		User u = new User(id,version);
		int userIndex = data.indexOf(u);
		int currentVersion = data.get(userIndex).getVersion();
		int userVersion = u.getVersion();
		if(userIndex >= 0 && currentVersion == userVersion) {
			if(anyUserRole(id) == false) {
				data.remove(userIndex);
				return true;
			}
		}
		return false;
	}

	private boolean anyUserRole(int id) {
		List<UserRole> userRoleList = new ArrayList<>();
		userRoleList = userRoleDao.listAll();
		for(int i = 0; i < userRoleList.size(); i++) {
			if(userRoleList.get(i).getUserId() == id) {
				return true;
			}
		}
		return false;
	}



}
