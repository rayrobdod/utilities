package com.rayrobdod.prompt;

import java.util.List;
import java.util.ArrayList;

/**
 * A question with several explicit options as answers
 * @author Raymond Dodge
 * @version 2012 Aug 22
 * @version 2013 Aug 19 - making setResponse protected and not-final
 */
public final class MultipleChoicePrompt<E> extends Prompt<E>
{
	private final List<E> options;
	
	public MultipleChoicePrompt(String question, List<E> options)
	{
		super(question);
		
		//defensive copy
		this.options = new ArrayList<E>(options);
	}
	
	public MultipleChoicePrompt(String question, E[] options)
	{
		this(question, java.util.Arrays.asList(options));
	}
	
	public List<E> options()
	{
		//defensive copy
		return new ArrayList<E>(this.options);
	}
	
	public final void setResponseIndex(int index)
	{
		this.setResponse(this.options.get(index));
	}
}
