public class ActiveGame extends ArcadeGame{
    protected int minAge = -1;
    public ActiveGame(String Pname, String PgameID, int PpricePerPlay,int PminAge) throws InvalidGameIdExecption {
        super(Pname,PgameID,PpricePerPlay) ;

        if (PgameID.startsWith("A")) {
            this.minAge = PminAge;
        }
        else{
            throw new InvalidGameIdExecption("this gameId/serial number does not start with a A");
        }
    }

    public int getMinAge(){
        return this.minAge;
    }

    public String toString(){
        return super.toString() + " / the min age is "+ minAge;
    }
    //calculates price based on if it is peak or not 

    @Override
    public int calculatePrice(boolean peak) {

        if(peak){
            return this.pricePerPlay;

        }else{
           return (int) (this.pricePerPlay*0.8);

        }

    }

    public static void main(String[] args)
    {
        try {
            ActiveGame h = new ActiveGame("game", "ADDDDDDDDD", 1, 1);
            System.out.println("Hello, World!");
            System.out.println(h.toString());
        } catch (InvalidGameIdExecption e) {
            throw new RuntimeException(e);
        }
    }

}
