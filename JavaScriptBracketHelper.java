/**
 * Title:        Evertz Coding Challenge A
 * Copyright:    Copyright (c) 2022
 * Company:      Evertz Microsystems
 * @version 1.0
 *
 * Submitted by: Daniel Nguyen
 * Email: daniel_nguyen-@hotmail.com
 * Last Modified: Mar. 7/24
 *
 */
package com.evertz.challenge.a;

/* Importing stack class */
import java.util.HashMap;
import java.util.Stack;

public class JavaScriptBracketHelper {

	public static void main(String[] args) {

		/**
		 * Below provides some initial test cases to run your solution against. However your
		 * solution will be run against more than provided here - so be thorough. Remember, the goal
		 * here is a JavaScript parser, if it can't parse JavaScript, it isn't very helpful.
		 */

		JavaScriptBracketHelper helper = new JavaScriptBracketHelper();

		// expected good cases
		System.out.println("Expected Good cases:");
		helper.run("{}[]()");
		helper.run(" (){[][] } ()()");
		helper.run("if (0 === 0 ) { return true; }");

		// expected missing opener cases
		System.out.println("Expected missing opener cases:");
		helper.run("[{]]");
		helper.run("([}][])");
		helper.run("if( condition )){code};");

		// expected missing closer cases
		System.out.println("Expected missing closer cases:");
		helper.run("{ [(] }");
		helper.run("{ [(]((( ) }");
		helper.run("const func = (i => { console.log(i) };");

		// expected out of order
		System.out.println("Expected out of order cases:");
		helper.run("[[(]])");
		helper.run("[((}])){");
		helper.run("console.log(input[i)]");

	}

	/**
	 * TODO -> fill in Big-O complexity of your algorithm here
	 * Time complexity: O(n) - traverse input n times
	 * Space Complexity: O(n) - stack which goes up to the input of n
	 */
	private AnalysisResult parseJavaScript(String input) {
		/**
		 * TODO -> fill in below method with your algorithm
		 */
		/*Remove every character except openers and closers () {} [] */
		String new_string = input.replaceAll("[^(){}\\[\\]]", "");
		/*Odd length strings cannot be GOOD*/
		if (new_string.length() % 2 != 0) {
			return bracketFault(new_string);
		}
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < new_string.length(); i++) {
			if (stack.isEmpty() && (new_string.charAt(i) == ')' || new_string.charAt(i) == '}' || new_string.charAt(i) == ']')) {
				return AnalysisResult.MISSING_OPENER;
			} else {
				if (new_string.charAt(i) == ')' && stack.peek() == '(')
					stack.pop();
				else if (new_string.charAt(i) == '}' && stack.peek() == '{')
					stack.pop();
				else if (new_string.charAt(i) == ']' && stack.peek() == '[')
					stack.pop();
				else
					stack.add(new_string.charAt(i));
			}
		}
		/*Something is still wrong with it*/
		if (!stack.isEmpty()) {
			return bracketFault(new_string);
		} else {
			return AnalysisResult.GOOD;
		}
	}

	private AnalysisResult bracketFault(String input) {
		/**
		 * Determines which type of bracket fault
		 */

		if (input.length() == 2) return AnalysisResult.MISSING_OPENER;

		HashMap<Character, Integer> bracketCount = new HashMap<Character, Integer>();
		char[] stringArray = input.toCharArray();

		/*Go through string and count occurrence of each bracket*/
		for (char c: stringArray) {
			if (bracketCount.containsKey(c)) {
				bracketCount.put(c, bracketCount.get(c) + 1);
			} else {
				bracketCount.put(c, 1);
			}
		}

		int openerCount = 0, closerCount = 0;

		if (bracketCount.get('(') != null) openerCount += bracketCount.get('(');
		if (bracketCount.get('{') != null) openerCount += bracketCount.get('{');
		if (bracketCount.get('[') != null) openerCount += bracketCount.get('[');

		if (bracketCount.get(')') != null) closerCount += bracketCount.get(')');
		if (bracketCount.get('}') != null) closerCount += bracketCount.get('}');
		if (bracketCount.get(']') != null) closerCount += bracketCount.get(']');

		if (openerCount > closerCount) return AnalysisResult.MISSING_CLOSER;
		else if (closerCount > openerCount) return AnalysisResult.MISSING_OPENER;
		else {
			int bracket1 = 0, bracket2 = 0, bracket3 = 0;
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) == ')') bracket1 += 1;
				if (input.charAt(i) == '(') bracket1 -= 1;
				if (input.charAt(i) == '}') bracket2 += 1;
				if (input.charAt(i) == '{') bracket2 -= 1;
				if (input.charAt(i) == ']') bracket1 += 1;
				if (input.charAt(i) == '[') bracket1 -= 1;
			} if ((bracket1 > 0) || (bracket2> 0) || (bracket3>0)) return AnalysisResult.MISSING_OPENER;
			else return AnalysisResult.OUT_OF_ORDER;
			}
		}

	private void run(String input) {

		// run the analysis
		AnalysisResult result = parseJavaScript(input);

		// truncate input longer than 25 characters
		String truncated = input.length() > 25 ? (input.substring(0, 25) + "...") : input;

		// print result to stdout, enum will be autoboxed (using toString)
		System.out.println("Input value: " + truncated + "=>" + result);
	}

	/**
	 * Enumeration representing the analysis result of the JavaScript bracket parser
	 */
	private enum AnalysisResult {
		GOOD("good"), MISSING_OPENER("missing opener"), MISSING_CLOSER("missing closer"), OUT_OF_ORDER("out of order");

		// friendly string of result to print out
		private final String friendlyString;

		// private constructor only used by this class
		private AnalysisResult(String friendlyString) {
			this.friendlyString = friendlyString;
		}

		// override toString to autobox the enum with formatted text
		@Override
		public String toString() {
			return friendlyString;
		}
	}

}
