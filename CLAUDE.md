# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Carbon 碳管理平台后端，基于 Spring Boot 2.2.12 + MyBatis + Spring Security (JWT) 的 Maven 多模块项目。涵盖招投标、制造执行(MES)、仓储(WMS)、供应链(SCM)、碳足迹、碳分配等业务域。共 905 个 Java 源文件、147 个 Mapper XML、137 张数据库表。

## 常用命令

```bash
# 构建（跳过测试）
mvn clean package -Dmaven.test.skip=true

# 仅编译
mvn clean compile

# 编译单个模块（含依赖）
mvn clean compile -pl neu-carbon-bid -am

# 运行应用（需先启动 MySQL 3306 和 Redis 6379）
cd neu-admin/target && java -Xms256m -Xmx1024m -jar carbon.jar

# 便捷脚本（bin/ 目录下，Windows .bat）
bin/package.bat    # 打包
bin/clean.bat      # 清理
bin/run.bat        # 启动
```

- 服务端口: **9090**
- 数据库: MySQL `127.0.0.1:3306/carbon`，SQL 初始化脚本: `carbon.sql`（含 137 表 + 种子数据）
- 缓存: Redis `localhost:6379` (DB 12)
- API 文档: 启动后访问 Knife4j → `http://localhost:9090/doc.html`

## 架构与模块依赖

```
neu-common (BaseEntity、BaseController、异常、枚举、注解)
  └── neu-system (用户/角色/菜单/部门/字典/配置管理)
       └── neu-framework (Spring Security、JWT、AOP 切面、数据源、全局异常)
            └── neu-carbon-mapper (业务域 Mapper 接口 + MyBatis XML)
                 └── neu-carbon-service (业务域 Service 接口 + 实现)
                      ├── neu-carbon-bid      # 招投标
                      ├── neu-carbon-mes      # 制造执行
                      ├── neu-carbon-wms      # 仓储
                      ├── neu-carbon-scm      # 供应链
                      ├── neu-carbon-footprint # 碳足迹
                      ├── neu-carbon-report   # 报表
                      ├── neu-carbon-chat     # 聊天/WebSocket
                      └── neu-carbon-distribusion # 碳分配
                           └── neu-admin (启动类 + 系统/监控控制器)
```

辅助模块: `neu-common-biz`（共享业务）、`neu-quartz`（定时任务）、`neu-generator`（代码生成器）

### 两种业务模块组织模式

1. **集中式**（bid, mes, wms, scm, footprint, report）— Entity/Mapper 在 `neu-carbon-mapper`，Service 在 `neu-carbon-service`，Controller 在业务模块。但部分模块（如 bid）对简单实体也有本地的 Service/Mapper。
2. **自包含式**（distribusion, chat）— Controller + Service + Mapper + Domain 全部在模块内部。

新增业务代码时，优先遵循集中式模式。

## 核心约定

### 分层模式

- **Entity** 继承 `BaseEntity`（自动填充 createBy/createTime/updateBy/updateTime/remark/params），用 `@ApiModelProperty` 描述字段，`@Excel` 标记导出列
- **Mapper** 标准 CRUD: `selectById`, `selectList`, `insert`, `update`, `deleteById`, `deleteByIds`，XML 中用 `<resultMap>` + 动态 SQL
- **Service** 接口 `I*Service`，实现 `*ServiceImpl`（`@Service`），方法直接委托 Mapper
- **Controller** 继承 `BaseController`，标准 CRUD 端点:
  - `GET /list` — 分页列表 (`startPage()` + `getDataTable()`)
  - `GET /export` — Excel 导出 (`ExcelUtil`)
  - `GET /{id}` — 详情 → `AjaxResult.success(data)`
  - `POST` — 新增 → `toAjax(service.insert(...))`
  - `PUT` — 修改 → `toAjax(service.update(...))`
  - `DELETE /{ids}` — 批量删除 → `toAjax(service.deleteByIds(...))`

### 统一响应

- `AjaxResult` — 通用响应 `{code, msg, data}`
- `TableDataInfo` — 分页响应 `{code, msg, rows, total}`

### 权限与日志注解

Controller 方法需加:
- `@PreAuthorize("@ss.hasPermi('业务域:实体:操作')")` — 权限校验
- `@Log(title = "...", businessType = BusinessType.INSERT/UPDATE/DELETE)` — 操作日志

### 安全

- JWT 无状态认证，Token 通过 `Authorization` 头传递（HS512 签名，1440 分钟过期，存 Redis）
- `@EnableGlobalMethodSecurity(prePostEnabled=true)` + `@PreAuthorize` 做接口级鉴权
- `DataScopeAspect` 按角色数据范围自动过滤
- `@DataSource` 注解 + `DynamicDataSource` 支持主从数据源切换
- XSS 过滤器默认拦截 `/system/*`、`/monitor/*`、`/tool/*`

### 关键配置文件

| 文件 | 用途 |
|------|------|
| `neu-admin/.../application.yml` | 端口、Redis、MyBatis、Swagger、Token |
| `neu-admin/.../application-druid.yml` | 数据源（主从）、Druid 连接池 |
| `neu-admin/.../mybatis/mybatis-config.xml` | MyBatis 全局设置、自定义拦截器 |

### 技术选型

- Java 1.8，Spring Boot 2.2.12，MyBatis + PageHelper
- JSON: FastJSON 1.2.74 + Jackson 并存
- 连接池: Alibaba Druid 1.2.4
- 工具库: Hutool 4.5.11
- 接口文档: Swagger 2 + Knife4j

### 已知问题

- 测试覆盖率为零（仅 1 个 stub 测试），无 CI/CD
- `application-druid.yml` 中数据库密码硬编码（root/123456）
- `application.yml` 中 JWT secret 硬编码
- 项目根目录存在 `log.path_IS_UNDEFINED/`，说明日志路径未正确配置
- WebSocket 依赖使用本地 jar（`src/main/resources/libs/spring-websocket-5.2.12.RELEASE.jar`）而非 Maven 依赖
