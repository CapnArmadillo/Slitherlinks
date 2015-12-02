package com.armadillo.slitherlink.altair;

import com.armadillo.common.DLog;
import com.armadillo.slitherlink.common.Edge;
import com.armadillo.slitherlink.common.SNode;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
/**
 * STable is a class to aid in solving Slitherlinks, based on the puzzles
 * found at http://www.krazydad.com/blog/2008/03/28/altair-slitherlinks/
 * This class is designed to read data from a file and create a table of 
 * production rules to tell the Slitherlinks class how to fill in edges.
 ****************************************************************************
 * @author John Pushnik
 * @version 03-31-09
 * @version 04-03-09 2.0 submittal
 * @version 05-13-10 2.0 resumed
 ****************************************************************************
 */
public class STable
{
    private static final String TAG = "STable";

    protected static String cName, eName, nName, iName, jName, yName, zName;
    protected static ArrayList<ArrayList<Integer>> 
        cRules, eRules, nRules, iRules, jRules, yRules, zRules;
    static boolean debug = false; 
//    protected static int eSize, nSize, iSize, length;
    /**
     * Constructor for objects of class STable
     */
    public STable() throws Exception
    {
//        BufferedReader input = 
//            new BufferedReader(new InputStreamReader(System.in));
//        if(debug){System.out.print("Please enter the input filename:");}
/*
        eName = "erules.dat";
        if(debug){System.out.println(eName);}
        nName = "nrules.dat";
        if(debug){System.out.println(nName);}
*/
        cName = "crules.dat";
        DLog.d(TAG, cName);
        iName = "irules.dat";
        DLog.d(TAG, iName);
        jName = "jrules.dat";
        DLog.d(TAG, jName);
        yName = "yrules.dat";
        DLog.d(TAG, yName);
        zName = "zrules.dat";
        DLog.d(TAG, zName);
//        eRules = new ArrayList<ArrayList<Integer>>();
//        nRules = new ArrayList<ArrayList<Integer>>();
        cRules = new ArrayList<ArrayList<Integer>>();
        iRules = new ArrayList<ArrayList<Integer>>();
        jRules = new ArrayList<ArrayList<Integer>>();
        yRules = new ArrayList<ArrayList<Integer>>();
        zRules = new ArrayList<ArrayList<Integer>>();
//        getMem(eName, eRules);
//        getMem(nName, nRules);
        getMem(cName, cRules);
        getMem(iName, iRules);
        getMem(jName, jRules);
        getMem(yName, yRules);
        getMem(zName, yRules);
    }
    /**
     * public static void main(String[] args)
     */
    public static void main(String[] args) throws Exception
    {
        eName = args[0];
        nName = args[1];
        iName = args[2];
        jName = args[3];
        yName = args[4];
        eRules = new ArrayList<ArrayList<Integer>>();
        nRules = new ArrayList<ArrayList<Integer>>();
        iRules = new ArrayList<ArrayList<Integer>>();
        jRules = new ArrayList<ArrayList<Integer>>();
        yRules = new ArrayList<ArrayList<Integer>>();
        getMem(eName, eRules);
        getMem(nName, nRules);
        getMem(iName, iRules);
        getMem(jName, jRules);
        getMem(yName, yRules);
    }

    /**
     * getMem(file) reads production rules from the specified file
     * 
     * @param   file    the name of the file
     * @return  int     the number of rules from the file 
     */
  private static int getMem(String file, ArrayList<ArrayList<Integer>> rules) throws Exception
  {
      BufferedReader input = new BufferedReader( new FileReader( file ));
      String line;

      ArrayList<Integer> rule;
      int token;

      int j=0;
      do 
      {
          line = input.readLine();
          DLog.d(TAG, "parsing line [" + line + "]");
          if( line != null )
          {// extract any tokens
              rule = getTokens(line);
              if( rule.size() > 1 )
              {// have tokens, so must have an rule (as opposed to empty line)
                  rules.add(rule);
                  ++j;
              }// have a token, so must be an instruction
          }// have a line
      } while( line != null );
        showMem(rules, 0, j);
        input.close();
        return j;
  }// getMem()
    /**
     * getTokens() 
     * 
     * @param   input    user string
     * @return  rule   An ArrayList of tokens
     */
  private static ArrayList<Integer> getTokens(String input)// throws Exception
  {
      StringTokenizer st;
      ArrayList<Integer> rule = new ArrayList<Integer>();
      Integer token;
              st = new StringTokenizer( input );
              if( st.countTokens() > 1 )
              {// have tokens, so must have an rule (as opposed to empty line)
                  rule = new ArrayList<Integer>();
                  while (st.hasMoreTokens()){
                      token = Integer.parseInt(st.nextToken());
                      DLog.d(TAG, "  " + token);
                      rule.add(token);  
                  }
              }// have a token, so must be an instruction

      return rule;
  }//getTokens()
    public static void check(SNode input)// throws Exception
    {
//        if (0 != input.getStatus())return;
        int size = input.getSides();
        Edge[] eIn, eOut;
        eIn = new Edge[size];
        eOut = new Edge[size];
//        Edge e1, e2;
        SNode n1, n2;
        SNode[] nodes = new SNode[size];
        ArrayList<Integer> din, d0, d1, d2, d3, d4, d5, d6;
        
        int empty = -3;
        for (int f = 0; f < size; f++){
            eIn[f] = input.getEdge(f);
            eOut[f] = eIn[f].otherEdge(input.getEdge((f + size - 1) % size));
            nodes[f] = eIn[f].otherNode(input);
        }
        for (int i = 0; i < size; i++){
            din = new ArrayList<Integer>();
            d0 = new ArrayList<Integer>();
            d1 = new ArrayList<Integer>();
            d2 = new ArrayList<Integer>();
            d3 = new ArrayList<Integer>();
            d4 = new ArrayList<Integer>();
            d5 = new ArrayList<Integer>();
            d6 = new ArrayList<Integer>();
//            e1 = e2;
//            e2 = eIn[i];
//            e3 = e1.otherEdge(e2);
/* grab eIn[i], eOut[i] for e1, e3*/
            din.add(input.getValue());
            din.add(input.getSides());
            d0.addAll(din);
            d3.addAll(din);
            d4.addAll(din);
            d5.addAll(din);
            d6.addAll(din);

            if ( null == eOut[i]) {
                d1.add(empty);
                d2.add(empty);
            }else{
                d1.add(eOut[i].getStatus());
                d2.add(eOut[i].getStatus());
            }
            for (int j = 0; j < size; j++){
                d1.add(eIn[(i + size - j -1) % size].getStatus());
                d2.add(eIn[(i + j) % size].getStatus());            
            }

            d3.addAll(d1);
            d4.addAll(d2);
            d5.addAll(1,d2);
            d6.addAll(1,d1);

            n1 = nodes[i];
            n2 = nodes[(i + size - 1) % size];

            if (null != n1){
                d0.add(n1.getValue());
                d0.add(n1.getSides());
            } else {
                d0.add(empty);
                d0.add(empty);
            }
            if (null != n2){
                d0.add(n2.getValue());
                d0.add(n2.getSides());
            } else {
                d0.add(empty);
                d0.add(empty);
            }
            check(eIn[i], d0, jRules);
            check(eIn[i], d0, jRules);
            check(eIn[i], d1, iRules);
            check(eIn[(i + size - 1) % size], d2, iRules);
            check(eIn[(i + size - 1) % size], d3, yRules);
            check(eIn[i], d4, yRules);
            check(eIn[i], d5, zRules);
            check(eIn[(i + size - 1) % size], d6, zRules);
            for (int k = 1; k < size - 1; k++){
                check(eIn[(i + k) % size], d3, cRules);
            }
        }
    }
  public static void check(Edge input, ArrayList<Integer> dat, 
    ArrayList<ArrayList<Integer>> rules)// throws Exception
  {
      if (0 != input.getStatus())return;
      int size = rules.size();
      ArrayList<Integer> rule;
      int count;
      for (int j = 0; j < size; j++)
      {
          rule = rules.get(j);
         
          count = 0;
          for(int k = 1; k < rule.size(); k++){
              if (dat.get(k - 1) == rule.get(k))
              count++; else break;
          }
          if (count + 1 == rule.size()){
                input.setStatus(rule.get(0));
                input.setID(j);
                return;
          }
      }
  }
    /**
     * showMem() outputs all the rules to screen
     */
  private static void showMem(ArrayList<ArrayList<Integer>> rules)
  { showMem(rules, 0, rules.size());
  }// showMem(ArrayList<ArrayList<Integer>> rules)
    /**
     * showMem(a,b) outputs a range of rules to screen
     * 
     * @param  a   first rule to output
     * @param  b   last rule to output
     */
  private static void showMem(ArrayList<ArrayList<Integer>> rules,
    int a, int b )
  {
      ArrayList<Integer> rule;
      for( int k=a; k<b; k++)
    {
        rule = rules.get(k);
        printRule(rule);
    }
  }// showMem(ArrayList<ArrayList<Integer>> rules, int a, int b)

    /**
     * printRule(a) outputs one rule to screen with spaces and a return
     * @param  a   the rule , as an ArrayList
     */
  private static void printRule(ArrayList<Integer> a ) // print spaces to right justify a string
  {
      for(int i=0; i<a.size(); i++){
          DLog.d(TAG, "  " + a.get(i));
      }
  }// printRule(ArrayList<Integer> a)

}