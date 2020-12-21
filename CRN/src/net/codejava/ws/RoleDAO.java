package net.codejava.ws;

import java.util.List;

public interface RoleDAO {

	public List<Role> listAll();
	
	public int create(Role u);
	
	public boolean update(Role u);
	
	public boolean delete(int id, int version);
	
}
