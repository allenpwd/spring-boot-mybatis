application.yml里配置了初始化时执行schema目录下的创建sql语句。

期间遇到的问题：
1.sql injection violation
druid sql黑名单 报异常，监控统计时用了防御sql注入的filter-wall
后来指定数据库引擎和字符集就通过了