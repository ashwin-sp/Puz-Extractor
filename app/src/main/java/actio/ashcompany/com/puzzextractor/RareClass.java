package actio.ashcompany.com.puzzextractor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by admin on 6/19/2016.
 */
public class RareClass {
    public static void main(String [] args) {

        FileInputStream fis = null;
        int i;
        char c;
        char[] bs = new char[5000];
        int step=2;
        int index;
        int flag;
        int cross_count;
        try{
            // create new file input stream
            fis = new FileInputStream("C:\\Users\\admin\\AndroidStudioProjects\\PuzzExtractor\\app\\src\\main\\assets\\December_2009.puz");
            InputStreamReader is = new InputStreamReader(fis,Charset.forName("ISO-8859-1"));
            // read bytes to the buffer
            i=is.read(bs);
            String s="";
            // prints
            System.out.println("Number of bytes read: "+i);
            System.out.print("Bytes read: ");
            // for each byte in buffer
            for(char b:bs)
            {
                // converts byte to character
                c=b;
                if(c=='\0')
                {
                    s+="\n"+c;
                }
                else {
                    s += c;
                }
                // print

            }
            //System.out.println(s);
            String[] sep = s.split("\n");
            //List sepr = new ArrayList<String>();
            for(int j=0;j<sep.length;j++){
                //System.out.println(j);
                //System.out.println(sep[j]);
                if(sep[j].length()>20){
                    step = j;
                    break;
                }
            }
            //System.out.println(s);
            /** for(int j=0;j<sep.length;j++)
            {
                System.out.println(j);
                System.out.println(sep[j]);
            }**/
            System.out.println(step);
            String[] subsep = sep[step].split("[a-z]+");
            System.out.println(subsep[0]);
            double d= Math.sqrt(subsep[0].length()/2);
            int size = (int) d;
            System.out.println(size);
            index =0;
            char[][] answers = new char[size][size];
            int[][] number_format = new int[size][size];
            List across = new ArrayList<Integer>();
            List down = new ArrayList<Integer>();
            for(int j=0;j<size;j++)
            {
                for(int k=0;k<size;k++)
                {
                    while(subsep[0].charAt(index) == '\0') {
                        index++;
                    }
                    answers[j][k] = subsep[0].charAt(index);
                    index++;
                    System.out.print(answers[j][k] + " ");
                }
                System.out.println();
            }
            flag = 0;
            cross_count = 0;
            for(int k=0;k<size;k++)
            {
                if(answers[0][k] != '.') {
                    if((flag == 0 && answers[0][k+1]!='.')||(answers[1][k]!='.')) {
                        cross_count++;
                        number_format[0][k] = cross_count;
                    }
                    if(flag == 0 && answers[0][k+1]!='.') {
                        across.add(cross_count);
                        flag = 1;
                    }
                    if(k+1!= size && answers[0][k+1] == '.')
                    {
                        flag = 0;
                    }
                    if(answers[1][k]!='.') {
                        down.add(cross_count);
                    }
                }
            }
            for(int j=1;j<size;j++)
            {
                flag = 0;
                for(int k=0;k<size;k++)
                {
                    if(answers[j][k] != '.')
                    {
                        if((flag == 0 && k+1!=size && answers[j][k+1] != '.')|| (answers[j-1][k] == '.' && j+1!=size && answers[j+1][k]!='.'))
                        {
                            cross_count++;
                            number_format[j][k] = cross_count;
                        }
                        if(flag == 0 && k+1!=size && answers[j][k+1] != '.')
                        {
                            across.add(cross_count);
                            flag = 1;
                        }
                        if(k+1 != size && answers[j][k+1] == '.' )
                        {
                            flag =0;
                        }
                        if(answers[j-1][k] == '.' && j+1!=size && answers[j+1][k]!='.')
                        {
                            down.add(cross_count);
                        }
                    }
                }
            }
            System.out.println(across);
            System.out.println(down);
            for(int j=0;j<size;j++)
            {
                for(int k=0;k<size;k++)
                {
                    System.out.print(number_format[j][k]+" ");
                }
                System.out.println();
            }
            cross_count = 0;
            String[] answer_list = new String[across.size() + down.size()];
            String temp = "";
            int counter;
            for(int j=0;j<size;j++)
            {
                for(int k=0;k<size;k++)
                {
                    if(answers[j][k] != '.' )
                    {
                        if(across.contains(number_format[j][k]))
                        {
                            counter = k;
                            while(counter < size && answers[j][counter] != '.')
                            {
                                temp += answers[j][counter];
                                counter++;
                            }
                            answer_list[cross_count] = temp;
                            cross_count++;
                            temp = "";
                        }
                        if(down.contains(number_format[j][k]))
                        {
                            counter = j;
                            while(counter < size && answers[counter][k] != '.')
                            {
                                temp+= answers[counter][k];
                                counter++;
                            }
                            answer_list[cross_count] = temp;
                            cross_count++;
                            temp = "";
                        }
                    }
                }
            }
            for(int j=0;j<answer_list.length;j++)
            {
                System.out.println(answer_list[j]);
            }
            index=step+3;
            String[] questions = new String[answer_list.length];
            for(int j=0;j<questions.length;j++)
            {
                questions[j] = sep[index];
                System.out.println(questions[j]);
                index++;
            }
            LinkedHashMap lhm = new LinkedHashMap();
            for(int j=0;j<answer_list.length;j++)
            {
                lhm.put(questions[j],answer_list[j]);
            }
            System.out.println(lhm);
        }catch(Exception ex){
            // if any error occurs
            ex.printStackTrace();
        }finally{

            // releases all system resources from the streams
            if(fis!=null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }
}
