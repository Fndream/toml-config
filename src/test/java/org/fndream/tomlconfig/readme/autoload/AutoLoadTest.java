package org.fndream.tomlconfig.readme.autoload;

import org.fndream.tomlconfig.TomlUtil;
import org.junit.jupiter.api.Test;

public class AutoLoadTest {
    @Test
    public void test() {
        AppToml appToml = TomlUtil.readConfig("config/app.toml", AppToml.class, true);
        System.out.println(appToml.toToml());
    }
}
