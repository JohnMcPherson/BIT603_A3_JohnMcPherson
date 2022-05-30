package nz.co.afleet.bit603_a3_johnmcpherson.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;

@Entity(tableName = "User", indices = {@Index(value = {"Name"},
        unique = true)})
public class User {


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
    private Boolean isAdmin;

    private User(@NonNull String name,
                @NonNull String password,
                String dateOfBirth,
                String employeeNumber,
                String phoneNumber,
                String address,
                Boolean isAdmin) {
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

    public static User createUser(@NonNull String name,
                                  @NonNull String password,
                                  String dateOfBirth,
                                  String employeeNumber,
                                  String phoneNumber,
                                  String address,
                                  Boolean isAdmin) {
        User newUser = new User(name, password, dateOfBirth, employeeNumber, phoneNumber, address, isAdmin);
        return newUser;
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



    public static ArrayList<User> getUsers(Application application) {
        ApplicationDatabase applicationDatabasee = ApplicationDatabase.getInstance(application);
        DaoUser daoUser = applicationDatabasee.daoUser();
        ArrayList<User> users = new ArrayList<>(daoUser.getUsers());
        users.add(getDefaultAdminUser());
        return users;
    }

    private static User getDefaultAdminUser() {
        String DEFAULT_ADMIN = "Admin";
        String DEFAULT_ADMIN_PASSWORD = "CookieManagement84";
        return new User(
                DEFAULT_ADMIN,
                DEFAULT_ADMIN_PASSWORD,
                "", "", "", "",
                true);
    }
}
