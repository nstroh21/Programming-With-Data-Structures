package evaluator.arith;

import language.Operand;
import language.Operator;
import parser.IllegalPostfixExpressionException;
import parser.PostfixParser.Type;
import parser.Token;
import parser.arith.ArithPostfixParser;
import stack.LinkedStack;
import stack.StackInterface;
import evaluator.PostfixEvaluator;

/** An {@link ArithPostfixEvaluator} is a postfix evaluator over simple arithmetic expressions. */
public class ArithPostfixEvaluator implements PostfixEvaluator<Integer> {

  private final StackInterface<Operand<Integer>> stack;

  /** Constructs an {@link ArithPostfixEvaluator} */
  public ArithPostfixEvaluator() {
    this.stack = new LinkedStack<Operand<Integer>>();
  }

  /** {@inheritDoc} */
  @Override
  public Integer evaluate(String expr) throws IllegalPostfixExpressionException {
    //initial check to ensure string is not empty ?   
    ArithPostfixParser parser = new ArithPostfixParser(expr);
    boolean existsOperator = false;
    boolean existsOperand = false;
    int count = 0; // use this count value to check certain conditions/edge cases (esepcially at the beginning)
    for (Token<Integer> token : parser) {
      count++;
      Type type = token.getType();
      System.out.println(token);
      System.out.println(type);
      //edge case check that the first token is indeed an Integer
      if (count == 1 && type.name() != "OPERAND" ){
        throw new IllegalPostfixExpressionException("The postfix expression must begin with an integer");
      }
      switch (type) {
        case OPERAND:
          stack.push(token.getOperand());
          existsOperand = true;
          break;
        case OPERATOR:
          // is instantiating this X operator object actually necessaty or would it work just from using token.getOperator() ?? 
          Operator<Integer> X = token.getOperator();
          //also be sure to set operator to true so we pass later exception test
          existsOperator = true;

          //consider unary and binary separately -- does not generalize to beyond binary oeprators
          if (X.toString() == "!") {
             if (stack.isEmpty() == true){
               throw new IllegalStateException("Not a valid postfix operation. There are too many oeprators for the operands.");
            }
            Operand<Integer> op0 = stack.pop();
            X.setOperand(0, op0);
            stack.push(X.performOperation()); 
            break;
          }
          //else its a binary operation adn do the same stuff but for binary -- is it necessary ? not sure
          else{
            if (stack.isEmpty() == true){
              throw new IllegalStateException("Not a valid postfix operation. There are too many operators for the given operands.");
            }
            else{ 
              Operand<Integer> op1 = stack.pop(); 
              X.setOperand(1, op1);
            }
            if (stack.isEmpty() == true) {
              throw new IllegalStateException("Not a valid postfix operation. There are too many operators for the given operands.");
            }
            else { 
              Operand<Integer> op0 = stack.pop();
              X.setOperand(0, op0);
            }

            //if we got past the above else-ifs it means we have 2 operands and an operator
            //thus a valid expression -- also note that order matters here with pop()        
            Operand<Integer> result = X.performOperation();
            stack.push(result);
            break;
          }
        // default case is if we got neither operator nor operand -- that's an illegal postfix -- example any kind of alpha char
        default:
          throw new IllegalStateException("Parser returned an invalid Type: " + type);
      
        }
    }
    // allow for simple case where there is just one integer token
    if (count == 1 ) {
      return stack.pop().getValue();
    }
    // the case where a binary operatr was tried with only one token should have already been handled so this is good
    else if (existsOperand == false || existsOperator == false) {
      throw new IllegalPostfixExpressionException("Expression must have at least one operand and one operator"); 
    }
    //else case is the majority of cases where we have a valid postfix longer than one token
    else {
    Integer finalResult = stack.pop().getValue();
    //System.out.println(stack.isEmpty());
    return finalResult;
    }
  }
}
