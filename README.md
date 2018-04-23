# ac-func-tion
![ac-func-tion](https://travis-ci.org/InCar/ac-func-tion.svg?branch=master)

Java has 2 very common interfaces: Runnable and Callable

But both have none input argument

I created this project for interfaces like Runnable and Callable, but support accept input arguments.

[maven](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.incarcloud%22%20AND%20a%3A%22ac-func-tion%22)
```xml
<dependency>
    <groupId>com.incarcloud</groupId>
    <artifactId>ac-func-tion</artifactId>
    <version>1.1.0</version>
</dependency>
```


```java
Action<String> actionShow = (String txt)->{
    System.out.println(String.format("action is running with argument '%s'", txt));
};
actionShow.run("Hello Action");

Func<Integer, String> funcSize = (String txt)->{
    System.out.println(String.format("'%s' has %d characters", txt, txt.length));
    return txt.length();
};

String txt = "Func";
int size = funcSize.call(txt);
```

There are total 10 interfaces, accepte 5 arguments at most.

Action<> like Runnable, no return value:

Action<T>, Action2<T1,T2> ... Action5<T1,T2,T3,T4,T5>

Func<> like Callable, has return value:

Func<R,T>, Func<R,T1,T2> ... Func5<R,T1,T2,T3,T4,T5>

R is the return type, and T is the input type

There are also 2 classes wrapped thread pool.

```java
Action2<Integer, Integer> action = (a, b)->{
    System.out.println(String.format("Action(%d, %d)", a, b));
};

ExecutorForAcFunction pool = new ExecutorForAcFunction(Executors.newFixedThreadPool(2));
for(int i=0;i<5;i++){
    pool.submit(action, i, 10-i);
}

ScheduledExecutorForAcFunction scheduler = new ScheduledExecutorForAcFunction(Executors.newSingleThreadScheduledExecutor());
scheduler.schedule(action, 1000, TimeUnit.MILLISECONDS, 15, 17);

```

more samples in [src/test/java/com/incarcloud/lang/Basic.java](https://github.com/InCar/ac-func-tion/blob/master/src/test/java/com/incarcloud/lang/Basic.java)

## prerequisite
- [JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Gradle 4.5+](http://gradle.org/gradle-download/)
```shell
# 执行以下命令检查环境
java -version
gradle --version
```

## Configuration
Users from China can copy [gradle-sample.properties](https://github.com/InCar/ac-func-tion/blob/master/gradle-sample.properties) to `gradle.properties` for accelerating downloading speed via ali-yun mirror

## Compile
```SHELL
gradle assemble
```

