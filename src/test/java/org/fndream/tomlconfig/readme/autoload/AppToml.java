package org.fndream.tomlconfig.readme.autoload;

import org.fndream.tomlconfig.AutoLoadTomlConfig;
import org.fndream.tomlconfig.annotation.TableField;
import org.tomlj.TomlTable;

public class AppToml extends AutoLoadTomlConfig {
    @TableField(value = "switch", topComment = "switch")
    protected boolean switched;

    @TableField(value = "strategy", topComment = "strategy")
    protected Strategy strategy = Strategy.NO_ONE;

    @TableField(value = "datasource", topComment = {"datasource", "dbsource"})
    protected DataSourceConfig datasourceConfig = DataSourceConfig.DEFAULT;

    @TableField(value = "test", topComment = "test")
    protected TestConfig testConfig = TestConfig.DEFAULT;

    public AppToml() {
        super(null);
    }

    public AppToml(TomlTable source) {
        super(source);
        this.load(AppToml.class);
    }
}
