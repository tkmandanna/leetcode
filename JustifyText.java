/**
 * Created by tkmandanna on 4/23/16.
 * Leetcode problem 68
 */
import java.util.*;
import java.io.*;

public class JustifyText {

    public static List<String> fullJustify(String[] words, int maxWidth) {

        List<String> result = new ArrayList<String>();
        for(int i=0;i<words.length;)
        {
            StringBuilder current=new StringBuilder();
            int total = -1;
            int wordCount=0;
            ArrayList<String>curList = new ArrayList<String>();
            boolean perfect = false ; // for when the words add up to get a string of length =  maxWidth
            boolean noJustify=false; // don't full justify for last line

            while((i<words.length)&&((total+words[i].length()+1)<=maxWidth)) /* exit loop condition..use short circuiting
            to prevent Array out of bounds in words[i] in the for loop condition*/
            {
                total+=words[i].length();


                if(i==(words.length-1))
                    noJustify=true; // this is the last line ..only left justify

                curList.add(words[i]);
                wordCount++;
                total++; //the increase in length when space is added before new word is added
                i++;
                if(total == maxWidth) //words align perfectly to give a line of length = maxWidth
                {
                    perfect = true;
                    for(String temp : curList)
                    {
                        current.append(temp);
                        wordCount--;
                        if(wordCount>0)
                            current.append(" ");
                    }

                    result.add(current.toString());// add current line to final result
                    break;
                }


            }

            if(perfect) //don't need to adjust spaces
                continue;

            if(total!=maxWidth) // current line did not end with a space at the end..will need to remove the last word
            {

                if(!noJustify) // not the last line
                {
                    int remSpace=maxWidth-total;
                    int spaceSlots = wordCount - 1;
                    int eachSlot = 0;
                    int leftOver = 0;
                    if(spaceSlots!=0) //if spaceSlots == 0 , then only one word in this line
                    {
                        eachSlot = remSpace / spaceSlots;
                        leftOver=remSpace % spaceSlots;

			/*
				"This_is_to____"  -> initially this is case..4 spaces at the end
				becomes This___is___to  ..so 4 spaces / spaceSlots (i.e. no of words -1)=> 4/2..so each
				slot gets extra 2 spaces + the 1 mandatory space

				then if remaining spaces was 5..then 5%2 = 1..so the initially spaceSlots on the left get the remaining ones
			*/

                        StringBuilder spaceStr = new StringBuilder();
                        for(int x =0 ;x<eachSlot;x++)
                        {
                            spaceStr.append(" ");
                        }
                        int appendCount=0;
                        for(String temp: curList )
                        {
                            current.append(temp);
                            appendCount++;
                            if(appendCount==curList.size()) // spaces don't need to be appended after the last word in current line
                                break;

                            current.append(" "); // mandatory Space
                            current.append(spaceStr.toString());
                            if(leftOver>0)
                            {
                                current.append(" ");
                                leftOver--;
                            }
                        }
                    }
                    else // only one word in this line and so it's left justified even though it's not the last line
                        {
                            for(String temp: curList )
                            {
                                current.append(temp);
                            }
                            for(int x=0;x<remSpace;x++)
                            {

                                current.append(" ");
                            }
                        }
                }
                else
                {
                    // this is the last line
                    int remSpaces=maxWidth-total;
                    int appendCount=0;
                    for(String temp : curList)
                    {
                        current.append(temp);
                        appendCount++;
                        if(appendCount==curList.size()) // spaces don't need to be appended after the last word in current line
                            break;
                        current.append(" ");
                    }
                    while(remSpaces>0)
                    {
                        current.append(" ");
                        remSpaces--;
                    }
                }

            }
            //System.out.println(""+current.toString());
            result.add(current.toString());

        }

        return result;
    }

    public static void main(String args[])
    {
        //String[] arr={"This","is","an","example","of","text","justification."};
        String []arr ={"Listen","to","many,","speak","to","a","few."};

        List<String>res = fullJustify(arr, 6);
        for(String temp : res)
        {
            System.out.println(temp+"|");
        }
    }

}

