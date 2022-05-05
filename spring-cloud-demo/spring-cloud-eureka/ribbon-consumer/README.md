Spring Cloud Ribbon是一个基于HTPP和TCP的客户端负载均衡工具。通过spring cloud 的封装，可以将面向服务的REST模板请求自动转换成客户端负载均衡的服务调用。Spring Cloud Ribbon虽然只是一个工具类框架，它不像注册中心，配置中心，API网关那样需要独立部署，但是它几乎存在于每一个Spring Cloud构建的微服务中。因为微服务间的调用，API网关的请求转发等内容，都是基于Ribbon来实现的（Feign也是基于Ribbon实现的工具）



# Ribbon的使用

+ 添加Ribbon的模块依赖spring-cloud-starter-ribbon

  ~~~xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-ribbon</artifactId>
       <version>1.4.7.RELEASE</version>
   </dependency>
  ~~~

+ 开启客户端负载均衡

  ~~~java
  @Bean
  @LoadBalanced  // 开启负载均衡功能
  RestTemplate restTemplate(){
  	return new RestTemplate();
  }
  ~~~

  >当Eureka与Ribbon联合使用时，Ribbon的服务实例清单RibbonServiceList会被DiscoveryEnabledNIWSServerList重写，扩展成从Eureka中获取服务列表，同时也会用NIWSDiscoveryPing取代Iping
  >
  >
