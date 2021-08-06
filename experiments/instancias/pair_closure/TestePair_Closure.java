import pair.Sugar;

  public class Test {
      private #int (char) p = #int(char c) {return c;};
      private (String, Integer) p = ("12", 34 + 10);

      public (int, int) divMod (int n , int q) {
        return(n / q, n % q);
      }
      
       public static void main(String[] args) {
        (int, int) divmod = divMod(100, 9);       
      }
  }