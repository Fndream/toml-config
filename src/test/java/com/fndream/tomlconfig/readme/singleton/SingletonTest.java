package com.fndream.tomlconfig.readme.singleton;

import org.junit.jupiter.api.Test;

public class SingletonTest {
    @Test
    public void test() {
        DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();
        TestConfig testConfig = TestConfig.getInstance();
        System.out.println(dataSourceConfig.toToml());
        System.out.println("=========================");
        System.out.println(testConfig.toToml());
    }
}
