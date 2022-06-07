package nz.co.afleet.bit603_a3_johnmcpherson.database;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "User", indices = {@Index(value = {"Name"},
        unique = true)})
public class User {

    private static User loggedInUser;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Password")
    private String password;

    @ColumnInfo(name = "DateOfBirth")
    private String dateOfBirth;

    @ColumnInfo(name = "EmployeeNumber")
    private String employeeNumber;

    @ColumnInfo(name = "ContactPhoneNumber")
    private String phoneNumber;

    // ASSUMPTION: a single text field is OK for address. (Address 1, Address 2 etc not required)
    @ColumnInfo(name = "Address")
    private String address;

    // isAdmin
    @ColumnInfo(name = "IsAdmin")
    private boolean isAdmin;

    private User(@NonNull String name,
                 @NonNull String password,
                 String dateOfBirth,
                 String employeeNumber,
                 String phoneNumber,
                 String address,
                 boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.employeeNumber = employeeNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isAdmin = isAdmin;
    }

    protected User() {
    }

    // Use of this method ensures that new users are added to the database
    // If user data is invalid, we can ensure it does NOT get added to the database
    public static User createUser(Context context,
                                  @NonNull String name,
                                  @NonNull String password,
                                  String dateOfBirth,
                                  String employeeNumber,
                                  String phoneNumber,
                                  String address,
                                  boolean isAdmin) {
        User newUser = null;
        if (allValuesArePopulated(name, password, dateOfBirth, employeeNumber, phoneNumber, address)
            && !isDuplicateOfUserName(context, name)) {
            newUser = new User(name, password, dateOfBirth, employeeNumber, phoneNumber, address, isAdmin);
            addUserToDatabase(context, newUser);
        }
        return newUser;
    }

    public static boolean isDuplicateOfUserName(Context context, String candidateName) {
        List<User> currentUsers = getDaoUser(context).getUsers();
        // check each current inventory item for the candidate name
        for (User currentUser : currentUsers) {
            if (currentUser.getName().equals(candidateName)) {
                // a match means there is a duplicate
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param context required to access the database
     * @param userName the name of the user we want to delete
     * @return true if the user was removed
     */
    public static boolean removeUser(Context context, String userName) {
        if (isDuplicateOfUserName(context, userName)) {
            getDaoUser(context).deleteByName(userName);
            return !isDuplicateOfUserName(context, userName); // this should always be true. But this way, we are certain we have removed the user
        }
        return false;
    }

    private static boolean allValuesArePopulated(String... values) {
        for (String value : values) {
            if (value == null || "".equals(value)) return false;
        }
        return true;
    }

    private static void addUserToDatabase(Context context, User newUser) {
        getDaoUser(context).addUser(newUser);
    }

    private static DaoUser getDaoUser(Context context) {
        ApplicationDatabase applicationDatabase = ApplicationDatabase.getInstance(context);
        return applicationDatabase.daoUser();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public static ArrayList<User> getUsers(Context context) {
        ApplicationDatabase applicationDatabasee = ApplicationDatabase.getInstance(context);
        DaoUser daoUser = applicationDatabasee.daoUser();
        ArrayList<User> users = new ArrayList<>(daoUser.getUsers());
        users.add(getDefaultAdminUser());
        return users;
    }

    /**
     * Returns a User with the default admin credentials
     * ASSUMPTION This user should not be added to the database. A user is not to be created (and added to the database) by the administrator,
     * unless all fields are non-blank. As we do not have contact etc details for the Admin user, I assume the admin should not be in the database either
     *
     * @return
     */

    private static User getDefaultAdminUser() {
        String DEFAULT_ADMIN = "Admin";
        String DEFAULT_ADMIN_PASSWORD = "CookieManagement84";
        return new User(
                DEFAULT_ADMIN,
                DEFAULT_ADMIN_PASSWORD,
                "", "", "", "",
                true);
    }

    // find a User, based on userName
    public static User find(Context context, String userName) {
        ArrayList<User> users = getUsers(context);
        User returnValue = null;
        for (User userToCheck: users) {
            if (userToCheck.getName().equals(userName)) {
                returnValue = userToCheck;
                break; // we have found a matching user, so stop looking
            }
        }
        return returnValue;
    }

    public static boolean loginUser(Context context, String userName, String password) {
        loggedInUser = null; // if we are trying to log in, we want to ensure that any existing users are logged out
        User user = find(context, userName);
        if (user == null) return false;
        if (user.getPassword().equals(password)) {
            loggedInUser = user;
            return true;
        } else {
            return false;
        }
    }

}
