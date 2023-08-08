package com.fndream.tomlconfig.readme.manualload;

import com.fndream.tomlconfig.TomlUtil;
import org.junit.jupiter.api.Test;

public class ManualLoadTest {
    @Test
    public void test() {
        AppToml appToml = TomlUtil.readConfig("config/app.toml", AppToml.class, true);
        System.out.println(appToml.toToml());
    }
}
