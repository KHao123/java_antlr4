# Java Code Refractor

本程序实现了自动格式化代码、简易命名重构以及循环语句转换等功能

## 简介

本程序是在语法分析阶段处理的程序，通过使用[Antlr4](https://www.antlr.org/)提供的Java8文法，生成Java8语言的语法分析器，并通过Antlr4生成的模板，重写其监听器实现上述功能。

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

#：java Main test.java


测试



开发者

讨论

以实现功能：

换行规范
运算符空格规范

将包名规范化
类名下划线转大驼峰
方法名变量名下划线转小驼峰
常量转大写

for和while语句互转


