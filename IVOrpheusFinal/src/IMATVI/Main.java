package IMATVI;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.speech.recognition.GrammarException;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import org.math.plot.render.AbstractDrawer;

import IMATVI.Attribute;

public class Main {
	
public static JFrame Interface;
public static Directory dir;
public static Attribute att;
public static String[][] matrix; // Variavel que guarda a matrix da base pre processada
public static Vis3D infoVisModule = new Vis3D();

public static void main(String[] args) throws IOException, GrammarException{	
dir = new Directory();
Interface = new Interface();
att = new Attribute(dir.GetDirectory().replaceAll("\\s+","")+"/"+dir.GetFileNames()[0].replaceAll("\\s+",""));
matrix = att.VectorBase(); 

//Reconhecedor.main();


Interface.setVisible(true);



}

}


//Corrigir a acao ao apertar o botao confirmar. OK
//Fazer a LAbel escala Atual funcionar OKOKO

//Criar Botoes e Labels para Filtrar  OKOK
//Usar o Painel de MG  OKOKOK
//Criar Label Inicio Fim  KOKOK
//Btn Confirmar KOKOKO
//Criar Gramatica de 0..9 e as comandos globais + confirmar KOKOKOK
//Diferenciar entre Configurar e Filtrar OKOKOKOKOK
//Pegar os limites dos eixos   KOKOKOKOKOK
//Pegar o menor e o maior valor da matrix. OOKOKOKOKOK
//Criar metodos que retornam estes valores de XYZ. KOKOKOKOKOK
//Criar o metodos diferentes do PlotBase, recebendo, double[], int y, int z.  NNNNNNNNNNN
//E as outras variacoes, sendo que os doubles vao ser os arrays filtrados   NNNNNNNNNNNNN
// Corrigir o Apagar OKOKOKOK
// O ponto ta zerando o texto KOKOKOKOK
// O primeiro numero esta sendo digitado duas vezes OKOKOKOK


//Configurar
//Empregra o novo modo de plotar os graficos OKOKOKOKOKOK
//Atualizar a classe atributo OKOKOKOKOKOKO
//Quando o eixos ja estiverem configurados, ao setar um novo eixo plotar o novo sete com os antigos eixos OKOKOKOKOKOKOK
//Como resetar a visualizacao para os novos plots OKOKOKOKOKOK
//Como retirar o null da visualizacao  OKOKOKOKOKOK

//Filtrar 
//Desabilitar Filtro ate que seja plotado algo  OKOKOKOKOKOK
//Pensar nas gramaticas para os filtros(usar a ideia de 1-9)
//Filtro para dados categoricos
//Filtro para dados numericos
//Escrever as gramaticas dos filtros
//Testar primeiro as gramaticas de dados numericos
//Testar carregamento de uma gramatica dinamicamente(Funciona)
//Como verificar se um numenro é inteiro ou double
//Verificar se o dado é String e chamar o painelFiltroString
//se o dado não for string chamar painelFiltroNumerico

//BtnListener
//Por que o filtro não está funcionando

//Interface
//Ao apertar no cancelar, se comportar como o voltar indo para o IFC. OKOKOKOKOKOKOK
//Apenas ao apertar a combinacao de atributo e confirmar setar o plot. OKOKOKOKOKOKOK
//Reduzir o tamanho dos paineis para apresentar mais a visualizacao OKOKOKOOKO
//Fazer com que o confirmar aplique o filtro OKOKOKOKOKOKO
//Resolver o problema da interface piscando usando setDoubleBuffered. OKOKOKOKOKOKOKOKO
//Deixar os paineis nao arrastaveis
//Deixar o mover pra frente como zoom
//Ajustar a posição dos paineis
//Fazer o Mover arrastar apenas o Plot e não todo JFrame
//Aplicar o os numerais antes dos botões do painel carregar
//Permitir que o usuário escolha um numero para selecionar um botão
//Fazer o Inicio e Termino Selecionar os JtextFields Correspondentes
//Filtros consecutivos OKOKOKOKOK

//V2
//Painel Legenda OKOKOKOKOKOKOKO
//PMG ST OKOKOKOKOKOKO
//Filtrar Cor Corretamente OKOKOKOKOKOKOKOK
//Configurar CFT corretamente OKOKOKOKOKOKOK
//Filtrar Forma Tamanho testar Corretamente OOKOKOKOKOKOKOK
//Inserir a porcentagem de dados antes e depois de aplicar os filtros(Painel Legenda). (Ultimo) OKOKOKOKOKOKOK

//V2.5
//Ataulizar o Tradutor e gramáticas ***
//Aplicar os detalhes sobre demanda OKOKOKOKOKOKOK
//Trabalhar na interação deixando os movimentos de rotação/zoom/Mover suaves(incrementando automáticamente os movimentos). OKOKOKOKOKOKOKOK
//Porcentagem Dinâmica Corrigir. OKOKOKOKOKOKOK
//Fazer a funcionalidade screenshot (Gravar Tela) OKOOKOKOKOKOKOK
//Migrar Legenda OKOKOKOKOKOK

//Corrigir Bugs
//Chamar Legenda junto com os detalhes OKOKOKOKOK
//Ao apertar em confirmar chamar o painel Correto e a threadDSD.

//V3
//Para o V3 pesquisar Visualização Narrativa
//Selecionar Base Corretamente gerando as gramaticas corretas
//Falar Com Eudes Sobre o ícone//
//Adicionar a segunda base
//Criar e carregar dinâmicamente todas as gramáticas necessárias as bases.
//Testar a performace com todas as bases carregadas.
//Testar Sphinx4 *****
//Fazer a funcionalidade Salvar
//Undo/Redo

//Coisas a se fazer 
//Fazer o configurar 3D Funcionar OKOKOKOK
//Filtrar OKOKOKOKOOKOK
//Legenda OKOKOKOKOKOK
//DSD Funcionar Corretamente no 3D. 
//    Desenhar apenas um número para selecionar
//    Chamar btns de 1..9 para selecionar ponto
//    Desenhar Coordenadas
//    Testar se está correto o filtro por quadrantes OKOKOKOKOKOK
//Bolar Tarefa Completa e o Screenshot no final para comparar duas telas
//Implementar Voz
//Esquematizar Testes
//Persistência das CFTs (opcional)