package com.example.kioskclient;

import android.graphics.drawable.Drawable;
import android.widget.Button;

public class ListViewSearchItem {
        private Drawable restIcon;
        private String restName;
        private Number restScore;
        private String restDistance;
        private String restMain;

        public void setRestIcon(Drawable icon) {
            restIcon = icon;
        }
        public void setRestName(String name) {
            restName = name;
        }
        public void setRestScore(Number score) {
            restScore = score;
        }
        public void setRestDistance(String distance) { restDistance = distance; }
        public void setRestMain(String mainMenu) {
            restMain = mainMenu;
        }

        public Drawable getRestIcon() {
            return this.restIcon;
        }
        public String getRestName() {
            return this.restName;
        }
        public Number getRestScore() {
            return this.restScore;
        }
        public String getRestDistance() { return this.restDistance; }
        public String getRestMain() { return this.restMain; }

}
