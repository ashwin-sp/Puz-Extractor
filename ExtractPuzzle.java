import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class ExtractPuzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  
		  try {
			  File f = new File("C:\\Data\\Puzzle\\src\\jz140925.puz");
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			byte[] work = new byte[8];
		    List across = new ArrayList<Integer>();
		    List down = new ArrayList<Integer>();
		    LinkedHashMap lhm = new LinkedHashMap();
			int index = 0;
			int cross_count = 0;
			int flag3 =0;
			in.readFully( work, 0, 2 ); 
	        short checksum1 = (short) ( ( work[ 1 ] & 0xff ) << 8 | ( work[ 0 ] & 0xff ) );
	        System.out.println(checksum1);
	        String s = readString(in,1024);
	        System.out.println(s);
	        in.readFully( work, 0, 2 ); 
	        short checksum2= ( short ) ( ( work[ 1 ] & 0xff ) << 8 | ( work[ 0 ] & 0xff ) );
            System.out.println(checksum2);
            in.readFully( work, 0, 4 ); 
            int checksum3 = ( work[ 3 ] ) << 24 | ( work[ 2 ] & 0xff ) << 16 | ( work[ 1 ] & 0xff ) << 8 
                   | ( work[ 0 ] & 0xff );
            System.out.println(checksum3);
            in.readFully( work, 0, 4 ); 
            int checksum4 = ( work[ 3 ] ) << 24 | ( work[ 2 ] & 0xff ) << 16 | ( work[ 1 ] & 0xff ) << 8 
                   | ( work[ 0 ] & 0xff );
            System.out.println(checksum4);            
            String version = readString(in,1024);
            System.out.println(version);
            in.skipBytes(2);  
            in.readFully( work, 0, 2 );       
            short scrambledChecksum = ( short ) ( ( work[ 1 ] & 0xff ) << 8 | ( work[ 0 ] & 0xff ) ); 
            in.skipBytes(0x0C);  
            byte width = in.readByte(); 
            byte height = in.readByte(); 
            System.out.println("width\t"+width+"height\t"+height);
            in.readFully( work, 0, 2 ); 
            short nClues = ( short ) ( ( work[ 1 ] & 0xff ) << 8 | ( work[ 0 ] & 0xff ) ); 
            System.out.println(nClues);
            in.skipBytes(2); 
            boolean scrambeld = in.readShort() != 0; 
            int size = width * height;    
            String board = readString(in,size);
            System.out.println(board);
            String player = readString(in,size);
            System.out.println(player);
            String title = readString(in,1024);
            System.out.println(title);
            String author = readString(in,1024);
            System.out.println(author);
            String cr = readString(in,1024);
            System.out.println(cr);
            double d = Math.sqrt(board.length()/2);
            width = (byte) d;
            height = (byte) d;
            System.out.println("width\t"+width+"height\t"+height);
            //String[] questions = new String[nClues];
            //for(int i=0;i<nClues;i++)
            //{
              // 	questions[i] =  readString(in, 1024);
            	//System.out.println(questions[i]);
            //}
            char[][] answers = new char[width][height];
            for(int j=0;j<width;j++)
            {
                for(int k=0;k<height;k++)
                {
                    answers[j][k] = board.charAt(index);
                    index++;
                    System.out.print(answers[j][k]+" ");
                }
                System.out.println();
            }
            int[][] number_format = new int[width][height];
            for(int k=0;k<height;k++)
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
                    if(k+1!= height && answers[0][k+1] == '.')
                    {
                        flag3 = 0;
                    }
                    if(answers[1][k]!='.') {
                        down.add(cross_count);
                    }
                }
            }
            for(int j=1;j<width;j++)
            {
                flag3 = 0;
                for(int k=0;k<height;k++)
                {
                    if(answers[j][k] != '.')
                    {
                        if((flag3 == 0 && k+1!=height && answers[j][k+1] != '.')|| (answers[j-1][k] == '.' && j+1!=width && answers[j+1][k]!='.'))
                        {
                            cross_count++;
                            number_format[j][k] = cross_count;
                        }
                        if(flag3 == 0 && k+1!=height && answers[j][k+1] != '.')
                        {
                            across.add(cross_count);
                            flag3= 1;
                        }
                        if(k+1 != height && answers[j][k+1] == '.' )
                        {
                            flag3 =0;
                        }
                        if(answers[j-1][k] == '.' && j+1!=width && answers[j+1][k]!='.')
                        {
                            down.add(cross_count);
                        }
                    }
                }
            }
            System.out.println(across);
            System.out.println(down);
            for(int j=0;j<width;j++)
            {
                for(int k=0;k<height;k++)
                {
                    System.out.print(number_format[j][k]+" ");
                }
                System.out.println();
            }
            cross_count = 0;
            String[] answer_list = new String[across.size() + down.size()];
            String temp = "";
            int counter;
            for(int j=0;j<width;j++)
            {
                for(int k=0;k<height;k++)
                {
                    if(answers[j][k] != '.' )
                    {
                        if(across.contains(number_format[j][k]))
                        {
                            counter = k;
                            while(counter < width && answers[j][counter] != '.')
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
                            while(counter < height && answers[counter][k] != '.')
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
            String[] questions = new String[across.size() + down.size()];
            for(int i=0;i<nClues;i++)
            {
               	questions[i] =  readString(in, 1024);
            	System.out.println(questions[i]);
            }
            System.out.println(questions.length);
            System.out.println(answer_list.length);
            for(int j=0;j<answer_list.length;j++)
            {
                lhm.put(questions[j],answer_list[j]);
            }
            System.out.println(lhm);
		 // short checksum = dis.readShort(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	 private static String readString(DataInputStream in, int bufferSize) throws IOException { 
		  byte[] buffer = new byte[bufferSize]; 
		   
		  int i; 
		  for (i=0; i<bufferSize; i++) { 
		   byte b = in.readByte(); 
		   if (b == 0) 
		    break; 
		    
		   buffer[i] = b; 
		  } 
		   
		  String s = new String(buffer, 0, i, "ISO-8859-1"); 
		  return s; 
		 } 
}
