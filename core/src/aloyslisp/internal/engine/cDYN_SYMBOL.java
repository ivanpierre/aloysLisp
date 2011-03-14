/**
 * aloysLisp.
 * <p>
 * A LISP interpreter, compiler and library.
 * <p>
 * Copyright (C) 2010 kilroySoft <aloyslisp@kilroysoft.ch>
 * 
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
// --------------------------------------------------------------------------
// history
// --------------------------------------------------------------------------
// IP 19 sept. 2010 Creation
// IP UB11 Correct function call, normally few orig call...
// IP UB12 Update commentaries
// IP UB20 Disconnect from tSYMBOL now own variable
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import static aloyslisp.core.L.*;

/**
 * cDYN_SYMBOL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 */
public class cDYN_SYMBOL extends cCELL implements tDYN_SYMBOL
{
	/**
	 * 
	 */
	protected tSYMBOL	orig;

	/**
	 * 
	 */
	protected tT		value;

	/**
	 * 
	 */
	protected tLIST		declare	= NIL;

	/**
	 * 
	 */
	protected boolean	special	= false;

	/**
	 * @param name
	 */
	public cDYN_SYMBOL(tSYMBOL orig, tT value)
	{
		this.orig = orig;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#set(aloyslisp.core.types.tT)
	 */
	public tSYMBOL SET_SYMBOL_VALUE(tT value)
	{
		this.value = value;
		return orig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#get()
	 */
	public tT SYMBOL_VALUE()
	{
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#unset()
	 */
	public tSYMBOL unset()
	{
		value = null;
		return orig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getPack()
	 */
	public tPACKAGE SYMBOL_PACKAGE()
	{
		return orig.SYMBOL_PACKAGE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#setSpecial(boolean)
	 */
	public tSYMBOL SETSPECIAL(boolean special)
	{
		this.special = special;
		return orig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#isSpecial()
	 */
	public boolean SPECIALP()
	{
		return special;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getName()
	 */
	public String SYMBOL_NAME()
	{
		return orig.SYMBOL_NAME();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getOrig()
	 */
	public tSYMBOL getOrig()
	{
		return orig;
	}

	/**
	 * Set Orig value. This one cannot be a keyword...
	 * 
	 * @return
	 */
	public tSYMBOL setOrig(tSYMBOL newOrig)
	{
		if (newOrig.KEYWORDP())
			throw new LispException("Keyword cannot be arguments : " + this);

		return orig = newOrig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#isSet()
	 */
	public boolean BOUNDP()
	{
		return SYMBOL_VALUE() != null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return orig.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#toString()
	 */
	public String toString()
	{
		return orig.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#DESCRIBE()
	 */
	public String DESCRIBE()
	{
		return "#<DYN_SYMB " + orig.toString() + " " + value + ""
				+ (special ? T : NIL) + " " + value + ">";
	}

}
