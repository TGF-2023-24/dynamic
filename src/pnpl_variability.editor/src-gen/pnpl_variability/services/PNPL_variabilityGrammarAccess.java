/*
 * generated by Xtext 2.14.0
 */
package pnpl_variability.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.common.services.TerminalsGrammarAccess;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractEnumRuleElementFinder;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class PNPL_variabilityGrammarAccess extends AbstractGrammarElementFinder {
	
	public class VariabilityElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.Variability");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cPnKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cPetrinetAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cPetrinetFileURIParserRuleCall_1_0 = (RuleCall)cPetrinetAssignment_1.eContents().get(0);
		private final Keyword cFmKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cFeaturemodelAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cFeaturemodelFileURIParserRuleCall_3_0 = (RuleCall)cFeaturemodelAssignment_3.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Assignment cPresenceconditionAssignment_4_0 = (Assignment)cGroup_4.eContents().get(0);
		private final RuleCall cPresenceconditionPresenceConditionParserRuleCall_4_0_0 = (RuleCall)cPresenceconditionAssignment_4_0.eContents().get(0);
		private final Keyword cSemicolonKeyword_4_1 = (Keyword)cGroup_4.eContents().get(1);
		
		//Variability:
		//	'pn' petrinet=FileURI
		//	'fm' featuremodel=FileURI (presencecondition+=PresenceCondition ";")*;
		@Override public ParserRule getRule() { return rule; }
		
		//'pn' petrinet=FileURI 'fm' featuremodel=FileURI (presencecondition+=PresenceCondition ";")*
		public Group getGroup() { return cGroup; }
		
		//'pn'
		public Keyword getPnKeyword_0() { return cPnKeyword_0; }
		
		//petrinet=FileURI
		public Assignment getPetrinetAssignment_1() { return cPetrinetAssignment_1; }
		
		//FileURI
		public RuleCall getPetrinetFileURIParserRuleCall_1_0() { return cPetrinetFileURIParserRuleCall_1_0; }
		
		//'fm'
		public Keyword getFmKeyword_2() { return cFmKeyword_2; }
		
		//featuremodel=FileURI
		public Assignment getFeaturemodelAssignment_3() { return cFeaturemodelAssignment_3; }
		
		//FileURI
		public RuleCall getFeaturemodelFileURIParserRuleCall_3_0() { return cFeaturemodelFileURIParserRuleCall_3_0; }
		
		//(presencecondition+=PresenceCondition ";")*
		public Group getGroup_4() { return cGroup_4; }
		
		//presencecondition+=PresenceCondition
		public Assignment getPresenceconditionAssignment_4_0() { return cPresenceconditionAssignment_4_0; }
		
		//PresenceCondition
		public RuleCall getPresenceconditionPresenceConditionParserRuleCall_4_0_0() { return cPresenceconditionPresenceConditionParserRuleCall_4_0_0; }
		
		//";"
		public Keyword getSemicolonKeyword_4_1() { return cSemicolonKeyword_4_1; }
	}
	public class FileURIElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.FileURI");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cFileURIAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cImportURIAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cImportURISTRINGTerminalRuleCall_1_0 = (RuleCall)cImportURIAssignment_1.eContents().get(0);
		
		//FileURI:
		//	{FileURI} importURI=STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//{FileURI} importURI=STRING
		public Group getGroup() { return cGroup; }
		
		//{FileURI}
		public Action getFileURIAction_0() { return cFileURIAction_0; }
		
		//importURI=STRING
		public Assignment getImportURIAssignment_1() { return cImportURIAssignment_1; }
		
		//STRING
		public RuleCall getImportURISTRINGTerminalRuleCall_1_0() { return cImportURISTRINGTerminalRuleCall_1_0; }
	}
	public class ExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.Expression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cFeatureParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cUnaryExpressionParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cBinaryExpressionParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//Expression:
		//	Feature | UnaryExpression | BinaryExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//Feature | UnaryExpression | BinaryExpression
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//Feature
		public RuleCall getFeatureParserRuleCall_0() { return cFeatureParserRuleCall_0; }
		
		//UnaryExpression
		public RuleCall getUnaryExpressionParserRuleCall_1() { return cUnaryExpressionParserRuleCall_1; }
		
		//BinaryExpression
		public RuleCall getBinaryExpressionParserRuleCall_2() { return cBinaryExpressionParserRuleCall_2; }
	}
	public class PresenceConditionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.PresenceCondition");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cPCKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cForKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cElementsAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final CrossReference cElementsEObjectCrossReference_2_0 = (CrossReference)cElementsAssignment_2.eContents().get(0);
		private final RuleCall cElementsEObjectEStringParserRuleCall_2_0_1 = (RuleCall)cElementsEObjectCrossReference_2_0.eContents().get(1);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cCommaKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cElementsAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final CrossReference cElementsEObjectCrossReference_3_1_0 = (CrossReference)cElementsAssignment_3_1.eContents().get(0);
		private final RuleCall cElementsEObjectEStringParserRuleCall_3_1_0_1 = (RuleCall)cElementsEObjectCrossReference_3_1_0.eContents().get(1);
		private final Keyword cEqualsSignKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cExpressionAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cExpressionExpressionParserRuleCall_5_0 = (RuleCall)cExpressionAssignment_5.eContents().get(0);
		
		//PresenceCondition:
		//	'PC' 'for' elements+=[ecore::EObject|EString] ("," elements+=[ecore::EObject|EString])* '=' expression=Expression;
		@Override public ParserRule getRule() { return rule; }
		
		//'PC' 'for' elements+=[ecore::EObject|EString] ("," elements+=[ecore::EObject|EString])* '=' expression=Expression
		public Group getGroup() { return cGroup; }
		
		//'PC'
		public Keyword getPCKeyword_0() { return cPCKeyword_0; }
		
		//'for'
		public Keyword getForKeyword_1() { return cForKeyword_1; }
		
		//elements+=[ecore::EObject|EString]
		public Assignment getElementsAssignment_2() { return cElementsAssignment_2; }
		
		//[ecore::EObject|EString]
		public CrossReference getElementsEObjectCrossReference_2_0() { return cElementsEObjectCrossReference_2_0; }
		
		//EString
		public RuleCall getElementsEObjectEStringParserRuleCall_2_0_1() { return cElementsEObjectEStringParserRuleCall_2_0_1; }
		
		//("," elements+=[ecore::EObject|EString])*
		public Group getGroup_3() { return cGroup_3; }
		
		//","
		public Keyword getCommaKeyword_3_0() { return cCommaKeyword_3_0; }
		
		//elements+=[ecore::EObject|EString]
		public Assignment getElementsAssignment_3_1() { return cElementsAssignment_3_1; }
		
		//[ecore::EObject|EString]
		public CrossReference getElementsEObjectCrossReference_3_1_0() { return cElementsEObjectCrossReference_3_1_0; }
		
		//EString
		public RuleCall getElementsEObjectEStringParserRuleCall_3_1_0_1() { return cElementsEObjectEStringParserRuleCall_3_1_0_1; }
		
		//'='
		public Keyword getEqualsSignKeyword_4() { return cEqualsSignKeyword_4; }
		
		//expression=Expression
		public Assignment getExpressionAssignment_5() { return cExpressionAssignment_5; }
		
		//Expression
		public RuleCall getExpressionExpressionParserRuleCall_5_0() { return cExpressionExpressionParserRuleCall_5_0; }
	}
	public class EObjectElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.EObject");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cEObjectAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cEObjectKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//EObject ecore::EObject:
		//	{ecore::EObject} 'EObject';
		@Override public ParserRule getRule() { return rule; }
		
		//{ecore::EObject} 'EObject'
		public Group getGroup() { return cGroup; }
		
		//{ecore::EObject}
		public Action getEObjectAction_0() { return cEObjectAction_0; }
		
		//'EObject'
		public Keyword getEObjectKeyword_1() { return cEObjectKeyword_1; }
	}
	public class FeatureElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.Feature");
		private final Assignment cFeatureAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cFeatureEStringParserRuleCall_0 = (RuleCall)cFeatureAssignment.eContents().get(0);
		
		//Feature:
		//	feature=EString;
		@Override public ParserRule getRule() { return rule; }
		
		//feature=EString
		public Assignment getFeatureAssignment() { return cFeatureAssignment; }
		
		//EString
		public RuleCall getFeatureEStringParserRuleCall_0() { return cFeatureEStringParserRuleCall_0; }
	}
	public class UnaryExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.UnaryExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cOperatorAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cOperatorUnaryOperatorEnumRuleCall_0_0 = (RuleCall)cOperatorAssignment_0.eContents().get(0);
		private final Assignment cRightAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cRightExpressionParserRuleCall_1_0 = (RuleCall)cRightAssignment_1.eContents().get(0);
		
		//UnaryExpression:
		//	operator=UnaryOperator right=Expression;
		@Override public ParserRule getRule() { return rule; }
		
		//operator=UnaryOperator right=Expression
		public Group getGroup() { return cGroup; }
		
		//operator=UnaryOperator
		public Assignment getOperatorAssignment_0() { return cOperatorAssignment_0; }
		
		//UnaryOperator
		public RuleCall getOperatorUnaryOperatorEnumRuleCall_0_0() { return cOperatorUnaryOperatorEnumRuleCall_0_0; }
		
		//right=Expression
		public Assignment getRightAssignment_1() { return cRightAssignment_1; }
		
		//Expression
		public RuleCall getRightExpressionParserRuleCall_1_0() { return cRightExpressionParserRuleCall_1_0; }
	}
	public class BinaryExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.BinaryExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cLeftAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cLeftExpressionParserRuleCall_1_0 = (RuleCall)cLeftAssignment_1.eContents().get(0);
		private final Assignment cOperatorAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cOperatorBinaryOperatorEnumRuleCall_2_0 = (RuleCall)cOperatorAssignment_2.eContents().get(0);
		private final Assignment cRightAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cRightExpressionParserRuleCall_3_0 = (RuleCall)cRightAssignment_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//BinaryExpression:
		//	'(' left=Expression operator=BinaryOperator right=Expression ')';
		@Override public ParserRule getRule() { return rule; }
		
		//'(' left=Expression operator=BinaryOperator right=Expression ')'
		public Group getGroup() { return cGroup; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_0() { return cLeftParenthesisKeyword_0; }
		
		//left=Expression
		public Assignment getLeftAssignment_1() { return cLeftAssignment_1; }
		
		//Expression
		public RuleCall getLeftExpressionParserRuleCall_1_0() { return cLeftExpressionParserRuleCall_1_0; }
		
		//operator=BinaryOperator
		public Assignment getOperatorAssignment_2() { return cOperatorAssignment_2; }
		
		//BinaryOperator
		public RuleCall getOperatorBinaryOperatorEnumRuleCall_2_0() { return cOperatorBinaryOperatorEnumRuleCall_2_0; }
		
		//right=Expression
		public Assignment getRightAssignment_3() { return cRightAssignment_3; }
		
		//Expression
		public RuleCall getRightExpressionParserRuleCall_3_0() { return cRightExpressionParserRuleCall_3_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_4() { return cRightParenthesisKeyword_4; }
	}
	public class EStringElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.EString");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTRINGTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cIDTerminalRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//EString:
		//	STRING | ID;
		@Override public ParserRule getRule() { return rule; }
		
		//STRING | ID
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STRING
		public RuleCall getSTRINGTerminalRuleCall_0() { return cSTRINGTerminalRuleCall_0; }
		
		//ID
		public RuleCall getIDTerminalRuleCall_1() { return cIDTerminalRuleCall_1; }
	}
	
	public class UnaryOperatorElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.UnaryOperator");
		private final EnumLiteralDeclaration cNOTEnumLiteralDeclaration = (EnumLiteralDeclaration)rule.eContents().get(1);
		private final Keyword cNOTNotKeyword_0 = (Keyword)cNOTEnumLiteralDeclaration.eContents().get(0);
		
		//enum UnaryOperator:
		//	NOT='not';
		public EnumRule getRule() { return rule; }
		
		//NOT='not'
		public EnumLiteralDeclaration getNOTEnumLiteralDeclaration() { return cNOTEnumLiteralDeclaration; }
		
		//'not'
		public Keyword getNOTNotKeyword_0() { return cNOTNotKeyword_0; }
	}
	public class BinaryOperatorElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "pnpl_variability.PNPL_variability.BinaryOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cANDEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cANDAndKeyword_0_0 = (Keyword)cANDEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cOREnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cOROrKeyword_1_0 = (Keyword)cOREnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cIMPLIESEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cIMPLIESImpliesKeyword_2_0 = (Keyword)cIMPLIESEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum BinaryOperator:
		//	AND='and' | OR='or' | IMPLIES='implies';
		public EnumRule getRule() { return rule; }
		
		//AND='and' | OR='or' | IMPLIES='implies'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//AND='and'
		public EnumLiteralDeclaration getANDEnumLiteralDeclaration_0() { return cANDEnumLiteralDeclaration_0; }
		
		//'and'
		public Keyword getANDAndKeyword_0_0() { return cANDAndKeyword_0_0; }
		
		//OR='or'
		public EnumLiteralDeclaration getOREnumLiteralDeclaration_1() { return cOREnumLiteralDeclaration_1; }
		
		//'or'
		public Keyword getOROrKeyword_1_0() { return cOROrKeyword_1_0; }
		
		//IMPLIES='implies'
		public EnumLiteralDeclaration getIMPLIESEnumLiteralDeclaration_2() { return cIMPLIESEnumLiteralDeclaration_2; }
		
		//'implies'
		public Keyword getIMPLIESImpliesKeyword_2_0() { return cIMPLIESImpliesKeyword_2_0; }
	}
	
	private final VariabilityElements pVariability;
	private final FileURIElements pFileURI;
	private final ExpressionElements pExpression;
	private final PresenceConditionElements pPresenceCondition;
	private final EObjectElements pEObject;
	private final FeatureElements pFeature;
	private final UnaryExpressionElements pUnaryExpression;
	private final BinaryExpressionElements pBinaryExpression;
	private final UnaryOperatorElements eUnaryOperator;
	private final BinaryOperatorElements eBinaryOperator;
	private final EStringElements pEString;
	
	private final Grammar grammar;
	
	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public PNPL_variabilityGrammarAccess(GrammarProvider grammarProvider,
			TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
		this.pVariability = new VariabilityElements();
		this.pFileURI = new FileURIElements();
		this.pExpression = new ExpressionElements();
		this.pPresenceCondition = new PresenceConditionElements();
		this.pEObject = new EObjectElements();
		this.pFeature = new FeatureElements();
		this.pUnaryExpression = new UnaryExpressionElements();
		this.pBinaryExpression = new BinaryExpressionElements();
		this.eUnaryOperator = new UnaryOperatorElements();
		this.eBinaryOperator = new BinaryOperatorElements();
		this.pEString = new EStringElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("pnpl_variability.PNPL_variability".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//Variability:
	//	'pn' petrinet=FileURI
	//	'fm' featuremodel=FileURI (presencecondition+=PresenceCondition ";")*;
	public VariabilityElements getVariabilityAccess() {
		return pVariability;
	}
	
	public ParserRule getVariabilityRule() {
		return getVariabilityAccess().getRule();
	}
	
	//FileURI:
	//	{FileURI} importURI=STRING;
	public FileURIElements getFileURIAccess() {
		return pFileURI;
	}
	
	public ParserRule getFileURIRule() {
		return getFileURIAccess().getRule();
	}
	
	//Expression:
	//	Feature | UnaryExpression | BinaryExpression;
	public ExpressionElements getExpressionAccess() {
		return pExpression;
	}
	
	public ParserRule getExpressionRule() {
		return getExpressionAccess().getRule();
	}
	
	//PresenceCondition:
	//	'PC' 'for' elements+=[ecore::EObject|EString] ("," elements+=[ecore::EObject|EString])* '=' expression=Expression;
	public PresenceConditionElements getPresenceConditionAccess() {
		return pPresenceCondition;
	}
	
	public ParserRule getPresenceConditionRule() {
		return getPresenceConditionAccess().getRule();
	}
	
	//EObject ecore::EObject:
	//	{ecore::EObject} 'EObject';
	public EObjectElements getEObjectAccess() {
		return pEObject;
	}
	
	public ParserRule getEObjectRule() {
		return getEObjectAccess().getRule();
	}
	
	//Feature:
	//	feature=EString;
	public FeatureElements getFeatureAccess() {
		return pFeature;
	}
	
	public ParserRule getFeatureRule() {
		return getFeatureAccess().getRule();
	}
	
	//UnaryExpression:
	//	operator=UnaryOperator right=Expression;
	public UnaryExpressionElements getUnaryExpressionAccess() {
		return pUnaryExpression;
	}
	
	public ParserRule getUnaryExpressionRule() {
		return getUnaryExpressionAccess().getRule();
	}
	
	//BinaryExpression:
	//	'(' left=Expression operator=BinaryOperator right=Expression ')';
	public BinaryExpressionElements getBinaryExpressionAccess() {
		return pBinaryExpression;
	}
	
	public ParserRule getBinaryExpressionRule() {
		return getBinaryExpressionAccess().getRule();
	}
	
	//enum UnaryOperator:
	//	NOT='not';
	public UnaryOperatorElements getUnaryOperatorAccess() {
		return eUnaryOperator;
	}
	
	public EnumRule getUnaryOperatorRule() {
		return getUnaryOperatorAccess().getRule();
	}
	
	//enum BinaryOperator:
	//	AND='and' | OR='or' | IMPLIES='implies';
	public BinaryOperatorElements getBinaryOperatorAccess() {
		return eBinaryOperator;
	}
	
	public EnumRule getBinaryOperatorRule() {
		return getBinaryOperatorAccess().getRule();
	}
	
	//EString:
	//	STRING | ID;
	public EStringElements getEStringAccess() {
		return pEString;
	}
	
	public ParserRule getEStringRule() {
		return getEStringAccess().getRule();
	}
	
	//terminal ID:
	//	'^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	}
	
	//terminal INT returns ecore::EInt:
	//	'0'..'9'+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	}
	
	//terminal STRING:
	//	'"' ('\\' . | !('\\' | '"'))* '"' | "'" ('\\' . | !('\\' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	}
	
	//terminal ML_COMMENT:
	//	'/*'->'*/';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//	'//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	}
	
	//terminal WS:
	//	' ' | '\t' | '\r' | '\n'+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	}
	
	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	}
}
