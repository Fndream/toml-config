package org.fndream.tomlconfig;

import org.junit.jupiter.api.Test;

public class TomlTest {

    @Test
    public void testAppConfig() {
        // create default "test.toml" config and load
        AppPermissionConfig.getInstance();
        System.out.println(AppToml.getInstance());

        AppToml.getInstance().reload();
    }
}
