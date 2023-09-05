package com.fndream.tomlconfig;

import com.fndream.tomlconfig.annotation.Reload;
import com.fndream.tomlconfig.annotation.TableField;
import org.tomlj.TomlTable;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static com.fndream.tomlconfig.ConfigFileWatcher.CONFIG_FILE_WATCHER;

/**
 * 支持重载的配置类
 *
 * @author Fndream
 */
public abstract class AutoReloadToml extends AutoLoadTomlConfig {
    @TableField(ignore = true)
    private Field field;
    @TableField(ignore = true)
    private Class<?> type;
    @TableField(ignore = true)
    private String path;
    @TableField(ignore = true)
    private boolean autoReload;

    public AutoReloadToml(TomlTable source) {
        super(source);
        try {
            findReloadFieldArgument();
        } catch (Exception ignored) {
        }
        if (field != null && autoReload) {
            CONFIG_FILE_WATCHER.register(this.path, this);
        }
    }

    /**
     * 赋值新的配置类实例给被 @{@link Reload} 注解的单例字段
     */
    public void reload() {
        if (this.field == null) {
            findReloadFieldArgument();
        }
        try {
            this.field.set(null, TomlUtil.readConfig(new File(this.path), this.type));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private void findReloadFieldArgument() {
        for (Field field : this.getClass().getDeclaredFields()) {
            Reload reload = field.getAnnotation(Reload.class);
            if (reload == null) {
                continue;
            }

            if (!reload.value().isEmpty() && Modifier.isStatic(field.getModifiers()) && field.getType() == this.getClass()) {
                field.setAccessible(true);
                this.field = field;
                this.path = reload.value();
                this.type = field.getType();
                this.autoReload = reload.autoReload();
                return;
            }
        }
        throw new IllegalStateException("Failed to reload. Unable to find a valid @Reload field in " + this.getClass().getName() + ".");
    }
}
