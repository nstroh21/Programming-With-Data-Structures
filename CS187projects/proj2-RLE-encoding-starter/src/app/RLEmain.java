package app;

public class RLEmain {

   public static void main(String[] args) throws Exception{
   
      RLEconverter con = new RLEconverter ();
      //System.out.println("This ran");
      /* run main to call compress to generate RLE_heart.txt  */
      con.compressFile("cherries.txt");
      /* then comment out the call to compress, uncomment the call to decompress.
         then run main to generate RLE_heart.txt  */
      //con.decompressFile("RLE_cherries.txt");
   }
}