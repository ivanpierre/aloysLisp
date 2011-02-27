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
import aloyslisp.core.clos.*;

/**
 * tAPI_ARG
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tAPI_ARG extends tBUILD_IN_CLASS
{
	/**
	 * Get evaluated default value
	 * @param env
	 * @return
	 */
	@Function(name = "api-arg-get-default")
	public tT API_ARG_GET_DEFAULT(tENV env);

	/**
	 * @param env
	 * @param value
	 * @return
	 */
	@Function(name = "api-arg-get-value")
	public tT API_ARG_GET_VALUE(tENV env, tT value);
}
