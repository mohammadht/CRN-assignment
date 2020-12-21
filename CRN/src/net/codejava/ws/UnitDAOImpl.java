package net.codejava.ws;

import java.util.ArrayList;
import java.util.List;

public class UnitDAOImpl implements UnitDAO{
	
	private static UnitDAOImpl instance;
	
	private static List<Unit> data = new ArrayList<>();
	
	static {
		data.add(new Unit(11, 2, "Kreftregisteret"));
		data.add(new Unit(12, 1, "Akershus universitetssykehus HF"));
		data.add(new Unit(13, 2, "Sørlandet sykehus HF"));
		data.add(new Unit(14, 2, "Vestre Viken HF"));
	}
	
	public static UnitDAOImpl getInstance() {
		if(instance == null) {
			instance = new UnitDAOImpl();
		}
		return instance;
	}

	@Override
	public List<Unit> listAll() {
		return new ArrayList<Unit>(data);
	}

	@Override
	public int create(Unit u) {
		int newID = data.get(data.size()-1).getId() + 1;
		u.setId(newID);
		u.setVersion(1);
		data.add(u);
		return newID;
	}

	@Override
	public boolean update(Unit u) {
		int unitIndex = data.indexOf(u);
		int currentVersion = data.get(unitIndex).getVersion();
		int unitVersion = u.getVersion();
		if(unitIndex >= 0 && currentVersion == unitVersion) {
			u.setVersion(++currentVersion);
			data.set(unitIndex, u);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(int id, int version) {
		Unit u = new Unit(id,version);
		int unitIndex = data.indexOf(u);
		int currentVersion = data.get(unitIndex).getVersion();
		int unitVersion = u.getVersion();
		if(unitIndex >= 0 && currentVersion == unitVersion) {
			data.remove(unitIndex);
			return true;
		}
		return false;
	}

}
