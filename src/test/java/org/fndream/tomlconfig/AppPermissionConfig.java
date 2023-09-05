package org.fndream.tomlconfig;

import org.fndream.tomlconfig.annotation.TableField;
import org.tomlj.TomlTable;

import java.util.Arrays;

public class AppPermissionConfig extends AutoLoadTomlConfig {
    @TableField(topComment = "master array", rightComment = "master array")
    protected long[] master = new long[0];

    @TableField(topComment = "admin array", rightComment = "admin array")
    protected long[] admin = new long[0];

    public AppPermissionConfig() {
        super(null);
    }

    public AppPermissionConfig(TomlTable source) {
        super(source);
        this.load(AppPermissionConfig.class);
    }

    public static AppPermissionConfig getInstance() {
        return AppConfig.getInstance().getAppPermissionConfig();
    }

    public long[] getMaster() {
        return master;
    }

    public long[] getAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return "AppPermissionConfig{" +
                "master=" + Arrays.toString(master) +
                ", admin=" + Arrays.toString(admin) +
                '}';
    }
}
