package vcardcreator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
public class VCardCreator
{
    public static void main(String[] args)
    {
        try{
            
        
       File photoFile = new File("photo.jpg");
       byte[] imageBytes =Files.readAllBytes(photoFile.toPath());
       String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        String vcard = 
                     "BEGIN:VCARD\r\n" +
                     "VERSION:3.0\r\n" +
                     "FN: Valentin Kolev\r\n" +
                     "EMAIL: Valyo300047@gmail.com\r\n"+
                     "ORG:Information Security in Computer Systems and Networks,FIRST COURSE,FIRST GROUP\r\n"+
                     "PHOTO;ENCODING=b;TYPE=JPEG:" + base64Image +"\r\n" +
                     "NOTE: Favorite languages:C++,JAVA, Web technologies\r\n" +
                      
                     "END:VCARD/r/n";
        
        
        String wd = System.getProperty("user.dir");
        File outFile = new File( "Mycard.vcf");
        System.out.println("Working dir =" + wd);
        System.out.println("Will write to:" + outFile.getAbsolutePath());

        try (FileWriter writer = new FileWriter(outFile))
        {
            writer.write(vcard);
            System.out.println("VCF File created");
            System.out.println("Път" + outFile.getAbsolutePath());
        }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
        }



