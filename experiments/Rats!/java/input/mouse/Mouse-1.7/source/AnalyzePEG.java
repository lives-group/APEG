//=========================================================================
//
//  Part of PEG parser generator Mouse.
//
//  Copyright (C) 2014 by Roman R. Redziejowski (www.romanredz.se).
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//
//-------------------------------------------------------------------------
//
//  Change log
//    141220 Created.
//
//=========================================================================

package mouse;

import mouse.runtime.SourceFile;
import mouse.utility.CommandArgs;
import mouse.utility.BitMatrix;
import mouse.utility.Convert;
import mouse.peg.*;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
//
//  AnalyzePEG
//
//-------------------------------------------------------------------------
//
//  Analyze the grammar.
//
//  Invocation
//
//    java mouse.AnalyzePEG <arguments>
//
//  The <arguments> are specified as options according to POSIX syntax:
//
//    -G <filename>
//       Identifies the file containing the grammar. Mandatory.
//       The <filename> need not be a complete path,
//       just enough to identify the file in current environment.
//       Should include file extension, if any.
//
//
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH

public class AnalyzePEG
{
  //=====================================================================
  //
  //  Data
  //
  //=====================================================================
  //-------------------------------------------------------------------
  //  PEG being analyzed, representaed by a structure of Expr objects.
  //-------------------------------------------------------------------
  PEG peg;

  //-------------------------------------------------------------------
  //  Index of all Expr objects.
  //  Each object has its position in the vector as the 'index' component.
  //  Rules and Terminals come first in the index.
  //  Refs are not included in the index, but they obtain index
  //  of the node they refer to.
  //-------------------------------------------------------------------
  Expr[] index;
  int N;  // Total length of index.
  int T;  // Index of first Terminal.
  int S;  // Index of first Subexpression.

  //-------------------------------------------------------------------
  //  Dictionary for retrieving Rules by name.
  //-------------------------------------------------------------------
  Hashtable<String, Expr.Rule> rules;

  //-------------------------------------------------------------------
  //  Relation first.
  //  last[i,j] = true means that expression with index j
  //  may appear as first in expression with index i.
  //-------------------------------------------------------------------
  BitMatrix first;

  //-------------------------------------------------------------------
  //  Relation last.
  //  last[i,j] = true means that expression with index i
  //  may appear as last in expression with index j.
  //-------------------------------------------------------------------
  BitMatrix last;

  //-------------------------------------------------------------------
  //  Relation next.
  //  last[i,j] = true means that expression with index j
  //  may appear after expression with index i.
  //-------------------------------------------------------------------
  BitMatrix next;

  //-------------------------------------------------------------------
  //  Relation disjoint.
  //  disjoint[i,j] = true means that Terminals with indexes i,j
  //  are disjoint.
  //-------------------------------------------------------------------
  BitMatrix disjoint;

  //-------------------------------------------------------------------
  //  Relation First.
  //  First[i,j] = true means that expression with index j belongs
  //  to First of expression with index i.
  //-------------------------------------------------------------------
  BitMatrix First;

  //-------------------------------------------------------------------
  //  Relation Follow.
  //  Follow[i,j] = true means that expression with index j belongs
  //  to Follow of expression with index i.
  //-------------------------------------------------------------------
  BitMatrix Follow;


  BitSet terms;


  //-------------------------------------------------------------------
  //  Output indentation.
  //-------------------------------------------------------------------
  int indent;


  //=====================================================================
  //
  //  Invocation
  //
  //=====================================================================

  public static void main(String argv[])
    {
      AnalyzePEG analyzer = new AnalyzePEG();
      analyzer.run(argv);
    }

  //=====================================================================
  //
  //  Do the job
  //
  //=====================================================================

  void run(String argv[])
    {
      //---------------------------------------------------------------
      //  Parse arguments.
      //---------------------------------------------------------------
      CommandArgs cmd = new CommandArgs
             (argv,      // arguments to parse
              "",        // options without argument
              "G",       // options with argument
               0,0);     // no positional arguments
      if (cmd.nErrors()>0) return;

      String gramName = cmd.optArg('G');
      if (gramName==null)
      {
        System.err.println("Specify -G grammar file.");
        return;
      }

      SourceFile src = new SourceFile(gramName);
      if (!src.created()) return;

      //---------------------------------------------------------------
      //  Create PEG object.
      //---------------------------------------------------------------
      PEG peg = new PEG(src);
      if (peg.errors>0) return;

      if (peg.notWF!=0)
      {
        System.out.println("The grammar is not well-formed.");
        return;
      }

      //---------------------------------------------------------------
      //  Get numbers of Expr objects.
      //---------------------------------------------------------------
      T = peg.rules.length;
      S = T + peg.terms.length;
      N = S + peg.subs.length;

      //---------------------------------------------------------------
      //  Build the index and dictionary of Rules.
      //---------------------------------------------------------------
      index = new Expr[N];
      rules = new Hashtable<String, Expr.Rule>();

      int i = 0;

      for (Expr.Rule e: peg.rules)
      {
        e.index = i;
        index[i] = e;
        rules.put(e.name,e);
        i++;
      }

      for (Expr e: peg.terms)
      {
        e.index = i;
        index[i] = e;
        i++;
      }

      for (Expr e: peg.subs)
      {
        e.index = i;
        index[i] = e;
        i++;
      }

      for (Expr.Ref e: peg.refs)
        e.index = e.rule.index;

      //---------------------------------------------------------------
      //  Compute first, last and next using MatrixVisitor.
      //---------------------------------------------------------------
      first = BitMatrix.empty(N);
      last  = BitMatrix.empty(N);
      next  = BitMatrix.empty(N);

      MatrixVisitor matrixVisitor = new MatrixVisitor();

      for (Expr e: index)
        e.accept(matrixVisitor);

      //---------------------------------------------------------------
      //  Compute First and Follow.
      //---------------------------------------------------------------
      First = first.star();
      Follow = last.star().times(next);

      //---------------------------------------------------------------
      //  Compute disjoint.
      //---------------------------------------------------------------
      disjoint = BitMatrix.empty(N);

      DisjointVisitor disjointVisitor = new DisjointVisitor();

      for (i=T;i<S;i++)
        index[i].accept(disjointVisitor);

      //---------------------------------------------------------------
      //  Create reader for user's input.
      //---------------------------------------------------------------
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader in = new BufferedReader(isr);


      terms = new BitSet(N);
      for (i=T;i<S;i++)
        terms.set(i);

      //---------------------------------------------------------------
      //  Keep processing user's requests.
      //---------------------------------------------------------------
      String request;

      while(true)
      {
        System.out.print("rule: ");

        try
        { request = in.readLine().trim(); }
        catch (IOException e)
        {
          System.out.println(e.toString());
          return;
        }

        if (request.length()==0) return;

        if (request.equals("LL1"))
        {
          for (i=0;i<T;i++)
          {
            Expr.Rule e = (Expr.Rule)index[i];
            if (!LL1(e,e.rhs))
              System.out.println(diagName(e));
          }

          for (i=S;i<N;i++)
          {
            if (!(index[i] instanceof Expr.Choice)) continue;
            Expr.Choice e = (Expr.Choice)index[i];
            if (!LL1(e,e.expr))
            System.out.println(diagName(e));
          }
          continue;
        }

        Expr.Rule r = rules.get(request);
        if (r==null) continue;

        //if (r.rhs.length<2) continue;
        //showFirst(r,r.rhs);

        showFollow(r);

      }
    }


  //=====================================================================
  //
  //  Show Follow of an Expression.
  //
  //=====================================================================
  void showFollow(final Expr e)
  {
    Vector<Expr> v = Follow(e);
    for (Expr f: v)
      System.out.println(diagName(f));
  }

  //=====================================================================
  //
  //  Show first terminals of alternatives in Rule or Choice.
  //
  //=====================================================================
  void showFirst(final Expr e, final Expr[] alt)
    {
      if (alt.length<2) return;
      for (int a=0;a<alt.length;a++)
      {
        indent = 0;
        write(diagName(alt[a]));
        indent = 2;
        BitSet firstTerms = First.row(alt[a].index);
        firstTerms.and(terms);
        for (int k=0;k<N;k++)
          if (firstTerms.get(k))
            write(diagName(index[k]));
      }
    }


  //=====================================================================
  //
  //  Check LL1 for Rule or Choice.
  //
  //=====================================================================
  boolean LL1(final Expr e, final Expr[] alt)
    {
      if (alt.length<2) return true;
      for (int a=0;a<alt.length-1;a++)
      {
        BitSet firstTerms1 = First.row(alt[a].index);
        firstTerms1.and(terms);
        BitSet firstTerms2 = First.row(alt[a+1].index);
        for (int i=a+2;i<alt.length;i++)
          firstTerms2.or(First.row(alt[i].index));
        firstTerms2.and(terms);
        BitMatrix pairs = BitMatrix.product(firstTerms1,firstTerms2,N);
        BitMatrix conflicts = pairs.and(disjoint.not());
        if (conflicts.weight()>0)
        {
         // System.out.println("---conflicts---");
         // showMatrix(conflicts);
          return false;
        }
      }
      return true;
    }


  //=====================================================================
  //
  //  Get Follow as Vector.
  //
  //=====================================================================
  Vector<Expr> Follow(Expr e)
    {
      Vector<Expr> result = new Vector<Expr>();
      int i = e.index;
      for (int j=0; j<N; j++)
        if (Follow.at(i,j))
          result.add(index[j]);
      return result;
    }


  //=====================================================================
  //
  //  Show matrix.
  //
  //=====================================================================
  void showMatrix(final BitMatrix M)
    {
      for (int i=0;i<N;i++)
      {
        BitSet row = M.row(i);
        if (!row.isEmpty())
        {
          indent = 0;
          write(diagName(index[i]));
          indent = 2;
          for (int j=0;j<N;j++)
            if (row.get(j))
              write(diagName(index[j]));
        }
      }
    }




  //=====================================================================
  //
  //  Diagnostic name for expression.
  //
  //=====================================================================
  private String diagName(Expr e)
    {
      if (e.name!=null) return e.name;
      return Convert.toPrint(e.asString());
    }


  //=====================================================================
  //
  //  Write line consisting of string 's',
  //  indented by 'indent' positions.
  //
  //=====================================================================
  public void write(final String s)
    {
      // if (indent>2) return;
      for (int i=0;i<indent;i++) System.out.print(" ");
       System.out.println(s);
    }

  //=====================================================================
  //
  //  Increment / decrement indentation.
  //
  //=====================================================================
  public void indent()
    { indent ++; }

  public void undent()
    { indent --; }



  //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
  //
  //  MatrixVisitor - builds matrices first, last and next.
  //
  //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH

  class MatrixVisitor extends mouse.peg.Visitor
  {

    //-----------------------------------------------------------------
    //  Rule.
    //-----------------------------------------------------------------
    public void visit(Expr.Rule e)
      {
        for (Expr expr: e.rhs)
        {
          first.set(e.index,expr.index);
          last.set(expr.index,e.index);
        }
      }

    //-----------------------------------------------------------------
    //  Choice.
    //-----------------------------------------------------------------
    public void visit(Expr.Choice e)
      {
        for (Expr expr: e.expr)
        {
          first.set(e.index,expr.index);
          last.set(expr.index,e.index);
        }
      }

    //-----------------------------------------------------------------
    //  Sequence.
    //-----------------------------------------------------------------
    public void visit(Expr.Sequence e)
      {
        for (int i=0; i<e.expr.length; i++)
        {
          first.set(e.index,e.expr[i].index);
          if (!e.expr[i].nul) break;
        }
        for (int i=e.expr.length-1; i>=0; i--)
        {
          last.set(e.expr[i].index,e.index);
          if (!e.expr[i].nul) break;
        }
        for (int i=0; i<e.expr.length-1; i++)
        {
          inner:
          for (int j=i+1; j<e.expr.length; j++)
          {
            next.set(e.expr[i].index,e.expr[j].index);
            if (!e.expr[j].nul) break inner;
          }
        }
      }

    //-----------------------------------------------------------------
    //  Plus.
    //-----------------------------------------------------------------
    public void visit(Expr.Plus e)
      {
        first.set(e.index,e.expr.index);
        last.set(e.expr.index,e.index);
        next.set(e.expr.index,e.expr.index);
      }

    //-----------------------------------------------------------------
    //  Star.
    //-----------------------------------------------------------------
    public void visit(Expr.Star e)
      {
        first.set(e.index,e.expr.index);
        last.set(e.expr.index,e.index);
        next.set(e.expr.index,e.expr.index);
      }

    //-----------------------------------------------------------------
    //  Query.
    //-----------------------------------------------------------------
    public void visit(Expr.Query e)
      {
        first.set(e.index,e.expr.index);
        last.set(e.expr.index,e.index);
      }

    //-----------------------------------------------------------------
    //  StarPlus.
    //-----------------------------------------------------------------
    public void visit(Expr.StarPlus e)
      {
        first.set(e.index,e.expr1.index);
        last.set(e.expr2.index,e.index);
        next.set(e.expr1.index,e.expr1.index);
        next.set(e.expr1.index,e.expr2.index);
      }

    //-----------------------------------------------------------------
    //  PlusPlus.
    //-----------------------------------------------------------------
    public void visit(Expr.PlusPlus e)
      {
        first.set(e.index,e.expr1.index);
        last.set(e.expr2.index,e.index);
        next.set(e.expr1.index,e.expr1.index);
        next.set(e.expr1.index,e.expr2.index);
      }
  }



  //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
  //
  //  DisjointVisitor - builds matrix 'disjoint'.
  //
  //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH

  class DisjointVisitor extends mouse.peg.Visitor
  {
    //-----------------------------------------------------------------
    //  StringLit.
    //-----------------------------------------------------------------
    public void visit(Expr.StringLit x)
    {
      int i = x.index;
      for (int j=i+1;j<S;j++)
      {
        if (index[j] instanceof Expr.StringLit)
          disjoint(x,(Expr.StringLit)index[j]);

        if (index[j] instanceof Expr.CharClass)
          disjoint(x,(Expr.CharClass)index[j]);

        if (index[j] instanceof Expr.Range)
          disjoint(x,(Expr.Range)index[j]);
      }
    }

    //-----------------------------------------------------------------
    //  CharClass.
    //-----------------------------------------------------------------
    public void visit(Expr.CharClass x)
    {
      int i = x.index;
      for (int j=i+1;j<S;j++)
      {
        if (index[j] instanceof Expr.StringLit)
          disjoint((Expr.StringLit)index[j],x);

        if (index[j] instanceof Expr.CharClass)
          disjoint(x,(Expr.CharClass)index[j]);

        if (index[j] instanceof Expr.Range)
          disjoint(x,(Expr.Range)index[j]);
      }
    }

    //-----------------------------------------------------------------
    //  Range.
    //-----------------------------------------------------------------
    public void visit(Expr.Range x)
    {
      int i = x.index;
      for (int j=i+1;j<S;j++)
      {
        if (index[j] instanceof Expr.StringLit)
          disjoint((Expr.StringLit)index[j],x);

        if (index[j] instanceof Expr.CharClass)
          disjoint((Expr.CharClass)index[j],x);

        if (index[j] instanceof Expr.Range)
          disjoint(x,(Expr.Range)index[j]);
      }
    }

    //=================================================================
    //  Checks for disjointness
    //=================================================================
    //-----------------------------------------------------------------
    //  String - String
    //-----------------------------------------------------------------
    private void disjoint(final Expr.StringLit x, final Expr.StringLit y)
      {
        if (!x.s.startsWith(y.s) && !y.s.startsWith(x.s))
          setBits(x,y);
      }

    //-----------------------------------------------------------------
    //  String - Class
    //-----------------------------------------------------------------
    private void disjoint(final Expr.StringLit x, final Expr.CharClass y)
      {
        if (y.s.indexOf(x.s.charAt(0))<0)
          setBits(x,y);
      }

    //-----------------------------------------------------------------
    //  String - Range
    //-----------------------------------------------------------------
    private void disjoint(final Expr.StringLit x, final Expr.Range y)
      {
        char c = x.s.charAt(0);
        if (c<y.a || c>y.z)
          setBits(x,y);
      }

    //-----------------------------------------------------------------
    //  Class - Class
    //-----------------------------------------------------------------
    private void disjoint(final Expr.CharClass x, final Expr.CharClass y)
      {
        boolean collision = false;
        for (int k=0;k<y.s.length();k++)
          if (x.s.indexOf(y.s.charAt(k))>=0)
            collision = true;
        if (!collision)
          setBits(x,y);
      }

    //-----------------------------------------------------------------
    //  Class - Range
    //-----------------------------------------------------------------
    private void disjoint(final Expr.CharClass x, final Expr.Range y)
      {
        boolean collision = false;
        for (char c=y.a;c<=y.z;c++)
          if (x.s.indexOf(c)>=0)
            collision = true;
        if (!collision)
          setBits(x,y);
      }

    //-----------------------------------------------------------------
    //  Range - Range
    //-----------------------------------------------------------------
    private void disjoint(final Expr.Range x, final Expr.Range y)
      {
        if (x.z<y.a || y.z<x.a)
          setBits(x,y);
      }

    //-----------------------------------------------------------------
    //  Set bits
    //-----------------------------------------------------------------
    private void setBits(final Expr x, final Expr y)
      {
        disjoint.set(x.index,y.index);
        disjoint.set(y.index,x.index);
      }
  }
}