

import app.Customer;
import app.OtherService;
import app.Service;

import java.util.*;
import java.io.*;


public static void main() {

    ArrayList<Service> tmp1 = OtherService.getAvailableServices();
    for (int i=0;i<tmp1.size();i++){
        System.out.println(tmp1.get(i).parsePrint());
    }

}
