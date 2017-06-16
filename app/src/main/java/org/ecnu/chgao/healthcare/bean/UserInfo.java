package org.ecnu.chgao.healthcare.bean;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by chgao on 17-6-16.
 */

public class UserInfo {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    private String userName;
    private int userSex;
    private String emergencyName;
    private String emergencyPhone;
    private int emergencySex;

    public UserInfo(String userName, int userSex, String emergencyName, String emergencyPhone, int emergencySex) {
        this.userName = userName;
        this.userSex = userSex;
        this.emergencyName = emergencyName;
        this.emergencyPhone = emergencyPhone;
        this.emergencySex = emergencySex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public int getEmergencySex() {
        return emergencySex;
    }

    public void setEmergencySex(int emergencySex) {
        this.emergencySex = emergencySex;
    }
}
