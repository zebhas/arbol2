/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subarbol;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        ArbolSubarbol arbolPrincipal = new ArbolSubarbol();
        ArbolSubarbol subArbol = new ArbolSubarbol();

        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("data/Arbol.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String nombre = properties.getProperty("nombre");
        String apellido = properties.getProperty("apellido");

        String preOrdenNombre = obtenerPreOrden(nombre);
        String inOrdenNombre = obtenerInOrden(nombre);
        String preOrdenApellido = obtenerPreOrden(apellido);
        String inOrdenApellido = obtenerInOrden(apellido);

        arbolPrincipal.reconstruirArbol(preOrdenNombre, inOrdenNombre);
        subArbol.reconstruirArbol(preOrdenApellido, inOrdenApellido);

        System.out.println("Árbol Principal:");
        arbolPrincipal.imprimirInorden(); // Muestra el árbol principal (recorrido inorden)
        System.out.println("\nSubárbol:");
        subArbol.imprimirInorden(); // Muestra el subárbol (recorrido inorden)

        System.out.println("\nAnálisis de Subárbol en Árbol Principal:");
        arbolPrincipal.contieneSubarbol(subArbol); // Verifica si el árbol principal contiene el subárbol
    }

    private static String obtenerPreOrden(String palabra) {
        char[] chars = palabra.toCharArray();
        StringBuilder preOrden = new StringBuilder();
        for (char c : chars) {
            preOrden.append(c).append(",");
        }
        return preOrden.substring(0, preOrden.length() - 1);
    }

    private static String obtenerInOrden(String palabra) {
        char[] chars = palabra.toCharArray();
        Arrays.sort(chars);
        StringBuilder inOrden = new StringBuilder();
        for (char c : chars) {
            inOrden.append(c).append(",");
        }
        return inOrden.substring(0, inOrden.length() - 1);
    }
}
