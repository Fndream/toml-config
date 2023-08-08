package com.fndream.tomlconfig.readme.reload;

import com.fndream.tomlconfig.AutoLoadTomlConfig;
import org.tomlj.TomlTable;

public class TestConfig extends AutoLoadTomlConfig {
    public static final TestConfig DEFAULT = new TestConfig();

    protected int intValue;
    protected long longValue;
    protected double doubleValue;
    protected int[] array = new int[0];
    protected int[][] multArray = new int[0][];

    public TestConfig() {
        super(null);
    }

    public TestConfig(TomlTable source) {
        super(source);
        this.load(TestConfig.class);
    }

    public static TestConfig getInstance() {
        return AppToml.getInstance().getTestConfig();
    }
}
