
package ase4;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client extends Thread {
public static String localhost = "127.0.0.1";
public static int port = 9809;
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_PURPLE = "\u001B[35m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_BLUE = "\u001B[34m";

    @Override
    public  long getId() {
        return super.getId(); 
    }



@Override
public void run(){
    
    try {
        Semaphore sem = new Semaphore(1);
        String data;
        String[] mots;
        int cmpt =0;
        int global =0;
        
        Socket csocket = new Socket(localhost,port);
        PrintWriter pw = new PrintWriter(csocket.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
        Scanner sc = new Scanner(System.in);
        
        String liste = br.readLine();
        String question = br.readLine();
        System.out.println(ANSI_PURPLE+"[BROKER]: " + ANSI_RESET+liste);
        System.out.println(question);
        String reponse = sc.nextLine();
        pw.println(reponse);
        
        int newport = parseInt(br.readLine());
        System.out.println(ANSI_PURPLE+"[BROKER]: " + ANSI_RESET+" Voici le nouveau port " + ANSI_PURPLE + newport + ANSI_RESET);
        
        System.out.println(ANSI_GREEN + "[SYSTEM] : " +ANSI_RESET + "Voulez vous connecter au serveur");
        System.out.println("OUI");
        System.out.println(ANSI_RED +"NON" +ANSI_RESET);
        String choix = sc.nextLine();
        
        if(choix.equalsIgnoreCase("oui")){
            br.close();
            pw.close();
            csocket = new Socket(localhost,newport);
            DataOutputStream dos = new DataOutputStream(csocket.getOutputStream());
            DataInputStream dis = new DataInputStream(csocket.getInputStream());
            String request = dis.readUTF();
            System.out.println(request);
            int nbr = sc.nextInt();
            dos.write(nbr);
            System.out.println("Veuillez attendre l'envoie du mot à rechercher.....");
            String mot = dis.readUTF();
            System.out.println(ANSI_BLUE + "[SERVER] : "+ ANSI_RESET + "Voici le mot à rechercher: " + mot);
            System.out.println(ANSI_BLUE + "[SERVER] : "+ ANSI_RESET + "Voici les fichiers fourni");
            
            for(int i=0;i<nbr;i++){
                String pathComplet = dis.readUTF();
                System.out.println(ANSI_BLUE + i + ANSI_RESET  + " "+ pathComplet);
                File file = new File(pathComplet);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while((data=reader.readLine())!=null){
                    mots = data.split(" ");
                    for(int j=0; j<mots.length;j++){
                        if(mot.equalsIgnoreCase(mots[j])){
                            cmpt+=1;
                        }
                    }
                }
                
                String response = ANSI_RED + "[CLIENT] :" +super.getId() + ANSI_RESET +"Le Nombre d'occurence dans le fichier : " + i +" est : " + cmpt;
                dos.writeUTF(response);
                
                sem.acquire();
                Server.somme=Server.somme+cmpt;
                sem.release();
            }
            String total = ANSI_RED+ "[CLIENT] : "+super.getId() +ANSI_RESET+" Le nombre globale mot  " +mot + " est : "+ Server.somme ;
            dos.writeUTF(total);
        }
        if(choix.equalsIgnoreCase("non")){
            System.err.println("Quit");
        }
        
        
    } catch (IOException ex) {
        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InterruptedException ex) {
        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    }
        
}
public static void main(String[] args){
    new Thread(new Client()).start();
}
}
