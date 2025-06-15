public class Customer implements Comparable<Customer> {
    private String accountId;
    private String name;
    private int age;
    private int accountBalance;
    private Discount discount;

    public Customer(String pID,String Pname,String Pdiscount,int Page)throws InvalidCustomerExeption{
        boolean isvalid = true;
        // checks that the gameid only contains letters numbers and no special characters
        for(int i = 0 ;i < pID.length();i++){
            if(!Character.isLetterOrDigit(pID.charAt(i))){
                isvalid = false;
            }
        }
        if(isvalid == true && pID.length() == 6) {
            this.accountBalance = 0;
            this.name = Pname;
            this.discount = Discount.valueOf(Pdiscount);
            this.accountId = pID;
            this.age = Page;
        }else{
            throw new InvalidCustomerExeption("the id is  not the right length/there are special characters in the ID ");
        }
    }
    public Customer(String pID,String Pname,String Pdiscount,int Page,int PaccountBalance)throws InvalidCustomerExeption{
        this(pID,Pname,Pdiscount,Page);
        if(PaccountBalance >= 0 ) {
            this.accountBalance = PaccountBalance;
        }else{
            throw new InvalidCustomerExeption("this balance is below 0 ");
        }

    }

    public String getAccountId() {
        return accountId;
    }

    public String getName(){
        return this.name;
    }

    public Discount getDiscount() {
        return discount;
    }

    public int getAge() {
        return age;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public String toString(){
        return "name : " + name +" / accountID : " + accountId +" / account balance : " + accountBalance + " / age : " + age + " / Discount :" + discount;
    }

    public int addFunds(int amount ){
        if(amount < 0){
            System.out.println("it is not allowed for amount to be minus");
        }else{
            this.accountBalance += amount;
            return accountBalance;
        }
        return accountBalance;
    }

    public int  chargeAccount(ArcadeGame o,boolean ispeak)throws InsufficientBalanceException,AgeLimitException {


        if(discount == Discount.NONE) {
            if (o.gameID.startsWith("C")) {
                CabinetGame m = (CabinetGame) o;
                if (m.calculatePrice(ispeak) <= accountBalance) {
                    accountBalance = accountBalance - m.calculatePrice(ispeak);
                    return m.calculatePrice(ispeak);

                } else {
                    throw new InsufficientBalanceException("this account hasn't got the funds for this transaction");
                }
            }
            if(o.gameID.startsWith("AV")){
                VirtualRealityGame m = (VirtualRealityGame) o;
                ActiveGame u = (ActiveGame) o;
                if (u.calculatePrice(ispeak) <= accountBalance && u.getMinAge() <= age) {
                    accountBalance = accountBalance - u.calculatePrice(ispeak);
                    return u.calculatePrice(ispeak);
                } else if (u.getMinAge() > age) {
                    throw new AgeLimitException("you are to young to play this game ");
                } else if (u.getPricePerPlay() > accountBalance) {
                    throw new InsufficientBalanceException("there are not enough funds in your bank");
                }

            }
            if (o.gameID.startsWith("A")) {
                ActiveGame u = (ActiveGame) o;
                if (u.calculatePrice(ispeak) <= accountBalance && u.getMinAge() <= age) {
                    accountBalance = accountBalance - u.calculatePrice(ispeak);
                    return u.calculatePrice(ispeak);
                } else if (u.getMinAge() > age) {
                    throw new AgeLimitException("you are to young to play this game ");
                } else if (u.calculatePrice(ispeak) > accountBalance) {
                    throw new InsufficientBalanceException("there are not enough funds in your bank");
                }

            }
        }
        if(discount == Discount.STUDENT) {

            if (o.gameID.startsWith("C")) {
                CabinetGame m = (CabinetGame) o;
                if ((accountBalance - (int) ((double) m.calculatePrice(ispeak) * 0.95)) >= -500) {
                    accountBalance = accountBalance - (int) ((double) m.calculatePrice(ispeak) * 0.95);
                    return (int) ((double) m.calculatePrice(ispeak) * 0.95);

                } else {
                    throw new InsufficientBalanceException("this account hasn't got the funds for this transfer");
                }
            }
            if (o.gameID.startsWith("AV")) {
                VirtualRealityGame m = (VirtualRealityGame) o;

                if ((accountBalance - (int) ((double) m.calculatePrice(ispeak) * 0.95)) >= -500 && m.getMinAge() <= age) {
                    accountBalance = accountBalance - (int) ((double) m.calculatePrice(ispeak) * 0.95);
                    return (int) ((double) m.calculatePrice(ispeak) * 0.95);
                } else if (m.getMinAge() > age) {
                    throw new AgeLimitException("you are to young to play this game ");
                } else if ((accountBalance - (int) ((double) m.calculatePrice(ispeak) * 0.95)) < -500) {
                    throw new InsufficientBalanceException("your over draft is finished mate");
                }

            }
            if (o.gameID.startsWith("A")) {
                ActiveGame u = (ActiveGame) o;
                if ((accountBalance - (int) ((double) u.calculatePrice(ispeak) * 0.95)) >= -500 && (u.getMinAge() <= age)) {
                    accountBalance = accountBalance - (int) ((double) u.calculatePrice(ispeak) * 0.95);
                    return (int) ((double) u.calculatePrice(ispeak) * 0.95);
                } else if (u.getMinAge() > age) {
                    throw new AgeLimitException("you are to young to play this game ");
                } else if ((accountBalance - (int) ((double) u.calculatePrice(ispeak) * 0.95)) < -500) {
                    throw new InsufficientBalanceException("there are not enough funds in your bank");
                }

            }
        }
        if(discount == Discount.STAFF){

                if (o.gameID.startsWith("C")) {
                    CabinetGame m = (CabinetGame) o;
                    if (((int)((double)m.calculatePrice(ispeak)*0.90)) <= accountBalance) {
                        accountBalance = accountBalance - (int)(((double)m.calculatePrice(ispeak))*0.90);
                        return (int)((double)m.calculatePrice(ispeak)*0.90);

                    } else {
                        throw new InsufficientBalanceException("this account hasn't got the funds for this transfer");
                    }
                }
                if(o.gameID.startsWith("AV")){
                    VirtualRealityGame m = (VirtualRealityGame) o;
                    if (((int) ((double) m.calculatePrice(ispeak) * 0.90)) <= accountBalance  && m.getMinAge() <= age) {
                        accountBalance = accountBalance - (int) ((double) m.calculatePrice(ispeak) * 0.90);
                        return ((int) ((double) m.calculatePrice(ispeak) * 0.90));
                    }else if (m.getMinAge() > age) {
                        throw new AgeLimitException("you are to young to play this game ");
                    } else if ((int)((double)m.calculatePrice(ispeak)*0.90) > accountBalance) {
                        throw new InsufficientBalanceException("there are not enough funds in your bank");
                    }

                }
                if (o.gameID.startsWith("A")) {
                    ActiveGame u = (ActiveGame) o;
                    if (((int)((double)u.calculatePrice(ispeak)*0.90) <= accountBalance && u.getMinAge() <= age)) {
                        accountBalance =  accountBalance -  (int)((double)u.calculatePrice(ispeak)*0.90);
                        return (int)((double)u.calculatePrice(ispeak)*0.90);
                    } else if (u.getMinAge() > age) {
                        throw new AgeLimitException("you are to young to play this game ");
                    } else if ((int)((double)u.calculatePrice(ispeak)*0.90) > accountBalance) {
                        throw new InsufficientBalanceException("there are not enough funds in your bank");
                    }

                }
        }
    return 0;


    }

    @Override
    public int compareTo(Customer o){
        return this.accountBalance - o.getAccountBalance();
    }

    public static void main(String[] args) throws InvalidCustomerExeption, InsufficientBalanceException,AgeLimitException {
        ActiveGame g = new ActiveGame("hardgame","Avvvvvvvvv",100,5000);
        VirtualRealityGame h = new VirtualRealityGame("mediamgame","AVkkkkkkkk",100,5000,"fullBodyTracking");

        CabinetGame c = new CabinetGame("easygame","CFFFFFFFFF",100,true);


        Customer ci = new Customer("kkkkkk","harry","NONE",5000,100);
        Customer st = new Customer("KKKKYn","staff","STAFF",5001,100);
        Customer s = new Customer("KKKKYY","student","STUDENT",5000,0);


        System.out.println(ci.chargeAccount((ArcadeGame) h,true));
        System.out.println(ci.getAccountBalance());
        System.out.println(st.chargeAccount((ArcadeGame) h,true));
        System.out.println(st.getAccountBalance());
        System.out.println(s.chargeAccount((ArcadeGame) h,true));
        System.out.println(s.getAccountBalance());




    }


}

