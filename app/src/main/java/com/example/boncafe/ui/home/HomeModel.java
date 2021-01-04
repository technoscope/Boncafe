package com.example.boncafe.ui.home;
import android.net.Uri;
class HomeModel {
    public int id;
    public String text;
    public int drawablePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDrawablePath() {
        return drawablePath;
    }

    public void setDrawablePath(int drawablePath) {
        this.drawablePath = drawablePath;
    }
}
