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

import static aloyslisp.core.streams.cSTRING_INPUT_STREAM.*;
import static aloyslisp.internal.engine.L.*;

import java.lang.annotation.*;
import java.lang.reflect.*;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.math.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.core.streams.*;
import aloyslisp.internal.iterators.*;

/**
 * cAPI_JAVA
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cAPI_JAVA extends cAPI
{
	protected Method	method	= null;

	/**
	 * @param args
	 * @param doc
	 * @param decl
	 */
	public cAPI_JAVA(Method method, Boolean special)
	{
		super(special);
		this.method = method;
		SET_API_ARGS(NIL);
		SET_API_DOC(NIL);
		SET_API_DECL(NIL);
		environment = e.topEnv;

	}

	/**
	 * @return
	 */
	public tLIST SET_API_ARGS(tLIST unused)
	{
		tLIST res = NIL;
		LISTIterator vars = new LISTIterator(res);
		for (Annotation[] an : method.getParameterAnnotations())
		{
			int state = 0;
			for (Annotation a : an)
			{
				if (a instanceof Arg && state < 1)
				{
					vars.append(new cARG(sym(((Arg) a).name()), NIL));
					state = 1;
				}
				else if (a instanceof Opt && state < 2)
				{
					vars.append(new cARG(((Opt) a).name(), ((Opt) a).def()));
					state = 2;
				}
				else if (a instanceof Rest && state < 3 && rest == null)
				{
					rest = sym(((Rest) a).name());
					state = 3;
				}
				else if (a instanceof Whole && state < 4 && whole == null)
				{
					whole = sym(((Whole) a).name());
					state = 4;
				}
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tCODE#CODE_CALL(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tT[] API_CALL(tLIST args)
	{
		args = API_OBJECT(args);
		tT[] res = new tT[]
		{ NIL };
		tT object = null;

		Function func = method.getAnnotation(Function.class);
		if (func != null)
		{
			object = args.CAR();
			args = (tLIST) args.CDR();
		}

		try
		{
			Object is = method.invoke(object, tranformArgs(args));
			if (is instanceof Object[])
				res = normalizeArray((Object[]) is);
			else
				res = new tT[]
				{ normalize(is) };
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tCODE#CODE_OBJECT(aloyslisp.core.sequences.
	 * tLIST)
	 */
	@Override
	public tLIST API_OBJECT(tLIST args)
	{
		int pos = -1;
		Function func = method.getAnnotation(Function.class);
		if (func == null)
			return args;

		BaseArg position = method.getAnnotation(BaseArg.class);
		if (position != null)
			pos = position.pos();

		if (pos <= 0)
			return args;

		LISTIterator iter = new LISTIterator(args);
		iter.go(pos);
		tT obj = iter.go(pos);
		iter.remove();

		return cCONS.CONS(obj, iter.getFinal());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tCODE#CODE_GET_MAC()
	 */
	@Override
	public String API_GET_MAC()
	{
		String mac = null;
		Mac annotation = method.getAnnotation(Mac.class);
		if (annotation != null)
			mac = annotation.prefix();
		return mac;
	}

	/**
	 * @return
	 */
	public String getLispDeclare()
	{
		// TODO api description
		// String lFunc = name.SYMBOL_NAME();
		// tLIST res = list(name);
		// if (!(method.toString().contains("static")) && baseArg == -1)
		// {
		// res = (tLIST) res.APPEND(list(sym(method.getDeclaringClass()
		// .getSimpleName().substring(1))));
		// }
		// res = (tLIST) res.APPEND(this.API_ARGS());
		// String decl = "* " + res.toString().replaceAll(" \\*", " \\\\*");
		// decl = decl.replaceFirst(
		// lFunc.replaceAll("\\*", "\\\\*").replaceAll("\\%", "\\\\%")
		// .replaceAll("\\+", "\\\\+"), "[[" + lFunc
		// + "|http://hyper.aloys.li/Body/" + this.API_DOC()
		// + ".htm]]");
		// return decl.toLowerCase().replace("/body/", "/Body/");
		return "";
	}

	/**
	 * @param paramTypes
	 * @param arg
	 * @return
	 */
	public Object[] tranformArgs(tLIST arg)
	{
		// Transform arguments
		Class<?>[] paramTypes = method.getParameterTypes();
		int i = 0;
		Object[] newArgs = new Object[paramTypes.length];

		// System.out.println("Args : " + arg);

		for (Class<?> classArg : paramTypes)
		{
			if (classArg.isArray())
			{
				// manage rest of args as an array, tT... argument.
				newArgs[i] = arg.VALUES_LIST();
				arg = NIL;
				continue;
			}
			else
			{
				newArgs[i] = transform(arg.CAR(), classArg);
			}

			arg = (tLIST) arg.CDR();
			i++;
		}
		return newArgs;
	}

	/**
	 * Coerce a lisp argument to the object class of the function
	 * 
	 * @param arg
	 * @param cl
	 * @return
	 */
	public Object transform(Object arg, Class<?> cl)
	{
		Object res = null;

		// Direct assign works
		if (tT.class.isAssignableFrom(cl))
			if (cl.isAssignableFrom(arg.getClass()))
			{
				res = arg;
			}
			else if (arg instanceof Boolean)
			{
				res = ((Boolean) arg) ? T : NIL;
			}
			else if (arg instanceof String)
			{
				res = str((String) arg);
			}
			else
			{
				throw new LispException("1Argument " + arg + " : "
						+ arg.getClass().getSimpleName()
						+ " should be of type " + cl.getSimpleName());
			}

		if (cl == Boolean.class)
		{
			res = arg != NIL;
		}

		if (cl == Integer.class && arg instanceof tINTEGER)
		{
			res = ((cINTEGER) arg).integerValue();
		}

		if (cl == Long.class && arg instanceof tINTEGER)
		{
			res = ((cINTEGER) arg).integerValue();
		}

		if (cl == Float.class && arg instanceof tREAL)
		{
			res = ((cREAL) arg).floatValue();
		}

		if (cl == Double.class && arg instanceof tREAL)
		{
			res = ((cREAL) arg).doubleValue();
		}

		if (cl == String.class)
		{
			if (arg instanceof tSTRING)
			{
				res = ((tSTRING) arg).getString();
			}
		}

		if (cl == Character.class && arg instanceof tCHARACTER)
		{
			res = ((tCHARACTER) arg).getChar();
		}

		if (res != null)
			arg = res;

		if ((cl == tPATHNAME_DESIGNATOR.class && arg instanceof tPATHNAME_DESIGNATOR))
		{
			res = cPATHNAME.PATHNAME((tPATHNAME_DESIGNATOR) arg);
		}
		else if ((cl == tPACKAGE_DESIGNATOR.class && arg instanceof tPACKAGE_DESIGNATOR))
		{
			res = cPACKAGE.FIND_PACKAGE((tPACKAGE_DESIGNATOR) arg);
		}
		else if ((cl == tSTRING_DESIGNATOR.class && arg instanceof tSTRING_DESIGNATOR))
		{
			res = cSTRING.STRING((tSTRING_DESIGNATOR) arg);
		}

		if (res == null)
			throw new LispException("Argument " + arg + "("
					+ arg.getClass().getSimpleName() + ")"
					+ " should be of type " + cl.getSimpleName());

		trace(" ~~~> " + arg + " (" + arg.getClass().getSimpleName() + ") -> "
				+ res + " (" + cl.getSimpleName() + ")");

		return res;
	}

	/**
	 * Change object to tT according to object type
	 * 
	 * @param cell
	 * @return
	 */
	protected tT normalize(Object cell)
	{
		if (cell instanceof tT)
			return (tT) cell;
		else if (cell instanceof String)
			return str((String) cell);
		else if (cell instanceof Boolean)
			return bool((Boolean) cell);
		else if (cell instanceof Integer)
			return nInt((Integer) cell);
		else if (cell instanceof Float)
			return nFloat((Float) cell);
		else if (cell instanceof Double)
			return nDouble((Double) cell);
		else if (cell instanceof Character)
			return c((Character) cell);

		throw new LispException("primitive : return value of type "
				+ cell.getClass().getSimpleName() + " not managed");
	}

	/**
	 * Normalize result of function call to a
	 * 
	 * @param vals
	 * @return
	 */
	protected tT[] normalizeArray(Object[] vals)
	{
		tT[] res = new tT[vals.length];

		for (int i = 0; i < vals.length; i++)
		{
			res[i] = normalize(vals[i]);
		}

		return res;
	}

	/**
	 * @param notes
	 * @return
	 */
	protected tLIST argsDecl(Annotation[][] notes)
	{
		tLIST res = NIL;
		res = (tLIST) res.APPEND(argsBase(notes, Arg.class, ""));
		res = (tLIST) res.APPEND(argsBase(notes, Opt.class, "&optional"));
		res = (tLIST) res.APPEND(argsBase(notes, Rest.class, "&rest"));
		return res;
	}

	/**
	 * @param notes
	 * @param type
	 * @param prefix
	 * @return
	 */
	protected tLIST argsBase(Annotation[][] notes,
			Class<? extends Annotation> type, String prefix)
	{
		tLIST res = NIL;
		boolean call = prefix == null;

		for (Annotation[] an : notes)
		{
			for (Annotation a : an)
			{
				tT arg = NIL;
				if (type.isAssignableFrom(a.getClass()))
				{
					if (a instanceof Arg)
					{
						arg = sym(((Arg) a).name());
						if (call)
							arg = unquote(arg);
					}
					else if (a instanceof Opt)
					{
						arg = sym(((Opt) a).name());
						if (call)
							arg = unquote(arg);
						else
							arg = list(arg).APPEND(
									list(getDefault(((Opt) a).def())));
					}
					else if (a instanceof Rest)
					{
						arg = sym(((Rest) a).name());
						if (call)
							arg = splice(arg);
					}
				}
				if (arg != NIL)
					res = (tLIST) res.APPEND(list(arg));
			}
		}

		// If a prefix is given and
		if (prefix != null && !prefix.equals("") && res.LENGTH() != 0)
			res = (tLIST) decl(prefix).APPEND(res);

		return res;
	}

	/**
	 * @param def
	 * @return
	 */
	protected tT getDefault(String def)
	{
		if (def.equals(""))
			return NIL;

		return MAKE_STRING_INPUT_STREAM(def, NIL, NIL).READ(false, NIL, false);
	}

}
