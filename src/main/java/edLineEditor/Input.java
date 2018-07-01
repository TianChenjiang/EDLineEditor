package edLineEditor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Input {

    public static Scanner content_input = new Scanner(System.in);

    public static Input instance = new Input();

    private Input(){}

    public static Input getInstance(){
        return instance;
    }

    String input = "";

    public String inputMode(){
        String result = "";

        while((input = content_input.nextLine()) != null) {
            if (input.equals(".")) {
                break;
            }
            result += input + '\n';
        }
        return result;
    }
}
