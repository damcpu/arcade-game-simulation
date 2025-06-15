import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class Simulation {
    public static Arcade initialiseArcade(String arcadeName , File gamesFile, File customerFile) throws IOException, InvalidCustomerExeption {
        Scanner scancustomer = new Scanner(customerFile);
        Scanner scangame = new Scanner(gamesFile);
        scancustomer.useDelimiter("\n");
        scangame.useDelimiter("\n");
        Arcade arcade = new Arcade(arcadeName);

        while (scancustomer.hasNext()){
            Scanner linescanc = new Scanner(scancustomer.next().stripTrailing());

            linescanc.useDelimiter("#");
            String ID = linescanc.next();
            String name = linescanc.next();
            int money = linescanc.nextInt();
            int age = linescanc.nextInt();
            String discount = null;
            if(linescanc.hasNext()) {
                discount = linescanc.next();
            }else{
                discount = null;
            }

            if(discount == null){
                arcade.addCustomer(new Customer(ID,name,"NONE",age,money));

            }else if(discount.equals("STUDENT")){
                arcade.addCustomer(new Customer(ID,name,"STUDENT",age,money));

            }else if(discount.equals("STAFF")){
                arcade.addCustomer(new Customer(ID,name,"STAFF",age,money));
            }

        }

        while(scangame.hasNext()) {
            Scanner linescang = new Scanner(scangame.next().stripTrailing());

            linescang.useDelimiter("@");
            String ID = linescang.next();
            String gamename = linescang.next();
            String typeofgame = linescang.next();
            int price = linescang.nextInt();
            if(typeofgame.equals("cabinet")){
                String reward = linescang.next();
                if(reward.equals("yes") ){
                    arcade.addGames(new CabinetGame(gamename,ID,price,true));
                }else{
                    arcade.addGames(new CabinetGame(gamename,ID,price,false));
                }
            }else if(typeofgame.equals("active")){
                int age = linescang.nextInt();
                arcade.addGames(new ActiveGame(gamename,ID,price,age));

            }else if (typeofgame.equals("virtualReality")){
                int age = linescang.nextInt();
                String equip = linescang.next();

                arcade.addGames( new VirtualRealityGame(gamename,ID,price,age,equip));

            }
        }


        return arcade;
    }

   public static void simulateFun(Arcade arcade, File transactionFile) throws FileNotFoundException, InvalidCustomerExeption, InsufficientBalanceException {
        Scanner transactionScanner = new Scanner(transactionFile);
        transactionScanner.useDelimiter("\n");
        while (transactionScanner.hasNext()){
            Scanner lineScan = new Scanner(transactionScanner.next().stripTrailing());
            lineScan.useDelimiter(",");
            String typeoftran = lineScan.next().stripTrailing();

            if (typeoftran.equals("PLAY") ){
                String IDcus = lineScan.next();
                String IDgame = lineScan.next();
                String peak = lineScan.next();
                if(peak.equals("OFF_PEAK")){
                    boolean ispeak = false;
                    try {
                        boolean success = arcade.proccessTransaction(IDcus, IDgame, ispeak);
                        if(success){
                            System.out.println("this transaction was successfull at off peaktime and the revenue has increased the game played was " + arcade.getArcadegame(IDgame).getName()
                                    +"the base price of the game is " + arcade.getArcadegame(IDgame).calculatePrice(ispeak));
                        }
                    }catch (InvalidCustomerExeption e){
                        System.out.println(e.getMessage());
                    }catch (InsufficientBalanceException e){
                        System.out.println(e.getMessage());
                    }catch (InvalidGameIdExecption e){
                        System.out.println(e.getMessage());
                    }catch (AgeLimitException e){
                        System.out.println(e.getMessage());
                    }


                }else{
                    try {
                        boolean ispeak = true;
                        boolean success = arcade.proccessTransaction(IDcus, IDgame, ispeak);
                        if (success) {
                            System.out.println("this transaction was successfull at  peaktime and the revenue has increased the game played was " + arcade.getArcadegame(IDgame).getName()
                                    + "the base price of the game is " + arcade.getArcadegame(IDgame).calculatePrice(ispeak));
                        }
                    }catch (InvalidCustomerExeption e){
                        System.out.println(e.getMessage());
                    }catch (InsufficientBalanceException e){
                        System.out.println("over draft finished");
                    }catch ( InvalidGameIdExecption e){
                        System.out.println(e.getMessage());
                    }

                }
            }else if(typeoftran.equals("ADD_FUNDS") ){
                try {
                    String idcus = lineScan.next();
                    int money = lineScan.nextInt();
                    arcade.getCustomer(idcus).addFunds(money);
                    System.out.println("money added to account " + money + " the name of the owner of the account is " + arcade.getCustomer(idcus).getName() + " and the account id is " + idcus);
                }catch (InvalidCustomerExeption e){
                    System.out.println(e.getMessage());
                }catch(NullPointerException e){
                    System.out.println(e.getMessage());
                }
            }else if(typeoftran.equals("NEW_CUSTOMER")){

                    String ID = lineScan.next();
                    String name = lineScan.next();
                    String discount = lineScan.next();
                    int money = lineScan.nextInt();
                    int age;
                    if (lineScan.hasNext()) {
                        age = lineScan.nextInt();
                    } else {
                        age = 0;
                    }


                if(discount.equals("STUDENT")){
                    try {
                        arcade.addCustomer(new Customer(ID, name, discount, age, money));
                        System.out.println(arcade.getCustomer(ID).toString() + " was added to the arcade");
                    }catch (InvalidCustomerExeption e){
                        System.out.println(e.getMessage());
                    }

                }else if(discount.equals("STAFF")){
                    try {
                        arcade.addCustomer(new Customer(ID, name, discount, age, money));
                        System.out.println(arcade.getCustomer(ID).toString() + " was added to the arcade");
                    }catch (InvalidCustomerExeption e){
                        System.out.println(e.getMessage());
                    }

                }else {
                    try {
                        age = money;
                        money = Integer.parseInt(discount);
                        arcade.addCustomer(new Customer(ID, name, "NONE", money, Integer.valueOf(discount)));
                        System.out.println(arcade.getCustomer(ID).toString() + " was added to the arcade");
                    }catch (InvalidCustomerExeption e){
                        System.out.println(e.getMessage());
                    }

                }



            }
        }
        System.out.println(arcade.toString());
        System.out.println(arcade.findRichestCustomer().toString());
        System.out.println("the median price of game is " + arcade.getMedianPrice());
        int[] games = arcade.countArcadeGames();
        System.out.println("cabinet games : " + games[0] );
       System.out.println("active games : " + games[1] );
       System.out.println("virtual reality games : " + games[2] );
       Arcade.printCorporationJargon();

   }

    public static void main(String[] args) throws InvalidCustomerExeption, IOException, FileNotFoundException, InsufficientBalanceException {
        System.out.println("Hello, World!");
        File filecus = new File("customers.txt");
        File filegame = new File("games.txt");
        File filetran =  new File("transactions.txt");
        simulateFun(initialiseArcade("huh",filegame,filecus),filetran) ;




    }
}