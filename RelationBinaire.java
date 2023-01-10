import javax.swing.*;
import java.util.*;
import java.lang.*;
public class RelationBinaire {

    // attributs

    private int n;           // n > 0, E = {0,1,2, ..., n-1}
    public boolean[][] matAdj;  // matrice d'adjacence de R
    public int m;           // cardinal de R
    public EE[] tabSucc;    // tableau des ensembles de successeurs

    // outils
    public static boolean [][] copieMat (boolean t [][]){
        boolean[][]tCop=new boolean[t.length][t[0].length];
        for(int i=0;i<t.length;i++){
            for (int j=0;j<t[0].length;j++){
                tCop[i][j]=t[i][j];
            }
        }
        return tCop;
    }
    // constructeurs

    /** pré-requis : nb > 0
     action : construit la relation binaire vide dans l'ensemble {0,1,2, ..., nb-1}
     */
    public RelationBinaire(int nb){
        this.n= nb;
        this.matAdj=new boolean[nb][nb];
        this.m=0;
        this.tabSucc=new EE[nb];
    }

    //______________________________________________


    /** pré-requis : nb > 0 et 0 <= p <= 1
     action : construit une relation binaire aléatoire dans l'ensemble {0,1,2, ..., nb-1}
     à laquelle chaque couple a la probabilité p d'appartenir.
     En particulier, construit la relation vide si p = 0 et la relation pleine si p = 1.
     Indication : Math.random() retourne un réel de type double aléatoire de l'intervalle [0,1[
     */
    public RelationBinaire(int nb,double p){
        this.n= nb;
        this.matAdj= new boolean[nb][nb];
        double prob=0;
        double slec=0;
        int card=0;
        this.tabSucc=new EE[nb];
        for (int i=0;i<nb;i++){
            this.tabSucc[i]=new EE(nb);
        }
        for(int i=0;i<nb;i++){
            for (int j=0;j<nb;j++){
                prob=100*p;
                slec=(double)(Math.random()*100);
                if(slec<=prob){
                    matAdj[i][j]=true;
                    this.tabSucc[i].ajoutElt(j);
                    card++;
                }
                else {
                    matAdj[i][j]=false;
                }
            }
        }
        this.m=card;
    }

    //______________________________________________



    /** pré-requis : nb > 0 et 1 <= choix <= 3
     action : construit la relation binaire dans l'ensemble {0,1,2, ..., nb-1}
     '=' si egal a la valeur vrai et '<=' sinon
     */
    public RelationBinaire(int nb, boolean egal){
        this.n= nb;
        this.matAdj= new boolean[nb][nb];
        this.tabSucc=new EE[nb];
        int card=0;
        for (int i=0;i<nb;i++){
            this.tabSucc[i]=new EE(nb);
        }
        if(egal){
            for (int i=0;i<nb;i++){
                for (int j=0;j<nb;j++){
                    if(i==j) {
                        matAdj[i][j] = true;
                        this.tabSucc[i].ajoutElt(j);
                        card++;
                    }
                    else matAdj[i][j]=false;
                }
            }
        }
        else{
            for (int i=0;i<nb;i++){
                for (int j=0;j<nb;j++){
                    if(i<=j) {
                        matAdj[i][j] = true;
                        this.tabSucc[i].ajoutElt(j);
                        card++;
                    }
                    else matAdj[i][j]=false;
                }
            }
        }
        this.m=card;
    }

//    //______________________________________________



    /** pré-requis : mat est une matrice carrée de dimension > 0
     action : construit une relation binaire dont la matrice d'adjacence
     est une copie de mat
     */
    public RelationBinaire(boolean[][] mat){
        this.n=mat.length;
        this.matAdj=copieMat(mat);
        this.tabSucc=new EE[mat.length];
        int card=0;
        for (int i=0;i< mat.length;i++){
            for (int j=0;j< mat.length;j++){
                if(mat[i][j]) {
                    this.tabSucc[i]=new EE(mat.length);
                    this.tabSucc[i].ajoutElt(j);
                    card++;
                }
            }
        }
        this.m=card;

    }


//    //______________________________________________


    /** pré-requis : tab.length > 0 et pour tout i, les éléments de tab[i]
     sont compris entre 0 et tab.length-1
     action : construit une relation binaire dont le tableau des ensembles de successeurs
     est une copie de tab
     */
    public RelationBinaire(EE[] tab){
        this.tabSucc=new EE[tab.length];
        for(int i=0;i<tab.length;i++){
            this.tabSucc[i]=new EE(tab[i]);
        }
        int card=0;
        this.n=tab.length;
        this.matAdj=new boolean[tab.length][tab.length];
        for(int i=0;i<tab.length;i++){
            for (int j=0;j<tab.length;j++){
                if(tabSucc[i].contient(j)){
                    this.matAdj[i][j]=true;
                    card++;
                }
                else this.matAdj[i][j]=false;
            }
        }
        this.m=card;
    }

//    //______________________________________________


    /** pré-requis : aucun
     action : construit une copie de r
     */
    public RelationBinaire(RelationBinaire r){
        this.n=r.n;
        this.m=r.m;
        this.matAdj=copieMat(r.matAdj);
        this.tabSucc=new EE[r.n];
        for(int i=0;i< r.n;i++){
            this.tabSucc[i]=new EE(r.tabSucc[i]);
        }
    }


//    //______________________________________________


    // méthodes


    /** pré-req/uis : aucun
     résultat : une chaîne de caractères permettant d'afficher this par sa matrice d'adjacence
     contenant des '0' et des '1' (plus lisibles que des 'V' et des 'F') et sa définition
     en extension (ensemble de couples {(..,..),(..,..), ...})
     */
    public String toString(){
        String s="";
        s+="\n Matrice d'adjacence :\n";
        for(int i=0;i<this.n;i++){
            for(int j=0;j<this.n;j++){
                if (this.matAdj[i][j]){
                    s+='1';
                }
                else s+='0';
                s+=" ";
            }
            s+="\n";
        }
        s+="\n----------------\n";
        s+="\n ensemble des couples :\n";
        s+="{";
        for (int i=0;i<this.n;i++){
            for(int j=0;j<this.n;j++){
                if(this.matAdj[i][j]){
                    s+="("+i+","+j+")";
                    if(i<this.n-1) {
                        s += ",";
                    }
                }
            }
        }
        s+="}";
        return s;
    }

//    //______________________________________________
//
//
//
//    // A) Logique et calcul matriciel
//    //-------------------------------


    /** pré-requis : m1 et m2 sont des matrices carrées de même dimension et 1 <= numConnecteur <= 5
     résultat : la matrice obtenue en appliquant terme à terme le  connecteur de numéro numConnecteur
     sur m1 si numConnecteur  = 3 (dans ce cas le paramètre m2 n'est pas utilisé),
     et sur m1 et m2 dans cet ordre sinon, sachant que les connecteurs "ou","et","non",
     "implique"et "equivalent" sont numérotés de 1 à 5 dans cet ordre
     */

    public static boolean[][] opBool(boolean[][] m1, boolean[][] m2, int numConnecteur){
        boolean m[][]=new boolean[m1.length][m1.length];
        if(numConnecteur==1){
            for (int i=0;i< m1.length;i++){
                for (int j=0;j< m1.length;j++){
                    if(m1[i][j]||m2[i][j])
                    m[i][j]=true;
                    else m[i][j]=false;
                }
            }
        }
        else if(numConnecteur==2){
            for (int i=0;i< m1.length;i++){
                for (int j=0;j< m1.length;j++){
                    if(m1[i][j]&&m2[i][j])
                        m[i][j]=true;
                    else m[i][j]=false;
                }
            }
        }
        else if(numConnecteur==3){
            for (int i=0;i< m1.length;i++){
                for (int j=0;j< m1.length;j++){
                    if(m1[i][j])
                        m[i][j]=false;
                    else m[i][j]=true;
                }
            }
        }
        else if(numConnecteur==4){
            for (int i=0;i< m1.length;i++){
                for (int j=0;j< m1.length;j++){
                    if(!m1[i][j]||m2[i][j])
                        m[i][j]=true;
                    else m[i][j]=false;
                }
            }
        }
        else{
            for (int i=0;i< m1.length;i++){
                for (int j=0;j< m1.length;j++){
                    if(m1[i][j]==m2[i][j])
                        m[i][j]=true;
                    else m[i][j]=false;
                }
            }
        }
        return m;
    }

//    //______________________________________________


    /** pré-requis : m1 et m2 sont des matrices carrées de même dimension
     résultat : le produit matriciel de m1 et m2
     */
    public static boolean[][] produit(boolean[][] m1, boolean[][] m2){
        boolean m[][]=new boolean[m1.length][m1.length];
        for (int i=0;i< m1.length;i++){
            for (int j=0;j< m1.length;j++){
                for (int k=0;k<m1.length; k++){
                    m[i][j]=m[i][j]||(m1[i][k] && m2[k][j]);
                }
            }
        }
        return m;
    }

//    //______________________________________________


    /** pré-requis : m est une matrice carrée
     résultat : la matrice transposée de m
     */
    public static boolean[][] transposee(boolean[][] m){
        boolean[][]t=new boolean[m.length][m.length];
        for (int i=0;i< m.length;i++){
            for (int j=0;j< m.length;j++){
                t[j][i]=m[i][j];
            }
        }
        return t;
    }

//    //______________________________________________
//
//
//    // B) Théorie des ensembles
//    //--------------------------


    /** pré-requis : aucun
     résultat : vrai ssi this est vide
     */
    public boolean estVide(){
        if (this.m==0)
            return true;
        else return false;
    }

//    //______________________________________________


    /** pré-requis : aucun
     résultat : vrai ssi this est pleinee (contient tous les couples d'éléments de E)
     */
    public boolean estPleine(){
        if(this.m==this.n*this.n)
            return true;
        else return false;
    }

//    //______________________________________________

    /** pré-requis : aucun
     résultat : vrai ssi (x,y) appartient à this
     */
    public boolean appartient(int x, int y){
        if(this.matAdj[x][y])
            return true;
        else return false;
    }

//    //______________________________________________


    /** pré-requis : 0 <= x < this.n et 0 <= y < this.n
     résultat : ajoute (x,y) à this s'il n'y est pas déjà
     */
    public void ajouteCouple(int x, int y){
        if (!this.matAdj[x][y]){
            this.matAdj[x][y]=true;
        }
        this.m++;
    }

//    //______________________________________________


    /** pré-requis : 0 <= x < this.n et 0 <= y < this.n
     résultat : enlève (x,y) de this s'il y est
     */
    public void enleveCouple(int x, int y){
        if (this.matAdj[x][y]){
            this.matAdj[x][y]=false;
        }
        this.m--;
    }

//    //______________________________________________


    /** pré-requis : aucun
     résultat : une nouvelle relation binaire obtenue à partir de this en ajoutant
     les couples de la forme  (x,x) qui n'y sont pas déjà
     */
    public RelationBinaire avecBoucles(){
        boolean m [][]= copieMat(this.matAdj);
        for (int i=0;i<this.n;i++){
            m[i][i]=true;
        }
        RelationBinaire R=new RelationBinaire(m);
        return R;
    }


//        //______________________________________________


        /** pré-requis : aucun
         résultat : une nouvelle relation binaire obtenue à partir de this en enlèvant
         les couples de la forme  (x,x) qui y sont
         */
        public RelationBinaire sansBoucles(){
            boolean m [][]= copieMat(this.matAdj);
            for (int i=0;i<this.n;i++){
                m[i][i]=false;
            }
            RelationBinaire R=new RelationBinaire(m);
            return R;
        }

//        //______________________________________________



        /** pré-requis : this.n = r.n
         résultat : l'union de this et r
         */
        public RelationBinaire union(RelationBinaire r){
            boolean [][] m=new boolean[this.n][this.n];
            for (int i=0;i<this.n;i++){
                for (int j=0;j<this.n;j++){
                    if(this.matAdj[i][j]||r.matAdj[i][j])
                        m[i][j]=true;
                }
            }
            RelationBinaire R=new RelationBinaire(m);
            return R;
        }

//        //______________________________________________


        /** pré-requis : this.n = r.n
         résultat : l'intersection de this et r
         */
        public RelationBinaire intersection(RelationBinaire r){
            boolean [][] m=new boolean[this.n][this.n];
            for (int i=0;i<this.n;i++){
                for (int j=0;j<this.n;j++){
                    if(this.matAdj[i][j]&&r.matAdj[i][j])
                        m[i][j]=true;
                }
            }
            RelationBinaire R=new RelationBinaire(m);
            return R;

        }

//        //______________________________________________



        /** pré-requis : aucun
         résultat : la relation complémentaire de this
         */
        public RelationBinaire complementaire(){
            boolean [][] m=new boolean[this.n][this.n];
            for (int i=0;i<this.n;i++){
                for (int j=0;j<this.n;j++){
                    if(!this.matAdj[i][j])
                        m[i][j]=true;
                }
            }
            RelationBinaire R=new RelationBinaire(m);
            return R;
        }

//        //______________________________________________


        /** pré-requis : this.n = r.n
         résultat : la différence de this et r
         */
        public RelationBinaire difference(RelationBinaire r){
            boolean [][] m=new boolean[this.n][this.n];
            for (int i=0;i<this.n;i++){
                for (int j=0;j<this.n;j++){
                    if(this.matAdj[i][j]!=r.matAdj[i][j])
                        m[i][j]=true;
                }
            }
            RelationBinaire R=new RelationBinaire(m);
            return R;
        }

//        //______________________________________________


        /** pré-requis : this.n = r.n
         résultat : vrai ssi this est incluse dans r
         */
        public boolean estIncluse(RelationBinaire r){
            for (int i=0;i<this.n;i++){
                for (int j=0;j<this.n;j++){
                    if (this.matAdj[i][j]&&!r.matAdj[i][j]){
                        return false;
                    }
                }
            }
            return true;

        }

//        //______________________________________________


        /** pré-requis : this.n = r.n
         résultat : vrai ssi this est égale à r
         */
        public boolean estEgale(RelationBinaire r){
            if(this.estIncluse(r)&&r.estIncluse(this)){
                return true;
            }
            else return false;
        }

//        //______________________________________________


        // C) Théorie des graphes orientés
        //---------------------------------

        /** pré-requis : 0 <= x < this.n
         résultat : l'ensemble des successeurs de x dans this, "indépendant"
         (c'est-à-dire dans une autre zône mémoire) de l'attribut this.tabSucc

         */
        public EE succ(int x){
            EE e = new EE(this.tabSucc[x]);
            return e;
        }

//        //______________________________________________


        /** pré-requis : 0 <= x < this.n
         résultat : l'ensemble des prédécesseurs de x dans this
         */
        public EE pred(int x){
            EE e =new EE(this.n);
            for (int i=0;i<this.n;i++){
                if(this.matAdj[i][x]){
                    e.ajoutPratique(i);
                }
            }
            return e;
        }

//        //______________________________________________


        // D) Relation binaire
        //---------------------

        /** pré-requis : aucun
         résultat : vrai ssi this est réflexive
         */
        public boolean estReflexive(){
            int i=0;
            while(this.succ(i).contient(i) && i<this.n-1){
                i++;
            }
            if (i!=this.n-1 || !this.succ(i).contient(i))
                return false;
            else return true;
        }

//        //______________________________________________


        /** pré-requis : aucun
         résultat : vrai ssi this est antiréflexive
         */
        public boolean estAntireflexive(){
            int i=0;
            while(!this.succ(i).contient(i) && i<this.n-1){
                i++;
            }
            if (i!=this.n-1 || this.succ(i).contient(i))
                return false;
            else return true;

        }

//        //______________________________________________


        /** pré-requis : aucun
         résultat : vrai ssi this est symétrique
         */
        public boolean estSymetrique(){
            boolean e[][]=transposee(this.matAdj);
            int j=0;
            int i=0;
            boolean symétrique=true;
            while (i<this.n && symétrique){
                while (j<this.n && symétrique) {
                    if (e[i][j] !=this.matAdj[i][j]) {
                        symétrique = false;
                    }
                    j++;
                }
                i++;
                j=0;
            }
            return symétrique;
        }

//        //______________________________________________


        /** pré-requis : aucun
         résultat : vrai ssi this est antisymétrique
         */
        public boolean estAntisymetrique(){
            int j=0;
            int i=0;
            boolean antisymétrique=true;
            while (i<this.n && antisymétrique){
                while (j<this.n && antisymétrique) {
                    if (this.matAdj[j][i]==this.matAdj[i][j] && i!=j) {
                        antisymétrique = false;
                    }
                    j++;
                }
                i++;
                j=0;
            }
            return antisymétrique;
        }

//        //______________________________________________


        /** pré-requis : aucun
         résultat : vrai ssi this est transitive
         */
        public boolean estTransitive(){
            boolean e=true;
            int i=0,j=0;
            while (e && i<this.n){
                while (e && j<this.n){
                    if(matAdj[i][j]){
                        for (int k=0;k<this.n;k++){
                            if (matAdj[j][k]){
                                if(!pred(k).contient(i)){
                                    e=false;
                                }
                            }
                        }
                    }
                j++;
                }
                i++;
                j=0;
            }
            return e;
        }

//        //______________________________________________


        /** pré-requis : aucun
         résultat : vrai ssi this est une relation d'ordre
         */
        public boolean estRelOrdre(){
            if(this.estAntireflexive()&&this.estAntisymetrique()&&this.estTransitive())
                return true;
            else return false;
        }

//        //______________________________________________



        /** pré-requis : aucun
         résultat : la relation binaire assiciée au diagramme de Hasse de this
         */
        public RelationBinaire hasse(){
            RelationBinaire H=new RelationBinaire(this);
            if(!H.estAntireflexive()){
                for (int i=0;i<this.n;i++){
                    H.matAdj[i][i]=false;
                }
            }
//            for (int i=0;i<this.n;i++){
//                for (int j=0;j<this.n;j++){
//                    if(H.matAdj[i][j]){
//                        for (int k=0;k<this.n;k++){
//                            if (H.matAdj[i][k]){
//                                if(tabSucc[k].contient(j)){
//                                    H.matAdj[i][j]=false;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
            for (int elementCOURANT =0;elementCOURANT<H.n;elementCOURANT++){
                for (int i=0;i< H.n;i++){
                    if (H.matAdj[i][elementCOURANT]) {
                        for (int j=0;j<H.n;j++){
                            if (H.matAdj[elementCOURANT][j]){
                                if(H.matAdj[i][j]){
                                    H.enleveCouple(i,j);
                                }
                            }
                        }
                    }

                }
            }
            return H;
        }

//        //______________________________________________

        /** pré-requis : aucun
         résultat : la fermeture transitive de this
         */
        public RelationBinaire ferTrans(){
            RelationBinaire F=new RelationBinaire(this);
                for (int i = 0; i < this.n; i++) {
                    for (int j = 0; j < this.n; j++) {
                        if (!this.estTransitive()) {
                            if (!F.matAdj[i][j]) {
                                for (int k = 0; k < this.n; k++) {
                                    if (F.matAdj[i][k]) {
                                        if (tabSucc[k].contient(j)) {
                                            F.matAdj[i][j] = true;
                                            i=0;
                                            j=0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            return F;
        }

//        //______________________________________________

        /** pré-requis : aucun
         action : affiche this sous 2 formes (matrice et ensemble de couples), puis affiche ses propriétés
         (réflexive, ..., relation d'ordre) et les relations binaires suivantes obtenues à partir de this :
         Hasse, fermeture transitive de Hasse et fermeture transitive de Hasse avec boucles (sous 2 formes aussi)
         */
        public void afficheDivers(){
            System.out.println(this);
            System.out.println("La R.B est :");
            if(this.estReflexive()){
                System.out.println("- réflexive");
            }
            if(this.estAntireflexive()){
                System.out.println("- antiréflexive");
            }
            if(this.estSymetrique()){
                System.out.println("- Symétrique");
            }
            if(this.estAntisymetrique()){
                System.out.println("- réflexive");
            }
            if(this.estTransitive()){
                System.out.println("- transitive");
            }
            if(this.estRelOrdre()){
                System.out.println("- Une relation d'ordre");
            }
            System.out.println("----------------Hasse : --------------");
            System.out.println(this.hasse());
            System.out.println("--------------fermeture transitive de Hasse--------------- :");
            System.out.println(this.hasse().ferTrans());
        }

        }

        //______________________________________________
