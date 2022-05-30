package nz.co.afleet.bit603_a3_johnmcpherson.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "User", indices = {@Index(value = {"Name"},
        unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Password")
    @NonNull
    private String password;

    @ColumnInfo(name = "DateOfBirth")
    @NonNull
    private String dateOfBirth;

    @ColumnInfo(name = "EmployeeNumber")
    @NonNull
    private String employeeNumber;

    @ColumnInfo(name = "ContactPhoneNumber")
    @NonNull
    private String contactPhoneNumber;

    // ASSUMPTION: a single text field is OK for address. (Address 1, Address 2 etc not required)
    @ColumnInfo(name = "Address")
    @NonNull
    private String address;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NonNull String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @NonNull
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(@NonNull String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @NonNull
    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(@NonNull String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public User() {}


    /**
     * this constructor enforces that all fields are non-null, at the database/entity level
     * @param name
     * @param password
     * @param dateOfBirth
     * @param employeeNumber
     * @param contactPhoneNumber
     * @param address
     */
    public User(String name, @NonNull String password, @NonNull String dateOfBirth, @NonNull String employeeNumber, @NonNull String contactPhoneNumber, @NonNull String address) {

        this.name = name;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.employeeNumber = employeeNumber;
        this.contactPhoneNumber = contactPhoneNumber;
        this.address = address;
    }
}
