/**
 * aloysLisp.
 * <p>
 * A LISP interpreter, compiler and library.
 * <p>
 * Copyright (C) 2010-2011 kilroySoft <aloyslisp@kilroysoft.ch>
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
// IP 29 d�c. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.conditions;

import aloyslisp.annotations.aType;
import aloyslisp.core.designators.tPATHNAME_DESIGNATOR;

/**
 * READER_ERROR
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "reader-error", doc = "e_rder_e")
public class READER_ERROR extends PARSE_ERROR
{
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param path
	 */
	public READER_ERROR(tPATHNAME_DESIGNATOR path)
	{
		super(path);
		message += "Reader error.";
	}

}
