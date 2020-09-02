# RuleLint
自定义的lint检测规则

## 功能
* Serializable：检查java序列化过程中，是否有遗忘参数对象
* !!：提醒注意!!的使用
* lateinit：提醒减少使用lateinit
* Log：提醒请使用项目中提供的Log工具

## 集成
```gradle
android {
 lintOptions {
     abortOnError true
 }

 // 配置assemble任务依赖lint
 applicationVariants.all { variant ->
     variant.outputs.each { output ->
         def lintTask = tasks["lint${variant.name.capitalize()}"]
         output.assemble.dependsOn lintTask
     }
 }
}
```
