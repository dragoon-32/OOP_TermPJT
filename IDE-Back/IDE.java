import java.io.*;
import java.util.*;

//main 객체
public class IDE {
    public static void main(String args[]) {
        new CLI();
    }
}

//GUI 개발할 때를 대비하여 CLI 부분을 따로 떼놓음.
//기능을 추가하려면 loop()의 switch문에서 동작 코드를, print_menu()에서 기능및 번호 설명 코드를 추가할것
class CLI {
    JavaFile Jfile;

    //Constructor
    CLI() {
        loop();
    }

    //CLI loop
    void loop() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            print_menu();
            int action = scan.nextInt();
            //기능을 추가하고 싶으면 여기에 case를 추가해주세요
            switch (action) {
                case 1:
                    Scanner path = new Scanner(System.in);
                    Jfile = upload_file(path);
                    break;
                case 2:
                    compile_JavaFile(Jfile);
                    break;
                case 3:
                    run_JavaFile(Jfile);
                    break;
                case 4:
                    reset_JavaFile();
                    break;
                case 5:
                    Jfile.PrintErrorFile();
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
        }
    }

    void print_menu() {
        //기능을 추가하고 싶으면 여기에 기능 설명을 적어주세요.
        System.out.println("************************");
        System.out.println("JAVA TERM PROJECT");
        System.out.println("1. Java File Upload");
        System.out.println("2. Compile");
        System.out.println("3. Run");
        System.out.println("4. Reset");
        System.out.println("5. Check Error File");
        System.out.println("6. Exit");
        System.out.println("************************");
        System.out.print(">");
    }

    JavaFile upload_file(Scanner scan) {
        String path;
        System.out.print("Java File Path:");
        path = scan.nextLine();
        return new JavaFile(path);
    }

    void compile_JavaFile(JavaFile file) {
        if(file == null){
            System.out.println("We Do Not Have ANY Java File!");
            System.out.println("please upload your Java file");
            return;
        }
        try {
            //에러 메세지를 저장할 파일 생성
            Jfile.ErrorFile = new File(file.path + ".error");
            //만약 에러 메세지 파일이 없으면 생성한다.
            if(!Jfile.ErrorFile.exists()){
                Jfile.ErrorFile.createNewFile();
            }

            ProcessBuilder compile = new ProcessBuilder("javac", file.path); //.class
            compile.redirectError(Jfile.ErrorFile);

            Process compileProcess = compile.start();
            java_FileOutput(compileProcess);
            int exit_code = compileProcess.waitFor();

            if (exit_code == 0) {
                System.out.println("“compiled successfully…");
                //정상적으로 컴파일되면 에러 파일을 지운다.
                Jfile.DeleteErrFile();
            } else {

                System.out.println(file.count_err() +" compile error occured - " + Jfile.ErrorFile.getName());
            }

        } catch (Exception e) {
            System.out.println("UnExpected Error Occured while compiling your JavaFile!!");
            System.exit(-1);
        }
    }

    void run_JavaFile(JavaFile file) {
        if(file == null){
            System.out.println("We Do Not Have ANY Java File!");
            System.out.println("please upload your Java file");
            return;
        }
        try {
            File javaFile = new File(file.path);
            File parent_Dir = javaFile.getParentFile();
            String java_class = javaFile.getName().replace(".java","");

            ProcessBuilder run_class = new ProcessBuilder("java", java_class);
            run_class.directory(parent_Dir);

            Process run_Process = run_class.start();
            java_FileOutput(run_Process);

        } catch (Exception e) {
            System.out.println("run time error");
        }
    }

    void java_FileOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        {
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }
        }
    }
    void reset_JavaFile(){
        if (Jfile == null) {
            System.out.println("no file exist");
            return;
        }
        String java_FilePath = Jfile.path;
        String class_FilePath = java_FilePath.replace(".java",".class");
        File class_File = new File(class_FilePath);
        if(class_File.exists()){
            class_File.delete();
            Jfile = null;
            System.out.println("reset complete");
        }
        else{
            System.out.println("reset fail");
        }

    }
}


class JavaFile {
    String path;
    File ErrorFile;

    JavaFile(String S) {
        path = S;
    }
    public void PrintErrorFile(){
        if(ErrorFile == null){
            System.out.println("There is no ErrorFile");
            return;
        }
        if(!ErrorFile.exists()){
            System.out.println("There is no ErrorFile");
            return;
        }
        try{
            System.out.println("Type Error Filename: "+ ErrorFile.getName());
            System.out.println();
            System.out.println();
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(ErrorFile), "MS949"));
            String s;
            while((s = bufReader.readLine()) != null){
                System.out.println(s);
            }
            bufReader.close();
        }
        catch(Exception e){
            System.out.println("Error At Printing ErrorFile");
            System.out.println(e.getMessage());
            return;
        }
    }
    public void DeleteErrFile(){
        if(ErrorFile == null){
            System.out.println("There is no ErrorFile");
            return;
        }
        if(!ErrorFile.exists()){
            System.out.println("There is no ErrorFile");
            return;
        }
        try{
            ErrorFile.delete();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public int count_err() throws IOException { //에러 개수세는 함수 추가 + JFile 을 null로 초기화 하는거 reset_JavaFile로 옮겼음
        int count =0;
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(ErrorFile), "MS949"));
        String s;
        while((s = bufReader.readLine()) != null){
            if(s.contains("error:")){
                count++;
            }
        }
         return count;
    }
}