package com.fndream.tomlconfig;

import com.fndream.tomlconfig.annotation.Reload;
import com.fndream.tomlconfig.annotation.TableField;
import org.tomlj.TomlTable;

public class AppToml extends AutoReloadToml {
    @Reload("config/app.toml")
    private static AppToml INSTANCE = TomlUtil.readConfig("config/app.toml", AppToml.class, true);

    @TableField(value = "app", topComment = "app")
    protected AppConfig appConfig = new AppConfig();

    @TableField(value = "switch", topComment = "system switch")
    protected boolean switched = false;

    private AppToml(TomlTable source) {
        super(source);
        this.load(AppToml.class);
    }

    public static AppToml getInstance() {
        return INSTANCE;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public boolean isSwitched() {
        return switched;
    }

    @Override
    public String toString() {
        return "AppToml{" +
                "appConfig=" + appConfig +
                ", switched=" + switched +
                '}';
    }
}
