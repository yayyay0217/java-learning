# 注册中心Eureka

Spring Cloud Eureka是Spring Cloud Netflix微服务套件中的一部分，基于Netflix Eureka 做了二次封装。主要负责完成微服务架构中的服务治理功能。

## 服务中心搭建

1. 创建一个spring boog项目（Eureka-Service）。

2. 添加相应的maven配置。

   ~~~xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
   
       <groupId>com.sy.eureka</groupId>
       <artifactId>eureka-service</artifactId>
       <version>1.0.0</version>
   
       <properties>
           <spring.cloud.version>Finchley.SR3</spring.cloud.version>
       </properties>
   
       <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.0.0.RELEASE</version>
           <relativePath/>
       </parent>
   
       <dependencies>
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
           </dependency>
       </dependencies>
   
       <dependencyManagement>
           <dependencies>
               <dependency>
                   <groupId>org.springframework.cloud</groupId>
                   <artifactId>spring-cloud-dependencies</artifactId>
                   <version>${spring.cloud.version}</version>
                   <type>pom</type>
                   <scope>import</scope>
               </dependency>
   
           </dependencies>
       </dependencyManagement>
   
   </project>
   
   ~~~

3. 创建application.yml或application.properties文件，添加相应的配置

   ~~~yaml
   server:
     port: 9080
   
   eureka:
     instance:
       hostname: loaclhost
     client:
       # 不向注册中心注册自己
       register-with-eureka: false
       # 关闭检索服务
       fetch-registry: false
   
   ~~~

   > server.port：修改服务的端口
   >
   >  eureka.client.register-with-eureka：不向注册中心注册自己（即自身服务不被自身发现）。
   >
   > eureka.client.fetch-registry：由于注册中心的职责时维护服务实例，它并不需要去检索服务，所以设置为false

4. 在项目的启动类上添加**@EnableEurekaServer**注解

   ~~~java
   @EnableEurekaServer
   @SpringBootApplication
   public class EurekaApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(EurekaApplication.class, args);
       }
   }
   ~~~

   >**@EnableEurekaServer**：启动一个注册中西提供给其他服务进行对话。

5. 启动项目，访问地址：ip+端口，会显示Eureka注册中心的界面。

   ![image-20211130104004635](https://cdn.jsdelivr.net/gh/yayyay0217/sy-data/image-20211130104004635.png)



完成上面步骤后，注册中心已经搭建完成，可以创建服务加入到注册中心了。



**注意：**

我这里是参考《Spring Cloud微服务实战_翟永超(著)》进行搭建的，这里采用的Sping boot版本为2.0，Spring Cloud的版本为Finchley.SR3。如果你需要使用其他版本，请参考官网Spring boot和Spring cloud的版本兼容关系。

参考链接：https://spring.io/projects/spring-cloud



## 高可用注册中心

在生产环境中，我们需要考虑服务发生故障的导致整个服务器不可用的情况。所以对生产环境的各个组件必须进行高可用的部署，注册中心也一样。

Eureka Service高可用就是将自己作为服务向其他服务注册中心注册自己，以确保某个注册中心宕机后还能正常运作。

**Eureka Service高可用配置：**

1. 将下面的配置修改为true

   ~~~yaml
   eureka:
     client:
       # 不向注册中心注册自己
       register-with-eureka: true
       # 关闭检索服务
       fetch-registry: true
   ~~~

2. 指定注册中心的服务地址,多个用`逗号`隔开

   ~~~
       service-url:
         defaultZone: http://127.0.0.1:9080/eureka/
   ~~~




完成上面配置后，部署两个或以上注册中心，同时将相应的服务同时注册到运行的注册中心中去。因为服务注册到两个服务中心，可以确保其中一个宕机的情况，依然能找到其他服务调用，来确保系统的高可用。



## 常用配置说明

**客服端常用配置**

| 参数名                                        | 说明                                                         | 默认值 |
| --------------------------------------------- | ------------------------------------------------------------ | ------ |
| enable                                        | 启用Eureka客服端                                             | true   |
| registryFetchIntervalSeconds                  | 从Eureka服务端获取注册信息的间隔时间，单位为秒               | 30     |
| instanceInfoReplicationIntervalSeconds        | 更新实例信息变化到Eureka服务端的间隔时间，单位为秒           | 30     |
| initialInstanceInfoReplicationIntervalSeconds | 初始化实例信息到Eureka服务端的间隔时间，单位为秒             | 40     |
| eurekaServiceUrlPollIntervalSeconds           | 轮询Eureka服务端地址更改的间隔时间，单位为秒。当我们与Spring Cloud Config配合，动态刷新Eureka的serviceURL地址时需要关注该参数 | 300    |
| eurekaServerReadTimeoutSeconds                | 读取Eureka Server信息的超时时间，单位为秒                    | 8      |
| eurekaServerConnectTimeoutSeconds             | 连接Eureka Server的超时时间，单位为秒                        | 5      |
| eurekaServerTotalConnections                  | 从Eureka客服端到所以Eureka服务端的连接总数                   | 200    |
| eurekaServerTotalConnectionsPerHost           | 从Eureka客服端到每个Eureka服务端主机的连接总数               | 50     |
| eurekaConnectionIdleTimeoutSeconds            | Eureka服务端连接的空闲关闭时间，单位为秒                     | 30     |
| heartbeatExecutorThreadPoolSize               | 心跳连接池的初始化线程数                                     | 2      |
| heartbeatExecutorExponentialBackOffBound      | 心跳超时重试延迟时间的最大乘数值                             | 10     |
| cacheRefreshExecutorThreadPoolSize            | 缓存刷新线程池的初始化线程数                                 | 2      |
| cacheRefreshExecutorExponentialBackOffBound   | 缓存刷新重试延迟时间的最大乘数值                             | 10     |
| useDnsForFetchingServiceUrls                  | 使用DNS来获取Eureka服务端的serviceUrl                        | false  |
| registerWithEureka                            | 是否将自身实例信息注册到Eureka服务端                         | true   |
| preferSameZoneEureka                          | 是否偏好使用处于相同Zone的Eureka服务端                       | true   |
| filterOnlyUpInstances                         | 获取实例时是否过滤，仅保留UP状态的实例                       | true   |
| fetchRegistry                                 | 是否从Eureka服务端获取注册信息                               | true   |



**服务实例类配置**

1. 实例名配置

   实例名是区分同一服务中不同实例的唯一标识。通过 `InstanceInfo` 的 `instanceId` 来进行设置。实例名采用主机名作为默认值，这样设置使得同一主机上无法启动多个服务实例。所以Spring Cloud Eureka 的配置中针对同一主机启动多实例的情况，对实例名的默认命名做了更合理的扩展。它采用的默认规则如下：

   ~~~yml
   ${spring.cloud.client.hostname}:${spring.application.name}:${sping.application.instance-id}:${server.port}
   ~~~

   
