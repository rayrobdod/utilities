package com.rayrobdod.prompt;

import java.util.Scanner;
import java.util.List;
import java.io.PrintStream;
import java.util.regex.Pattern;

/**
 * Methods that can show prompts to a user using a console
 * 
 * Think of this as a package object with one method in it.
 *
 * @author Raymond Dodge
 * @version 2013 Aug 20
 */
final public class consoleView {
	private consoleView() {}
	
	private static final Pattern booleanPattern = Pattern.compile("(Y|N)");
	
	private static void apply(Prompt<?> prompt, Scanner in, PrintStream out) {
		out.print(prompt.getQuestion());
		
		if (prompt instanceof OpenEndedPrompt<?>) {
			final OpenEndedPrompt<?> prompt2 = (OpenEndedPrompt<?>) prompt;
			
			if (prompt2.clazz() == Boolean.TYPE) {
				final OpenEndedPrompt<Boolean> prompt3 = (OpenEndedPrompt<Boolean>) prompt2;
				
				out.print(" (Y/N): ");
				prompt3.setResponse( in.next(booleanPattern) == "Y");
			} else
			if (prompt2.clazz() == String.class) {
				final OpenEndedPrompt<String> prompt3 = (OpenEndedPrompt<String>) prompt2;
				
				out.print(" (String) : ");
				prompt3.setResponse( in.nextLine() );
			} else
			if (prompt2.clazz() == Integer.TYPE) {
				final OpenEndedPrompt<Integer> prompt3 = (OpenEndedPrompt<Integer>) prompt2;
				
				out.print(" (Number) : ");
				prompt3.setResponse( in.nextInt() );
			}
		} else
		if (prompt instanceof MultipleChoicePrompt<?>) {
			final MultipleChoicePrompt<?> prompt2 = (MultipleChoicePrompt<?>) prompt;
			out.println();
			
			int i = 0;
			for ( Object o : prompt2.options() ) {
				out.print('\t');
				out.print(i);
				i++;
				out.print(": ");
				out.println(o.toString());
			}
			out.print("Choose (0-");
			out.print(prompt2.options().size());
			out.print("): ");
			prompt2.setResponseIndex( in.nextInt() );
		} else
		if (prompt instanceof NotifyPrompt) {
			final NotifyPrompt prompt2 = (NotifyPrompt) prompt;
			// question has already been printed
			out.println();
			prompt2.setResponse();
		}
		
		throw new IllegalArgumentException("Unknown Prompt Type");
	}
	
	/**
	 * Prints and recieves an answer from each prompt
	 * <p>
	 * This will only return when the prompts have been provided 
	 * 
	 * @param prompts the prompts to show a user
	 * @param title the dialog's title
	 */
	public static void promptEach(String title, List<Prompt<?>> prompts) {
		PrintStream out = System.out;
		Scanner in = new Scanner(System.in);
		out.println(title);
		
		for ( Prompt<?> p : prompts ) {
			apply(p, in, out);
		}
		
		
	}
}
