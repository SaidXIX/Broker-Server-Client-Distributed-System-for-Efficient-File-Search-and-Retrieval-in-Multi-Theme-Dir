
package ase4;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Broker{
public static String [] NomDuDossier;
public static int port = 9809;
public static String localhost = "127.0.0.1";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE = "\u001B[35m";
public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_RED = "\u001B[31m";


public static int search(String[] arr, String key){
    for(int i=0; i<arr.length;i++){
        if(arr[i].equalsIgnoreCase(key)){
            return i;
        }
    }
    return -1;
}

public static void main(String[] args) throws IOException{
    ServerSocket listener = new ServerSocket(port);
    Random random = new Random();
    Scanner sc = new Scanner(System.in);
    
   
    System.out.println(ANSI_PURPLE+"[BROKER]: " + ANSI_RESET+ " Veuillez entrer le path du dossier");
    String path = sc.nextLine();
    File dossier = new File(path);
    NomDuDossier = dossier.list();
    Server[] servers = new Server[NomDuDossier.length];
    System.out.println(ANSI_PURPLE+"[BROKER]: " + ANSI_RESET+ " Voici les sous dossiers");
    
    for(int j=0; j<NomDuDossier.length; j++){
        
        System.out.println(ANSI_PURPLE + j + ANSI_RESET+" " + NomDuDossier[j]);
        
    }

    while(true){
        System.out.println(ANSI_PURPLE+"[BROKER]: " + ANSI_RED+ " En attente de la connexion");
        Socket bsocket=listener.accept();
        PrintWriter pw = new PrintWriter(bsocket.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(bsocket.getInputStream()));
        System.out.println(ANSI_PURPLE + "[BROKER]: "+ANSI_BLUE + " Connexion établie");
        String question = " Quel dossier voulez vous traiter";
        pw.println(question);
        pw.println(Arrays.toString(NomDuDossier));
        String reponse = br.readLine();
        int result = search(NomDuDossier, reponse);
        System.out.println(result);
        if(result==-1){
            System.err.println("Le dossier que le client a choisi n'éxiste pas");
            break;
        }
        else{
            System.out.println(ANSI_PURPLE + "[BROKER]: "+ANSI_RESET + " le dossier choisi est : " + reponse);
            int x = random.nextInt(9000);
            
            Server s = new Server(path,NomDuDossier[result],x);
            s.start();
            pw.println(s.getPort());
            
        }
    }
}
}
