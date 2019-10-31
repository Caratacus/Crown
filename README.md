<p align="center">
    <img src="https://raw.githubusercontent.com/Caratacus/Crown/master/Crown.png" width="300">
    <p align="center">
        Based on SpringBoot2, Crown builds a rapidly developed web application scaffolding.
        <br>      
        <br>      
        <span>
            <span>
                Crown官方交流群：223706133
            </span>
            <span>
                <a target="_blank" href="https://shang.qq.com/wpa/qunwpa?idkey=180c0eb468ec425c7208f49f142e4057f3f83a2fdabfe07ccb4606a414cd6413">
                <img border="0" src="https://pub.idqqimg.com/wpa/images/group.png" alt="Crown官方交流群" title="Crown官方交流群"></a>
            </span>
        </span>
        <br>
        <br>
        <a href="https://spring.io/projects/spring-boot">
        <img src="https://img.shields.io/badge/spring--boot-2.1.8.RELEASE-blue.svg" alt="spring-boot" title="spring-boot"></a>
		<a href="https://github.com/Caratacus/Crown2">
        <img src="https://tokei.rs/b1/github/Caratacus/Crown2?category=lines" alt="lines" title="lines"></a>
		<a href="https://github.com/Caratacus/Crown2">
		<img src="https://img.shields.io/badge/JDK-1.8-green.svg" alt="JDK 1.8" title="JDK 1.8"></a>
		<a href="https://mit-license.org">
        <img src="https://img.shields.io/cocoapods/l/Alamofire.svg?style=flat" alt="mit" title="mit"></a>
    </p>
</p>

-----------------------------------------------------------------------------------------------

> **文档中心** [https://caratacus.github.io](https://caratacus.github.io)

> **layUI前后端分离版本** [https://github.com/Caratacus/Crown](https://github.com/Caratacus/Crown/tree/layui)

-----------------------------------------------------------------------------------------------
##### Feature :rocket:
<sup>
<sup>1</sup> 防止XSS攻击、SQL注入，妈妈再也不用担心我的安全问题 <br/>
<sup>2</sup> 深度定制mybatis-plus，各种玩法意想不到 <br/>
<sup>3</sup> 深入拓展ModelMapper，各种类型一键转换 <br/>
<sup>4</sup> 接口日志详情打印，所有访问信息一览无遗 <br/>
<sup>5</sup> P6spy打印SQL，一切操作尽在掌握 <br/>
<sup>6</sup> Shiro鉴权 <br/>
<sup>N</sup> 更多特性持续更新 <br/>
</sup>

-----------------------------------------------------------------------------------------------
##### Frameworks :microscope:
<sup>
<sup>1</sup> 核心框架: SpringBoot <br/>
<sup>2</sup> 持久层框架: mybatis、mybatis-plus <br/>
<sup>3</sup> 数据库连接池: HikariCP <br/>
<sup>4</sup> 数据校验: HibernateValidator <br/>
<sup>5</sup> 对象转换: ModelMapper <br/>
<sup>6</sup> JSON转换: Jackson FastJson<br/>
<sup>7</sup> 接口文档: Swagger <br/>
<sup>8</sup> 基础工具类: ApacheCommons、VjTools <br/>
<sup>9</sup> 日志: SLF4J、Async Log4j2 <br/>
<sup>10</sup> SQL打印: P6spy <br/>
<sup>11</sup> 权限认证: Shiro <br/>
<sup>12</sup> 数据库主从: baomidou dynamic-datasource <br/>
<sup>N</sup> 以上依赖基本都会升级为最新版本 <br/>
</sup>

##### Function :neckbeard:
<sup>
<sup>1</sup> 用户管理：用户是系统操作者，该功能主要完成系统用户配置 <br/>
<sup>2</sup> 部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限 <br/>
<sup>3</sup> 岗位管理：配置系统用户所属担任职务 <br/>
<sup>4</sup> 菜单管理：配置系统菜单，操作权限，按钮权限标识等 <br/>
<sup>5</sup> 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分 <br/>
<sup>6</sup> 字典管理：对系统中经常使用的一些较为固定的数据进行维护 <br/>
<sup>7</sup> 参数管理：对系统动态配置常用参数 <br/>
<sup>8</sup> 通知公告：系统通知公告信息发布维护 <br/>
<sup>9</sup> 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询 <br/>
<sup>10</sup> 登录日志：系统登录日志记录查询包含登录异常 <br/>
<sup>11</sup> 在线用户：当前系统中活跃用户状态监控 <br/>
<sup>12</sup> 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志 <br/>
<sup>13</sup> 代码生成：前后端代码的生成（java、html、xml、sql)支持CRUD下载 <br/>
<sup>14</sup> 系统接口：根据业务代码自动生成相关的api接口文档 <br/>
<sup>15</sup> 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息 <br/>
<sup>16</sup> 在线构建器：拖动表单元素生成相应的HTML代码 <br/>
<sup>17</sup> 即时日志：管理后台即可查看项目在线运行日志 <br/>
</sup>

-----------------------------------------------------------------------------------------------
##### Ready :cat:
<sup>
<sup>1</sup> JDK1.8+ <br/>
<sup>2</sup> MySQL5.7+ <br/>
<sup>3</sup> Gradle4.10+ <br/>
</sup>

-----------------------------------------------------------------------------------------------
##### Start :dog:
<sup>
<sup>1</sup> 准备好上述基本环境 <br/>
<sup>2</sup> 导入crown2.sql文件(sql/crown.sql) <br/>
<sup>3</sup> 启动Crown2Application.java <br/>
<sup>4</sup> 访问http://localhost:8088 <br/>
</sup>

-----------------------------------------------------------------------------------------------
##### Show :palm_tree:

![login.png](https://images.gitee.com/uploads/images/2019/0723/184701_e503cdb9_620321.png)
<br>
![index.png](https://images.gitee.com/uploads/images/2019/0725/130956_17cb391a_620321.png)
<br>
![job.png](https://images.gitee.com/uploads/images/2019/0725/131034_d7b84efd_620321.png)
<br>
![console.png](https://images.gitee.com/uploads/images/2019/0725/131105_7bdbb649_620321.png)
<br>

-----------------------------------------------------------------------------------------------
##### Thanks :yum:

  该项目改造自 [https://gitee.com/y_project/RuoYi](https://gitee.com/y_project/RuoYi)

-----------------------------------------------------------------------------------------------
##### License :globe_with_meridians:

   The Crown2 is released under of the [Mit License](https://mit-license.org). <br/>

-----------------------------------------------------------------------------------------------
##### 有事烧钱 :octocat:

<img src="https://raw.githubusercontent.com/Caratacus/Resource/master/pay.jpg" alt="pay.jpg" width="650" hight="150">
