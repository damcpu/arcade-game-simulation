
public class CabinetGame extends ArcadeGame{
    private boolean givesreward;



    public CabinetGame(String Pname, String PgameID, int PpricePerPlay,boolean Preward) throws InvalidGameIdExecption {
        super(Pname,PgameID,PpricePerPlay) ;

        if (PgameID.toString().startsWith("C")) {
            this.givesreward = Preward;

        }
        else{
            throw new InvalidGameIdExecption("this gameId/serial number does not start with a C");
        }
    }

    public boolean isReward() {
        return givesreward;
    }
    public String toString(){
        return super.toString() + " / this game gives reward ? "+ givesreward +" / game : "+ name +" / gameId : "+ gameID +" /price per pay : " + pricePerPlay  ;
    }

    @Override
    public int calculatePrice(boolean peak) {

        if(peak){
            return this.pricePerPlay;

        }else{
            if(this.givesreward){
                return (int) (this.pricePerPlay*0.80);

            }else{
                 return(int) (this.pricePerPlay*0.5);
            }

        }
    }

    public static void main(String[] args)
    {
        try {
            CabinetGame h = new CabinetGame("game", "CDDDDDDDDD", 1, true);
            System.out.println("Hello, World!");
            System.out.println(h.toString());
        } catch (InvalidGameIdExecption e) {
            throw new RuntimeException(e);
        }
    }
}


