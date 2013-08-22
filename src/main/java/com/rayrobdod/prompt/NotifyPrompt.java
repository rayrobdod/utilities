package com.rayrobdod.prompt;

/**
 * A Question with no meaningful reponse. 
 * @author Raymond Dodge
 * @version 2013 Aug 19
 */
public final class NotifyPrompt extends Prompt<NotifyPrompt>
{
	/**
	 * Creates a prompt with the specified question
	 * @param question the thing to ask the user
	 */
	public NotifyPrompt(String question)
	{
		super(question);
	}
	
	/**
	 * Sets the answer to the prompt.
	 * @param response the new response to the question
	 */
	public void setResponse()
	{
		super.setResponse(this);
	}
}
