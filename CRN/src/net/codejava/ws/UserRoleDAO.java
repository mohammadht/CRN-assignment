package net.codejava.ws;

import java.text.ParseException;
import java.util.List;

public interface UserRoleDAO {
	
	public List<UserRole> listAll();
	
	public List<UserRole> listAll(int userId, int unitId);
	
	public List<UserRole> listValid(int userId, int unitId, String time) throws ParseException;
	
	public int create(UserRole u);
	
	public boolean update(UserRole u);
	
	public boolean delete(int id, int version);

}
