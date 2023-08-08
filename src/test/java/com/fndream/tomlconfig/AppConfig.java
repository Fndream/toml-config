package com.fndream.tomlconfig;

import com.fndream.tomlconfig.annotation.TableField;
import org.tomlj.TomlTable;

public class AppConfig extends AutoLoadTomlConfig {
    @TableField(value = "permission", topComment = "permission")
    protected AppPermissionConfig appPermissionConfig = new AppPermissionConfig();

    @TableField(topComment = "username", rightComment = "username")
    protected String username = "username";

    @TableField(topComment = "username", rightComment = "username")
    protected String password = "password";

    public AppConfig() {
        super(null);
    }

    public AppConfig(TomlTable source) {
        super(source);
        this.load(AppConfig.class);
    }

    public static AppConfig getInstance() {
        return AppToml.getInstance().getAppConfig();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AppPermissionConfig getAppPermissionConfig() {
        return appPermissionConfig;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", appPermissionConfig=" + appPermissionConfig +
                '}';
    }
}
