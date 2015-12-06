package application;

/**
 * Created by Frank on 27/11/15.
 */
public class User {

    private static String id;
    private static String name;
    private static String privilege ="0";


    public static int getPrivilege() {
        return Integer.parseInt(privilege);
    }

    public static String getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static void setId(String id) {User.id = id;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static void setPrivilege(String privilege) {
        User.privilege = privilege;
    }
}
