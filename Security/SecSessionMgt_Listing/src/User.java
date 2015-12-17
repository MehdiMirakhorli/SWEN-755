import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class User {
	private static int id_counter = 1;
	private static Map<Integer, User> users = new HashMap<Integer, User>();

	int id;
	String name;
	String role;
	String password;

	public User(String name, String password, String role) {
		this.id = User.id_counter;
		this.name = name;
		this.role = role;
		this.password = SessionManager.encryptPassword(password);
		User.users.put(id, this);
		id_counter += 1;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public int getId() {
		return id;
	}

	public String getRole() {
		return role;
	}

	static User find(String name){
		for (User user : users.values()) {
			if(user.getName().equals(name)){
				return user;
			}
		}
		return null;
	}

	static JSONObject getList(){
		JSONObject list = new JSONObject();
		JSONArray names = new JSONArray();
		for (User user : users.values()) {
			names.add(user.getName());
		}
		list.put("users", names);
		return list;
	}

	public String toString(){
		return name;
	}
}
