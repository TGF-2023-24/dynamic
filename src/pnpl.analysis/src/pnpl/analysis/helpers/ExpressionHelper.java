package pnpl.analysis.helpers;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Deque;
//import java.util.StringTokenizer;
//import java.util.Map;

import solver.presenceConditions.Operator;
import variability.BinaryExpression;
import variability.BinaryOperator;
import variability.Expression;
import variability.Feature;
//import variability.PresenceCondition;
import variability.UnaryExpression;
import variability.UnaryOperator;
//import variability.Variability;
import variability.VariabilityFactory;

//import org.eclipse.emf.ecore.EObject;
import org.prop4j.*;

//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//
//import PetriNets.Arc;
//import PetriNets.Place;
//import PetriNets.Transition;
//import de.ovgu.featureide.fm.core.base.IFeatureModel;

/**
 * Functions to work with Expressions (to represent the presence conditions)
 * @author Elena Gómez-Martínez
 *
 */

public class ExpressionHelper {
	private Deque<Expression> operandStack = new ArrayDeque<>();
	private Deque<Object> operatorStack = new ArrayDeque<>();

	private boolean isOpen = false;
	private boolean isUnary = false;
	
	public int getSizeNestedExpression(Expression expr) {
		int value = 0;

		if(expr != null) {
			if (expr instanceof Feature) 
				value = 1;
			else if (expr instanceof UnaryExpression)
				value = 1 + getSizeNestedExpression(((UnaryExpression) expr).getRight());
			else if (expr instanceof BinaryExpression) {
				BinaryExpression bin = (BinaryExpression) expr;
				value = getSizeNestedExpression(bin.getLeft())  
						+ getSizeNestedExpression(bin.getRight());
			}
		}
		return value;
	}

	// Transform a constraint (as a Node) in the Feature Model into a Expression
	public Expression buildExpression(Node node) {
		if (node.getChildren() == null) {
			Feature f = VariabilityFactory.eINSTANCE.createFeature();
			f.setFeature(node.getContainedFeatures().get(0));
			return (Expression) f;
		} else {
			if (node.getChildren().length == 1) {
				if (node instanceof Not) {
					UnaryExpression una =  VariabilityFactory.eINSTANCE.createUnaryExpression();
					una.setOperator(UnaryOperator.NOT);
					una.setRight(this.buildExpression(node.getChildren()[0]));
					return (Expression) una;
				}
			} else {
				BinaryExpression bin =  VariabilityFactory.eINSTANCE.createBinaryExpression();
				
				if (node instanceof And) {
					bin.setOperator(BinaryOperator.AND);
				} else if (node instanceof Or) {
					bin.setOperator(BinaryOperator.OR);	
				} else if(node instanceof Implies) {
					bin.setOperator(BinaryOperator.IMPLIES);
				}
				
				bin.setLeft(this.buildExpression(node.getChildren()[0]));
				bin.setRight(this.buildExpression(node.getChildren()[1]));
				
				return (Expression) bin;
			}
		}
		return null;		
	}
	
	// Transform a constraint (as a Node) in the Feature Model into a String
	public Expression buildExpression(String condition) {		
		System.out.println("[petri nets analysis] Loading pc " +condition);
		return getExpression(condition);
	}

	private Expression getExpression(String condition) {
		StreamTokenizer stk = new StreamTokenizer(new StringReader(condition));
		stk.wordChars('_', '_'); // including underscore
		
		int numOpen = 0;
		int token;
		try {
			token = stk.nextToken();
			while(token!=StreamTokenizer.TT_EOF){ 
				if(stk.ttype == StreamTokenizer.TT_WORD) {
					Expression expr = handleWord(stk.sval);
					if (expr != null) {
						operandStack.push(handleWord(stk.sval));
						if(isUnary) {
							operandStack.push(handleOperator());
							isUnary = false;
						}
					}
				}
				else if (stk.ttype == '(') {
					isOpen = true;
					numOpen++;
				}
				else if (stk.ttype == ')') {
					if(isOpen) {
						operandStack.push(handleOperator());
						numOpen--;
						if(numOpen == 0) 
							isOpen = false;
					}					
				}
				token=stk.nextToken();
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return operandStack.pop();
	}

	private Expression handleWord(String token) {
		if (isFeature(token)) {    		
			Feature f = VariabilityFactory.eINSTANCE.createFeature();
			f.setFeature(token);
			return (Expression) f;
		}
		else {
			UnaryOperator una = getUnaryOperator(token);
			if (una != null) {
				operatorStack.push(una);
				isUnary = true;
			} else {
				BinaryOperator bin = getBinaryOperator(token);			
				if (bin != null) {
					operatorStack.push(bin);
				}
			} 
		}
		return null;
	}

	private Expression handleOperator() {
		Object o = operatorStack.pop();
		if(o instanceof UnaryOperator) {
			UnaryExpression una =  VariabilityFactory.eINSTANCE.createUnaryExpression();
			una.setOperator((UnaryOperator) o);
			una.setRight(operandStack.pop());
			return (Expression) una;
		} else if (o instanceof BinaryOperator) {
			BinaryExpression bin =  VariabilityFactory.eINSTANCE.createBinaryExpression();
			bin.setOperator((BinaryOperator) o);
			bin.setRight(operandStack.pop());
			bin.setLeft(operandStack.pop());
			return (Expression) bin;
		}			
		return null;
	}

	private boolean isFeature(String tok) {
		for (Operator o : Operator.values()) {
			if (o.getOperator().equals(tok)) return false;
		}
		return true;
	}

	private BinaryOperator getBinaryOperator(String tok) {
		for (BinaryOperator o : BinaryOperator.values()) {
			if (o.getLiteral().equals(tok.toUpperCase())) return o;
		}
		return null;
	}

	private UnaryOperator getUnaryOperator(String tok) {
		for (UnaryOperator o : UnaryOperator.values()) {
			if (o.getLiteral().equals(tok.toUpperCase())) return o;
		}
		return null;
	}
}
