package language.arith;

import language.BinaryOperator;
import language.Operand;

/**
 * The {@link DivOperator} is an operator that performs division on two integers.
 */
public class DivOperator extends BinaryOperator<Integer> {

 @Override
  public Operand<Integer> performOperation() {
    if (op0 == null || op1 == null) {
      throw new IllegalStateException("Both operands must be set prior to performing operation.");
    }
    else{
      Integer div = op0.getValue() / op1.getValue();
      return new Operand<Integer>(div); 
    }
  }
  
  public String toString() {
    return "/";
  }
}
