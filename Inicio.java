/*
NOMBRE: Diego Valencia Figueroa
PROYECTO: El juego de la vida de John. H Conway
FECHA: 18 de septiembre del 2025
FUNCIÓN: El juego consiste en que el usuario ingrese las dimensiones del tablero, el número de generaciones y el numero de
celulas iniciales, junto con sus coordenadas. Una vez ingresado estos datos iniciales, el juego empezara y mediante "ENTER"
el juego se ejecutara por ciclo hasta terminar. El juego ternima cuando todas las celulas estan muertas, no haya cambios en
la siguiente generacion o se acaben las generaciones.
 */
public class Inicio {
    public static void main(String[] args)
    {
        String reglas =
        """
        ----------EL JUEGO DE LA VIDA----------
        Reglas:
        1. Cada celda del tablero puede tener 3, 5 u 8 vecinos.
        2. Los vecinos de una celda son aquellas que se encuentran adyacentes.
        3. Una celula viva permanece viva si tiene 2 o 3 vecinos vivos.
        4. Las celulas vivas con menos de 2 vecinos mueren de aislamiento.
        5. Las celulas vivas con más de 3 vecinos mueren de hacinamiento.
        6. Las celulas muertas con exactamente 3 vecinos reviven.
        """;

        System.out.println(reglas);
        int nf = leerValor("Ingrese de 2 a 20 filas: ", 20, 2);//Filas.
        int nc = leerValor("Ingrese de 2 a 20 columnas: ", 20, 2);//Columnas.
        int gen = leerValor("Ingrese de 1 a 50 generaciones: ", 50, 1);//Generaciones.
        int[] cel = leerCel(nf, nc);

        Tablero ronda = new Tablero(nf, nc, gen, cel);
        ronda.ciclos();
    }

    public static int leerValor(String texto, int max, int min)//Método para ingresar los valores iniciales.
    {
        int valor;
        do{
            System.out.print(texto);
            valor = Keyboard.readInt();
            if(valor<min || valor>max){System.out.println("Número inválido.");}
        }while(valor<min || valor>max);
        return valor;
    }

    public static int[] leerCel(int nf, int nc)
    {
        String valor;
        int[] cel;
        do
        {
            System.out.println("Ingrese hasta "+(nf*nc)/2+" celulas:");
            valor = Keyboard.readString();
            String[] strValor = valor.split(",");
            cel = new int[strValor.length];
            for(int i=0; i<strValor.length; i++)
            {
                try{cel[i] = Integer.parseInt(strValor[i]);}
                catch (NumberFormatException e)
                {System.out.println("ERROR. "+e.getMessage());}
            }
            if(cel[0]>(nf*nc)/2){System.out.println("Número de celulas mayor al permitido.");}
            if(cel.length-1!=cel[0]*2){System.out.println("Número de coordenadas inválido");}
        }while(cel.length%2 ==0 || cel[0]>(nf*nc)/2 || cel.length-1!=cel[0]*2);

        return cel;
    }
}