# eagle-alpha



## streaming
  start:
    sbt "project streaming" run
    
  verify:
    curl -N "http://localhost:9000/streaming"
    
  how to scale:
    streaming app thread pool is configured to use one thread per available processor,
     with a maximum of 24 threads in the pool. All operations should be non-blocking and asynchronous 
     for maximum performance per-host. We should be able to scale out  by deploying separate instance of
     this application to additional host and using load balancer to distribute the load.
     
  How to test scalability of the app(support for 50000 concurrent connections - response time less than an second )
     We need to create stress test using stress testing framework (e.g JMeter or loadrunner) and test that our app 
     can handle the request with desired response time. We should also fit within out CPU 
     and memory consumption constraints.
     
  How to improve resilience:
    Just run the same service on separate hosts and use load balancer to distribute the load
     
  To run the test
    sbt test
    
## jaccard

   how to run sample app:
     sbt jaccard/run
   how to run the test:
      sbt jaccard/test