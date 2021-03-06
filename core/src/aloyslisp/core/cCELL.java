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
// IP 9 sept. 2010-2011 Creation
// IP UB12 Update commentaries
// --------------------------------------------------------------------------

package aloyslisp.core;

import aloyslisp.annotations.*;
import aloyslisp.core.clos.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import static aloyslisp.core.L.*;

/**
 * cCELL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cCELL implements tSTANDARD_OBJECT
{

	/**
	 * Current function test for order
	 */
	public static tFUNCTION	currTest	= null;

	/**
	 * Enable trace() to be active
	 */
	protected boolean		trace		= false;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@aJavaInternal
	public String toString()
	{
		return TO_STRING();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@aJavaInternal
	public boolean equals(Object o)
	{
		Boolean t = false;
		if (!(o instanceof tT))
			t = super.equals(o);
		else if (currTest == null)
			t = EQUAL((tT) o);
		else
			t = currTest.e((tT) o)[0] != NIL;
		// if (t)
		// System.out.println("equal : " + this + " " + o);
		return t;
	}

	/**
	 * Write trace on environment
	 * 
	 * @param msg
	 */
	@aJavaInternal
	protected void trace(String msg)
	{
		if (trace)
			System.err.println(msg);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	@aJavaInternal
	public int hashCode()
	{
		return this.SXHASH();
	}

	/**
	 * @param message
	 * @param args
	 * @return
	 */
	@aFunction(name = "error", doc = "f_error")
	public static String ERROR( //
			@aArg(name = "mess") String message, //
			@aRest(name = "args") tT... args)
	{

		String err = FORMAT(message, args);
		throw new LispException(err);
	}

	/**
	 * @param format
	 * @param args
	 * @return
	 */
	@aFunction(name = "format", doc = "f_format")
	public static String FORMAT( //
			@aArg(name = "format") String format, //
			@aRest(name = "args") tT... args)
	{
		// This is a basic format with ony ~S and ~s tags
		String tmpl = format.replaceAll("~[sS]", "%s");
		return String.format(tmpl, (Object[]) args);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#car()
	 */
	public tT CAR()
	{
		if (!(this instanceof tCONS))
		{
			throw new LispErrorFunctionCannotApplyOn("car", this);
		}
		return ((tCONS) this).CAR();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#cdr()
	 */
	public tT CDR()
	{
		if (!(this instanceof tCONS))
		{
			throw new LispErrorFunctionCannotApplyOn("cdr", this);
		}
		return ((tCONS) this).CDR();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tSTANDARD_OBJECT#CLASS_OF()
	 */
	public tCLASS CLASS_OF()
	{
		// TODO Implements CLASS_OF
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#COERCE(aloyslisp.core.tT)
	 */
	public tT COERCE(tT type)
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#compile()
	 */
	public String COMPILE()
	{
		// throw new LispException("Error no mean for cDYN_SYMBOL(" + value +
		// ") compilation");
		return "/* Error no mean for " + getClass().getCanonicalName()
				+ "() */" + NIL.COMPILE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#CONSTANTP()
	 */
	public boolean CONSTANTP()
	{
		return true;
	}

	// MAIN GLOBAL LISP FUNCTIONS
	/**
	 * PUBLIC cCOMPILED_METHOD cFUNCTION
	 */
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#copy()
	 */
	public tT COPY_CELL()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#describe()
	 */
	public String DESCRIBE()
	{
		return TO_STRING();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#eq(aloyslisp.core.types.tT)
	 */
	public boolean EQ(tT cell)
	{
		return this == cell;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#eql(aloyslisp.core.types.tT)
	 */
	public boolean EQL(tT cell)
	{
		return this == cell;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#equal(aloyslisp.core.types.tT)
	 */
	public boolean EQUAL(tT cell)
	{
		return EQL(cell);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#equal(aloyslisp.core.types.tT)
	 */
	public boolean EQUALP(tT cell)
	{
		return EQUAL(cell);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#eval()
	 */
	public tT[] EVAL()
	{
		tT[] res = new cCELL[1];
		res[0] = this;
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#evalCompile()
	 */
	public String EVALCOMPILE()
	{
		return "res = " + COMPILE() + ".eval();";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#istype(aloyslisp.core.types.tT)
	 */
	public boolean ISTYPE(tSYMBOL type)
	{
		// get name of type
		String name = "";
		if (type instanceof tSYMBOL)
			name = "t"
					+ ((tSYMBOL) type).SYMBOL_NAME().toUpperCase()
							.replace('-', '_');
		else
			throw new LispException("type should be defined by a symbol : "
					+ type);

		try
		{
			// Test with assignation ability
			return Class.forName("aloyslisp.core.types." + name)
					.isAssignableFrom(this.getClass());
		}
		catch (ClassNotFoundException e)
		{
			throw new LispException("Unknown type : " + name);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#KEYWORDP()
	 */
	@Override
	public boolean KEYWORDP()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#macroExpand()
	 */
	public tT[] MACROEXPAND()
	{
		tT[] res = MACROEXPAND_1();
		if (res[1] == NIL)
			return res;

		while (res[1] != NIL)
		{
			res = res[0].MACROEXPAND_1();
		}
		res[1] = T;
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#macroExpand1()
	 */
	public tT[] MACROEXPAND_1()
	{
		return new tT[]
		{ this, NIL };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#printable()
	 */
	@Override
	public String TO_STRING()
	{
		return "#<" + getClass().getSimpleName() + ">";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#SXHASH()
	 */
	public Integer SXHASH()
	{
		return getClass().getSimpleName().hashCode();
	}
}
