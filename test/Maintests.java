/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import subarbol.ArbolSubarbol;

/**
 *
 * @author Guest
 */
public class Maintests {

    @Test
    public void pruebaReconstruirArbol() {
        ArbolSubarbol arbol = new ArbolSubarbol();

        // Recorridos preorden e inorden proporcionados
        String preOrden = "A,B,D,E,C,F,G";
        String inOrden = "D,B,E,A,F,C,G";

        // Reconstrucción del árbol
        arbol.reconstruirArbol(preOrden, inOrden);

        // Verificación de la estructura del árbol reconstruido
        String arbolReconstruido = arbol.obtenerArbolReconstruido();
        String arbolEsperado = "A,B,D,null,null,E,null,null,C,F,null,null,G,null,null,";
        assertEquals(arbolEsperado, arbolReconstruido);
    }

    /*
        A
       / \
      B   C
     / \ / \
    D  E F  G
       
     */

    @Test
    public void pruebaReconstruirArbol2() {
        ArbolSubarbol arbol = new ArbolSubarbol();

        // Recorridos preorden e inorden proporcionados
        String preOrden = "J,K,L,O,Q,H,P";
        String inOrden = "K,L,J,H,Q,O,P";

        // Reconstrucción del árbol
        arbol.reconstruirArbol(preOrden, inOrden);

        // Verificación de la estructura del árbol reconstruido
        String arbolReconstruido = arbol.obtenerArbolReconstruido();
        String arbolEsperado = "J,K,null,L,null,null,O,Q,H,null,null,null,P,null,null,";
        assertEquals(arbolEsperado, arbolReconstruido);
    }

    /*
        J
       / \
      K    O
      \   / \
      L  Q  P
        /
       H
       
     */

    @Test
    public void pruebaContieneSubarbol() {
        ArbolSubarbol arbolPrincipal = new ArbolSubarbol();
        ArbolSubarbol subArbol = new ArbolSubarbol();

        String preOrdenPrincipal = "A,B,D,E,C,F,G";
        String inOrdenPrincipal = "D,B,E,A,F,C,G";

        String preOrdenSub = "B,D,E";
        String inOrdenSub = "D,B,E";

        arbolPrincipal.reconstruirArbol(preOrdenPrincipal, inOrdenPrincipal);
        subArbol.reconstruirArbol(preOrdenSub, inOrdenSub);

        arbolPrincipal.contieneSubarbol(subArbol);

    }

    @Test
    public void pruebaContieneSubarbolNoExistente() {
        ArbolSubarbol arbolPrincipal = new ArbolSubarbol();
        ArbolSubarbol subArbol = new ArbolSubarbol();

        String preOrdenPrincipal = "A,B,C";
        String inOrdenPrincipal = "B,A,C";

        String preOrdenSub = "D,E,F";
        String inOrdenSub = "D,F,E";

        arbolPrincipal.reconstruirArbol(preOrdenPrincipal, inOrdenPrincipal);
        subArbol.reconstruirArbol(preOrdenSub, inOrdenSub);

        arbolPrincipal.contieneSubarbol(subArbol);

    }

    @Test
    public void pruebaReconstruirArbolIguales() {
        ArbolSubarbol arbol1 = new ArbolSubarbol();
        ArbolSubarbol arbol2 = new ArbolSubarbol();

        // Recorridos preorden e inorden proporcionados para el primer árbol
        String preOrden1 = "A,B,D,E,C,F,G";
        String inOrden1 = "D,B,E,A,F,C,G";

        // Recorridos preorden e inorden proporcionados para el segundo árbol (idéntico al primero)
        String preOrden2 = "A,B,D,E,C,F,G";
        String inOrden2 = "D,B,E,A,F,C,G";

        // Reconstrucción de los árboles
        arbol1.reconstruirArbol(preOrden1, inOrden1);
        arbol2.reconstruirArbol(preOrden2, inOrden2);

        // Verificación si los árboles reconstruidos son iguales
        assertTrue(arbol1.sonIguales(arbol1.raiz, arbol2.raiz));
    }

}
