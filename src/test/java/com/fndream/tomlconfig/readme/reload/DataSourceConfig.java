package com.fndream.tomlconfig.readme.reload;

import com.fndream.tomlconfig.AutoLoadTomlConfig;
import com.fndream.tomlconfig.annotation.TableField;
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

    public static DataSourceConfig getInstance() {
        return AppToml.getInstance().getDatasourceConfig();
    }
}
