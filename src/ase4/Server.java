
package ase4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread{
    public static int somme;
    private   final  String dossier;
    private final  String NomDuDossier;
    private  final int port;
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static ServerSocket server;
    public static String [] NomDuFichier;
    
    public Server(String dossier, String NomDuDossier, int port) throws IOException {
        this.dossier=dossier;
        this.NomDuDossier=NomDuDossier;
        this.port=port;
        server = new ServerSocket(port);
    }
    

    public String getNomDuDossier() {
        return NomDuDossier;
    }

    public int getPort() {
        return port;
    }
    

  @Override
    public void run(){
      try{
            System.out.println(ANSI_BLUE+"[SERVER]: " + ANSI_RESET +"pour le dossier " + ANSI_BLUE + NomDuDossier + ANSI_RESET + " est créé / le port est : " +ANSI_BLUE +  port + ANSI_RESET);
            File path = new File(dossier + "\\" + NomDuDossier);
            NomDuFichier = path.list();
            System.out.println(ANSI_BLUE+"[SERVER]: " + ANSI_RESET );
            System.out.println("Les fichiers de ce dossier sont : ");
            for(int i=0; i<NomDuFichier.length; i++){
                System.out.println(NomDuFichier[i]);
            }
        while(true){
            System.out.println(ANSI_BLUE+"[SERVER]: " + port + ANSI_RESET +"  est en atttente de la connexion du client ");
            Socket ssocket = server.accept();
            System.out.println(ANSI_BLUE+"[SERVER]: " + port + "  " + NomDuDossier + ANSI_RESET +"  est en connexion avec le client");
            DataOutputStream dos = new DataOutputStream(ssocket.getOutputStream());
            DataInputStream dis = new DataInputStream(ssocket.getInputStream());
            String question = ANSI_BLUE + "[SERVER] : " +NomDuDossier + ANSI_RESET+"  Combien de fichier pouvez vous traiter?";
            dos.writeUTF(question);
            int nbr = dis.read();
            System.out.println(ANSI_RED + "[CLIENT]: "+ANSI_RESET + nbr);
            System.out.println(ANSI_BLUE + "[SERVER] : " +NomDuDossier + ANSI_RESET+"  Veuillez saisir le mot à rechercher");
            Scanner sc = new Scanner(System.in);
            String mot = sc.nextLine();
            System.out.println(ANSI_BLUE + "[SERVER] : " +NomDuDossier + ANSI_RESET+ " Le mot " +mot +" à recherché est envoyé vers le client");
            dos.writeUTF(mot);
            
            for(int j=0; j<nbr;j++){
                String pathComplet = path +"\\"+NomDuFichier[j];
                dos.writeUTF(pathComplet);
                String response=dis.readUTF();
                System.out.println(response);
            }
            
            String total = dis.readUTF();
            System.out.println(total);
        }
      }catch(Exception e){
          e.printStackTrace();
       }
    }
public static void main(String[] args){
  
}
}
