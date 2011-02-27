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
// IP 27 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import aloyslisp.core.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.engine.*;
import static aloyslisp.internal.engine.L.*;

/**
 * cLAMBDA_FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cLAMBDA_FUNCTION extends cFUNCTION implements tLAMBDA_FUNCTION
{

	/**
	 * Lisp function
	 */
	tLIST	func	= null;

	public cLAMBDA_FUNCTION(tLIST args, tLIST func)
	{
		super();
		tT doc = API_PARSE_FUNC(func);
		api = new cAPI_LAMBDA(args, doc.CAR(), (tLIST) doc.CDR().CAR());
		this.func = (tLIST) doc.CDR().CDR().CAR();
	}

	public cLAMBDA_FUNCTION(tLIST args, tT doc, tLIST decl, tLIST func)
	{
		super();
		api = new cAPI_LAMBDA(args, doc, decl);
		this.func = func;
	}

	/**
	 * 
	 */
	public cLAMBDA_FUNCTION()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.functions.tFUNCTION#exec(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tT[] exec(tLIST args)
	{
		tT[] res = new tT[]
		{ NIL };
		api.API_PUSH_ENV(args);

		try
		{
			res = cCOMPILED_SPECIAL.PROGN(func.VALUES_LIST());
		}
		finally
		{
			api.API_POP_ENV();
		}

		return res;
	}

}
