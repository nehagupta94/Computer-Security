import java.util.Random;

public class RsaAlgorithm {

    public static int gcd(int a, int h)
    {
        int temp;
        while (true)
        {
            temp = a%h;
            if (temp == 0)
                return h;
            a = h;
            h = temp;
        }
    }

    public static double eRandom(double phi){
        int rnd;
        double e;
        while(true){
            rnd = new Random().nextInt((int) phi);
            if (rnd > 1 && rnd < phi && gcd(rnd, (int) phi) == 1) {
                e = rnd;
                break;
            }
        }
        return e;
    }

    public static void main(String[] args){
        int p, q;
        try{
            p = Integer.parseInt(args[0]);
            q = Integer.parseInt(args[1]);

            double n = p*q;
            double phi = (p-1)*(q-1);

            System.out.println("n= "+n);
            System.out.println("phi(n)= "+phi);

            double e = eRandom(phi);

            System.out.println("e= "+e);

            double d = 0.0;

            while(true){
                if(e*d % phi == 1 && d>=0 && d<=n){
                    System.out.println("d= "+d);
                    break;
                }else{
                    d++;
                }
            }

        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }


}
