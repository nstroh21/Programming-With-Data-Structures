package app;

import java.util.Scanner;
//updated 2
//import static org.junit.Assert.fail;

import java.io.*;

public class RLEconverter {
  private final static int DEFAULT_LEN = 100; // used to create arrays.

   /*
    *  This method reads in an uncompressed ascii image file that contains
    *  2 characters. It stores each line of the file in an array.
    *  It then calls compressLines to get an array that stores the compressed
    *  version of each uncompressed line from the file. The compressed array
    *  is then passed to the getCompressedFileStr method which returns a String
    *  of all compressed lines (the two charcaters are written in the first line)
    *  in CSV format. This String is written to a text file with the prefix "RLE_"
    *  added to the original, uncompressed file name.
    *  Note that dataSize keeps track of the number of lines in the file. The array
    *  that holds the lines of the file is initialized to the DEFAULT_LEN, which
    *  is assumed to be << the number of lines in the file.
    */
  public void compressFile(String fileName) throws IOException{
    Scanner scan = new Scanner(new FileReader(fileName));
    String line = null;
    String[] decompressed = new String [DEFAULT_LEN];
    int dataSize = 0;
    while(scan.hasNext()){
      line = scan.next();
      if(line != null && line.length()>0)
        decompressed[dataSize]=line;
        dataSize++;
        //System.out.println(line);  // to test what line is --- the delimiter is new line -- every line is a token in scan.next()

    }
    scan.close();


    // test output of discvoerAllChars
    //char[] fileChars = discoverAllChars(decompressed, dataSize);
    /*String[] line1 = {"0004ftdg0f",  "ppt9hdg", "ttttt", "$$$$$t", "**$**_&"};
    char[] fileChars = discoverAllChars(line1, 5);
    //test that discoverallChars returns expected output
    for (int i= 0; i < 15; i++) {
    System.out.println(fileChars[i]);
    };*/

    // Test output of compressLine
    //String compressline1 = compressLine(decompressed[11], fileChars);
    //System.out.println(compressline1);

    char[] fileChars = discoverAllChars(decompressed, dataSize);
    String[] compressed = compressLines(decompressed, dataSize, fileChars);
    writeFile(getCompressedFileStr(compressed, fileChars), "RLE_"+fileName);

  }

/*
 * This method implements the RLE compression algorithm. It takes a line of uncompressed data
 * from an ascii file and returns the RLE encoding of that line in CSV format.
 * The two characters that make up the image file are passed in as a char array, where
 * the first cell contains the first character that occurred in the file.
*/
  public String compressLine(String line, char[] fileChars){

    char startChar = fileChars[0];
    //char nextChar = fileChars[1];
    String RLECode = "";
    int count = 0;
    if (line.charAt(0) != startChar) {
      RLECode = RLECode + "0,";
      count += 1;
    }
    else {count += 1;}

    for (int i = 1; i < line.length(); i++) {
      char curr = line.charAt(i);
      char last = line.charAt(i-1);
      if (i == line.length()-1) {
        if (curr == last) {
          count += 1;
          String countStr = String.valueOf(count);
          RLECode = RLECode + countStr;
        }
        else {
          String countStr = String.valueOf(count);
          RLECode = RLECode + countStr + ",1";
        }
      }

    else{
      if (curr == last) {
        count += 1;
      }
      else {
        String countStr = String.valueOf(count);
        RLECode = RLECode + countStr + ",";
        count = 1;
      }
    }
  }
    return RLECode;
  }

  /*
   *  This method discovers the two ascii characters that make up the image.
   *  It iterates through all of the lines and writes each compressed line
   *  to a String array which is returned. The method compressLine is called on
   *  each line.
   *  The dataSize is the number of lines in the file, which is likely to be << the length of lines.
   */
  public String[] compressLines(String[] lines, int dataSize, char[] fileChars){

      //declare final file and add first element which is the special chars
    String [] finalArr = new String[dataSize];
    //String line1 = fileChars[0] + "," + fileChars[1];
    //finalArr[0] = line1;
    //placeholder variable declarations
    String rleTemp = "";
    String str = "";

     for (int i = 0; i < dataSize; i++) {
        str = lines[i];
        //System.out.println(lines[i-1]);
        rleTemp = compressLine(str, fileChars);
        finalArr[i] = rleTemp;
        //System.out.println(finalArr[i]);
    }

    return finalArr;
  }

/*
 *  This method assembles the lines of compressed data for
 *  writing to a file. The first line must be the 2 ascii characters
 *  in comma-separated format.
 */
  public String getCompressedFileStr(String[] compressed, char[] fileChars) {
    String printStr = Character.toString(fileChars[0]) + "," + Character.toString(fileChars[1]) + "\n";

    //compressed length = dataSize + 1
    for (int i = 0; i < compressed.length; i ++) {
      String item = compressed[i];
      if (i != compressed.length - 1) {
        printStr = printStr + item + "\n";
      }
      else {
        printStr = printStr + item;
      }
    }
    return printStr;
  }
   /*
    *  This method reads in an RLE compressed ascii image file that contains
    *  2 characters. It stores each line of the file in an array.
    *  It then calls decompressLines to get an array that stores the decompressed
    *  version of each compressed line from the file. The first row contains the two
    *  ascii charcaters used in the original image file. The decompressed array
    *  is then passed to the getDecompressedFileStr method which returns a String
    *  of all decompressed lines, thus restoring the original, uncompressed image.
    *  This String is written to a text file with the prefix "DECOMP_"
    *  added to the original, compressed file name.
    *  Note that dataSize keeps track of the number of lines in the file. The array
    *  that holds the lines of the file is initialized to the DEFAULT_LEN, which
    *  is assumed to be << the number of lines in the file.
    */
  public void decompressFile(String fileName) throws IOException{
    Scanner scan = new Scanner(new FileReader(fileName));
    String line = null;
    String[] compressed = new String [DEFAULT_LEN];
    int dataSize =0;
    while(scan.hasNext()){
      line = scan.next();
      if(line != null && line.length()>0)
        compressed[dataSize]=line;
        dataSize++;
    }
    scan.close();
    //System.out.println(compressed[0]);
    String[] decompressed = decompressLines(compressed, dataSize);
    writeFile(getDecompressedFileStr(decompressed), "DECOMP_"+fileName);
  }

   /*
   * This method decodes lines that were encoded by the RLE compression algorithm.
   * It takes a line of compressed data and returns the decompressed, or original version
   * of that line. The two characters that make up the image file are passed in as a char array,
   * where the first cell contains the first character that occurred in the file.
   */
  public String decompressLine(String line, char[] fileChars){

    String outLine = "";
    String startChar = Character.toString(fileChars[0]);
    String otherChar = Character.toString(fileChars[1]);

    //int len = line.length();  // sum of all values in line when converted to ints
    // need to look up how to convert char back to int

    //count commas  -- number of elements is commas + 1
    int len =  1;
    for (int i= 0; i < line.length(); i++) {
      if (line.charAt(i) == ',') { len += 1;}
    }

    int [] numTimes = new int [len]; //length of the arrqy

    //create integer intructions in numTimes
    int index = 0;
    String numStr = "";  // this  means null initial
    String place = "";
    for (int i = 0; i < line.length(); i++ ) {
      //immediately convert to string so we can concat below
      place = Character.toString(line.charAt(i));
      //boolean comma = (line.charAt(i) == ',');
      if (line.charAt(i) == ',') {
        int num = Integer.parseInt(numStr);
        numTimes[index] = num;
        index += 1;
        numStr = "";
      }
      else {
        numStr = numStr + place;
      }
    }
    //apend last value in csv list
    int num = Integer.parseInt(numStr);
    numTimes[index] = num;

    // build the output line -- if index is even use startchar, if odd use otherchar
    for (int j = 0; j < numTimes.length; j++) {

      int n = numTimes[j];
      if (j % 2 == 0) {
        for (int k = 0; k < n; k++){
          outLine = outLine + startChar;
        }
      }
      else {
        for (int k = 0; k < n; k++){
          outLine = outLine + otherChar;
        }
      }
    }
    return outLine;
  }
    /*
   *  This method iterates through all of the compressed lines and writes
   *  each decompressed line to a String array which is returned.
   *  The method decompressLine is called on each line. The first line in
   *  the compressed array passed in are the 2 ascii characters used to make
   *  up the image.
   *  The dataSize is the number of lines in the file, which is likely to be << the length of lines.
   *  The array returned contains only the decompressed lines to be written to the decompressed file.
   */
  public String[] decompressLines(String[] lines, int dataSize){

    String [] decompArr = new String[dataSize-1];
    String drawWith = lines[0];
    char [] fileChars = new char[2];
    fileChars[0] = drawWith.charAt(0);
    fileChars[1] = drawWith.charAt(2);

    String decomp = "";
    for (int i = 1; i < dataSize; i++) {
      //System.out.println(lines[i]);
      decomp = decompressLine(lines[i], fileChars);
     //System.out.println(decomp);
      decompArr[i-1] = decomp;
    }
    return decompArr;
  }

  /*
   *  This method assembles the lines of decompressed data for
   *  writing to a file.
   */
  public String getDecompressedFileStr(String[] decompressed){
    String drawPic = "";
    for (int i = 0; i < decompressed.length; i++) {
      if (i != decompressed.length - 1) {
        drawPic = drawPic + decompressed[i] + "\n";
      }
      else {
        drawPic = drawPic + decompressed[i];
      }
    }
    return drawPic;
  }

  // assume the file contains only 2 different ascii characters.
  public char[] discoverAllChars(String[] decompressed, int dataSize){

    // using the assumption of 2 chars -- first- append the frrst character to the array
    //then search the string until we find a new char ( linear search fine ?)
    char[] uniqChars = new char[2];
    char firstChar = decompressed[0].charAt(0);
    uniqChars[0] = firstChar;
    for (int i = 0; i < dataSize; i++) {
        String tempString = decompressed[i];
        for(int j= 0; j < tempString.length(); j++){
          char check = tempString.charAt(j);
          if (check != firstChar) {
            uniqChars[1] = check;
            break;
          }
        }
      }
    // Second Way to do this method where we try to generalize it
    // to take in more than just 2 characters --
    //but we'd have to make updates elsewhere to generalize the whoel program
    /*
    String unique = "";
    String decompStr = "";
    for (int i = 0; i < dataSize; i++) {
      String temp = decompressed[i];
      decompStr = decompStr + temp;
    }
    boolean found = false;
    for (int i = 0; i < decompStr.length(); i++) {
      char check = decompStr.charAt(i);
      String testChar = Character.toString(check);
      for (int j = 0; j < unique.length(); j++) {
        if (check == unique.charAt(j)) {
          found = true;
          break;
        }
        else {
          found = false;
        }
      }
      if (found == false){
        unique = unique + testChar;
      }
    }
    char [] uniqChars = new char [unique.length()];
    for (int k = 0; k < unique.length(); k++) {
      char appChar = unique.charAt(k);
      uniqChars[k] = appChar;
    }
    return uniqChars;
    */

    return uniqChars;
  }


   public void writeFile(String data, String fileName) throws IOException{
		PrintWriter pw = new PrintWriter(fileName);
      pw.print(data);
      pw.close();
   }
}
