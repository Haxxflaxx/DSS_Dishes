package application;

/**
 * Created by Frank on 27/11/15.
 */
public class User {

    private static String id;
    private static String name;
    private static String privilege;

    public User(String id, String name, String privilege){
        this.id = id;
        this.name = name;
        this.privilege = name;

    }
    public static String getPrivilege() {
        return privilege;
    }

    public static String getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
