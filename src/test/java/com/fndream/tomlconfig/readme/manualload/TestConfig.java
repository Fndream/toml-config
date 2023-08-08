package com.fndream.tomlconfig.readme.manualload;

import com.fndream.tomlconfig.TomlConfig;
import com.fndream.tomlconfig.TomlUtil;
import org.tomlj.TomlTable;

public class TestConfig extends TomlConfig {
    protected int intValue;
    protected long longValue;
    protected double doubleValue;
    protected int[] array;
    protected int[][] multArray;

    public TestConfig() {
        super(null);
        this.array = new int[0];
        this.multArray = new int[0][];
    }

    public TestConfig(TomlTable source) {
        super(source);
        this.intValue = TomlUtil.getInt(source, "int-value", 0);
        this.longValue = TomlUtil.getLong(source, "long-value", 0L);
        this.doubleValue = TomlUtil.getDouble(source, "double-value", 0.0D);
        this.array = TomlUtil.getIntArray(source, "array", () -> new int[0]);
        this.multArray = TomlUtil.getArray(source, "mult-array", int[][].class, () -> new int[0][]);
    }
}
