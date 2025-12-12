
import app.Customer;

import java.util.*;
import java.io.*;


public static void main() {
    try{
        File data = new File("src/app/DataBase/Customers.txt");
        Scanner dataS = new Scanner(data);
        String tmp=dataS.nextLine();
        String[] arr= tmp.split(",");
        ArrayList<Customer> arr1=new ArrayList<>();
        for(int i=0;i< arr.length;i++){
            String[] tmp2 = arr[i].split("/");
            arr1.add(new Customer(  Integer.parseInt(tmp2[0]), tmp2[1],tmp2[2], Integer.parseInt(tmp2[3]) )  );
            System.out.println(arr1.get(i).parsePrint());
        }

    } catch (FileNotFoundException e) {
        System.out.println("IDC");
    }

}
