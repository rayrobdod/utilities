package com.rayrobdod.prompt;

import java.util.List;
import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Methods that can show prompts to a user using javax.swing
 * 
 * Think of this as a package object with one method in it.
 
 * @author Raymond Dodge
 * @version 2013 Aug 20
 */
final public class swingView {
	private swingView() {}
	
	private static interface TransferResult {
		public void apply();
	}
	
	private static class MyPair {
		public final TransferResult res;
		public final Component comp;
		
		public MyPair(Component comp, TransferResult res) {
			this.comp = comp;
			this.res = res;
		}
	}
	
	/**
	 * Converts a prompt into a component to display and a function to put the compoent's input into the prompt
	 */
	private static MyPair apply(Prompt<?> prompt) {
		if (prompt instanceof OpenEndedPrompt<?>) {
			final OpenEndedPrompt<?> prompt2 = (OpenEndedPrompt<?>) prompt; 
			
			if (prompt2.clazz() == Boolean.TYPE) {
				final OpenEndedPrompt<Boolean> prompt3 = (OpenEndedPrompt<Boolean>) prompt2;
				
				final JCheckBox comp = new JCheckBox(prompt.getQuestion());
				
				return new MyPair(comp, new TransferResult() {
					public void apply() { prompt3.setResponse( comp.isSelected()); }
				});
			} else
			if (prompt2.clazz() == String.class) {
				final OpenEndedPrompt<String> prompt3 = (OpenEndedPrompt<String>) prompt2;
				
				final JPanel comp = new JPanel();
				final JTextField field = new JTextField(); 
				comp.add(new JLabel(prompt.getQuestion()));
				comp.add(field);
				
				return new MyPair(comp, new TransferResult() {
					public void apply() { prompt3.setResponse(field.getText()); }
				});
			} else
			if (prompt2.clazz() == Integer.TYPE) {
				final OpenEndedPrompt<Integer> prompt3 = (OpenEndedPrompt<Integer>) prompt2;
				
				final JPanel comp = new JPanel();
				final JFormattedTextField field = new JFormattedTextField(new java.text.DecimalFormat());
				comp.add(new JLabel(prompt.getQuestion()));
				comp.add(field);
				
				return new MyPair(comp, new TransferResult() {
					public void apply() { prompt3.setResponse((Integer) field.getValue()); }
				});
			}
		} else
		if (prompt instanceof MultipleChoicePrompt<?>) {
			final MultipleChoicePrompt<?> prompt2 = (MultipleChoicePrompt<?>) prompt; 
			final JList<Object> comp = new JList<Object>(prompt2.options().toArray());
			
			return new MyPair(comp, new TransferResult() {
					public void apply() { prompt2.setResponseIndex(comp.getSelectedIndex()); }
			});
		} else
		if (prompt instanceof NotifyPrompt) {
			final NotifyPrompt prompt2 = (NotifyPrompt) prompt;
			final JLabel comp = new JLabel(prompt.getQuestion());
			
			return new MyPair(comp, new TransferResult() {
					public void apply() { prompt2.setResponse(); }
			});
		}
		
		throw new IllegalArgumentException("Unknown Prompt Type");
	}
	
	/**
	 * Creates a JDialog to show the prompts in.
	 * <p>
	 * This will return once the dialog is created; the prompts will not be ready
	 * yet; they will wait for the user to interact with a button on the JDialog.
	 * Basically, if you call this, don't interact with the Prompts using
	 * the AWT thread. <small>Since the point of this is that you're trying to MVC,
	 * you're probably not touching the AWT thread anyway, but still, warnings!</small> 
	 * 
	 * @param prompts the prompts to show a user
	 * @param title the dialog's title
	 */
	public static void showDialog(String title, List<Prompt<?>> prompts) {
		
		// Basically a map function
		final List<MyPair> comps = new java.util.ArrayList<MyPair>();
		for ( Prompt<?> p : prompts ) {
			comps.add(apply(p));
		}
		
		final JDialog display = new JDialog();
		final JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setVisible(false);
				for ( MyPair c : comps ) {
					c.res.apply();
				}
			}
		});
		
		display.getContentPane().setLayout(new BoxLayout(display.getContentPane(), BoxLayout.Y_AXIS));
		for ( MyPair c : comps ) {
			display.getContentPane().add(c.comp);
		}
		display.getContentPane().add(submit);
		display.setTitle(title);
		display.setVisible(true);
	}
}
