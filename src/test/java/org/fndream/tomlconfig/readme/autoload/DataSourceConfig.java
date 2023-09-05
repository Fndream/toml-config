package org.fndream.tomlconfig.readme.autoload;

import org.fndream.tomlconfig.AutoLoadTomlConfig;
import org.fndream.tomlconfig.annotation.TableField;
import org.tomlj.TomlTable;

public class DataSourceConfig extends AutoLoadTomlConfig {
    public static final DataSourceConfig DEFAULT = new DataSourceConfig();

    @TableField(required = true, rightComment = "url")
    protected String url = "";

    @TableField(required = true, rightComment = "username")
    protected String username = "";

    @TableField(required = true, rightComment = "password")
    protected String password = "";

    public DataSourceConfig() {
        super(null);
    }

    public DataSourceConfig(TomlTable source) {
        super(source);
        this.load(DataSourceConfig.class);
    }
}
