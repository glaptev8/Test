package edu.school21.numbers;

public class NumberWorker {
  public boolean isPrime(int number) {
    if (number < 2) {
      throw new IllegalNumberException("IllegalArgument");
    }
    if (number == 2) {
      return true;
    }
    int i = 2;
    for (; i * i <= number; i++) {
      if (number % i == 0) {
        return false;
      }
    };
    return true;
  }

  public int digitsSum(int number) {
    if (number < 0) {
      number *= -1;
    }
    int sum = 0;
    while (number > 0) {
      sum += number % 10;
      number /= 10;
    }
    return sum;
  }
}
