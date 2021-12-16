本项目主要是spring boot + WebSocket创建的一个简单demo，旨在让自己了解到如何使用websocket进行前后端通信

# 服务端

1. 创建一个spring boot项目，添加websocket的依赖

   ~~~xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
        <version>2.6.1</version>
   </dependency>
   ~~~

   

2. 创建WebSocket的配置文件

   ~~~java
   @Configuration
   public class WebSocketConfig {
   
       /**
        * 作用：自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
        */
       @Bean
       public ServerEndpointExporter serverEndpointExporter(){
           return new ServerEndpointExporter();
       }
   }
   ~~~

   

3. 编写websocket的实现类

   ~~~java
   @ServerEndpoint("/myWs")
   @Component
   public class WebSocketService {
   
   
       private static Map<String,Session> map = new HashMap<>();
   
       /**
        * 连接成功
        *
        * @param session
        */
       @OnOpen
       public void onOpen(Session session) {
           map.put("",session);
           System.out.println("连接成功");
       }
   
       /**
        * 连接关闭
        *
        * @param session
        */
       @OnClose
       public void onClose(Session session) {
           System.out.println("连接关闭");
       }
   
       /**
        * 接收到消息
        *
        * @param text
        */
       @OnMessage
       public String onMsg(String text) throws IOException {
           System.out.println("接受到客户端的信息 {}" +text);
           return "servet 发送：" + text;
       }
   
   
       /**
        * 发生错误
        */
       @OnError
       public void onError(Session session, Throwable throwable) {
           System.out.println("发生错误");
           throwable.printStackTrace();
       }
   
   
       /**
        * 发送消息
        *
        * @param message
        * @throws IOException
        */
       public void sendMessage(String message) throws IOException {
           map.forEach((k,v) ->{
               try {
                   v.getBasicRemote().sendText(message);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           });
       }
   }
   ~~~

   >@OnOpen：当有客户端连接成功时触发
   >
   >@OnMessage：当客户端发送消息给服务端时触发
   >
   >@OnClose：当客户端连接关闭时触发
   >
   >@OnError：发生错误时执行的回调函数
   >
   >上面我因为用来测试，websocket的session存在一个静态的map中，如果多个客户端连接的情况下发消息的时候是群发的，实际情况可以根据根据客户端的唯一标识进行发送

   

4. 创建接口，模拟消息发送

   ~~~java
   @RestController
   @RequestMapping("/web/socket")
   public class WebSocketController {
   
       @Autowired
       WebSocketService webSocketService;
   
   
   
       @GetMapping("/send")
       public void send() throws IOException {
           webSocketService.sendMessage("hellow word");
       }
   }
   ~~~



# 客服端

1. 创建一个测试的html

   ~~~html
   <!DOCTYPE html>
   <html lang="en">
   <head>
       <meta charset="UTF-8">
       <title>websocket测试</title>
   </head>
   <body>
   
   </body>
   <script type="text/javascript">
       var ws = null;
       if ('WebSocket' in window) {
           ws = new WebSocket("ws://localhost:9300/myWs");
           ws.onopen = function(){
               //当WebSocket创建成功时，触发onopen事件
               console.log("open");
               ws.send("hello"); //将消息发送到服务端
           }
   
           ws.onmessage = function(e){
               //当客户端收到服务端发来的消息时，触发onmessage事件，参数e.data包含server传递过来的数据
               console.log(e.data);
           }
           ws.onclose = function(e){
               //当客户端收到服务端发送的关闭连接请求时，触发onclose事件
               console.log("close");
           }
           ws.onerror = function(e){
               //如果出现连接、处理、接收、发送数据失败的时候触发onerror事件
               console.log(error);
           }
       }else{
           console.log("当前浏览器暂不支持webocket")
       }
   
   </script>
   </html>
   
   ~~~

   >关于websocket的介绍可以查看该链接：https://developer.mozilla.org/zh-CN/docs/Web/API/WebSocket



