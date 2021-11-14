import java.util.Scanner;

public class Tree234Demo { public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      System.out.print("Enter insert values with spaces between: ");
      String userValues = scnr.nextLine();

      // Create a 2-3-4 tree and add the values
      Tree234 tree = new Tree234();
      for (String value : userValues.split(" ")) {
         tree.insert(Integer.parseInt(value));
      }
  
      // Display the height after all inserts are complete.
      System.out.printf("2-3-4 tree with %d keys has height %d%n",
         tree.length(), tree.getHeight());

      // Read user input to get a list of values to remove
      System.out.print("Enter remove values with spaces between: ");
      userValues = scnr.nextLine();

      for (String value : userValues.split(" ")) {
         Integer toRemove = Integer.parseInt(value);
         System.out.printf("Remove node %s: ", value);
         if (tree.remove(toRemove)) {
            System.out.printf("2-3-4 tree with %d keys has height %d%n", 
               tree.length(), tree.getHeight());
         }
         else {
            System.out.println("*** Key not found. Tree is not changed. ***");
         }
      }
   }
}