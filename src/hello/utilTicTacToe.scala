package hello

object utilTicTacToe {

  val nombreDeCase = 9

  val cGagnante: List[List[Int]] = List(
    List(0, 1, 2),
    List(3, 4, 5),
    List(6, 7, 8),
    List(0, 3, 6),
    List(1, 4, 7),
    List(2, 5, 8),
    List(0, 4, 8),
    List(2, 4, 6))

  /**
   *
   */
  def setValeur(grille: List[Char], joueur: Char, index: Int): List[Char] = {
    var grilleRetour: List[Char] = grille
    if (grilleRetour(index) == '-') {
      grilleRetour = grilleRetour.updated(index, joueur.toString().toCharArray()(0))
    }

    return grilleRetour
  }

  /**
   * Fonction qui permet de renitialiser la partie.
   */
  def initList(grille: List[Char]): List[Char] = {
    var grilleTemp = grille
    grilleTemp = List('-', '-', '-', '-', '-', '-', '-', '-', '-')

    return grilleTemp
  }

  /**
   *
   */
  def estGagnant(grille: List[Char], joueur: Char): Boolean = {
    var retour = false

    for (a <- 0 to 7) {
      var nmb = 0
      for (b <- 0 to 2) {
        if (grille(cGagnante(a)(b)) == joueur) {
          nmb = nmb + 1
          if (nmb == 3) {
            retour = true
          }
        }
      }
    }

    return retour
  }

  /**
   *
   */
  def jouerTour(grille: List[Char], joueur: Char, index: Int, compteurTour: Int): List[Char] = {
    var grilleRetour = grille
    if (peutJouer(grille, index) == true) {
      grilleRetour = setValeur(grille, joueur, index)
    }

    return grilleRetour
  }

  /**
   *
   */
  def partieEquals(grille: List[Char], other_grille: List[Char]): Boolean = {
    return grille.sameElements(other_grille)
  }

  /**
   *
   */
  def gameOver(grille: List[Char]): Boolean = {
    var nombreCaseNonVide = 0
    var retour = false

    grille.foreach { x => if (x != '-') nombreCaseNonVide = nombreCaseNonVide + 1 }

    return nombreCaseNonVide == nombreDeCase
  }

  def peutJouer(grille: List[Char], index: Int): Boolean = {
    return grille(index) == '-'
  }
}