# TomlConfig
TomlConfig是一个基于[Tomlj](https://github.com/tomlj/tomlj)封装的高效Toml配置文件库，具有以下特性：

* 自动创建默认配置文件、自动key映射、整洁的Toml配置格式输出。
* 必需key、默认值、行上注释、行右注释、自动重载等功能。
* int、long、double、String、Enum、array、多维数组等基本类型映射、List、多维List、Map等集合映射。

## 快速上手
app.toml
```toml
switch = true
strategy = "NO_ONE"

[datasource]
url = "url"
username = "username"
password = "password"

[test]
int-value = 2147483647
long-value = 2147483648
double-value = 0.0
array = [1, 2, 3]
mult-array = [[10, 10], [20, 20], [30, 30]]
```
### 手动加载
```java
// 1.创建root节点的AppToml类，继承TomlConfig
public class AppToml extends TomlConfig {
    // 指定key
    @TableField("switch")
    protected boolean switched;
    
    // 枚举类型
    protected Strategy strategy;
    
    // 子表
    protected DataSourceConfig datasourceConfig;
    protected TestConfig testConfig;

    // 定义默认配置
    public AppToml() {
        super(null);
        this.switched = true;
        this.strategy = Strategy.NO_ONE;
        this.datasourceConfig = new DataSourceConfig();
        this.testConfig = new TestConfig();
    }

    // 从配置源中读取值
    public AppToml(TomlTable source) {
        super(source);
        // 使用TomlUtil中的各种Get方法获取source中的值
        this.switched = TomlUtil.getBoolean(source, "switch", false);
        this.strategy = TomlUtil.getEnum(source, "strategy", Strategy.class, Strategy.NO_ONE);
        
        // 带有Required的方法表示要获取的key是必须在Toml文件中声明的，否则会抛出异常
        this.datasourceConfig = TomlUtil.getRequiredTomlConfig(source, "datasource", DataSourceConfig.class);
        this.testConfig = TomlUtil.getTomlConfig(source, "test", TestConfig.class, TestConfig::new);
    }

    // getter setter...
}

// 2.创建DataSourceConfig类，继承TomlConfig
public class DataSourceConfig extends TomlConfig {
    protected String url;
    protected String username;
    protected String password;

    public DataSourceConfig() {
        super(null);
        this.url = "";
        this.username = "";
        this.password = "";
    }
    
    public DataSourceConfig(TomlTable source) {
        super(source);
        this.url = TomlUtil.getRequiredString(source, "url");
        this.username = TomlUtil.getRequiredString(source, "username");
        this.password = TomlUtil.getRequiredString(source, "password");
    }

    // getter setter...
}

// 3.创建TestConfig类，继承TomlConfig
public class TestConfig extends TomlConfig {
    protected int intValue;
    protected long longValue;
    protected double doubleValue;
    protected int[] array;
    protected int[][] multArray;

    public TestConfig() {
        super(null);
        // 为引用类型赋值默认值，用于生成默认配置文件
        // (生成Toml格式字符串时，不会包含null值)
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

    // getter setter...
}

// 4.创建main方法测试
public class Main {
    public static void main(String[] args) {
        AppToml appToml = TomlUtil.readConfig("config/app.toml", AppToml.class, true);
        System.out.println(appToml.toToml());
        /*      输出的内容：
                switch = true
                strategy = "NO_ONE"

                [datasource]
                url = "url"
                username = "username"
                password = "password"

                [test]
                int-value = 2147483647
                long-value = 2147483648
                double-value = 0.0
                array = [1, 2, 3]
                mult-array = [[10, 10], [20, 20], [30, 30]]
         */
        /*
                文件不存在则会创建一个默认的配置实例并写进app.toml文件：
                switch = true
                strategy = "NO_ONE"
                
                [datasource]
                url = ""
                username = ""
                password = ""
                
                [test]
                int-value = 0
                long-value = 0
                double-value = 0.0
                array = []
                mult-array = []
         */
    }
}
```

### 自动加载
```java
// 使用自动加载则继承AutoLoadTomlConfig
public class AppToml extends AutoLoadTomlConfig {
    
    // value属性用于指定key(非必需)，comment用于在toToml()时写出注释
    @TableField(value = "switch", topComment = "switch")
    protected boolean switched = true;
    
    @TableField(value = "strategy", topComment = "strategy")
    protected Strategy strategy = Strategy.NO_ONE;

    // 可以使用数组的形式创建多行注释，当然也可以使用 \n 或 """
    @TableField(value = "datasource", topComment = {"datasource", "dbsource"})
    protected DataSourceConfig datasourceConfig = DataSourceConfig.DEFAULT;

    @TableField(value = "test", topComment = "test")
    protected TestConfig testConfig = TestConfig.DEFAULT;

    // 默认配置
    public AppToml() {
        super(null);
    }

    // 从配置源中读取值
    public AppToml(TomlTable source) {
        super(source);
        // 使用自动加载代替TomlUtil，传入当前类的实例。this.getClass()是错误的写法。
        this.load(AppToml.class);
    }
}

// 创建DataSourceConfig类
public class DataSourceConfig extends AutoLoadTomlConfig {
    public static final DataSourceConfig DEFAULT = new DataSourceConfig();

    // required = true表示此字段在加载时必须存在，否则抛出异常
    @TableField(required = true, rightComment = "url")
    protected String url = "";
    
    @TableField(required = true, rightComment = "username")
    protected String username = "";

    @TableField(required = true, rightComment = "password")
    protected String password = "";
    
    public DataSourceConfig() {
        super(null);
    }

    public DataSourceConfig(TomlTable source) {
        super(source);
        this.load(DataSourceConfig.class);
    }
}

// 创建TestConfig类
public class TestConfig extends AutoLoadTomlConfig {
    public static final TestConfig DEFAULT = new TestConfig();

    protected int intValue;
    protected long longValue;
    protected double doubleValue;
    
    // 为引用类型赋值默认值
    protected int[] array = new int[0];
    protected int[][] multArray = new int[0][];

    public TestConfig() {
        super(null);
    }

    public TestConfig(TomlTable source) {
        super(source);
        this.load(TestConfig.class);
    }
}

public class Main {
    public static void main(String[] args) {
        AppToml appToml = TomlUtil.readConfig("config/app.toml", AppToml.class, true);
        System.out.println(appToml.toToml());
        /*      输出的内容：
                # switch
                switch = true
                
                # strategy
                strategy = "NO_ONE"
                
                
                # datasource
                # dbsource
                [datasource]
                url = "url"  # url
                username = "username"  # username
                password = "password"  # password
                
                # test
                [test]
                int-value = 2147483647
                long-value = 2147483648
                double-value = 0.0
                array = [1, 2, 3]
                mult-array = [[10, 10], [20, 20], [30, 30]]
         */
        /*     文件不存在时创建的默认配置文件：
                # switch
                switch = true
                
                # strategy
                strategy = "NO_ONE"
                
                
                # datasource
                # dbsource
                [datasource]
                url = ""  # url
                username = ""  # username
                password = ""  # password
                
                # test
                [test]
                int-value = 0
                long-value = 0
                double-value = 0.0
                array = []
                mult-array = []
         */
    }
}
```

> **在上面的例子中，我们使用以Toml为后缀的类表示一个.toml文件的root节点，它一般包含着很多个Config表，表示着这个.toml文件中都含有哪些配置分类。使用Config为后缀的类表示Toml中的其中一个配置表，定义具体的配置项。**
>
> **不推荐多级子表的配置类，这可能会使配置类的结构过于复杂。尽可能保证一个.toml文件中仅有一级嵌套表，建议多善用分词构建key。例如："min-award" | "max-award"。**

### 单例配置
```java
// 为AppToml添加单例常量
public class AppToml extends AutoLoadTomlConfig {
    private static final AppToml INSTANCE = TomlUtil.readConfig("config/app.toml", AppToml.class, true);
    
    public static AppToml getInstance() {
        return INSTANCE;
    }
}

// 为DataSourceConfig添加get方法，从AppToml根节点中获取
public class DataSourceConfig extends AutoLoadTomlConfig {
    public static DataSourceConfig getInstance() {
        return AppToml.getInstance().getDatasourceConfig();
    }
}

// 为TestConfig添加get方法，从AppToml根节点中获取
public class TestConfig extends AutoLoadTomlConfig {
    public static TestConfig getInstance() {
        return AppToml.getInstance().getTestConfig();
    }   
}

public class Main {
    public static void main(String[] args) {
        // 外部只需访问使用即可，在AppToml第一次被访问时，会自动加载配置文件/创建默认配置文件并加载
        DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();
        TestConfig testConfig = TestConfig.getInstance();
        
        System.out.println(dataSourceConfig.toToml());
        System.out.println("=========================");
        System.out.println(testConfig.toToml());
        /*      输出的内容：
                url = "url"  # url
                username = "username"  # username
                password = "password"  # password
                =========================
                int-value = 2147483647
                long-value = 2147483648
                double-value = 0.0
                array = [1, 2, 3]
                mult-array = [[10, 10], [20, 20], [30, 30]]
         */
        /*      文件不存在时输出的内容：
                url = ""  # url
                username = ""  # username
                password = ""  # password
                =========================
                int-value = 0
                long-value = 0
                double-value = 0.0
                array = []
                mult-array = []
         */
    }
}
```

### 配置重载
```java
// 将Toml类继承自AutoReloadToml
public class AppToml extends AutoReloadToml {
    // 为单例常量添加@Reload注解指定文件路径并将final去除，调用父类的reload()方法即可进行重载。
    // 若autoReload = true，当文件内容发生更改时同样会进行重载。
    // 重载后再通过AppToml.getInstance()获取到的便是新的实例。
    @Reload(value = "config/app.toml", autoReload = true)
    private static AppToml INSTANCE = TomlUtil.readConfig("config/app.toml", AppToml.class, true);
}
```