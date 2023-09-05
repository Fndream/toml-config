package org.fndream.tomlconfig.readme.reload;

import org.fndream.tomlconfig.AutoReloadToml;
import org.fndream.tomlconfig.TomlUtil;
import org.fndream.tomlconfig.annotation.Reload;
import org.fndream.tomlconfig.annotation.TableField;
import org.tomlj.TomlTable;

public class AppToml extends AutoReloadToml {
    @Reload(value = "config/app.toml", autoReload = true)
    private static AppToml INSTANCE = TomlUtil.readConfig("config/app.toml", AppToml.class, true);

    @TableField(value = "switch", topComment = "switch")
    protected boolean switched;

    @TableField(value = "strategy", topComment = "strategy")
    protected Strategy strategy = Strategy.NO_ONE;

    @TableField(value = "datasource", topComment = {"datasource", "dbsource"})
    protected DataSourceConfig datasourceConfig = DataSourceConfig.DEFAULT;

    @TableField(value = "test", topComment = "test")
    protected TestConfig testConfig = TestConfig.DEFAULT;

    private AppToml() {
        super(null);
    }

    private AppToml(TomlTable source) {
        super(source);
        this.load(AppToml.class);
    }

    public static AppToml getInstance() {
        return INSTANCE;
    }

    public boolean isSwitched() {
        return switched;
    }

    public AppToml setSwitched(boolean switched) {
        this.switched = switched;
        return this;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public AppToml setStrategy(Strategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public DataSourceConfig getDatasourceConfig() {
        return datasourceConfig;
    }

    public AppToml setDatasource(DataSourceConfig datasource) {
        this.datasourceConfig = datasource;
        return this;
    }

    public TestConfig getTestConfig() {
        return testConfig;
    }

    public AppToml setTest(TestConfig test) {
        this.testConfig = test;
        return this;
    }
}
