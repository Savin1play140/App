package app.utils;

public class Math {
  private static final int maxBits = 8;
  
  public static double Fraction(int numerator, int denominator) {
    return (numerator / denominator);
  }
  
  public static double RootOfANum(int number) {
    double I = java.lang.Math.sqrt(number);
    return I;
  }
  
  public static int S(int num, int s) {
    int ret = 0;
    for (int i = 1; i <= s; i++)
      ret += num; 
    return ret;
  }
  
  public static double AverageArephmitic(int[] numbers) {
    int Count = 0;
    int Summ = 0;
    byte b;
    int i, arrayOfInt[];
    for (i = (arrayOfInt = numbers).length, b = 0; b < i; ) {
      int number = arrayOfInt[b];
      Summ += number;
      Count++;
      b++;
    } 
    Summ /= Count;
    return Summ;
  }
  
  public static int getMaxMem(int averageMb) {
    return averageMb * 8 / 2;
  }
  
  public static boolean isAvaible(int mbMem) {
    if (mbMem / 8 % 2 == 0 && mbMem / 8 <= 64)
      return true; 
    return false;
  }
  
  public static int getMaxAvaible(int averageMb) {
    if (isAvaible(averageMb))
      return getMaxMem(averageMb); 
    return -1;
  }
  
  public static int min(int v0, int v1) {
    if (v0 > v1)
      return v1; 
    return v0;
  }
  
  public static int max(int v0, int v1) {
    if (v0 < v1)
      return v1; 
    return v0;
  }
}
