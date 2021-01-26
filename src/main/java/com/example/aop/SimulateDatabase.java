package com.example.aop;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SimulateDatabase {

  /**
   * Simulate a database operation which can fail randomly.
   */
  public void storeToDB(String s) {
    if (new Random().nextBoolean()) {
      throw new RuntimeException();
    }
  }
}
