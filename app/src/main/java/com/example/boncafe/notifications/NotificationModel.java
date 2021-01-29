package com.example.boncafe.notifications;

public class NotificationModel {
    String notifications_id;
    String user_ids;
    String notifications_message;
    String notifications_created_at;

    public String getNotifications_id() {
        return notifications_id;
    }

    public void setNotifications_id(String notifications_id) {
        this.notifications_id = notifications_id;
    }

    public String getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(String user_ids) {
        this.user_ids = user_ids;
    }

    public String getNotifications_message() {
        return notifications_message;
    }

    public void setNotifications_message(String notifications_message) {
        this.notifications_message = notifications_message;
    }

    public String getNotifications_created_at() {
        return notifications_created_at;
    }

    public void setNotifications_created_at(String notifications_created_at) {
        this.notifications_created_at = notifications_created_at;
    }
}
