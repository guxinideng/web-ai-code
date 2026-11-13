// package：关键字，声明"这个类属于哪个包"。包就是文件夹的逻辑分组，防止不同类重名。
// shop.guxinideng：包名，用点分隔层级，对应磁盘目录 shop/guxinideng/。
// 末尾分号：Java 每条语句必须以 ; 结束。
package shop.guxinideng;

// import：关键字，导入别的包里的类，之后才能直接写短类名。
// org.junit.jupiter.api：JUnit 5 的 API 包。jupiter 是 JUnit 5 的代号，api 表示给开发者调用的那部分。
// Assertions：断言工具类，里面是一堆静态方法（assertEquals、assertTrue 等），用来"判断结果对不对"。
import org.junit.jupiter.api.Assertions;
// Test：注解（annotation）类，用来标记"这个方法是一个测试方法"。
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// public：公开访问，任何地方都能访问这个类。
// class：关键字，声明一个类。
// UserServiceTest：类名。命名惯例是"被测类名 + Test"，所以这是 UserService 的测试类。
public class UserServiceTest {

    // private：私有，只有本类内部能访问。
    // final：这个变量只能赋值一次，之后不能再指向别的对象（对象内部的状态仍可变）。
    // UserService：变量的类型，即被测试的业务类。
    //   它和本类在同一个包 shop.guxinideng 里，所以不需要 import。
    // userService：变量名，小驼峰命名。
    // new UserService()：new 是创建对象的关键字，调用 UserService 的无参构造方法，造出一个实例。
    // JUnit 5 默认给每个 @Test 方法都新建一个测试类实例，
    //   所以每个测试方法拿到的都是全新的 userService，互不影响。
    private static final UserService userService = new UserService();

    // @Test：注解，告诉 JUnit"下面这个方法是测试用例，请自动运行它"。
    //   没有它，方法只是普通方法，不会被执行。
    @BeforeAll
    @DisplayName("年龄是否大于25")
    public static void testGetAge() {
        // 这是单行注释：// 后面倒行尾都会被编译器忽略。
        // 出生于 2000-01-01，年龄随当前日期变化，这里只验证合理范围
        // Integer：int 的包装类（对象类型）。可以为 null，int 不行。
        // age：变量名，存放计算出的年龄。
        // userService.getAge(...)：调用 userService 对象的 getAge 方法，返回值赋给 age。
        // "110101200001011234"：18 位身份证号字符串。
        //   前 6 位 110101 = 地区码（北京东城区）
        //   中间 8 位 20000101 = 出生日期 2000年1月1日
        //   后 3 位 123 = 顺序码，其中第 17 位（这里是 3）奇数=男、偶数=女
        //   最后 1 位 4 = 校验码
        Integer age = userService.getAge("110101200001011234");

        // Assertions.assertTrue(条件)：断言条件为 true，否则测试失败。
        // age >= 25：age 是 Integer，这里会自动拆箱成 int 再比较。
        //   若 getAge 返回 null，拆箱时会抛 NullPointerException，测试同样失败。
        // 为什么是 25：出生于 2000 年，到 2025 年起至少 25 岁，且只会越来越大，
        //   所以这个断言不会随时间失效。
        Assertions.assertTrue(age >= 25);
    }

    // 同上，标记下面是一个测试方法
    @Test
    // testGetGender：测试"根据身份证获取性别"
    public void testGetGender() {
        // Assertions.assertEquals(期望值, 实际值)：断言两者相等，否则失败。
        //   参数顺序固定：第一个是 expected（期望），第二个是 actual（实际）。
        //   写反了不影响通过与否，但失败时的提示信息会颠倒。
        //   比较字符串用的是 equals()，比较的是内容，不是引用地址。
        // "男"：期望值。
        // userService.getGender("110101200001011234")：实际值。
        //   第 17 位是 3（奇数）-> 应返回 "男"。
        Assertions.assertEquals("男", userService.getGender("110101200001011234"));

        // 这次身份证号的第 17 位是 2（偶数）-> 应返回 "女"。
        // 两个断言写在同一个方法里：只要有一个失败，整个方法就算失败，
        //   而且第一个失败后，后面的断言不会再执行。
        Assertions.assertEquals("女", userService.getGender("110101200001011224"));
    }

    // 同上，标记下面是一个测试方法
    @Test
    // testIllegalIdCard：测试"非法身份证号"的情况
    public void testIllegalIdCard() {
        // Assertions.assertThrows(异常类型, 要执行的代码)：
        //   断言"执行这段代码时，必须抛出指定类型的异常"。
        //   通过的条件：抛出的是该类型或其子类的异常。
        //   不通过的条件：没抛异常，或抛出了别的类型的异常。
        // IllegalArgumentException：Java 自带异常，意思是"传入的参数不合法"。
        // .class：拿到这个类的 Class 对象，把"异常的类型"当参数传进去。
        // 末尾的逗号：参数还没写完，下一行继续（这里只是换行排版）。
        Assertions.assertThrows(IllegalArgumentException.class,
                // () -> ...：Lambda 表达式，可以理解为"一小段待执行的代码"。
                //   ()：没有参数。
                //   ->：箭头，左边是参数，右边是代码体。
                //   为什么要包成 Lambda：不能直接写 userService.getAge("123")，
                //     否则异常会在 assertThrows 被调用之前就抛出，断言方法接不住。
                //     包成 Lambda 后，由 assertThrows 内部去执行并捕获异常。
                // userService.getAge("123")：传入 "123"，它不是 18 位，是非法身份证。
                //   期望 getAge 内部校验后抛出 IllegalArgumentException。
                () -> userService.getAge("123"));
        // ); 收尾：右括号结束 assertThrows 的参数列表，分号结束语句。
    }
}
// 最后这个 } 结束整个类