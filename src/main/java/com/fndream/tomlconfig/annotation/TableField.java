package com.fndream.tomlconfig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置字段加载与写出时的行为
 * @author Fndream
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {
    /**
     * 加载与写出时生效：key名称
     */
    String value() default "";

    /**
     * 仅在加载时生效：是否必需
     */
    boolean required() default false;

    /**
     * 加载与写出时生效：是否忽略此字段的加载与写出
     */
    boolean ignore() default false;

    /**
     * 加载时生效：是否忽略此字段的加载
     */
    boolean ignoreLoad() default false;

    /**
     * 写出时生效：是否忽略此字段的写出
     */
    boolean ignoreWrite() default false;

    /**
     * 仅在加载时生效：默认值，支持int、long、double、boolean、String、Enum、array/List、LocalDate、LocalDateTime类型的字符串表达式。
     * @deprecated 存在众多兼容问题且类型不完善，代码不清晰，建议使用为字段赋值初始值的方式设置默认值。
     */
    @Deprecated
    String defaultValue() default "";

    /**
     * 仅在写出时生效：在字段上方的注释
     */
    String[] topComment() default {};

    /**
     * 仅在写出时生效：在字段右侧的注释
     */
    String[] rightComment() default {};

    /**
     * 仅在写出时生效：写出top注释前添加空行的数量
     */
    int prefixLine() default 0;

    /**
     * 仅在写出时生效：写出top注释和键值对后添加空行的数量
     */
    int suffixLine() default 1;
}
