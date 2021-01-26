package com.example.aop;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyRestController {

  public MyRestController(FibonacciService fibonacciService, SimulateDatabase simulateDatabase) {
    this.fibonacciService = fibonacciService;
    this.simulateDatabase = simulateDatabase;
  }

  private final FibonacciService fibonacciService;
  private final SimulateDatabase simulateDatabase;

  @LogMethodName
  @MonitorTime
  @Cacheable("Fibonacci")
  @GetMapping(path = "/api/fibonacci/{number}")
  public Long fibonacci(@PathVariable(value = "number") Long number) {
    return fibonacciService.nthFibonacciTerm(number);
  }

  @LogMethodName
  @PostMapping(path = "/api/storeData")
  public void storeData(@RequestParam(value = "data") String data) {
    simulateDatabase.storeToDB(data);
  }
}
