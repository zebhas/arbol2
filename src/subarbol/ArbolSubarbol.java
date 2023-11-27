/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subarbol;

import java.io.FileWriter;
import java.io.IOException;

class Nodo {
    String valor;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(String valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }

    public String getValor() {
        return valor;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }
    
}

public class ArbolSubarbol {
    public Nodo raiz;

    public ArbolSubarbol() {
        raiz = null;
    }

    public void reconstruirArbol(String preOrden, String inOrden) {
        String[] preOrdenArray = preOrden.split(",");
        String[] inOrdenArray = inOrden.split(",");

        if (preOrdenArray.length != inOrdenArray.length) {
            System.out.println("Los recorridos preorden e inorden no tienen la misma longitud.");
            return;
        }

        raiz = reconstruirArbolRec(preOrdenArray, inOrdenArray, 0, inOrdenArray.length - 1, new int[]{0});
    }

    private Nodo reconstruirArbolRec(String[] preOrdenArray, String[] inOrdenArray, int inStart, int inEnd, int[] preIndex) {
        if (inStart > inEnd || preIndex[0] >= preOrdenArray.length) {
            return null;
        }

        Nodo nodo = new Nodo(preOrdenArray[preIndex[0]]);
        preIndex[0]++;

        if (inStart == inEnd) {
            return nodo;
        }

        int inIndex = buscarIndiceInorden(inOrdenArray, inStart, inEnd, nodo.valor);

        nodo.izquierdo = reconstruirArbolRec(preOrdenArray, inOrdenArray, inStart, inIndex - 1, preIndex);
        nodo.derecho = reconstruirArbolRec(preOrdenArray, inOrdenArray, inIndex + 1, inEnd, preIndex);

        return nodo;
    }

    private int buscarIndiceInorden(String[] inOrdenArray, int start, int end, String valor) {
        for (int i = start; i <= end; i++) {
            if (inOrdenArray[i].equals(valor)) {
                return i;
            }
        }
        return -1;
    }

    public void contieneSubarbol(ArbolSubarbol subArbol) {
        boolean contiene = contieneSubarbolRec(raiz, subArbol.raiz);

        if (contiene) {
            System.out.println("El árbol principal contiene el siguiente subárbol:");
            imprimirArbol(subArbol.raiz);
            generarArchivoJSON("resultado.json", true, subArbol);
        } else {
            System.out.println("El árbol principal NO contiene el subárbol proporcionado.");
            System.out.println("Razón: El subárbol no está presente en el árbol principal.");
            generarArchivoJSON("resultado.json", false, null);
        }
        
    }
    private boolean contieneSubarbolRec(Nodo nodoArbol, Nodo nodoSubarbol) {
        if (nodoSubarbol == null) {
            return true;
        }

        if (nodoArbol == null) {
            return false;
        }

        if (sonIguales(nodoArbol, nodoSubarbol)) {
            return contieneSubarbolRec(nodoArbol.izquierdo, nodoSubarbol.izquierdo) &&
                    contieneSubarbolRec(nodoArbol.derecho, nodoSubarbol.derecho);
        }

        return contieneSubarbolRec(nodoArbol.izquierdo, nodoSubarbol) || 
               contieneSubarbolRec(nodoArbol.derecho, nodoSubarbol);
    }

    public void generarArchivoJSON(String nombreArchivo, boolean contieneSubarbol, ArbolSubarbol subArbol) {
        String recorridoPreorden = obtenerRecorridoPreorden(raiz);
        String recorridoInorden = obtenerRecorridoInorden(raiz);
        String subArbolInfo = "";

        if (subArbol != null) {
            subArbolInfo = obtenerRecorridoPreorden(subArbol.raiz) + ", " + obtenerRecorridoInorden(subArbol.raiz);
        }

        String resultado = "{\n" +
                "  \"contieneSubarbol\": " + contieneSubarbol + ",\n" +
                "  \"recorridoPreorden\": \"" + recorridoPreorden + "\",\n" +
                "  \"recorridoInorden\": \"" + recorridoInorden + "\",\n" +
                "  \"subarbolInfo\": \"" + subArbolInfo + "\"\n}";

        try (FileWriter file = new FileWriter("data/" + nombreArchivo)) {
            file.write(resultado);
            System.out.println("¡Archivo JSON generado con éxito!");
        } catch (IOException e) {
            System.out.println("Error al generar el archivo JSON: " + e.getMessage());
        }
    }

    
    private void imprimirArbol(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            imprimirArbol(nodo.izquierdo);
            imprimirArbol(nodo.derecho);
        }
    }

   public boolean sonIguales(Nodo nodo1, Nodo nodo2) {
    if (nodo1 == null && nodo2 == null) {
        return true;
    }

    if (nodo1 == null || nodo2 == null) {
        return false;
    }

    return (nodo1.valor.equals(nodo2.valor) &&
            sonIguales(nodo1.izquierdo, nodo2.izquierdo) &&
            sonIguales(nodo1.derecho, nodo2.derecho));
}

    public void imprimirInorden() {
        System.out.println("Recorrido Inorden del Árbol:");
        imprimirInordenRec(raiz);
        System.out.println();
    }

    private void imprimirInordenRec(Nodo nodo) {
        if (nodo != null) {
            imprimirInordenRec(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            imprimirInordenRec(nodo.derecho);
        }
    }

    private String obtenerRecorridoPreorden(Nodo nodo) {
        StringBuilder sb = new StringBuilder();
        obtenerRecorridoPreordenRec(nodo, sb);
        return sb.toString();
    }

    private void obtenerRecorridoPreordenRec(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.valor).append(",");
            obtenerRecorridoPreordenRec(nodo.izquierdo, sb);
            obtenerRecorridoPreordenRec(nodo.derecho, sb);
        }
    }

    private String obtenerRecorridoInorden(Nodo nodo) {
        StringBuilder sb = new StringBuilder();
        obtenerRecorridoInordenRec(nodo, sb);
        return sb.toString();
    }
   




    private void obtenerRecorridoInordenRec(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            obtenerRecorridoInordenRec(nodo.izquierdo, sb);
            sb.append(nodo.valor).append(",");
            obtenerRecorridoInordenRec(nodo.derecho, sb);
        }
    }
    public String obtenerArbolReconstruido() {
        StringBuilder resultado = new StringBuilder();
        obtenerArbolReconstruidoRec(raiz, resultado);
        return resultado.toString();
    }

    private void obtenerArbolReconstruidoRec(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.getValor()).append(",");
            obtenerArbolReconstruidoRec(nodo.getIzquierdo(), sb);
            obtenerArbolReconstruidoRec(nodo.getDerecho(), sb);
        } else {
            sb.append("null").append(",");
        }
    }
}