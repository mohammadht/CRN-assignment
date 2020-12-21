package net.codejava.ws;

import java.util.List;

public interface UnitDAO {

	public List<Unit> listAll();
	
	public int create(Unit u);
	
	public boolean update(Unit u);
	
	public boolean delete(int id, int version);
}
