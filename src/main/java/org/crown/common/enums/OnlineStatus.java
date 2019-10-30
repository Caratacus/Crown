package org.crown.common.enums;

public enum OnlineStatus {
    /**
     * 用户状态
     */
    on_line("在线"), off_line("离线");
    private final String info;

    OnlineStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}