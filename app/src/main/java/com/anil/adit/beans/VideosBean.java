package com.anil.adit.beans;

import java.io.Serializable;

public class VideosBean implements Serializable {
    private String strPath;
    private String strThumb;
    private boolean booleanSelected;

    public String getStrPath() {
        return strPath;
    }

    public void setStrPath(String strPath) {
        this.strPath = strPath;
    }

    public String getStrThumb() {
        return strThumb;
    }

    public void setStrThumb(String strThumb) {
        this.strThumb = strThumb;
    }

    public boolean isBooleanSelected() {
        return booleanSelected;
    }

    public void setBooleanSelected(boolean booleanSelected) {
        this.booleanSelected = booleanSelected;
    }
}
