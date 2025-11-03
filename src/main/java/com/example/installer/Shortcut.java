package com.example.installer;

import javax.persistence.*;

@Entity
@Table(name = "shortcuts")
public class Shortcut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean createShortcut;

    @Column(nullable = false)
    private String shortcutPath;

    @ManyToOne
    @JoinColumn(name = "icon_id", nullable = false)
    private Icon icon;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Shortcut() {

    }

    public Shortcut(boolean createShortcut, String shortcutPath, Icon icon, Project project) {
        this.createShortcut = createShortcut;
        this.shortcutPath = shortcutPath;
        this.icon = icon;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCreateShortcut() {
        return createShortcut;
    }

    public void setCreateShortcut(boolean createShortcut) {
        this.createShortcut = createShortcut;
    }

    public String getShortcutPath() {
        return shortcutPath;
    }

    public void setShortcutPath(String shortcutPath) {
        this.shortcutPath = shortcutPath;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getShortcutDetails() {
        return "Create Shortcut: " + createShortcut + ", Path: " + shortcutPath + ", Icon: " + icon.getIconDetails();
    }
}
