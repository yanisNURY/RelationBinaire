public class MainRelationBinaire {
    public static void afficher(boolean[][] M1){
        System.out.println("{");
        for(int i=0;i<M1.length;i++){
            if(i!=0){
                System.out.println("},");
            }
            for(int j=0;j< M1[0].length;j++){
                if(j==0){
                    System.out.print("  {");
                }
                System.out.print(M1[i][j]);
                if(j!=M1[0].length-1){
                    System.out.print(", ");
                }
            }
        }
        System.out.println("}");
        System.out.println("};");
    }
    public static void afficherTab (int []t){
        for(int i=0;i<t.length;i++){
            System.out.println("{");
            System.out.println(t[i]);
            if(i< t.length-1)
                System.out.println(",");
            System.out.println("}");
        }
    }
    public static void main(String [] args) {
        boolean [][]m1={{true,false,false},{false,true,false},{true,false,true}};
        boolean [][]m2={{true,true,true},{false,true,false},{true,true,true}};
        RelationBinaire R = new RelationBinaire(4,0.55);;

//        while (!R.estRelOrdre()) {
//            R = new RelationBinaire(4, 0.8);
//            System.out.println(R);
//            for (int i = 0; i < 3; i++) {
//                System.out.println(i + ":");
//                System.out.println(R.tabSucc[i]);
//            }
//
//            System.out.println(R.estRelOrdre());
//        }
//        System.out.println(R.hasse());
        R.afficheDivers();

    }
}
