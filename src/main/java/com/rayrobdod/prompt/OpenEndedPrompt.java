package com.rayrobdod.prompt;

/**
 * A Prompt with an open-ended resonse. Nothing other than the prompt
 * and resonse type is needed about the prompt to display it.
 * 
 * It stores a copy of the class the answer is, due to the nature
 * of type erasure.
 * 
 * The way I see it, the clazz should be something like
 * Boolean.TYPE, String.class, or Integer.TYPE.
 * @author Raymond Dodge
 * @version 2012 Aug 22
 */
public final class OpenEndedPrompt<E> extends Prompt<E>
{
	private final Class<E> clazz;
	
	public OpenEndedPrompt(String question, Class<E> clazz)
	{
		super(question);
		this.clazz = clazz;
	}
	
	public Class<E> clazz() {return clazz;}
	
	public final void setResponse(E response) {
		super.setResponse(response);
	}
}
