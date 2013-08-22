package com.rayrobdod.prompt;

/**
 * This class represents a Question and Resonse pair.
 * 
 * The question is immutable throughout the life of the object.
 * The resonse will be set after the object is made.
 * @author Raymond Dodge
 * @version 2012 Aug 22
 * @version 2013 Aug 19 - implementing getQuestion
 * @version 2013 Aug 19 - making setResponse protected and not-final
 */
public abstract class Prompt<E>
{
	private final String question;
	private boolean isSet;
	private E response;
	
	/**
	 * Creates a prompt with the specified question
	 * @param question the thing to ask the user
	 */
	public Prompt(String question)
	{
		this.question = question;
		this.isSet = false;
		this.response = null;
	}

	public final String getQuestion()
	{
		return this.question;
	}
	
	/**
	 * Sets the answer to the prompt.
	 * @param response the new response to the question
	 */
	protected synchronized void setResponse(E response)
	{
		this.response = response;
		this.isSet = true;
		this.notifyAll();
	}
	
	/**
	 * returns the resonse; Blocks until the response is set.
	 * @return the resonse
	 */
	public final synchronized E getResponse()
	{
		while (!isSet)
		{
			try {this.wait();}
			catch (InterruptedException e) { /* ignore */ }
		}
		
		return this.response;
	}
}
