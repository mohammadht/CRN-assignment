package net.codejava.ws;

import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO{
	
	private static RoleDAOImpl instance;
	
	private static List<Role> data = new ArrayList<>();
	
	static {
		data.add(new Role(101, 1, "User administration"));
		data.add(new Role(102, 2, "Endoscopist administration"));
		data.add(new Role(103, 1, "Report colonoscopy capacity"));
		data.add(new Role(104, 2, "Send invitations"));
		data.add(new Role(105, 1, "View statistics"));
	}
	
	public static RoleDAOImpl getInstance() {
		if(instance == null) {
			instance = new RoleDAOImpl();
		}
		return instance;
	}

	@Override
	public List<Role> listAll() {
		return new ArrayList<Role>(data);
	}

	@Override
	public int create(Role role) {
		int newID = data.get(data.size()-1).getId() + 1;
		role.setId(newID);
		role.setVersion(1);
		data.add(role);
		return newID;
	}

	@Override
	public boolean update(Role role) {
		int roleIndex = data.indexOf(role);
		int currentVersion = data.get(roleIndex).getVersion();
		int roleVersion = role.getVersion();
		if(roleIndex >= 0 && currentVersion == roleVersion) {
			role.setVersion(++currentVersion);
			data.set(roleIndex, role);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(int id, int version) {
		Role u = new Role(id,version);
		int roleIndex = data.indexOf(u);
		int currentVersion = data.get(roleIndex).getVersion();
		int roleVersion = u.getVersion();
		if(roleIndex >= 0 && currentVersion == roleVersion) {
			data.remove(roleIndex);
			return true;
		}
		return false;
	}

}
