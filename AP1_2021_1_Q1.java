/**
 * AP1_2021_1_Q1
 * Created on 4/22/2021
 *
 * @author: AmandaMiquilini
 **/

import java.util.*;
import java.lang.Math;

public class AP1_2021_1_Q1 {
    public static void main(String[] args) {
        List<String> lista_caracteres = new ArrayList<>();
        String caracter;
        String palavra;

        Scanner sc1 = new Scanner(System.in);
        System.out.println("Digite os caracteres com espaços: "); // caracteres devem ser digitados com espaços simples
        caracter = sc1.next().toUpperCase(); // para fins de compatibilidade, todas as letras serão convertidas em maiúsculas
        while(!caracter.equals("FIM")){
            lista_caracteres.add(caracter);
            caracter = sc1.next().toUpperCase();
        }
        System.out.println("Digite a string: ");
        palavra = sc1.next().toUpperCase();

        sc1.close();

        Matriz matriz = new Matriz(lista_caracteres); // cria um objeto da classe matriz, que formará uma matriz quadrada a partir da lista de caracteres fornecida
        System.out.println("\n" + "A palavra "+ palavra + " está na matriz abaixo? \n" + matriz); // imprime a matriz quadrada com os caracteres fornecidos
        System.out.println(matriz.procuraPalavra(palavra)); // método que procura uma palavra na matriz fornecida e retorna TRUE ou FALSE
    }
}

class Matriz {
    String p; // armazena a string
    char[][] m; // matriz de caracteres
    List<String> c;
    int dim, a=0;
    boolean resultado, r_subbusca;
    int[][] m_visitada; // matriz de caracteres já utilizados

    //Construtor
    Matriz(List<String> lista_caracteres){
        c = lista_caracteres;

        double tam = Math.sqrt(c.size()); //raiz quadrada da quantidade de caracteres para descobrir a dimensão da matriz quadrada
        dim = (int) tam;
        m = new char[dim][dim]; //matriz quadrada de tamanho "dim" para armazenar os caracteres
        m_visitada = new int[dim][dim]; //matriz de mesmo tamanho para guardar as posições já visitadas pelo algoritmo, para não usar o mesmo caracter mais de uma vez
        for(int i = 0 ; i < dim ; i++){
            for (int j = 0 ; j < dim ; j++){
                m[i][j] = c.get(a++).charAt(0); //adiciona o caracter da lista c na matriz m na sequência em que foram fornecidos
            }
        }
    }
    //Métodos
    public String toString(){ //método para retornar a matriz pronta
        String mat = "";
        for(int i = 0 ; i < dim ; i++){
            mat = mat + "|";
            for (int j = 0 ; j < dim ; j++){
                mat = mat + m[i][j]+ "|";
            }
            mat = mat + "\n";
        }
        return mat;
    }
    public boolean procuraPalavra(String palavra){ // método para buscar a string passada como parâmetro na matriz
        p = palavra; // armazena a string
        superloop: { // loop para procurar o primeiro caracter da string fornecida na matriz
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (m[i][j]==(p.charAt(0))) { // se o caracter for encontrado, entra em uma subrotina para buscar os próximos caracteres da string
                        resultado = sub_busca(i, j, 0); // subrotina para procurar os próximos caracteres da string, partindo da posição (i, j) atual
                        if(resultado){break superloop;} // se retornar true a palavra foi encontrada, então o loop é interrompido
                    }
                }
            }
        }
        return resultado;
    }
    boolean sub_busca(int i, int j, int pc){
        int[] pos = new int[2]; // vetor de posições
        m_visitada[i][j] = 1; // caso entre nessa subrotina é porque o caracter está correto, então a posição desse caracter recebe o valor 1 na matriz de caracteres visitados/utilizados
        pc++; // incrementa a posição do caracter da string fornecida, para avaliar o próximo
        masterloop: // avalia as posições vizinhas ao caracter sendo analisado, respeitando o índice máximo da matriz
        {
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    if(i+x>=0 && j+y>=0 && i+x<=dim-1 && j+y<=dim-1) { // condição para não ultrapassar as bordas da matriz
                        if (m_visitada[i + x][j + y] != 1 && (m[i + x][j + y] == (p.charAt(pc)))) { // verifica se o elemento vizinho já foi visitado, pra não usar o mesmo caracter mais de uma vez, e então compara o caracter da string
                            pos[0] = i + x; // se o caracter for igual, a nova posição é registrada no vetor pos
                            pos[1] = j + y;
                            break masterloop;
                        }
                    }
                }
            }
            pos[0] = i;
            pos[1] = j;
        }

        if(pos[0] == i && pos[1] == j){ //se a posição armazenada no vetor pos após o loop for igual à posição anterior significa que o próximo caracter não foi encontrado na vizinhaça, então a busca retorna FALSO
            r_subbusca=false;
        }else if(pc == p.length()-1){ //caso a posição do caracter na string seja igual ao comprimento da string, a string foi encontrada na matriz, e a busca retorna TRUE
            r_subbusca=true;
        }else if((pos[0] != i || pos[1] != j) && pc<p.length()-1){ //se a nova posição for diferente da anterior, significa que o próximo caracter foi encontrado na vizinhança, iniciando uma chamada recursiva
            sub_busca(pos[0], pos[1], pc); //chamada recursiva para continuar buscando os próximos caracteres da string
        }

        return r_subbusca;
    }
}
