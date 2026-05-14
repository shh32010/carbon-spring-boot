# Carbon 双碳管理平台（后端）

基于 Spring Boot 2.2.12 + MyBatis + Spring Security (JWT) 的碳管理综合平台后端服务，涵盖招投标、制造执行（MES）、仓储管理（WMS）、供应链（SCM）、碳足迹追踪、碳分配等业务模块。

## 技术栈

| 类别 | 技术 |
|------|------|
| 框架 | Spring Boot 2.2.12、Spring Security |
| 持久层 | MyBatis、PageHelper |
| 数据库 | MySQL 8（Druid 连接池） |
| 缓存 | Redis（Lettuce 客户端） |
| 认证 | JWT (jjwt 0.9.1)、BCrypt |
| 接口文档 | Swagger 2 + Knife4j |
| JSON | FastJSON 1.2.74 |
| 工具 | Hutool 4.5.11、Apache POI、Lombok |
| 构建 | Maven、Java 8 |

## 项目结构

```
carbon/
├── neu-common              # 基础工具：BaseEntity、BaseController、异常、枚举
├── neu-common-biz          # 通用业务：Demo 控制器、CMS API
├── neu-system              # 系统管理：用户、角色、菜单、部门、字典、配置
├── neu-framework           # 核心框架：Security、JWT 过滤器、AOP 切面、数据源
├── neu-quartz              # 定时任务（Quartz 集成）
├── neu-generator           # 代码生成器（Velocity 模板）
├── neu-carbon-mapper       # 所有业务域的 MyBatis Mapper 接口 + XML
├── neu-carbon-service      # 所有业务域的 Service 接口 + 实现
├── neu-carbon-bid          # 招投标模块
├── neu-carbon-mes          # 制造执行系统（MES）
├── neu-carbon-wms          # 仓储管理系统（WMS）
├── neu-carbon-scm          # 供应链管理（SCM）
├── neu-carbon-footprint    # 碳足迹追踪
├── neu-carbon-distribusion # 碳分配与排放计算
├── neu-carbon-report       # 报表模块
├── neu-carbon-chat         # 聊天（WebSocket）
├── neu-admin               # 启动入口 + 系统/监控控制器
├── carbon.sql              # 数据库初始化脚本
└── pom.xml                 # Maven 父 POM
```

### 模块依赖关系

```
neu-common → neu-system → neu-framework → neu-carbon-mapper → neu-carbon-service
                                                                  ↓
                                                    neu-carbon-bid/mes/wms/...
                                                                  ↓
                                                               neu-admin
```

> **注意：** Controller 层按业务域分模块，但 Service 和 Mapper 层集中在 `neu-carbon-service` 和 `neu-carbon-mapper`。新增业务代码时：
> - Entity / Mapper → `neu-carbon-mapper`
> - Service 接口 + 实现 → `neu-carbon-service`
> - Controller → 对应业务模块（如 `neu-carbon-bid`）

## 功能特性

### 碳管理核心
- **碳足迹追踪** — 产品全生命周期碳排放核算
- **碳分配与排放计算** — 碳配额分配与排放量计算引擎
- **碳排放报表** — 碳排放数据统计与报表生成

### 招投标管理
- 招标发布、投标管理、评标定标全流程

### 制造执行（MES）
- 生产计划排程、生产模型配置、质检管理、生产执行跟踪

### 仓储管理（WMS）
- 库存管理、出入库申请、仓储报表

### 供应链管理（SCM）
- 采购管理、销售管理、物流运输

### 系统管理
- 用户/角色/菜单/部门/字典/配置管理
- 基于 RBAC 的数据权限控制
- 在线用户监控、服务健康检查
- 定时任务管理（Quartz）
- 代码生成器

## 快速开始

### 环境准备

- **JDK** 1.8+
- **Maven** 3.6+
- **MySQL** 8（创建数据库 `carbon`，导入根目录 `carbon.sql`）
- **Redis**（默认 `localhost:6379`，DB 12）

### 构建运行

```bash
# 克隆项目
git clone <仓库地址>

# 打包（跳过测试）
mvn clean package -Dmaven.test.skip=true

# 启动（端口 9090）
java -Xms256m -Xmx1024m -jar neu-admin/target/carbon.jar
```

或使用 `bin/` 目录下的便捷脚本（Windows）：

```bash
bin/package.bat   # 打包
bin/clean.bat     # 清理
bin/run.bat       # 启动
```

启动后访问 Knife4j 接口文档：http://localhost:9090/doc.html

## 配置说明

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| 服务端口 | 9090 | `application.yml` |
| 数据库 | `127.0.0.1:3306/carbon` | `application-druid.yml` |
| Redis | `localhost:6379` DB 12 | `application.yml` |
| 文件上传路径 | `home/neu/uploadPath` | `application.yml` |
| Token 过期时间 | 1440 分钟 | `application.yml` |
| 验证码类型 | math（数学计算） | `application.yml` |

## 核心约定

- **统一响应**：`AjaxResult`（通用）+ `TableDataInfo`（分页）
- **分页**：Controller 继承 `BaseController`，调用 `startPage()` + `getDataTable()`
- **数据权限**：`DataScopeAspect` 按角色数据范围自动过滤
- **数据源切换**：`@DataSource` 注解支持主从切换
- **XSS 防护**：全局过滤器，默认拦截 `/system/*`、`/monitor/*`、`/tool/*`
