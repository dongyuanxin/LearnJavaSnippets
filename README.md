# 目录结构

```declarative

LearnJavaSnippets on  main is 📦 v1.0-SNAPSHOT via ☕ v21.0.9 took 12s 
❯ tree 
.
├── CUSTOM-WEB-PAGES
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── array
│   │   │   │   └── demo
│   │   │   │       ├── ArraysDemo.java
│   │   │   │       ├── CollectionsDemo.java
│   │   │   │       └── StringUtilsDemo.java
│   │   │   ├── com
│   │   │   │   └── demo
│   │   │   │       ├── http
│   │   │   │       │   ├── HttpConnectionDemo.java
│   │   │   │       │   └── HttpPoolDemo.java
│   │   │   │       ├── jackson
│   │   │   │       │   └── JacksonDemo1.java
│   │   │   │       ├── jdbc
│   │   │   │       │   ├── pool
│   │   │   │       │   │   └── JdbcPoolDemo1.java
│   │   │   │       │   └── single
│   │   │   │       │       ├── JdbcSingleDemo1.java
│   │   │   │       │       └── Student.java
│   │   │   │       ├── socket
│   │   │   │       │   ├── tcp
│   │   │   │       │   │   ├── SocketBioTcpServerDemo.java
│   │   │   │       │   │   ├── SocketNioTcpServerDemo.java
│   │   │   │       │   │   └── SocketTcpClientDemo.java
│   │   │   │       │   └── udp
│   │   │   │       │       ├── UdpClientDemo.java
│   │   │   │       │       └── UdpServerDemo.java
│   │   │   │       └── xml
│   │   │   │           ├── HandleXmlByDom.java
│   │   │   │           └── HandleXmlBySax.java
│   │   │   ├── data
│   │   │   │   └── structure
│   │   │   │       └── demo
│   │   │   │           └── concurrent
│   │   │   │               └── ConcurrentHashMapDemo.java
│   │   │   ├── io
│   │   │   │   └── demo
│   │   │   │       ├── AioStreamDemo.java
│   │   │   │       ├── AioStreamStepDemo.java
│   │   │   │       ├── ByteEncodeAndDecodeDemo.java
│   │   │   │       ├── CountInputFilterStreamDemo.java
│   │   │   │       ├── FileOpDemo.java
│   │   │   │       ├── InputStreamDemo.java
│   │   │   │       └── IoStreamDemo.java
│   │   │   ├── oof
│   │   │   │   └── demo
│   │   │   │       └── JavaLambdaDemo.java
│   │   │   ├── oop
│   │   │   │   └── demo
│   │   │   │       ├── annotation
│   │   │   │       │   ├── AnnotationDemo.java
│   │   │   │       │   ├── LogReportAspect.java
│   │   │   │       │   └── RetryAspect.java
│   │   │   │       ├── buffer
│   │   │   │       │   └── BufferOpDemo.java
│   │   │   │       ├── generics
│   │   │   │       │   └── QuestionMarkDemo.java
│   │   │   │       ├── nep
│   │   │   │       │   └── NepHandleDemo.java
│   │   │   │       ├── reflect
│   │   │   │       │   └── ReflectDemo.java
│   │   │   │       └── trywithresources
│   │   │   │           └── MyResourceDemo1.java
│   │   │   ├── org
│   │   │   │   └── example
│   │   │   │       └── Main.java
│   │   │   ├── spring
│   │   │   │   └── demo
│   │   │   │       ├── annotation
│   │   │   │       │   ├── aspect
│   │   │   │       │   │   ├── MetricAspect.java
│   │   │   │       │   │   └── MetricTime.java
│   │   │   │       │   ├── entry
│   │   │   │       │   │   └── AnnotationEntry.java
│   │   │   │       │   ├── lifecycle
│   │   │   │       │   │   └── DatabaseInitializer.java
│   │   │   │       │   └── service
│   │   │   │       │       ├── AppService.java
│   │   │   │       │       ├── MailService.java
│   │   │   │       │       ├── SmtpConfig.java
│   │   │   │       │       ├── User.java
│   │   │   │       │       └── UserService.java
│   │   │   │       └── application
│   │   │   │           ├── entry
│   │   │   │           │   └── ApplicationEntry.java
│   │   │   │           └── service
│   │   │   │               ├── MailService.java
│   │   │   │               ├── User.java
│   │   │   │               └── UserService.java
│   │   │   ├── stream
│   │   │   │   └── demo
│   │   │   │       ├── CreateStreamDemo.java
│   │   │   │       └── OpStreamDemo.java
│   │   │   ├── tdd
│   │   │   │   └── demo
│   │   │   │       └── TddFactorial.java
│   │   │   ├── thread
│   │   │   │   └── demo
│   │   │   │       ├── ThreadBaseDemo.java
│   │   │   │       ├── ThreadLocalDemo.java
│   │   │   │       ├── ThreadPollDemo.java
│   │   │   │       ├── ThreadStatusDemo.java
│   │   │   │       ├── ThreadSynchronizedDemo1.java
│   │   │   │       ├── ThreadSynchronizedDemo2.java
│   │   │   │       ├── ThreadVolatileDemo.java
│   │   │   │       └── ThreadWaitNotifyDemo.java
│   │   │   └── tomcat
│   │   │       └── demo
│   │   │           ├── TomcatServer.java
│   │   │           ├── context
│   │   │           │   ├── TomcatRequestContext.java
│   │   │           │   └── TomcatRequestContextHolder.java
│   │   │           ├── controller
│   │   │           │   └── api
│   │   │           │       └── UserApiController.java
│   │   │           ├── filter
│   │   │           │   └── TomcatContextFilter.java
│   │   │           ├── framework
│   │   │           │   ├── DispatcherServlet.java
│   │   │           │   ├── GetMapping.java
│   │   │           │   └── PostMapping.java
│   │   │           ├── listener
│   │   │           │   ├── TomcatAppListener.java
│   │   │           │   └── TomcatRequestListener.java
│   │   │           └── servlet
│   │   │               ├── ForwardServlet.java
│   │   │               ├── HelloServlet.java
│   │   │               ├── HomeServlet.java
│   │   │               ├── RedirectServlet.java
│   │   │               ├── SignInServlet.java
│   │   │               └── ViewsServlet.java
│   │   ├── resources
│   │   │   ├── application.xml
│   │   │   ├── book.xml
│   │   │   ├── capitalize.csv
│   │   │   ├── jdbc.properties
│   │   │   ├── logo.txt
│   │   │   ├── person.bin
│   │   │   ├── readme.txt
│   │   │   └── smtp.properties
│   │   └── webapp
│   │       ├── README.md
│   │       └── WEB-INF
│   │           ├── views
│   │           │   └── ip.jsp
│   │           └── web.xml
│   └── test
│       └── java
│           └── tdd
│               └── demo
│                   ├── TddFactorialTest.java
│                   └── TddFactorialTestV5.java
├── target
│   ├── classes
│   │   ├── application.xml
│   │   ├── array
│   │   │   └── demo
│   │   │       ├── ArraysDemo.class
│   │   │       ├── CollectionsDemo.class
│   │   │       ├── CustomArraySortAlgorithm.class
│   │   │       └── StringUtilsDemo.class
│   │   ├── book.xml
│   │   ├── capitalize.csv
│   │   ├── com
│   │   │   └── demo
│   │   │       ├── http
│   │   │       │   ├── HttpConnectionDemo.class
│   │   │       │   └── HttpPoolDemo.class
│   │   │       ├── jackson
│   │   │       │   ├── JacksonDemo1.class
│   │   │       │   └── Person.class
│   │   │       ├── jdbc
│   │   │       │   ├── pool
│   │   │       │   │   └── JdbcPoolDemo1.class
│   │   │       │   └── single
│   │   │       │       ├── JdbcSingleDemo1.class
│   │   │       │       └── Student.class
│   │   │       ├── socket
│   │   │       │   ├── tcp
│   │   │       │   │   ├── SocketBioTcpServerDemo.class
│   │   │       │   │   ├── SocketNioTcpServerDemo.class
│   │   │       │   │   ├── SocketTcpClientDemo.class
│   │   │       │   │   └── SocketTcpServerDemo1Thread.class
│   │   │       │   └── udp
│   │   │       │       ├── UdpClientDemo.class
│   │   │       │       └── UdpServerDemo.class
│   │   │       └── xml
│   │   │           ├── HandleXmlByDom.class
│   │   │           ├── HandleXmlBySax.class
│   │   │           └── HandleXmlBySaxHandler.class
│   │   ├── data
│   │   │   └── structure
│   │   │       └── demo
│   │   │           └── concurrent
│   │   │               └── ConcurrentHashMapDemo.class
│   │   ├── io
│   │   │   └── demo
│   │   │       ├── AioStreamDemo$1.class
│   │   │       ├── AioStreamDemo.class
│   │   │       ├── AioStreamStepDemo$1.class
│   │   │       ├── AioStreamStepDemo.class
│   │   │       ├── ByteEncodeAndDecodeDemo.class
│   │   │       ├── CountInputFilterStream.class
│   │   │       ├── CountInputFilterStreamDemo.class
│   │   │       ├── FileOpDemo.class
│   │   │       ├── InputStreamDemo.class
│   │   │       ├── IoStreamDemo.class
│   │   │       └── PersonSerializable.class
│   │   ├── jdbc.properties
│   │   ├── logo.txt
│   │   ├── oof
│   │   │   └── demo
│   │   │       ├── GreetingService.class
│   │   │       ├── JavaLambdaDemo.class
│   │   │       └── JavaLambdaDemoOtherCls.class
│   │   ├── oop
│   │   │   └── demo
│   │   │       ├── annotation
│   │   │       │   ├── AnnotationDemo.class
│   │   │       │   ├── AnnotationPerson.class
│   │   │       │   ├── LogReport.class
│   │   │       │   ├── LogReportAspect.class
│   │   │       │   ├── Retry.class
│   │   │       │   └── RetryAspect.class
│   │   │       ├── buffer
│   │   │       │   └── BufferOpDemo.class
│   │   │       ├── generics
│   │   │       │   ├── QuestionMarkClass1.class
│   │   │       │   ├── QuestionMarkClass2.class
│   │   │       │   └── QuestionMarkDemo.class
│   │   │       ├── nep
│   │   │       │   └── NepHandleDemo.class
│   │   │       ├── reflect
│   │   │       │   ├── ReflectCls1.class
│   │   │       │   ├── ReflectCls2.class
│   │   │       │   └── ReflectDemo.class
│   │   │       └── trywithresources
│   │   │           ├── MyResourceDemo1.class
│   │   │           └── RealResource.class
│   │   ├── org
│   │   │   └── example
│   │   │       └── Main.class
│   │   ├── person.bin
│   │   ├── readme.txt
│   │   ├── smtp.properties
│   │   ├── spring
│   │   │   └── demo
│   │   │       ├── annotation
│   │   │       │   ├── aspect
│   │   │       │   │   ├── MetricAspect.class
│   │   │       │   │   └── MetricTime.class
│   │   │       │   ├── entry
│   │   │       │   │   └── AnnotationEntry.class
│   │   │       │   ├── lifecycle
│   │   │       │   │   └── DatabaseInitializer.class
│   │   │       │   └── service
│   │   │       │       ├── AppService.class
│   │   │       │       ├── MailService.class
│   │   │       │       ├── SmtpConfig.class
│   │   │       │       ├── User.class
│   │   │       │       └── UserService.class
│   │   │       └── application
│   │   │           ├── entry
│   │   │           │   └── ApplicationEntry.class
│   │   │           └── service
│   │   │               ├── MailService.class
│   │   │               ├── User.class
│   │   │               └── UserService.class
│   │   ├── stream
│   │   │   └── demo
│   │   │       ├── CreateStreamDemo.class
│   │   │       ├── NatualSupplier.class
│   │   │       └── OpStreamDemo.class
│   │   ├── tdd
│   │   │   └── demo
│   │   │       └── TddFactorial.class
│   │   ├── thread
│   │   │   └── demo
│   │   │       ├── CustomCurrentTaskQueue.class
│   │   │       ├── GrandsonThread2.class
│   │   │       ├── Point7.class
│   │   │       ├── SonThread.class
│   │   │       ├── SonThread2.class
│   │   │       ├── SonThread3.class
│   │   │       ├── SonThread4.class
│   │   │       ├── SonThread5.class
│   │   │       ├── SonThread6.class
│   │   │       ├── SonThread7.class
│   │   │       ├── SonThread8.class
│   │   │       ├── ThreadBaseDemo.class
│   │   │       ├── ThreadLocalDemo.class
│   │   │       ├── ThreadLocalDemoContext.class
│   │   │       ├── ThreadLocalDemoContextHolder.class
│   │   │       ├── ThreadLocalDemoInnerTask1.class
│   │   │       ├── ThreadLocalDemoInnerTask2.class
│   │   │       ├── ThreadLocalDemoTask.class
│   │   │       ├── ThreadPollDemo.class
│   │   │       ├── ThreadPollDemoTask.class
│   │   │       ├── ThreadStatusDemo.class
│   │   │       ├── ThreadSynchronizedDemo1.class
│   │   │       ├── ThreadSynchronizedDemo1Lock.class
│   │   │       ├── ThreadSynchronizedDemo2.class
│   │   │       ├── ThreadVolatileDemo.class
│   │   │       ├── ThreadWaitNotifyDemo$1.class
│   │   │       └── ThreadWaitNotifyDemo.class
│   │   └── tomcat
│   │       └── demo
│   │           ├── TomcatServer.class
│   │           ├── context
│   │           │   ├── TomcatRequestContext.class
│   │           │   └── TomcatRequestContextHolder.class
│   │           ├── controller
│   │           │   └── api
│   │           │       ├── UserApiController.class
│   │           │       └── UserApiControllerPerson.class
│   │           ├── filter
│   │           │   └── TomcatContextFilter.class
│   │           ├── framework
│   │           │   ├── AbstractDispatcher.class
│   │           │   ├── DispatcherServlet.class
│   │           │   ├── GetDispatcher.class
│   │           │   ├── GetMapping.class
│   │           │   ├── PostDispatcher.class
│   │           │   └── PostMapping.class
│   │           ├── listener
│   │           │   ├── TomcatAppListener.class
│   │           │   └── TomcatRequestListener.class
│   │           └── servlet
│   │               ├── ForwardServlet.class
│   │               ├── HelloServlet.class
│   │               ├── HomeServlet.class
│   │               ├── RedirectServlet.class
│   │               ├── SignInServlet.class
│   │               └── ViewsServlet.class
│   ├── generated-sources
│   │   └── annotations
│   ├── generated-test-sources
│   │   └── test-annotations
│   └── test-classes
│       └── tdd
│           └── demo
│               ├── TddFactorialTest.class
│               ├── TddFactorialTestV5$1.class
│               └── TddFactorialTestV5.class
└── tomcat.8080
    └── work
        └── Tomcat
            └── localhost
                └── ROOT
                    └── org
                        └── apache
                            └── jsp
                                └── WEB_002dINF
                                    └── views
                                        ├── ip_jsp.class
                                        └── ip_jsp.java

144 directories, 217 files
```