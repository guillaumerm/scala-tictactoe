package hello
/**
 * Cette classe contient les fonctions de bases pour un jeu de Tic-Tac-Toe
 * @author Guillaume Rochefort-Mathieu & Antoine Laplante
 */
object utilTicTacToe {

  val nombreDeCase = 81

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

  /**
   * Fonction qui vérifie si l'AI peut jouer une case qui va empêcher l'autre joueur de gagner
   * @param grille La grille de jeu
   * @param joueur Le joueur actif
   * @return Int La position iédale à jouer
   */
  private def doisBloquer(index: Int, grille: List[List[Char]], joueur: Char, adversaire: Char): Int = {
    var position = -1
    var aJoue = false
    var partieTemp = grille(index)
    var grilleTemp = grille
    for (a <- 0 to (cGagnante.size - 1)) {
      for (b <- 0 to (cGagnante(a).size - 1)) {
        if (partieTemp(cGagnante(a)(b)) == '-') {
          partieTemp = partieTemp.updated(cGagnante(a)(b), adversaire)
          grilleTemp = grilleTemp.updated(index, partieTemp)
          if (utilTicTacToe.estGagnant(grilleTemp, adversaire)) {
            //Si sa bug cest cette ligne
            //aJoue = true
            partieTemp = partieTemp.updated(cGagnante(a)(b), '-')
            grilleTemp = grilleTemp.updated(index, partieTemp)
            position = cGagnante(a)(b)
          } else {
            partieTemp = partieTemp.updated(cGagnante(a)(b), '-')
            grilleTemp = grilleTemp.updated(index, partieTemp)
          }
        }
      }
    }
    return position
  }

  /**
   * Fonction qui vérifie si l'AI peut jouer une case qui va le faire gagner
   * @param grille La grille de jeu
   * @param joueur Le joueur actif
   * @return Int La position iédale à jouer
   */
  private def doisGagner(index: Int, grille: List[List[Char]], joueur: Char): Int = {
    var position = -1
    var aJoue = false
    var partieTemp = grille(index)
    var grilleTemp = grille
    for (a <- 0 to (cGagnante.size - 1)) {
      for (b <- 0 to (cGagnante(a).size - 1)) {
        if (partieTemp(cGagnante(a)(b)) == '-' && !aJoue) {
          partieTemp = partieTemp.updated(cGagnante(a)(b), joueur)
          grilleTemp = grilleTemp.updated(index, partieTemp)
          if (utilTicTacToe.estGagnant(grilleTemp, joueur)) {
            aJoue = true
            partieTemp = partieTemp.updated(cGagnante(a)(b), '-')
            grilleTemp = grilleTemp.updated(index, partieTemp)
            position = cGagnante(a)(b)
          } else {
            partieTemp = partieTemp.updated(cGagnante(a)(b), '-')
            grilleTemp = grilleTemp.updated(index, partieTemp)
          }
        }
      }
    }
    return position
  }

  /**
   * Fonction qui appel aléatoirement un position prioritaire si aucune
   * place prioritaire n'est disponible appeller un position moin prioritaire
   * @param grille La grille de jeu
   * @param joueur Le joueur actif
   * @return Int La position iédale à jouer
   */
  private def doisJouer(indexPartie: Int, grille: List[List[Char]], joueur: Char, adversaire: Char): Int = {
    var positionPossible: List[Int] = List()
    var positionPasPossible: List[Int] = List()
    var grilleRetour = grille
    var position = -1

    /*
     * Obtien les positions ou que l'adversaire gagnerait le prochain tour.
     */
    val it = Iterator(grille)
    var i = 0
    while (i < grille.size - 1 && position == -1) {
      if (doisBloquer(i, grille, joueur, adversaire) != -1) {
        if (!positionPasPossible.contains(i)) {
          positionPasPossible = positionPasPossible :+ i
        }
      }
      i = i + 1
    }

    /*
     * Obtien les positions possible par rapport au position prioritaires
     */
    for (a <- 0 to positionPrioritaire.size - 1) {
      if (grilleRetour(indexPartie)(positionPrioritaire(a)) == '-') {
        positionPossible = positionPossible :+ positionPrioritaire(a)
      }
    }

    var positionJoue = -1

    /*
     * Obtien les positions possible par rapport au position non prioritaires
     */
    if (positionPossible.isEmpty) {
      for (a <- 0 to positionNonPrioritaire.size - 1) {
        if (grilleRetour(indexPartie)(positionNonPrioritaire(a)) == '-') {
          positionPossible = positionPossible :+ positionNonPrioritaire(a)
        }
      }
    }

    /*
     * Enlève des positions possibles les positions que l'adversaire gagnerait le prochain tour
     */
    positionPossible = positionPossible.filterNot(positionPasPossible.toSet)
    
    if (positionPossible.isEmpty) { // Si les position possibles est vide nous obtenons un position aléatoirement dans les positions ou que l'adversaire gangerait le prochain tour
      positionJoue = positionPasPossible(scala.util.Random.nextInt(positionPossible.size))
    } else {// Sinon nous retournons une positions aléatoirement des positions possibles
      positionJoue = positionPossible(scala.util.Random.nextInt(positionPossible.size))
    }
    return positionJoue
  }

  /**
   * Fonction utiliser pour determiner la position ou que IA devrait jour son tour
   * Nous nous sommes inspiré de l'agorithme "Heuristique"
   * Notre algorithme fait les étapes suivante:
   * 1. Vérifie si il peut jouer une case qui va le faire gagner par rapport aux combinaisons gagnantes
   * 2. Vérifie si il peut jouer une case qui va empêcher l'autre joueur de gagner
   * 3. Vérifie si un des coins sont libre
   * 4. Vérifie si le centre est libre
   * 5. Joue au hasard dans les 4 positions restantes
   * @param grille La grille de jeu
   * @param joueur Le joueur actif
   * @param adversaire L'adversaire du joueur actif
   * @return Int La position où l'AI devrait jouer
   */
  def obtenirPositionAI(index: Int, grille: List[List[Char]], joueur: Char, adversaire: Char): Int = {
    var aJoue = false
    var grilleTemp = grille
    var position = -1

    position = doisGagner(index, grille, joueur)

    if (position == -1) {
      position = doisBloquer(index, grille, joueur, adversaire)

      if (position == -1) {
        position = doisJouer(index, grille, joueur, adversaire)
      }
    }
    return position
  }
  /**
   * Le joueur actif joue un tour
   * @param grille La grille de jeu
   * @param joueur Le joueur actif
   * @param index La position ou le joueur actif veux jouer
   * @return List[Char] La grille de jeu après avoire ajouté le tour du joueur actif
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
   * @param grille La grille de jeu
   * @return List[Char] Une nouvelle grille de jeu
   */
  def initList(grille: List[List[Char]]): List[List[Char]] = {
    var grilleTemp = grille

    for (a <- 0 to 8) {
      grilleTemp = grilleTemp :+ List('-', '-', '-', '-', '-', '-', '-', '-', '-')
    }

    return grilleTemp
  }

  /**
   * Vérifie si le joueur à gagner la partie
   * @param grille La grille de jeu
   * @param joueur Le joueur actif
   * @return Boolean True si je joueur a gagné, False si le joueur n'a pas gagné
   */
  def estGagnant(grille: List[List[Char]], joueur: Char): Boolean = {
    var retour = false

    for (x <- 0 to 8) {
      var partieTemp = grille(x)
      for (a <- 0 to 7) {
        var nmb = 0
        for (b <- 0 to 2) {
          if (partieTemp(cGagnante(a)(b)) == joueur) {
            nmb = nmb + 1
            if (nmb == 3) {
              retour = true
            }
          }
        }
      }
    }

    return retour
  }

  /**
   * Si la position est vide, le joueur actif joue un tour
   * @param grille La grille de jeu
   * @param joueur Le joueur actif
   * @param index La position ou le joueur actif veux jouer
   * @return List[Char] La grille de jeu après avoire ajouté le tour du joueur actif
   */
  def jouerTour(grille: List[Char], joueur: Char, index: Int): List[Char] = {
    var grilleRetour = grille
    if (peutJouer(grille, index) == true) {
      grilleRetour = setValeur(grille, joueur, index)
    }

    return grilleRetour
  }

  /**
   * Vérifie si les deux grille de jeu son Identique.
   * @param grille La première grille de jeu
   * @param grille La deuxième grille de jeu
   * @return Boolean True si les deux grille de jeux sont identique, False si les deux grille de jeux sont différentes
   */
  def partieEquals(grille: List[List[Char]], other_grille: List[List[Char]]): Boolean = {
    return grille.sameElements(other_grille)
  }

  /**
   * Vérifie si il reste une case libre.
   * @param grille Une grille de jeu
   * @return Boolean True si la grille est pleine, False si la grille n'est pas pleine
   */
  def gameOver(grille: List[List[Char]]): Boolean = {
    var nombreCaseNonVide = 0
    var retour = false
    grille.foreach { x => x.foreach { y => if (y != '-') nombreCaseNonVide = nombreCaseNonVide + 1 } }

    return nombreCaseNonVide == nombreDeCase
  }

  /**
   * Vérifie si la case est libre.
   * @param grille Une grille de jeu
   * @param une position dans la grille de jeu
   * @return Boolean True si la case est libre, False si la case n'est pas libre
   */
  def peutJouer(grille: List[Char], index: Int): Boolean = {
    return grille(index) == '-'
  }
}