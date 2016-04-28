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

  val positionPrioritaire: List[Int] = List(0, 2, 4, 6, 8)
  val positionNonPrioritaire: List[Int] = List(1, 3, 5, 7)

  private def doisBloquer(grille: List[Char], joueur: Char, adversaire: Char): Int = {
    var position = -1
    var grilleTemp = grille
    var aJoue = false
    for (a <- 0 to (cGagnante.size - 1)) {
      for (b <- 0 to (cGagnante(a).size - 1)) {
        if (grilleTemp(cGagnante(a)(b)) == '-' && !aJoue) {
          grilleTemp = grilleTemp.updated(cGagnante(a)(b), adversaire)
          if (utilTicTacToe.estGagnant(grilleTemp, adversaire)) {
            aJoue = true
            grilleTemp = grilleTemp.updated(cGagnante(a)(b), '-')
            position = cGagnante(a)(b)
          } else {
            grilleTemp = grilleTemp.updated(cGagnante(a)(b), '-')
          }
        }
      }
    }
    return position
  }

  private def doisGagner(grille: List[Char], joueur: Char): Int = {
    var grilleTemp = grille
    var position = -1
    var aJoue = false
    //Si il peut jouer pour gagner
    for (a <- 0 to (cGagnante.size - 1)) {
      for (b <- 0 to (cGagnante(a).size - 1)) {
        if (grilleTemp(cGagnante(a)(b)) == '-' && !aJoue) {
          grilleTemp = grilleTemp.updated(cGagnante(a)(b), joueur)
          if (utilTicTacToe.estGagnant(grilleTemp, joueur)) {
            aJoue = true
            grilleTemp = grilleTemp.updated(cGagnante(a)(b), '-')
            position = cGagnante(a)(b)
          } else {
            grilleTemp = grilleTemp.updated(cGagnante(a)(b), '-')
          }
        }
      }
    }
    return position
  }

  /**
   * Fonction qui appel aléatoirement un position prioritaire si aucune
   * place prioritaire n'est disponible appeller un position moin prioritaire
   */
  private def doisJouer(grille: List[Char], joueur: Char): Int = {
    var positionPossible: List[Int] = List()
    var grilleRetour = grille

    for (a <- 0 to positionPrioritaire.size - 1) {
      if (grilleRetour(positionPrioritaire(a)) == '-') {
        positionPossible = positionPossible :+ positionPrioritaire(a)
      }
    }

    var positionJoue = -1

    if (positionPossible.isEmpty) {
      for (a <- 0 to positionNonPrioritaire.size - 1) {
        if (grilleRetour(positionNonPrioritaire(a)) == '-') {
          positionPossible = positionPossible :+ positionNonPrioritaire(a)
        }
      }
    }
    return positionPossible(scala.util.Random.nextInt(positionPossible.size))
  }

  /**
   * Fonction utiliser pour determiner la position ou que IA devrait jour son tour
   */
  def obtenirPositionAI(grille: List[Char], joueur: Char, adversaire: Char): Int = {
    var aJoue = false
    var grilleTemp = grille
    var position = -1

    position = doisGagner(grille, joueur)

    if (position == -1) {
      position = doisBloquer(grille, joueur, adversaire)

      if (position == -1) {
        position = doisJouer(grille, joueur)
      }
    }
    return position
  }
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
  def jouerTour(grille: List[Char], joueur: Char, index: Int): List[Char] = {
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