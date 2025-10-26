import java.util.*;
import java.io.*;

//main 객체
public class IDE {
    public static void main(String args[]){
        new CLI();
    }
}

//GUI 개발할 때를 대비하여 CLI 부분을 따로 떼놓음.
//기능을 추가하려면 loop()의 switch문에서 동작 코드를, print_menu()에서 기능및 번호 설명 코드를 추가할것
class CLI{
    Scanner scan = new Scanner(System.in);
    JavaFile Jfile = new JavaFile();
    //Constructor
    CLI(){
        loop();
    }
    //CLI loop
    void loop(){
        int action;
        while(true){
            print_menu();
            action = scan.nextInt();
            scan.nextLine();    //버퍼 비우기

            //기능을 추가하고 싶으면 여기에 case를 추가해주세요
            switch(action){
                case 1:
                    upload_file();
                    System.out.println(Jfile.check_path());
                    break;
                case 2:
                    Jfile.compile();
                    break;
                case 3:
                    break;
                case 4:
                    Jfile.getName("");
                    break;
                case 5:
                    break;
                case 6:
                    System.out.println("GoodBye");
                    scan.close();
                    return;
                default:
                    System.out.println("We Do Not have your option.");
                    System.out.println("Please Try Again");
                    break;
            }
            //System.out.println(scan.hasNextInt());
        }
    }
    void print_menu(){
        //기능을 추가하고 싶으면 여기에 기능 설명을 적어주세요.
        System.out.println("************************");
        System.out.println("JAVA TERM PROJECT");
        System.out.println("1. Java File Upload");
        System.out.println("2. Compile");
        System.out.println("3. Run");
        System.out.println("4. Reset");
        System.out.println("5. Check Error File");
        System.out.println("6. Exit");
        System.out.print(">");
    }
    void upload_file(){
        String path;
        System.out.print("Java File name:");
        path = scan.nextLine();
        System.out.println(path);
        Jfile.getName(path);
    }
}

//자바 파일 객체
class JavaFile{
    String path = ".\\";
    String name = "";
    File ErrMsg = new File(".\\Error.txt");
    ProcessBuilder proc = new ProcessBuilder("");

    JavaFile(){
        if(!ErrMsg.exists()){
            try{
                ErrMsg.createNewFile();
            }
            catch(Exception e){
                System.out.println("Error at creating Error log file");
                System.exit(-1);
            }
        }
    }
    void getPath(String path){
        this.path = path;
    }
    void getName(String name){
        this.name = name;
    }
    Boolean check_path(){
        return new File(path + "\\" + name).exists();
    }
    void compile(){
        if(name.isEmpty()){
            System.out.println("ERROR: There is no java file.");
            return;
        }
        proc.command("cmd", "/c", "javac " + path + name);
        proc.redirectError(ErrMsg);
        try{
            Process act = proc.start();
            BufferedReader output = new BufferedReader(new InputStreamReader(act.getInputStream(), "MS949"));
            String s;
            while((s = output.readLine()) != null){
                System.out.println(s);
            }
        }
        catch(Exception e){
            System.out.println("ERROR!!!\n" + e.getMessage());
        }
    }
}