package actio.ashcompany.com.puzzextractor;

import java.io.FileInputStream;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExtractedClass {
    public static void main(String [] args) {

        FileInputStream fis = null;
        int i;
        int index =0;
        int step=2;
        int cross_count = 0;
        double dsize;
        int size;
        char c;
        byte[] bs = new byte[5000];
        List across = new ArrayList<Integer>();
        List down = new ArrayList<Integer>();
        LinkedHashMap lhm = new LinkedHashMap();
        try{
            // create new file input stream
            fis = new FileInputStream("C:\\Users\\admin\\AndroidStudioProjects\\PuzzExtractor\\app\\src\\main\\assets\\Crypt.puz");

            // read bytes to the buffer
            i=fis.read(bs);
            String s="";
            // prints
            System.out.println("Number of bytes read: "+i);
            System.out.print("Bytes read: ");
            int flag1 =1;
            int flag2 =0;
            int flag3;
            // for each byte in buffer
            for(byte b:bs)
            {
                // converts byte to character
                c=(char)b;

                // print
                if(c =='\u0001')
                {
                    System.out.print(c);
                    System.out.println();
                    s+=c+"\n";
                }
                else if(c== '-' && flag1 == 1)
                {
                    System.out.println();
                    System.out.print(c);
                    s+="\n"+c;
                    flag1 =0;
                    flag2 =1;
                }
                else if(c!= '-' && c!= '.' && flag2 == 1)
                {
                    System.out.println();
                    System.out.print(c);
                    s+= "\n"+c;
                    flag2 = 0;
                }
                else {
                    System.out.print(c);
                    s+=c;
                }
            }
            System.out.println();
            String[] sep = s.split("\n");
            for(int j=0;j<sep.length;j++)
            {
                System.out.println(j);
                System.out.println(sep[j]);
            }
            for(int j=sep.length-1;j>0;j--)
            {
                if(sep[j].contains("-"))
                {
                    step = j;
                }
            }

            System.out.println();
            String[] questions = sep[step+1].split("\0");
            for(int j=0;j<questions.length;j++)
            {
                System.out.println(questions[j]);
            }
            System.out.println(sep[step-1].length());
            System.out.println(sep[step].length());
            dsize = (Math.sqrt(sep[step].length()));
            size = (int) dsize;
            System.out.println(size);
            char[][] grid = new char[size][size];
            int[][] number_format = new int[size][size];
            char[][] answers = new char[size][size];
            for(int j=0;j<size;j++)
            {
                for(int k=0;k<size;k++)
                {
                    grid[j][k] = sep[step].charAt(index);
                    index++;
                    System.out.print(grid[j][k]+" ");
                }
                System.out.println();
            }
            index =0;
            for(int j=0;j<size;j++)
            {
                for(int k=0;k<size;k++)
                {
                    while(sep[step-1].charAt(index) == '\0') {
                        index++;
                    }
                    answers[j][k] = sep[step-1].charAt(index);
                    index++;
                    System.out.print(answers[j][k] + " ");
                }
                System.out.println();
            }
            flag3 = 0;
            for(int k=0;k<size;k++)
            {
                if(answers[0][k] != '.') {
                    if((flag3 == 0 && answers[0][k+1]!='.')||(answers[1][k]!='.')) {
                        cross_count++;
                        number_format[0][k] = cross_count;
                    }
                    if(flag3 == 0 && answers[0][k+1]!='.') {
                        across.add(cross_count);
                        flag3 = 1;
                    }
                    if(k+1!= size && answers[0][k+1] == '.')
                    {
                        flag3 = 0;
                    }
                    if(answers[1][k]!='.') {
                        down.add(cross_count);
                    }
                }
            }
            for(int j=1;j<size;j++)
            {
                flag3 = 0;
                for(int k=0;k<size;k++)
                {
                    if(answers[j][k] != '.')
                    {
                        if((flag3 == 0 && k+1!=size && answers[j][k+1] != '.')|| (answers[j-1][k] == '.' && j+1!=size && answers[j+1][k]!='.'))
                        {
                            cross_count++;
                            number_format[j][k] = cross_count;
                        }
                        if(flag3 == 0 && k+1!=size && answers[j][k+1] != '.')
                        {
                            across.add(cross_count);
                            flag3= 1;
                        }
                        if(k+1 != size && answers[j][k+1] == '.' )
                        {
                            flag3 =0;
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
            System.out.println(questions.length);
            System.out.println(answer_list.length);
            for(int j=0;j<answer_list.length;j++)
            {
                lhm.put(questions[j+3],answer_list[j]);
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
