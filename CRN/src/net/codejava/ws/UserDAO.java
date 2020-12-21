package net.codejava.ws;

import java.util.List;

public interface UserDAO {
	
	public List<User> listAll();
	
	public List<User> listValid(int unitId, String time);
	
	public List<User> listAll(int unitId);
	
	public int create(User u);
	
	public boolean update(User u);
	
	public boolean delete(int id, int version);

}
