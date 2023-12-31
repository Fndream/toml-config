package org.fndream.tomlconfig.readme.manualload;

import org.fndream.tomlconfig.TomlConfig;
import org.fndream.tomlconfig.TomlUtil;
import org.tomlj.TomlTable;

public class DataSourceConfig extends TomlConfig {
    protected String url;
    protected String username;
    protected String password;

    public DataSourceConfig() {
        super(null);
        this.url = "";
        this.username = "";
        this.password = "";
    }

    public DataSourceConfig(TomlTable source) {
        super(source);
        this.url = TomlUtil.getRequiredString(source, "url");
        this.username = TomlUtil.getRequiredString(source, "username");
        this.password = TomlUtil.getRequiredString(source, "password");
    }
}
