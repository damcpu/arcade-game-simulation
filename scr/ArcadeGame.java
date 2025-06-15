abstract public class  ArcadeGame implements  Comparable<ArcadeGame> {
    protected String name;
    protected String gameID;
    protected int pricePerPlay;

    public  ArcadeGame(String Pname,String PgameID,int PpricePerPlay)throws InvalidGameIdExecption {
        boolean isvalid = true;
        // checks that the gameid only contains letters numbers and no special characters
        for(int i = 0 ;i < PgameID.length();i++){
            if(!Character.isLetterOrDigit(PgameID.charAt(i))){
                isvalid = false;
            }
        }


        if (PgameID.length() == 10 && isvalid){
            this.pricePerPlay = PpricePerPlay;
            this.name = Pname;
            this.gameID = PgameID;
        }
        else{
            throw new InvalidGameIdExecption("this is not a valid gameid/serial number either make it the right length or make sure there arent any specisl characters in the id");

        }

    }

    public String getGameID() {
        return gameID;
    }

    public int getPricePerPlay() {
        return pricePerPlay;
    }

    public String toString() {
        return "  gamename : "+name +" / gameId : "+ gameID +" / price per pay : " + pricePerPlay;
    }

    public String getName() {
        return name;
    }

    public int calculatePrice(boolean peak){
     return 0;
    }

    @Override
    public int compareTo(ArcadeGame o){
        return this.pricePerPlay - o.getPricePerPlay();
    }


    public static void main(String[] args)
    {


        System.out.println("Hello, World!");

    }
}

