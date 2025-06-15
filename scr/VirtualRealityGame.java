import java.util.ArrayList;

public class VirtualRealityGame extends ActiveGame{
    private Gear gear;


    public  VirtualRealityGame(String Pname, String PgameID, int PpricePerPlay,int PminAge,String g) throws InvalidGameIdExecption{
        super(Pname,PgameID,PpricePerPlay,PminAge);



        if(PgameID.startsWith("AV")  ){
            this.gameID = PgameID;

            gear = Gear.valueOf(g);




        }else {
            throw new InvalidGameIdExecption("this serial number should start with a AV");

        }

    }
    public  VirtualRealityGame(String Pname, String PgameID, int PpricePerPlay,int PminAge) throws InvalidGameIdExecption{
        super(Pname,PgameID,PpricePerPlay,PminAge);
        if(PgameID.startsWith("AV")  ){

            this.gameID = PgameID;
        }else {
            throw new InvalidGameIdExecption("this serial number should start with a AV");
        }

    }

    public Gear getgear(){
        return this.gear;
    }





    public String toString(){

        return super.toString() + " / the gear required for this game is " +  gear  ;
    }

    @Override
    public int calculatePrice(boolean peak) {
        if (peak) {
            return pricePerPlay;
        } else {
            if (gear == Gear.headsetOnly) {
                return (int) (pricePerPlay * 0.90);

            }else if(gear  == Gear.fullBodyTracking){
                return pricePerPlay;
            } else if (gear == Gear.headsetAndController) {
                return (int) (pricePerPlay * 0.95);
            }
        }
        return 0;
    }

    public static void main(String[] args)
    {
        try {
            VirtualRealityGame h = new VirtualRealityGame("game", "AVDDDDDDDD", 1, 1,"CONTROLLER");
            VirtualRealityGame k = new VirtualRealityGame("game", "AVDDDDDDDD", 1, 1);
            System.out.println("Hello, World!");

            System.out.println(h.toString());
        } catch (InvalidGameIdExecption e) {
            throw new RuntimeException(e);
        }
    }
}


