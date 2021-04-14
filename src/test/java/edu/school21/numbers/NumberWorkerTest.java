package edu.school21.numbers;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {

  NumberWorker numberWorker = new NumberWorker();

  @ParameterizedTest
  @ValueSource(ints = {2, 3, 7})
  public void isPrimeForPrimes(int number) {
    Assert.assertTrue(numberWorker.isPrime(number));

  }

  @ParameterizedTest
  @ValueSource(ints = {4, 6, 8})
  public void isPrimeForNotPrimes(int number) {
    Assert.assertFalse(numberWorker.isPrime(number));
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 0, -100})
  public void isPrimeForIncorrectNumbers(int number) {
    Assertions.assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(number));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/data.csv")
  public void digitsSum(int number, int expected) {
    Assert.assertEquals(expected, numberWorker.digitsSum(number));
  }
}
