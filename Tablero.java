/*
NOMBRE: Diego Valencia Figueroa
PROYECTO: El juego de la vida de John. H Conway
FECHA: 18 de septiembre del 2025
FUNCIÓN: El juego consiste en que el usuario ingrese las dimensiones del tablero, el número de generaciones y el numero de
celulas iniciales, junto con sus coordenadas. Una vez ingresado estos datos iniciales, el juego empezara y mediante "ENTER"
el juego se ejecutara por ciclo hasta terminar. El juego ternima cuando todas las celulas estan muertas, no haya cambios en
la siguiente generacion o se acaben las generaciones.
 */
import java.util.*;
public class Tablero {
    private final int NF, NC, GEN;
    private final int[] CEL;
    private char[][] matriz;

    public Tablero(int nf, int nc, int gen, int[] celulas)
    {
        this.NF = nf;
        this.NC = nc;
        this.GEN = gen;
        this.CEL = celulas;
    }

    private void iniciarJuego()
    {
        matriz = new char[NF][NC];

        for(int i=0; i<NF; i++)
        {
            Arrays.fill(matriz[i],'X');
        }


        for(int i=0; i<CEL[0]; i++)
        {
            int f = CEL[1+i*2];
            int c = CEL[2+i*2];

            if(f>=0 && f<NF && c>=0 && c<NC)//Coordenadas dentro del tablero?
            {matriz[f][c] = 'O';}//Celulas vivas.
            else
            {System.out.println("Coordenada fuera de rango: (" + (f) + "," + (c) + ")");}
        }
    }

    private int contarVecinos(int f, int c)
    {
        int totalVecinos = 0;
        for(int i=f-1; i<=f+1; i++)//Recorre las filas adyacentes.
        {
            for(int j=c-1; j<=c+1; j++)//Recorre las columnas adyacentes.
            {
                if(i>=0 && i<NF && j>=0 && j<NC)//Si la celula esta dentro del tablero?
                {
                    if(matriz[i][j] == 'O'){totalVecinos++;}//Solo cuenta celulas vivas.
                }
            }
        }
        if(matriz[f][c]=='O'){totalVecinos--;}//Se salta la celula central.
        return totalVecinos;
    }

    private char[][] sigGeneracion()
    {
        char[][] sigGen = new char[NF][NC];
        for(int i=0; i<NF; i++)
        {
            for(int j=0; j<NC; j++)
            {
                int vecinos = contarVecinos(i,j);
                if(matriz[i][j] == 'O')//Si la celula esta viva?
                {
                    if(vecinos == 2 || vecinos == 3){sigGen[i][j] = 'O';}//La celula sigue viva.
                    else{sigGen[i][j] = 'X';}//La ceula se muere.
                }
                else//Si la celula esta muerta?
                {
                    if(vecinos == 3){sigGen[i][j] = 'O';}//La celula revive.
                    else{sigGen[i][j] = 'X';}//La celula sigue muerta.
                }

            }
        }
        return sigGen;
    }

    private boolean matrizMuerta(char[][] matriz)
    {
        for(int i=0; i<NF; i++)
        {
            for(int j=0; j<NC; j++)
            {
                if(matriz[i][j] != 'X'){return false;}//Si encunetra una celula viva?
            }
        }
        return true;
    }

    private boolean matricesIguales(char[][] matrizA)
    {
        for(int i=0; i<NF; i++)
        {
            for(int j=0; j<NC; j++)
            {
                if(matrizA[i][j] != matriz[i][j])//Si encuentra una celda diferente?
                {return false;}
            }
        }
        return true;
    }

    private void copiarMatriz(char[][] copia)
    {
        for(int i=0; i<NF; i++)
        {
            System.arraycopy(copia[i], 0, matriz[i], 0, NC); //Asigna el valor de la matriz al atributo matriz.
        }
    }

    private String toString(char[][] matriz, int gen)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("----------GENERACIÓN ").append(gen).append(" ----------\n");
        sb.append(" \n");

        for(int i=0; i<NF; i++)
        {
            for(int j=0; j<NC; j++)
            {sb.append(matriz[i][j]).append(" ");}
            sb.append(" \n");
        }
        
        sb.append(" \n");
        return sb.toString();
    }

    private String contarCel(char[][] mat1, char[][] mat2, int gen)
    {
        int vivas = 0, muertas = 0, revividas = 0;
        StringBuilder coordVivas = new StringBuilder();
        StringBuilder coordMuertas = new StringBuilder();
        StringBuilder coordRev = new StringBuilder();

        for(int i=0; i<NF; i++){
            for(int j=0; j<NC; j++){
                char celAnt = mat1[i][j];
                char celSig = mat2[i][j];

                if(celAnt == 'O' && celSig == 'O')//Si la celula se mantiene viva?
                {
                    coordVivas.append("(").append(i).append(",").append(j).append(") ");//Coordenada celula viva.
                    vivas++;
                }
                if(celAnt == 'O' && celSig == 'X')//Si la celula se muere?
                {
                    coordMuertas.append("(").append(i).append(",").append(j).append(") ");//Coordenada celula muerta.
                    muertas++;
                }
                if(celAnt == 'X' && celSig == 'O')//Si la celula revive?
                {
                    coordRev.append("(").append(i).append(",").append(j).append(") ");//Coordenada celula revivida.
                    revividas++;
                }
            }
        }
        StringBuilder resumen = new StringBuilder();
        resumen.append("GENEREACIÓN ").append(gen).append("\n");
        resumen.append("Vivas (").append(vivas).append("): ").append(coordVivas).append("\n");
        resumen.append("Muertas (").append(muertas).append("): ").append(coordMuertas).append("\n");
        resumen.append("Revividas (").append(revividas).append("): ").append(coordRev).append("\n");
        resumen.append("\n");
        return resumen.toString();
    }

    public void ciclos()
    {
        Scanner sc = new Scanner(System.in);
        StringBuilder historial = new StringBuilder();
        char[][] next;
        iniciarJuego();

        int vivas = 0;
        StringBuilder coordVivas = new StringBuilder();
        int muertas = 0;
        StringBuilder coordMuertas = new StringBuilder();

        for (int i = 0; i < NF; i++){
            for (int j = 0; j < NC; j++){
                if (matriz[i][j] == 'O'){
                    coordVivas.append("(").append(i).append(",").append(j).append(") ");
                    vivas++;
                }else{
                    coordMuertas.append("(").append(i).append(",").append(j).append(") ");
                    muertas++;
                }
            }
        }

        historial.append("Generación 1:\n");
        historial.append("Vivas (").append(vivas).append("): ").append(coordVivas).append("\n");
        historial.append("Muertas (").append(muertas).append("): ").append(coordMuertas).append("\n");
        historial.append("Revividas (0): \n");
        historial.append("\n");

        for(int i=1; i<=GEN; i++)
        {
            System.out.println(toString(matriz, i));
            next = sigGeneracion();
            sc.nextLine();//Esperar una tecla antes de desplegar.
            historial.append(contarCel(matriz, next, i+1));

            if(matricesIguales(next))//Si no hubo cambios en la siguiente generación?
            {
                System.out.println(toString(next, i+1));
                System.out.println("No hubo cambios en la generación.");
                break;
            }

            if(matrizMuerta(next))//Si todas las celulas estan muertas?
            {
                System.out.println(toString(next, i+1));
                System.out.println("Todas las celulas están muertas.");
                break;
            }

            copiarMatriz(next);
        }

        System.out.println("----------FIN DEL JUEGO----------\n");
        System.out.println("----------HISTORIAL----------\n");
        System.out.println(historial.toString());//Imprime historial.
        sc.close();
    }
}