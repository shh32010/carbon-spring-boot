# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Carbon 碳管理平台后端，基于 Spring Boot 2.2.12 + MyBatis + Spring Security (JWT) 的 Maven 多模块项目。涵盖招投标、制造执行(MES)、仓储(WMS)、供应链(SCM)、碳足迹、碳分配等业务域。

## 常用命令

```bash
# 构建（跳过测试）
mvn clean package -Dmaven.test.skip=true

# 仅编译
mvn clean compile

# 运行单个模块测试
mvn test -pl neu-system

# 运行应用（需先启动 MySQL 3306 和 Redis 6379）
cd neu-admin/target && java -Xms256m -Xmx1024m -jar carbon.jar

# 便捷脚本（bin/ 目录下）
bin/package.bat    # 打包
bin/clean.bat      # 清理
bin/run.bat        # 启动
```

- 服务端口: **9090**
- 数据库: MySQL `127.0.0.1:3306/carbon`，SQL 初始化脚本: `carbon.sql`
- 缓存: Redis `localhost:6379` (DB 12)
- API 文档: 启动后访问 Knife4j (Swagger)

## 架构与模块依赖

```
neu-common (基础工具类、BaseEntity、BaseController、异常、枚举)
  └── neu-system (用户/角色/菜单/部门/字典/配置管理)
       └── neu-framework (Spring Security、JWT 过滤器、AOP 切面、数据源配置)
            └── neu-carbon-mapper (所有业务域的 MyBatis Mapper 接口 + XML)
                 └── neu-carbon-service (所有业务域的 Service 接口 + 实现)
                      ├── neu-carbon-bid (招投标控制器)
                      ├── neu-carbon-mes (制造执行控制器)
                      ├── neu-carbon-wms (仓储控制器)
                      ├── neu-carbon-scm (供应链控制器)
                      ├── neu-carbon-footprint (碳足迹控制器)
                      ├── neu-carbon-report (报表控制器)
                      ├── neu-carbon-chat (聊天/WebSocket 控制器)
                      └── neu-carbon-distribusion (碳分配控制器)
                           └── neu-admin (Spring Boot 启动类 + 系统/监控控制器)
```

辅助模块:
- `neu-common-biz` — 共享业务逻辑（Demo 控制器、CMS API），被 `neu-carbon-service` 依赖
- `neu-quartz` — Quartz 定时任务管理
- `neu-generator` — Velocity 模板代码生成器

## 核心约定

### 分层模式
- **Controller** 继承 `BaseController`，使用 `startPage()` 分页，`getDataTable()`/`toAjax()` 封装响应
- **Service** 接口命名 `I*Service`，实现命名 `*ServiceImpl`，集中在 `neu-carbon-service`
- **Mapper** 接口 + MyBatis XML 集中在 `neu-carbon-mapper`
- **Entity** 继承 `BaseEntity`（自动填充 `createBy/createTime/updateBy/updateTime` 等字段）

### 统一响应格式
- `AjaxResult` — 通用响应 `{code, msg, data}`
- `TableDataInfo` — 分页响应 `{code, msg, rows, total}`

### 业务模块垂直切分特点
Controller 按业务域分模块，但 Service 和 Mapper 层集中在 `neu-carbon-service` 和 `neu-carbon-mapper`。新增业务代码时：
1. Entity/Mapper 放 `neu-carbon-mapper`
2. Service 接口+实现放 `neu-carbon-service`
3. Controller 放对应业务模块（如 `neu-carbon-bid`）
4. 在 `neu-admin` 的 pom.xml 确认已依赖该业务模块

### 安全与数据权限
- JWT 无状态认证，Token 通过 `Authorization` 头传递（1440 分钟过期）
- `DataScopeAspect` 实现数据权限过滤（基于角色的数据范围）
- `@DataSource` 注解 + `DynamicDataSource` 支持主从数据源切换

### 技术选型要点
- JSON 序列化: **FastJSON** (1.2.74) + Jackson 并存
- 连接池: **Alibaba Druid** 1.2.4
- 工具库: **Hutool** 4.5.11
- Java 版本: **1.8**
