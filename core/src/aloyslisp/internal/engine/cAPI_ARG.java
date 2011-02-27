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
// IP 26 f�vr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.core.*;
import aloyslisp.annotations.*;
import aloyslisp.core.sequences.*;
import static aloyslisp.internal.engine.L.*;

/**
 * cAPI_ARG
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cAPI_ARG extends cCELL implements tAPI_ARG
{
	@Symb(name = "lambda-list-keywords", doc = "v_lambda")
	public static tLIST	StaticListKeywords	= list(sym("&allow-other-keys"),
													sym("&aux"), sym("&body"),
													sym("&environment"),
													sym("&key"),
													sym("&optional"),
													sym("&rest"), sym("&whole"));

	/**
	 * 
	 */
	public cAPI_ARG()
	{
		// TODO Auto-generated constructor stub
	}

}
