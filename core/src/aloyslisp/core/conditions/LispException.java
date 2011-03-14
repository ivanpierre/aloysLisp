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
// IP 12 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.conditions;

import aloyslisp.core.L;
import aloyslisp.core.tT;

/**
 * LispException
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 */
public class LispException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7785621250806132893L;

	public Boolean				trace				= false;

	/**
	 * @param message
	 */
	public LispException(String message)
	{
		super(message);
		tT res = L.sym("*trace*").SYMBOL_VALUE();
		// System.out.println("*trace* = " + res);
		L.e.ENV_DUMP();
		if (res != null && res != L.NIL)
			trace = true;
	}

}
