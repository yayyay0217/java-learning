# 服务提供者

注册中心搭配完成后，接下来就可以添加服务并注入的注册中心中。

+ 新建一个Spring boot服务，作为服务提供者

+ 添加相应的模块依赖

  ~~~xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <groupId>com.sy.user</groupId>
      <artifactId>user-service</artifactId>
      <version>1.0</version>
  
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
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-test</artifactId>
              <scope>test</scope>
          </dependency>
          <!-- SpringBoot整合eureka客户端 -->
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
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

+ 在application.yml上添加配置

  ~~~yaml
  server:
    port: 9081
  spring:
    application:
       # 服务名称
      name: user-service
  
  eureka:
    client:
      serviceUrl:
        # 指定注册中心的服务地址
        defaultZone: http://localhost:9080/eureka/
  
  ~~~

+ 在启动类上添加**@EnableDiscoveryClient**注解

  ~~~java
  @EnableDiscoveryClient
  @SpringBootApplication
  public class UserAoolication {
  
      public static void main(String[] args) {
          SpringApplication.run(UserAoolication.class,args);
      }
  }
  ~~~

+ 启动服务提供者，启动完成后打开Eureka的页面，查看服务提供者是否已经添加进去了。

  ![image-20211130152944420](https://cdn.jsdelivr.net/gh/yayyay0217/sy-data/image-20211130152944420.png)

  有上图可以看出，服务提供者USER-SERVICE已经添加到注册中心了。

  

