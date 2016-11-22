package com.example.hp.eduapp.data_models;

import java.util.List;

/**
 * Created by HP on 10/14/2016.
 */
public class Group {

    private String groupName;
    private String adminName;
    private String id;
    private ChatMessage chatMsg;

//    private boolean classGroup;
//    private List<String> userList;
   // private List<Instructor> instructors;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

//    public boolean isClassGroup() {
//        return classGroup;
//    }
//
//    public void setClassGroup(boolean classGroup) {
//        this.classGroup = classGroup;
//    }
//
//    public void setUserList(List<String> userList) {
//        this.userList = userList;
//    }
//
//    public List<String> getUserList() {
//        return userList;
//    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ChatMessage getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMessage chatMsg) {
        this.chatMsg = chatMsg;
    }
}
