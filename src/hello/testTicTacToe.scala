package hello

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

class testTicTacToe extends FunSuite {
  test("util.setValeur devrait retourner une valeur") {
    var grilleTest1 = List('-', '-', '-', '-', '-', '-', '-', '-', '-')    
    assert(utilTicTacToe.setValeur(grilleTest1, 'X', 0)(0) == 'X')
    assert(utilTicTacToe.jouerTour(grilleTest1, 'O', 0)(0) == 'O')   
    assert(utilTicTacToe.jouerTour(grilleTest1, 'X', 0)(1) == '-') 
  }

  test("util.initList devrait init une list rempli par des tirets") {
    var grilleTest1 = List('X', '-', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest2 = List('X', 'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X')
    var grilleTest3 = List('X', 'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X')
    assert(!utilTicTacToe.initList(grilleTest1).contains('X'))
    assert(!utilTicTacToe.initList(grilleTest2).contains('X'))
    assert(!utilTicTacToe.initList(grilleTest2).contains('O'))
  }

  test("util.estGagnant dois retourner vrai quand un joueur gagne") {
    var grilleTest1 = List('X', 'X', 'X', '-', '-', '-', '-', '-', '-')
    var grilleTest2 = List('-', '-', '-', 'X', 'X', 'X', '-', '-', '-')
    var grilleTest3 = List('-', '-', '-', '-', '-', '-', 'X', 'X', 'X')
    var grilleTest4 = List('X', '-', '-', 'X', '-', '-', 'X', '-', '-')
    var grilleTest5 = List('-', 'X', '-', '-', 'X', '-', '-', 'X', '-')
    var grilleTest6 = List('-', '-', 'X', '-', '-', 'X', '-', '-', 'X')
    var grilleTest7 = List('X', '-', '-', '-', 'X', '-', '-', '-', 'X')
    var grilleTest8 = List('-', '-', 'X', '-', 'X', '-', 'X', '-', '-')
    
    var grilleTest9 = List('-', 'X', 'X', 'X', '-', '-', '-', '-', '-')
    var grilleTest10 = List('X', 'O', 'X', '-', '-', '-', '-', '-', '-')
    
    assert(utilTicTacToe.estGagnant(grilleTest1, 'X') == true)
    assert(utilTicTacToe.estGagnant(grilleTest2, 'X') == true)
    assert(utilTicTacToe.estGagnant(grilleTest3, 'X') == true)
    assert(utilTicTacToe.estGagnant(grilleTest4, 'X') == true)
    assert(utilTicTacToe.estGagnant(grilleTest5, 'X') == true)
    assert(utilTicTacToe.estGagnant(grilleTest6, 'X') == true)
    assert(utilTicTacToe.estGagnant(grilleTest7, 'X') == true)
    assert(utilTicTacToe.estGagnant(grilleTest8, 'X') == true)   
    assert(utilTicTacToe.estGagnant(grilleTest9, 'X') == false)
    assert(utilTicTacToe.estGagnant(grilleTest10, 'X') == false) 
  }

  test("util.gameOver dois retourner true quand la grille est plaine") {
    var grilleTest = List('X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X')
    var grilleTest2 = List('X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', '-')
    var grilleTest3 = List('-', '-', '-', '-', '-', '-', '-', '-', '-')
    assert(utilTicTacToe.gameOver(grilleTest) == true)
    assert(utilTicTacToe.gameOver(grilleTest2) == false)
    assert(utilTicTacToe.gameOver(grilleTest3) == false)
  }

  test("util.peutJouer dois retourner false quand la case n'est pas vide") {
    var grilleTest = List('X', '-', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest2 = List('X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X')
    assert(utilTicTacToe.peutJouer(grilleTest, 0) == false)
    assert(utilTicTacToe.peutJouer(grilleTest, 1) == true)
    assert(utilTicTacToe.peutJouer(grilleTest2, 8) == false)
  }

  test("util.partieEqulals dois retouner vrai quand les deux listes sont égale") {
    var grilleTest1 = List('X', '-', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest2 = List('X', '-', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest3 = List('X', 'X', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest4 = List('-', 'X', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest5 = List('-', '-', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest6 = List('X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X')
    assert(utilTicTacToe.partieEquals(grilleTest1, grilleTest2) == true)
    assert(utilTicTacToe.partieEquals(grilleTest1, grilleTest3) == false)
    assert(utilTicTacToe.partieEquals(grilleTest1, grilleTest4) == false)
    assert(utilTicTacToe.partieEquals(grilleTest1, grilleTest5) == false)
    assert(utilTicTacToe.partieEquals(grilleTest1, grilleTest6) == false)
  }

  test("util.obtenirPositionAI dois bloquer un joueur quand il a un tor de gagner") {
    var grilleTest = List('X', 'X', '-', '-', '-', '-', '-', '-', '-')
    assert(utilTicTacToe.obtenirPositionAI(grilleTest, 'O', 'X') == 2)
  }

  test("util.obtenirPositionAI dois gagner si il lui manque une case a remplire") {
    var grilleTest = List('-', '-', '-', 'O', 'O', '-', '-', '-', '-')
    assert(utilTicTacToe.obtenirPositionAI(grilleTest, 'O', 'X') == 5)
  }
  
  test("util.jouerTour dois jouer un tour si la case est libre") {
    var grilleTest1 = List('-', '-', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest2 = List('X', '-', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest3 = List('X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X')
    var grilleTest4 = List('O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X')
    assert(utilTicTacToe.jouerTour(grilleTest1, 'X', 0).sameElements(grilleTest2) == true)
    assert(utilTicTacToe.jouerTour(grilleTest1, 'O', 0).sameElements(grilleTest2) == false)
    assert(utilTicTacToe.jouerTour(grilleTest3, 'O', 0).sameElements(grilleTest4) == false)
  }
}