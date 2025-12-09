package com.example.installer.service.installer;
import org.springframework.stereotype.Service;

@Service
public class Launch4jConfigBuilder {

    private String jarPath;
    private String exePath;
    private String mainClass;
    private String minJreVersion;
    private String iconPath;
    private boolean createDesktopShortcut;

    private String headerType = "gui";

    public Launch4jConfigBuilder setJarPath(String jarPath) {
        this.jarPath = jarPath;
        return this;
    }

    public Launch4jConfigBuilder setExePath(String exePath) {
        this.exePath = exePath;
        return this;
    }

    public Launch4jConfigBuilder setMainClass(String mainClass) {
        this.mainClass = mainClass;
        return this;
    }

    public Launch4jConfigBuilder setMinJreVersion(String minJreVersion) {
        this.minJreVersion = minJreVersion;
        return this;
    }

    public Launch4jConfigBuilder setIconPath(String iconPath) {
        this.iconPath = iconPath;
        return this;
    }

    public Launch4jConfigBuilder setCreateDesktopShortcut(boolean val) {
        this.createDesktopShortcut = val;
        return this;
    }

    public Launch4jConfigBuilder setHeaderType(String headerType) {
        this.headerType = headerType;
        return this;
    }

    public String build() {
        StringBuilder xml = new StringBuilder();

        xml.append("""
                <launch4jConfig>
                    <dontWrapJar>false</dontWrapJar>
                """);

        xml.append("    <headerType>").append(headerType).append("</headerType>\n");

        xml.append("    <jar>").append(jarPath).append("</jar>\n");
        xml.append("    <outfile>").append(exePath).append("</outfile>\n");

        if (iconPath != null && !iconPath.isEmpty()) {
            xml.append("    <icon>").append(iconPath).append("</icon>\n");
        }

        xml.append("""
                    <errTitle>Installer Generator</errTitle>
                    <classPath>
                """);

        xml.append("        <mainClass>").append(mainClass).append("</mainClass>\n");
        xml.append("    </classPath>\n");

        xml.append("""
                <jre>
                """);

        xml.append("    <minVersion>").append(minJreVersion).append("</minVersion>\n");

        xml.append("""
                </jre>
                </launch4jConfig>
                """);

        return xml.toString();
    }
}