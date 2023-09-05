package org.fndream.tomlconfig.readme.reload;

import org.junit.jupiter.api.Test;

public class ReloadTest {
    @Test
    public void test() {
        DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();
        TestConfig testConfig = TestConfig.getInstance();
        System.out.println(dataSourceConfig.toToml());
        System.out.println("=========================");
        System.out.println(testConfig.toToml());
        while (true) {
            AppToml appToml = AppToml.getInstance();
            System.out.println(System.identityHashCode(appToml));
            System.out.println(appToml.toToml());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
