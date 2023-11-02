package com.mycompany.tpmutantes_opcional;

import java.util.Scanner;

public class TPMutantes_Opcional {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner scN = new Scanner(System.in);

        boolean salir = false;

        while (true) {
            String[] adn = new String[6];
            String[][] adnMatriz = new String[6][6];

            menu();
            int opcionMenu = scN.nextInt();

            switch (opcionMenu) {
                case 1:
                    adn = cargarPropioAdn();
                    break;
                case 2:
                    adn = adnNoMutante(adn);
                    break;
                case 3:
                    adn = adnFilaColumna(adn);
                    break;
                case 4:
                    adn = adnOblicuas(adn);
                    break;
                case 5:
                    adn = adnTodo(adn);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("**ERROR** OPCIÓN INCORRECTA");
                    break;
            }

            if (opcionMenu < 1 || opcionMenu >= 6) {  // la opción elegida es 6 o incorrecta
                break;
            }

            adnMatriz = convertirAMatriz(adn);

            System.out.println("------- SU ADN ES: -------");
            mostrarMatriz(adnMatriz);

            esperarSegundo();
            System.out.println("Analizando...");
            esperarSegundo();
            System.out.println("Analizando...");
            esperarSegundo();
            System.out.println("Analizando...");
            esperarSegundo();

            if (esMutante(adn)) {
                System.out.println("USTED ES MUTANTE! Magneto se contactará pronto...");
            } else {
                System.out.println("USTED NO ES MUTANTE! No podrá luchar contra los X-Men");
            }
            
            System.out.println("\n\n");
        }
    }
    //  FUNCIONES PROGRAMA MUTANTE  //
    public static boolean sonATCG(String letras){
        boolean todasATCG = true;
        
        for (int i = 0; i < letras.length(); i++){
            char letra = letras.charAt(i);
            
            if (!(letra == 'A' || letra == 'C' || letra == 'G' || letra == 'T')){
                todasATCG = false;
            }
        }
        
        return todasATCG;
    }
    
    public static boolean esMutante(String[] adn){
        String[][] adnMatriz = new String[6][6];
        adnMatriz = convertirAMatriz(adn);  //convierto el array a matriz
        
        int coincTotales = secHorizontales(adnMatriz) + secVerticales(adnMatriz) + secOblicPrinc(adnMatriz) + secOblicSecun(adnMatriz);
        
        if (coincTotales >= 1){
            return true;
        }
        
        return false;
    }
    
    public static int secHorizontales(String[][] adnMatriz){
        int coincHoriz = 0;
        
        for (int i = 0; i < adnMatriz.length; i++) {
            int coincLetra = 0;
            
            for (int j = 0; j < adnMatriz[i].length; j++) {
                if (coincLetra >= 3) {  //si las coincidencias ya son 3, suma una coincidencia horizontal se sale del bucle j
                    coincHoriz++;
                    break;
                }   //else...
                
                if (j < 5){ //la j no está en la última columna de la matriz
                    if (adnMatriz[i][j].equals(adnMatriz[i][j+1])){
                        coincLetra++;
                    } else {
                        coincLetra = 0;
                    }
                    
                } else {    // la j está en la última columna de la matriz
                    if (adnMatriz[i][j-1].equals(adnMatriz[i][j])){
                        coincLetra++;
                    }
                }
            }
        }
        
        return coincHoriz;
    }
    
    public static int secVerticales(String[][] adnMatriz){
        int coincVert = 0;
        
        for (int j = 0; j < adnMatriz[0].length; j++) {
            int coincLetra = 0;
            
            for (int i = 0; i < adnMatriz.length; i++) {
                if (coincLetra >= 3) {  //si las coincidencias ya son 3, suma una coincidencia vertical y se sale del bucle i
                    coincVert++;
                } //else...
                
                if (i < 5){ // la i no está en la última fila de la matriz
                    if (adnMatriz[i][j].equals(adnMatriz[i+1][j])){
                        coincLetra++;
                    } else {
                        coincLetra = 0;
                    }
                    
                } else {    // la i está en la última fila de la matriz
                    if (adnMatriz[i-1][j].equals(adnMatriz[i][j])){
                        coincLetra++;
                    }
                }
            }
        }
        
        return coincVert;
    }
    
    public static int secOblicPrinc(String[][] adnM){   //secuencias oblicuas principales (de izquierda a derecha)
        int mitad = (int) adnM.length / 2;
        int coincOblic = 0;
        
        for (int i = 0; i < mitad; i++){
            for (int j = 0; j < mitad; j++) {
                    // 1ra letra   =   2da letra       y    1ra letra    =   3ra letra     y   1era letra  =   4ta letra
                if (adnM[i][j].equals(adnM[i+1][j+1]) && adnM[i][j].equals(adnM[i+2][j+2]) && adnM[i][j].equals(adnM[i+3][j+3])){
                    coincOblic++;
                }
            }
        }
        
        return coincOblic;
    }
    
    public static int secOblicSecun(String[][] adnM){   //secuencias oblicuas secundarias (de derecha a izquierda)
        int mitad = (int) adnM.length / 2;
        int coincOblic = 0;
        
        for (int i = 0; i < mitad; i++){
            for (int j = adnM.length - 1; j >= mitad; j--) {
                    // 1ra letra   =   2da letra       y    1ra letra    =   3ra letra     y   1era letra  =   4ta letra
                if (adnM[i][j].equals(adnM[i+1][j-1]) && adnM[i][j].equals(adnM[i+2][j-2]) && adnM[i][j].equals(adnM[i+3][j-3])){
                    coincOblic++;
                }
            }
        }
        
        return coincOblic;
    }
    
    public static String[][] convertirAMatriz(String[] adn){
        String[][] adnMatriz = new String[6][6];
        
        for (int i = 0; i < adn.length; i++) {
            for (int j = 0; j < adn.length; j++) {
                adnMatriz[i][j] = String.valueOf(adn[i].charAt(j));
            }
        }
        
        return adnMatriz;
    }
    
    public static void mostrarMatriz(String[][] adnMatriz){
        for (int i = 0; i < adnMatriz.length; i++) {
            for (int j = 0; j < adnMatriz[0].length; j++) {
                System.out.print(adnMatriz[i][j] + " | ");
            }
            System.out.println("");
        }
    }
    
    public static void esperarSegundo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
    
    //  FUNCIONES PRUEBAS MUTANTE Y MENÚ   //
    public static void menu(){
        System.out.println("------- TEST MUTANTE -------\n");
        System.out.println("1. CARGAR PROPIO ADN");
        System.out.println("2. PROBAR ADN NO MUTANTE");
        System.out.println("3. PROBAR ADN MUTANTE - (Coincidencia Fila y Columna)");
        System.out.println("4. PROBAR ADN MUTANTE - (Coincidencia Oblicua Principal y Secundaria)");
        System.out.println("5. PROBAR ADN MUTANTE - (Coincidencia Fila, Columna y Oblicuas)");
        System.out.println("6. SALIR");
        System.out.println("------------------------------");
    }

    public static String[] cargarPropioAdn(){
        Scanner sc = new Scanner(System.in);
        
        String[] adn = new String[6];
        
        System.out.println("Ingrese su ADN cada 6 letras...");
        int filas = 0;
        
        while (filas < 6){
            System.out.println((filas+1) + "as 6 letras");
            String letras = sc.nextLine().toUpperCase();
            
            if (letras.length() != 6){  //  la cant. de letras es menor o mayor a 6
                System.out.println("**ERROR** Debe ingresar de a 6 letras. Ingreselas de nuevo\n");
                continue;
            } else if (!sonATCG(letras)){   //las letras no son unicamente A, T, C o G
                System.out.println("**ERROR** Esas 6 letras son inválidas. Ingreselas de nuevo\n");
                continue;
            }
            
            adn[filas] = letras;
            filas++;
        }
        
        return adn;
    }
    
    public static String[] adnNoMutante(String [] adn){      
        adn[0] = "ATCGAT";
        adn[1] = "CTCTTG";
        adn[2] = "CAAGGC";
        adn[3] = "GGTATT";
        adn[4] = "ATCGAT";
        adn[5] = "AAGTCC";
        return adn;
    }
    
    public static String[] adnFilaColumna(String [] adn){
        adn[0] = "CGATCA";
        adn[1] = "GATGCT";
        adn[2] = "TCCCCT";
        adn[3] = "TACAGT";
        adn[4] = "GTAACT";
        adn[5] = "ACCGTA";
        return adn;
    }
    
    public static String[] adnOblicuas(String [] adn){
        adn[0] = "GTGACC";
        adn[1] = "CTGCAG";
        adn[2] = "AGACTT";
        adn[3] = "TACGCG";
        adn[4] = "ACATAT";
        adn[5] = "CTGAGC";
        return adn;
    }
    
    public static String[] adnTodo(String [] adn){
        adn[0] = "GGTGTG";
        adn[1] = "TCGCCG";
        adn[2] = "CCAAAA";
        adn[3] = "ACTGAT";
        adn[4] = "GCCAGC";
        adn[5] = "CTACTA";
        return adn;
    }
}
