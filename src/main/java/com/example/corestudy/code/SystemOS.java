package com.example.corestudy.code;

public enum SystemOS {
    Window,
    Linux,
    MacOS,
    Other;

    private static SystemOS os;

    public static SystemOS getSystemOS() {
        if (os != null) {
            return os;
        }

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("window")) {
            os = Window;
        } else if (osName.contains("mac")) {
            os = MacOS;
        } else if (osName.contains("linux")) {
            os = Linux;
        } else {
            os = Other;
        }
        return os;
    }
}
