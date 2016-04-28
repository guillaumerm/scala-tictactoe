package hello

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

class testTicTacToe extends FunSuite {
  test("util.setValeur devrait retourner une valeur") {
    var grilleTest = List('-', '-', '-', '-', '-', '-', '-', '-', '-')
    assert(utilTicTacToe.setValeur(grilleTest, 'X', 0)(0) == 'X')
  }

  test("util.initList devrait init une list rempli par des tirets") {
    var grilleTest = List('X', '-', '-', '-', '-', '-', '-', '-', '-')
    assert(!utilTicTacToe.initList(grilleTest).contains('X'))
  }

  test("util.estGagnant dois retourner vrai quand un joueur gagne") {
    var grilleTest = List('X', 'X', 'X', '-', '-', '-', '-', '-', '-')
    assert(utilTicTacToe.estGagnant(grilleTest, 'X') == true)
  }

  test("util.gameOver dois retourner true quand la grille est plaine") {
    var grilleTest = List('X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X')
    assert(utilTicTacToe.gameOver(grilleTest) == true)
  }

  test("util.peutJouer dois retourner false quand la case n'est pas vide") {
    var grilleTest = List('X', '-', '-', '-', '-', '-', '-', '-', '-')
    assert(utilTicTacToe.peutJouer(grilleTest, 0) == false)
  }

  test("util.partieEqulals dois retouner vrai quand les deux listes sont égale") {
    var grilleTest1 = List('X', '-', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest2 = List('X', '-', '-', '-', '-', '-', '-', '-', '-')
    assert(utilTicTacToe.partieEquals(grilleTest1, grilleTest2) == true)
  }

  test("util.setValeurAI dois bloquer un joueur quand il a un tor de gagner") {
    var grilleTest = List('X', 'X', '-', '-', '-', '-', '-', '-', '-')
    assert(utilTicTacToe.obtenirPositionAI(grilleTest, 'O', 'X') == 2)
  }

  test("util.setValeurAI dois gagner si il lui manque une case a remplire") {
    var grilleTest = List('-', '-', '-', 'O', 'O', '-', '-', '-', '-')
    assert(utilTicTacToe.obtenirPositionAI(grilleTest, 'O', 'X') == 5)
  }
}