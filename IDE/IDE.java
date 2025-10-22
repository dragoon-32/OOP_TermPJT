import java.util.*;

//main 객체
public class IDE {
    public static void main(String args[]){
        new CLI();
    }
}

//GUI 개발할 때를 대비하여 CLI 부분을 따로 떼놓음.
//기능을 추가하려면 loop()의 switch문에서 동작 코드를, print_menu()에서 기능및 번호 설명 코드를 추가할것
class CLI{
    JavaFile Jfile;
    //Constructor
    CLI(){
        loop();
    }
    //CLI loop
    void loop(){
        Scanner scan = new Scanner(System.in);
        while(true){
            print_menu();
            int action = scan.nextInt();

            //기능을 추가하고 싶으면 여기에 case를 추가해주세요
            switch(action){
                case 1:
                    Jfile = upload_file(scan);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    System.out.println("GoodBye");
                    return;
                default:
                    System.out.println("We Do Not have your option.");
                    System.out.println("Please Try Again");
                    break;
            }
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
    JavaFile upload_file(Scanner scan){
        String path;
        System.out.print("Java File Path:");
        path = scan.nextLine();
        return new JavaFile(path);
    }
}

//자바 파일 객체
class JavaFile{
    String path;

    JavaFile(String S){
        path = S;
    }
    JavaFile(){}
}