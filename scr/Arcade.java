import javax.print.attribute.standard.PrinterName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.math.*;



public class Arcade {
    private String name;
    private int revenue = 0;
    private ArrayList<ArcadeGame> collectionOfGames = new ArrayList<>(10);
    private ArrayList<Customer> collectionOfCustomer = new ArrayList<>(10);

    public  Arcade(String Pname){
        name = Pname;
    }

    public void addCustomer(Customer p){
        if(!collectionOfCustomer.contains(p)){
            collectionOfCustomer.add(p);
        }else{
            System.out.println("this person is already in the collection");
        }

    }

    public void addGames(ArcadeGame o){
        if(!collectionOfGames.contains(o)){
            collectionOfGames.add(o);
        }else{
            System.out.println("this game is already in the collection");
        }


    }

    public String getName(){
        return name;
    }

    public int getRevenue(){
        return revenue;
    }

    public String toString(){
        return "name = "+name+" revenue = " + revenue;
    }

    public Customer getCustomer(String customerID)throws InvalidCustomerExeption {
        boolean isvalid = true;
        for (int i = 0; i < customerID.length(); i++) {
            if (!Character.isLetterOrDigit(customerID.charAt(i))) {
                isvalid = false;
            }
        }

        if (customerID.length() == 6 && isvalid) {
            for(int i = 0;i < collectionOfCustomer.size(); i++) {
                if(customerID.equals(collectionOfCustomer.get(i).getAccountId())){
                    return collectionOfCustomer.get(i);
                }



            }

        }else{
            throw new InvalidCustomerExeption("not valid customer id ");
        }
        System.out.println("this customer id is " + customerID + " is not in the list ");



        return null;
    }

    public ArcadeGame getArcadegame(String gameID)throws InvalidGameIdExecption{

        boolean isvalid = true;
        for (int i = 0; i < gameID.length(); i++) {
            if (!Character.isLetterOrDigit(gameID.charAt(i))) {
                isvalid = false;
            }
        }


        if (gameID.length() == 10 && isvalid) {
            for(int i = 0;i < collectionOfGames.size(); i++) {
                if(gameID.equals(collectionOfGames.get(i).getGameID())){
                    return collectionOfGames.get(i);
                }
            }

        }else{
            throw new InvalidGameIdExecption("not valid gameid id ");
        }
        System.out.println("this is not in the list ");




        return null;
    }

    public boolean proccessTransaction(String CustomerID,String gameID,boolean peak) throws InvalidCustomerExeption, InsufficientBalanceException {
        revenue += getCustomer(CustomerID).chargeAccount(getArcadegame(gameID),peak);
        return true;
    }

    public Customer findRichestCustomer(){
        // using the comparablew interface
        Collections.sort(collectionOfCustomer);

        return collectionOfCustomer.get(collectionOfCustomer.size()-1);


        
    }

    public int getMedianPrice(){
        if(!collectionOfGames.isEmpty()) {
            Collections.sort(collectionOfGames);
            int mid = (collectionOfGames.size() / 2) - 1;
            if (collectionOfGames.size() % 2 == 0) {

                return (collectionOfGames.get(mid).getPricePerPlay() + collectionOfGames.get(mid - 1).getPricePerPlay()) / 2;
            } else {
                return collectionOfGames.get(mid).getPricePerPlay();
            }
        }else{
            System.out.println("collection empty");

        }
        return -1;

    }

    public int[] countArcadeGames(){
        int[] games = {0,0,0};
        for(int i = 0;i < collectionOfGames.size();i++){
            if(collectionOfGames.get(i).getGameID().startsWith("C")){
                games[0] += 1;
            }else if(collectionOfGames.get(i).getGameID().startsWith("A") && !(collectionOfGames.get(i).getGameID().charAt(1) == 'V')){
                games[1] +=  1;
            }else if(collectionOfGames.get(i).getGameID().startsWith("A") && (collectionOfGames.get(i).getGameID().charAt(1) == 'V')){
                games[2] +=  1;
            }
        }
        return games;
    }

    static public void printCorporationJargon(){
        System.out.println("GamesCo does not take responsibility for any accidents or fits of rage that occur onthe premises");
    }


    public static void main(String[] args) throws InvalidCustomerExeption, InsufficientBalanceException {
        ActiveGame g = new ActiveGame("hardgame","Avvvvvvvvv",99,5000);
        ActiveGame t = new ActiveGame("hardgame","Avvvvvvvvv",99,5000);
        VirtualRealityGame h = new VirtualRealityGame("mediamgame","AVkkkkkkkk",100,5000,"fullBodyTracking");
        VirtualRealityGame u = new VirtualRealityGame("mediamgame","AVkkkkkkkk",100,5000,"fullBodyTracking");

        CabinetGame c = new CabinetGame("easygame","CFFFFFFFFF",101,false);
        CabinetGame r = new CabinetGame("easygame","CFFFFFFFFF",99,false);


        Customer ci = new Customer("kkkkkk","harry","NONE",5000,0);
        Customer st = new Customer("KKKKYn","staff","STAFF",5001,100);
        Customer s = new Customer("KKKKYY","student","STUDENT",5000,0);
        Arcade glibe = new Arcade("glib");
        glibe.addCustomer(ci);
        glibe.addCustomer(st);
        glibe.addCustomer(s);
        glibe.addGames(g);
        glibe.addGames(h);
        glibe.addGames(c);
        glibe.addGames(r);
        glibe.addGames(u);
        glibe.addGames(t);

        ArcadeGame c1 = glibe.getArcadegame(h.getGameID());
        System.out.println(c1);
        System.out.println(glibe.getRevenue());
        System.out.println(glibe.findRichestCustomer());
        System.out.println(glibe.getMedianPrice());
        for(int i = 0; i < glibe.countArcadeGames().length;i++){
            System.out.println(glibe.countArcadeGames()[i]);
        }

    }


}
