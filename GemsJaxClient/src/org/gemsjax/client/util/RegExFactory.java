package org.gemsjax.client.util;

/**
 * Is a simple factory that generates regular expressions (RegEx) and return them as String
 * @author Hannes Dorfmann
 *
 */
public class RegExFactory {
	
	/**
	 * Generates a RegEx that looks like this: <br /><br />
	 * &lt;xmlRootTag ... attributeToLook="attributeToLookValue" ... &gt; ... 
	 * 
	 * 
	 * The dots (...) are placeholders for every other string of arbitrary length (also the empty string, string of length zero).
	 * The generated RegEx also allows arbitrary whitespaces between the elements. So the RegEx would match to true for the following examples:
	 * <ul>
	 * <li>&lt;xmlRootTag attributeToLook="attributeToLookValue" ... &gt; ... </li>
	 * 
	 * <li>&lt;xmlRootTag &nbsp; &nbsp; attributeToLook="attributeToLookValue" ... &gt; ... </li>
	 * <li>&lt;xmlRootTag &nbsp; &nbsp; attributeToLook ="attributeToLookValue" ... &gt; ... </li>
	 * <li>&lt;xmlRootTag &nbsp; &nbsp; attributeToLook = "attributeToLookValue" ... &gt; ... </li>
	 * <li>&lt;xmlRootTag &nbsp; &nbsp; attributeToLook &nbsp;&nbsp;= &nbsp;&nbsp;"attributeToLookValue" ... &gt; ... </li>
	 * 
	 * 
	 * <li>&lt;xmlRootTag attributeToLook="attributeToLookValue" &gt;... </li>
	 * <li>&lt;xmlRootTag attributeToLook="attributeToLookValue"&gt; </li>
	 * <li> and many other </li>
	 * </ul>
	 * <b>Notice:</b> The generated RegEx doesn't check if there is a closing xmlRootTag at the end. And it also doesn't allow whitespaces at the beginning between &lt; and xmlRootTag like "&lt; xmlRootTag"
	 * @param xmlRootTag
	 * @param attributeToLook
	 * @param attributeToLookValue
	 * @return
	 */
	public static synchronized String generate(String xmlRootTag, String attributeToLook, String attributeToLookValue)
	{
		return "\\A\\<(" +xmlRootTag+ " " +attributeToLook+ "|" +xmlRootTag+ " .* " +attributeToLook+ ") ( )*=( )*\"" + attributeToLookValue +"\"( |.*)\\>.*";
	}

}