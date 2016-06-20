import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;


public class ExtractPuzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  
		  try {
			  File f = new File("C:\\Data\\Puzzle\\src\\December_2009.puz");
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			byte[] work = new byte[8];
			in.readFully( work, 0, 2 ); 
	        short checksum1 = (short) ( ( work[ 1 ] & 0xff ) << 8 | ( work[ 0 ] & 0xff ) );
	        System.out.println(checksum1);
	        byte[] magicbuffer = new byte[12];  
	        for (int i=0; i<magicbuffer.length; i++) { 
	         byte b = in.readByte(); 
	         if (b == 0) 
	          break; 
	          
	         magicbuffer[i] = b; 
	        } 
	        String s = new String(magicbuffer, 0, 12, "ISO-8859-1");
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
            byte[] versionBuffer = new byte[4]; 
            for (int i=0; i<4; i++) { 
             byte b = in.readByte(); 
             if (b == 0) 
              break; 
              
             versionBuffer[i] = b; 
            } 
             
            String version = new String(versionBuffer, 0, 4, "ISO-8859-1");
            System.out.println(version);
            in.skipBytes(2);  
            in.readFully( work, 0, 2 );       
            short scrambledChecksum = ( short ) ( ( work[ 1 ] & 0xff ) << 8 | ( work[ 0 ] & 0xff ) ); 
            in.skipBytes(0x0C);  
		 // short checksum = dis.readShort(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
