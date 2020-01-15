package Gestion_Transfert;

import Gestion_Compte.Compte;
import Gestion_Compte.CompteDAOIMPL;
import Gestion_Paiement.Paiement;
import Gestion_Paiement.PaiementDAOIMPL;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


    public class Server extends Thread {
        ServerSocket serverSocket;
        Socket server;
        private int port;
        CompteDAOIMPL daocompte;
//        Paiement p=getPaiement();

        public Server(int port) throws IOException {
            System.out.println("Starting payement server...");
            serverSocket = new ServerSocket(port);
        }
        Paiement pai;
        Compte compte=null;
        public Paiement getPaiement() throws IOException, ClassNotFoundException {
            return (Paiement) (new ObjectInputStream(this.server.getInputStream())).readObject();
        }


//                = daocompte.find(getPaiement().getVente().getClient().getId());





        public boolean verifySold() throws IOException {
            Scanner sc = new Scanner(this.server.getInputStream());
            double amount = Double.parseDouble(sc.nextLine());
            String message = amount <= this.compte.getSolde() ? "ok" : "ko";
            PrintStream ps = new PrintStream(this.server.getOutputStream());
            ps.println(message);
            ps.flush();
            if(amount <= this.compte.getSolde()){
                this.compte.setSolde( this.compte.getSolde() - amount );
                return true;
            } return false;
        }

        @Override
        public void run(){
            CompteDAOIMPL doo=new CompteDAOIMPL();
            try {
                server = serverSocket.accept();
//                Paiement paiement = getPaiement();
//                compte=doo.find(paiement.getVente().getClient().getId());
                compte=new Compte(1, (double) 10000);
                while (true){
                    if(verifySold()){
                        System.out.println("paiement en cours!");
                        Paiement paiemennt = getPaiement();
                        saveTransaction(paiemennt);
                        PrintStream ps = new PrintStream(this.server.getOutputStream());
                        ps.println(" ");
                        ps.flush();
                        System.out.println("Il vous reste " + compte.getSolde() + " Dh dans votre compte.");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void saveTransaction(Paiement p) {
            (new PaiementDAOIMPL()).create(p);
        }

        public void startFromOutside() throws IOException {
            Server server = new Server(1500);
            server.start();
        }

        public static void main(String[] args) throws IOException {
            Server server = new Server(1500);
            server.start();
        }
    }

