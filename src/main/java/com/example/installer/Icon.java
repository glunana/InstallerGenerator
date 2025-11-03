package com.example.installer;

import javax.persistence.*;

@Entity
@Table(name = "icons")
public class Icon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String iconName;

    @Column(nullable = false)
    private String iconPath;

    public Icon() {

    }

    public Icon(String iconName, String iconPath) {
        this.iconName = iconName;
        this.iconPath = iconPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconDetails() {
        return "Icon Name: " + iconName + ", Path: " + iconPath;
    }
}
