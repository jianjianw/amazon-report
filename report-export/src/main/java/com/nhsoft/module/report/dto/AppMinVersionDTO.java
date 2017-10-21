package com.nhsoft.module.report.dto;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by wangqianwen on 2017/6/15.
 */
public class AppMinVersionDTO implements Serializable {
    private static final long serialVersionUID = -2892735424207501650L;
    private String touchVersion;
    private String synchVersion;
    private String firebirdVersion;


    public String getTouchVersion() {
        return touchVersion;
    }

    public void setTouchVersion(String touchVersion) {
        this.touchVersion = touchVersion;
    }

    public String getSynchVersion() {
        return synchVersion;
    }

    public void setSynchVersion(String synchVersion) {
        this.synchVersion = synchVersion;
    }

    public String getFirebirdVersion() {
        return firebirdVersion;
    }

    public void setFirebirdVersion(String firebirdVersion) {
        this.firebirdVersion = firebirdVersion;
    }

    public static boolean checkVersion(String appVersion, String minVersion) {
        if(StringUtils.isEmpty(appVersion)) {
            return true;
        }
        if(StringUtils.isEmpty(minVersion)) {
            return true;
        }
        String[] appVersionSplit = appVersion.split("\\.");
        String[] minVersionSplit = minVersion.split("\\.");
        if(appVersionSplit.length != minVersionSplit.length) {
            return false;
        }
        for(int i = 0;i<appVersionSplit.length;i++) {
            if(appVersionSplit[i].compareTo(minVersionSplit[i]) < 0) {
                return false;
            }
        }
        return true;
    }

}
