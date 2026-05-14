# Carbon 双碳管理平台（后端）

基于 Spring Boot 2.2.12 + MyBatis + Spring Security (JWT) 的 Maven 多模块碳管理综合平台，涵盖招投标、MES、WMS、SCM、碳足迹、碳分配等业务域。

## 技术栈

| 类别 | 技术 |
|------|------|
| 框架 | Spring Boot 2.2.12、Spring Security |
| 持久层 | MyBatis、PageHelper |
| 数据库 | MySQL 8（Druid 连接池） |
| 缓存 | Redis（Lettuce 客户端） |
| 认证 | JWT (jjwt 0.9.1)、BCrypt |
| 接口文档 | Swagger 2 + Knife4j |
| JSON | FastJSON 1.2.74 + Jackson |
| 工具 | Hutool 4.5.11、Apache POI、Lombok |
| 构建 | Maven、Java 1.8 |

## 项目结构

```
neu-common              # 基础工具：BaseEntity、BaseController、异常、枚举
neu-common-biz          # 通用业务：Demo 控制器、CMS API
neu-system              # 系统管理：用户、角色、菜单、部门、字典、配置
neu-framework           # 核心框架：Security、JWT、AOP、数据源、全局异常
neu-quartz              # 定时任务（Quartz）
neu-generator           # 代码生成器（Velocity 模板）
neu-carbon-mapper       # 业务域 Mapper 接口 + MyBatis XML
neu-carbon-service      # 业务域 Service 接口 + 实现
neu-carbon-bid          # 招投标            neu-carbon-mes       # 制造执行
neu-carbon-wms          # 仓储管理          neu-carbon-scm       # 供应链
neu-carbon-footprint    # 碳足迹            neu-carbon-distribusion # 碳分配
neu-carbon-report       # 报表              neu-carbon-chat      # 聊天/WebSocket
neu-admin               # 启动入口 + 系统/监控控制器
```

### 模块依赖

```
neu-common → neu-system → neu-framework → neu-carbon-mapper → neu-carbon-service
                                                                           ↓
                                                             neu-carbon-bid/mes/wms/...
                                                                           ↓
                                                                        neu-admin
```

Controller 按业务域分模块，Service 和 Mapper 集中在 `neu-carbon-service` / `neu-carbon-mapper`。详见 [CLAUDE.md](CLAUDE.md)。

## 快速开始

### 环境

- JDK 1.8+、Maven 3.6+
- MySQL 8 → 创建 `carbon` 数据库，导入 `carbon.sql`
- Redis `localhost:6379`（DB 12）

### 构建运行

```bash
# 打包
mvn clean package -Dmaven.test.skip=true

# 启动（端口 9090）
java -Xms256m -Xmx1024m -jar neu-admin/target/carbon.jar
```

Windows 下可用 `bin/` 脚本：`package.bat` / `clean.bat` / `run.bat`

启动后访问接口文档：**http://localhost:9090/doc.html**

### 核心配置

| 配置项 | 默认值 | 文件 |
|--------|--------|------|
| 服务端口 | 9090 | `application.yml` |
| 数据库 | `127.0.0.1:3306/carbon` | `application-druid.yml` |
| Redis | `localhost:6379` DB 12 | `application.yml` |
| 文件上传 | `home/neu/uploadPath` | `application.yml` |
| Token 过期 | 1440 分钟 | `application.yml` |

## 业务模块

- **碳足迹** — 产品全生命周期碳排放核算
- **碳分配** — 碳配额分配与排放量计算引擎
- **招投标** — 招标发布、投标管理、评标定标全流程
- **MES** — 生产计划排程、质检管理、执行跟踪
- **WMS** — 库存管理、出入库申请、仓储报表
- **SCM** — 采购管理、销售管理、物流运输
- **系统管理** — RBAC 权限、数据权限、定时任务、代码生成器

## 开发约定

- 统一响应：`AjaxResult`（通用）+ `TableDataInfo`（分页）
- 分页：`startPage()` + `getDataTable()`
- 权限：`@PreAuthorize("@ss.hasPermi('...')")`
- 数据权限：`DataScopeAspect` 按角色自动过滤
- 数据源切换：`@DataSource` 注解
