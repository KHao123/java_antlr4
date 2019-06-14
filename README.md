# Java Code Refractor

本程序实现了自动格式化代码、简易命名重构以及循环语句转换等功能

## 简介

本程序是在语法分析阶段处理的程序，通过使用[Antlr4](https://www.antlr.org/)提供的Java8文法，生成Java8语言的语法分析器，并通过Antlr4生成的模板，重写其监听器实现上述功能。

## 目前实现功能

代码格式化规范以[谷歌官方代码规范文档](https://google.github.io/styleguide/javaguide.html)为准

### 代码格式化
* 换行规范
* 运算符空格规范

### 重命名
* 包名规范化
* 类名下划线转大驼峰
* 方法名变量名下划线转小驼峰
* 常量转大写

### 代码整理
* for和while语句互转

## 目录介绍
```
    java_antlr4
    |
    |-- src                              源代码文件
    |   |-- Main.java                    程序入口
    |   |-- Standardizer.java            实现重命名以及循环语句转换的功能
    |   |-- Standardizer2.java           实现空格转换的功能
    |   |-- JavaLexer.java               Antlr4生成的词法分析器模板
    |   |-- JavaParser.java              Antlr4生成的语法分析器模板
    |   |-- JavaParserBaseListener.java  Antlr4提供的语法分析器监听器模板，为Standardizer的基础
    |   `-- JavaParserListener.java      Antlr4提供的语法分析器监听器默认实现
    |
    |-- demo.java                        提供格式化代码的样例
    |-- JavaLexer.g4                     Java8词法分析器的文法
    `-- JavaParser.g4                    Java8语法分析器的文法  
```

## 快速开始

### 构建项目

1. 安装 [Antlr 4.7.2](https://www.antlr.org/)。
2. 克隆项目，构建项目，配置Artifact，生成jar。
3. 运行以下命令格式化代码：
    
    `java -jar JavaAntlr4.jar test.java`

## 测试

原始代码文件：
```
public class de_mo {
    final int abc = 0;
    int t_T;

    void fun_asd(int i) {
        int j = i;
    }

    int ad;

    public static void main0(String[] args) {
        aas_qweq_sad a = new aas_qweq_sad();
        fun_asd(3);
        int j = abc;
        t_T = 9;
        for (int i = 1; i <= 10; i++) {
            j++;
        }
    }
}

public class aas_qweq_sad {

}
```

格式化代码后：

```
public class DeMo{
        final int ABC = 0;
        int tT;
        void funAsd(int i){
                int j = i;
        }
        int ad;
        public static void main0(String[] args){
                AasQweqSad a = new AasQweqSad();
                funAsd(3);
                int j = ABC;
                tT = 9;
                int i = 1;
                while (i <= 10){
                        j++;
                        i++;
                }
        }
}
public class AasQweqSad{
}
```


