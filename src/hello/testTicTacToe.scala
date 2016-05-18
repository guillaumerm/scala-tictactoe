package hello

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

class testTicTacToe extends FunSuite {
  test("util.obtenirPositionAI dois bloquer l'adversaire si celui-ci peut gagner dans la grille active") {
    var grilleTest1 = List('X', 'X', '-', '-', '-', '-', '-', '-', '-') 
    var grilleTest2 = List('-', '-', '-', '-', '-', '-', '-', '-', '-')   
    var grilleComplete = List(grilleTest1, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2) 
    assert(utilTicTacToe.obtenirPositionAI(0, grilleComplete, 'O','X') == 2)
    assert(!(utilTicTacToe.obtenirPositionAI(0, grilleComplete, 'O','X') == 3))
  }
  
  test("util.obtenirPositionAI dois gagner si il a l'occasion") {
    var grilleTest1 = List('X', 'X', '-', '-', '-', '-', '-', '-', '-') 
    var grilleTest2 = List('-', '-', '-', '-', '-', '-', '-', '-', '-')   
    var grilleComplete = List(grilleTest1, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2) 
    assert(utilTicTacToe.obtenirPositionAI(0, grilleComplete, 'X','O') == 2)
  }
  
  test("util.estGagnant devrait retourner true si le joueur envoyé en paramêtre a gagner la partie") {
      var grilleTest2 = List('X', 'X', '-', '-', '-', '-', '-', '-', '-')
   	  var grilleTest1 = List('X', 'X', 'X', '-', '-', '-', '-', '-', '-')
   	  var grilleTest3 = List('-', '-', '-', '-', '-', '-', 'O', 'O', 'O')
   	  var grilleComplete = List(grilleTest1, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2) 
   	  var grilleComplete2 = List(grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest3) 
   	  assert(utilTicTacToe.estGagnant(grilleComplete, 'X'))
   	  assert(!utilTicTacToe.estGagnant(grilleComplete, 'O'))
   	  assert(!utilTicTacToe.estGagnant(grilleComplete2, 'X'))
   	  assert(utilTicTacToe.estGagnant(grilleComplete2, 'O'))
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
  
  test("util.setValeur devrait mettre a jour la grille de jeu") {
  	var grilleTest1 = List('-', '-', '-', '-', '-', '-', '-', '-', '-')    
    assert(utilTicTacToe.setValeur(grilleTest1, 'X', 0)(0) == 'X')
    assert(utilTicTacToe.jouerTour(grilleTest1, 'O', 0)(0) == 'O')   
    assert(utilTicTacToe.jouerTour(grilleTest1, 'X', 0)(1) == '-') 
  }
  
  test("util.partieEqulals dois retouner vrai quand les deux listes de listes sont égale") {
    var grilleTest1 = List('X', 'X', '-', '-', '-', '-', '-', '-', '-') 
    var grilleTest2 = List('-', '-', '-', '-', '-', '-', '-', '-', '-')   
    var grilleComplete = List(grilleTest1, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2) 
    var grilleComplete2 = List(grilleTest2, grilleTest1, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2) 
    var grilleComplete3 = grilleComplete
    assert(utilTicTacToe.partieEquals(grilleComplete, grilleComplete3) == true)
    assert(utilTicTacToe.partieEquals(grilleComplete, grilleComplete2) == false)
    assert(utilTicTacToe.partieEquals(grilleComplete2, grilleComplete3) == false)
  }
  
  test("util.initList devrait réinitialiser la grille de jeu") {
      var grilleComplete = List() 
      var grilleRetour = utilTicTacToe.initList(grilleComplete)
      assert(!grilleRetour(0).contains('X'))
      assert(grilleRetour(3).contains('-'))
      assert(!grilleRetour(6).contains('O'))
  }
  
  test("util.gameOver dois retourner true quand la grille est plaine") {
    var grilleTest = List('X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X')
    var grilleTest2 = List('X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', '-')
    var grilleTest3 = List('-', '-', '-', '-', '-', '-', '-', '-', '-')
    var grilleComplete = List(grilleTest, grilleTest, grilleTest, grilleTest, grilleTest, grilleTest, grilleTest, grilleTest, grilleTest) 
    var grilleComplete2 = List(grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2, grilleTest2) 
    var grilleComplete3 = List(grilleTest3, grilleTest3, grilleTest3, grilleTest3, grilleTest3, grilleTest3, grilleTest3, grilleTest3, grilleTest3) 
    var grilleComplete4 = List(grilleTest, grilleTest2, grilleTest, grilleTest, grilleTest, grilleTest, grilleTest, grilleTest, grilleTest) 
    assert(utilTicTacToe.gameOver(grilleComplete) == true)
    assert(utilTicTacToe.gameOver(grilleComplete2) == false)
    assert(utilTicTacToe.gameOver(grilleComplete3) == false)
    assert(utilTicTacToe.gameOver(grilleComplete4) == false)
  }
  
  test("util.peutJouer dois retourner false quand la case n'est pas vide") {
    var grilleTest = List('X', '-', '-', '-', '-', '-', '-', '-', '-')
    var grilleTest2 = List('X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X')
    assert(utilTicTacToe.peutJouer(grilleTest, 0) == false)
    assert(utilTicTacToe.peutJouer(grilleTest, 1) == true)
    assert(utilTicTacToe.peutJouer(grilleTest2, 8) == false)
  }
  
}