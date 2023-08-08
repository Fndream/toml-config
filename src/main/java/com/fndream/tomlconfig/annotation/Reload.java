package com.fndream.tomlconfig.annotation;

import com.fndream.tomlconfig.AutoReloadToml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 重载 {@link AutoReloadToml} 实例<br/>
 * 应注解在继承自 {@link AutoReloadToml} 并且Class类型为自身的static字段上。
 * 在 <code>AutoReloadToml.reload()</code> 方法被调用时，会为被 @Reload 注解的字段赋值新解析的对象。
 * 若被注解的字段不符合要求，将抛出异常。
 * <pre>
 * &#064;Reload("config/app.toml")
 * private static AppToml INSTANCE = TomlUtil.parseConfig("config/app.toml", AppToml.class);
 * </pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reload {
    /**
     * 配置文件路径
     */
    String value();

    /**
     * 在文件被修改时进行重载
     */
    boolean autoReload() default false;
}
