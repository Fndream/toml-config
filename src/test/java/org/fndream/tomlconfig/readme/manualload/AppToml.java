package org.fndream.tomlconfig.readme.manualload;

import org.fndream.tomlconfig.TomlConfig;
import org.fndream.tomlconfig.TomlUtil;
import org.fndream.tomlconfig.annotation.TableField;
import org.tomlj.TomlTable;

public class AppToml extends TomlConfig {
    @TableField("switch")
    protected boolean switched;
    protected Strategy strategy;
    protected DataSourceConfig dataSourceConfig;
    protected TestConfig testConfig;

    public AppToml() {
        super(null);
        this.switched = true;
        this.strategy = Strategy.NO_ONE;
        this.dataSourceConfig = new DataSourceConfig();
        this.testConfig = new TestConfig();
    }

    public AppToml(TomlTable source) {
        super(source);
        this.switched = TomlUtil.getBoolean(source, "switch", false);
        this.strategy = TomlUtil.getEnum(source, "strategy", Strategy.class, Strategy.NO_ONE);
        this.dataSourceConfig = TomlUtil.getRequiredTomlConfig(source, "datasource", DataSourceConfig.class);
        this.testConfig = TomlUtil.getTomlConfig(source, "test", TestConfig.class, TestConfig::new);
    }
}
