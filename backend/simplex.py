"""
------- Algoritmo Simplex -------
Grupo : Joao Fernando Campolina Dias
        Carlos Augusto Alves


Execucao : python simplex.py input.txt

exemplo input:
Sistema:
    MAX Z = 80x1 + 60x2
    R1: 4x1 + 6x2 >= 24
    R2: 4x1 + 2x2 <= 16
    R3: x2 >= 3

input.txt:
    MAX 80 60
    4 6  >= 24
    4 2  <= 16
    0 1  >= 3
"""
import numpy as np
import sys
import math

def algoritmoTroca(LinPivo,ColPivo,mCima):
    #Criar Matriz de Baixo
    numLins = mCima.shape[0]
    numCols = mCima.shape[1]
    mBaixo = np.zeros((numLins,numCols))
    #Copiar valores iguais
    mBaixo[0] = mCima[0]
    for i in range(2,numLins):
      mBaixo[i,0] = mCima[i,0]
    #Calcular pivo baixo
    pivo = 1 / mCima[LinPivo,ColPivo]
    mBaixo[LinPivo,ColPivo] = pivo
    #atualizar pivo
    mCima[LinPivo,ColPivo] = mBaixo[LinPivo,ColPivo]

    #multiplicar linha pelo pivo
    for i in range (1,numCols):
        if ( i != ColPivo):
            mBaixo[LinPivo,i] = pivo * mCima[LinPivo,i]
    
    #multiplicar coluna pelo inverso do pivo
    for i in range (1,numLins):
        if ( i !=LinPivo):
            mBaixo[i,ColPivo] =  ( -1 * pivo ) * mCima[i,ColPivo]

    #calcular a multiplicacao de coluna,linha.Somar em matriz cima
    for i in range(1,numCols):
        if ( i != ColPivo ):
            for j in range(1,numLins):
                if ( j != LinPivo ):
                    mBaixo[j,i] = mCima[LinPivo,i] * mBaixo[j,ColPivo]
                    mCima[j,i] = mCima[j,i] + mBaixo[j,i]
                    mCima[j,ColPivo] = mBaixo[j,ColPivo]
            mCima[LinPivo,i] = mBaixo[LinPivo,i]

    
    #trocar variaveis
    tmp = mCima[0,ColPivo]
    mCima[0,ColPivo] = mCima[LinPivo,0]
    mCima[LinPivo,0] = tmp

def modelarProblema(nomeArquivo):
    #Abrir Entrada
    file = open(nomeArquivo,'r')
    linhas = file.readlines()
    #Pegar coeficientes na funcao objetivo
    linhaZ = linhas[0].split()
    #Seter dimensoes da matriz
    numLins = len (linhas) + 1
    numCols = len (linhaZ) + 1
    matrizCima = np.zeros((numLins,numCols))
    #sinal da funcao obj
    sinal = 1
    if (linhaZ[0] == 'MIN'):
        sinal = -1
    
    #setar as variaveis x na primeira linha e primeira coluna, ignorar as 2 primeiras linhas e colunas
    x = 1
    for i in range(2,numCols):
        matrizCima[0,i] = x
        x=x+1
    for i in range(2,numLins):
        matrizCima[i,0] = x
        x=x+1
    
    #setar linha da funcao obj
    for i in range(2,numCols):
        matrizCima[1,i] = float(linhaZ[i-1]) * sinal

    
    #pegar os sinais de cada restricao
    sinais = np.zeros(numLins-2)
    for i in range(1,len(linhas)):
        if (linhas[i].split()[numCols-2] == ">="):
            sinais[i-1] = -1
        else:
            sinais[i-1] = 1

    #setar a coluna ML,coluna 1 na matriz
    for i in range(2,numLins):
        matrizCima[i,1] = float(linhas[i-1].split()[numCols-1]) * sinais[i-2]

    #setar o resto da matriz
    for i in range(2,numLins):
        linha = linhas[i-1].split()
        sinal = sinais[i-2]
        for j in range(2,numCols):
            matrizCima[i,j] = float(linha[j-2]) * sinal


    return matrizCima



#testar de tem algum negativo na coluna ML
def temNegML(matriz,numLins):
    for i in range (2,numLins):
        if ( matriz[i,1] < 0 ):
            return True
    return False

#retorna o maior elemento na linha F
def temPosFx(matriz,numCols):
    maior = matriz[1,2]
    for i in range(3,numCols):
        if (matriz[1,i] > maior):
            maior = matriz[1,i]
            
    return maior


#dividir elementos da coluna ml com a permissiva
#retornar o menor nao negativo
#retorna -1 se nao encontrar
def acharLinPivo(matriz,colPivo,numLins):
    linPivo = -1
    menor = math.inf
    for i in range (2,numLins):
        if (matriz[i,colPivo] != 0):
            quoci = matriz[i,1] / matriz[i,colPivo]
            if ((quoci < menor) & (quoci > 0 )):
                menor = quoci
                linPivo = i
    return linPivo


#Procurar um elemento negativo na coluna ML,com um negativo na mesma linha
#Se encontrar,calcula pivo e retorna coordenadas
#Se nao,problema impossivel
def acharPivoCol(matriz,numLins,numCols):
    for i in range (2,numLins):
        if (matriz[i,1] < 0):
            for j in range(2,numCols):
                if (matriz[i,j] < 0):
                    colPivo = j
                    linPivo = acharLinPivo(matriz,colPivo,numLins)
                    if (linPivo > 0):
                        return (linPivo,colPivo)
    print ("Problema Impossivel")
    sys.exit()


#Procurar um elemento Positivo na linha FX,com um positivo na mesma coluna
#Se encontar,calcula pivo e retorna coordenadas
#Se nao,problema Ilimitado
def acharPivoLin(matriz,numLins,numCols):
    for j in range(2,numCols):
        if (matriz[1,j] > 0):
            for i in range(2,numLins):
                if(matriz[i,j] >= 0):
                    colpivo = j
                    linPivo = acharLinPivo(matriz,colpivo,numLins)
                    if (linPivo > 0):
                        return (linPivo,colpivo)

    print('Problema Ilimitado')
    sys.exit()

#Extrair respostas da matriz final
#Procurar pelas variaveis da equacao do Z
def getResposta(matriz,numLins,numCols):
    x = []
    for i in range(1,numCols-1):
        x.append(i)

    for i in range(0,len(x)):
        found = False
        for j in range(2,numLins):
            if (x[i] == matriz[j,0]):
                found = True
                break
        if (found):
            print('X' + str(x[i]) + '=' + str(matriz[j,1]))
        else:
            print('X' + str(x[i]) + '=0')
    print('Z=' + str(abs(matriz[1,1])))
        
    


def simplex(matriz):
    numLins = matriz.shape[0]
    numCols = matriz.shape[1]
    checkNeg = temNegML(matriz,numLins)
    checkPos = temPosFx(matriz,numCols)
    #Tem negativo na col ML?
    if (checkNeg):
        coordPivo = acharPivoCol(matriz,numLins,numCols)
        algoritmoTroca(coordPivo[0],coordPivo[1],matriz)
        simplex(matriz)
    #Tem positivo na linha F(x)?
    elif (checkPos > 0 ):
        coordPivo = acharPivoLin(matriz,numLins,numCols)
        algoritmoTroca(coordPivo[0],coordPivo[1],matriz)
        simplex(matriz)
    #Testar se zero e o maior numero da linha F(x)
    elif (checkPos == 0):
        print('Multiplas-Solucoes')
    else:
        print('Solucao-Otima-Encontrada:')
        getResposta(matriz,numLins,numCols)


def main(nomeArquivo):
    print(nomeArquivo)
    matrizP = modelarProblema(nomeArquivo)
    matrizProblema = np.matrix(matrizP,dtype=float)
    simplex(matrizProblema)

main('input.txt')



